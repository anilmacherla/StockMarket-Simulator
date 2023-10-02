package stocks.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import stocks.dataaccess.DataAccessService;
import stocks.dataaccess.DataServiceFactory;
import stocks.dataaccess.SourceDataTypeEnum;


/**
 * This class implements PortfolioModel interface.
 */
public class PortfolioModelImpl implements PortfolioModel, java.io.Serializable {

  String portfolioName;
  LocalDate createdDate;

  List<StockModel> listOfStocks;
  final String userPortfolioFileName = "./UserPortfolio.csv";
  final DataAccessService dataAccessServiceObject;

  double transactionFees;

  /**
   * This method constructs PortfolioModelImpl Object.
   */
  public PortfolioModelImpl() {
    this.portfolioName = "";
    this.createdDate = null;
    this.listOfStocks = new ArrayList<>();
    this.transactionFees = 5.0;
    this.dataAccessServiceObject = getDataServiceObject();
  }

  /**
   * This method constructs PortfolioModelImpl object for a given portfolio name, localdate and
   * listofstocks inputs.
   *
   * @param portfolioName name of the portfolio
   * @param createdDate   created date of the portfolio
   * @param listOfStocks  stocks to be added
   */
  public PortfolioModelImpl(String portfolioName, LocalDate createdDate, List<StockModel>
          listOfStocks) {
    this.portfolioName = portfolioName;
    this.createdDate = createdDate;
    this.listOfStocks = listOfStocks;
    this.dataAccessServiceObject = getDataServiceObject();
  }

  @Override
  public int addPortfolioName(String pName) {
    if (getUserPortfolioData(null) != null
            && getUserPortfolioData(null).containsKey(pName)) {
      return 0;
    }
    setPortfolioName(pName);
    return 1;
  }

  @Override
  public HashMap<String, List<StockModel>> displayPortfolioByName(String name) {
    name = name.toUpperCase();
    HashMap<String, List<StockModel>> result = new HashMap<>();
    result.put(name, getUserPortfolioData(null).get(name));
    return result;
  }

  @Override
  public String saveStocksToList(String stockList, boolean dollarCost, LocalDate date) {

    String result = performStockValidations(stockList, dollarCost);

    if (result.equals("")) {
      String[] stockArray = stockList.split(",");
      result = saveStocks(listOfStocks, stockArray, date);
    }

    return result;
  }

  String performStockValidations(String stockList, boolean weighted) {
    if (isTickerEmpty(stockList)) {
      return "Error: Ticker cannot be empty";
    }
    String[] stockArray = stockList.split(",");
    if (!isTickerValid(stockArray)) {
      return "Error: Please enter a valid ticker";
    }
    if (weighted) {
      stockArray[1] = stockArray[1].split(Pattern.quote("."))[0];
      if (stockArray.length < 2 || !isPositiveInteger(stockArray[1])) {
        return "Error: Enter number of shares as a positive integer";
      }
    } else {
      if (stockArray.length < 2 || Double.parseDouble(stockArray[1]) < 0) {
        return "shares shouldn't be negative";
      }
    }
    return "";
  }

  @Override
  public void saveData(PortfolioModel pModel) {
    try {
      storeUserPortfolioDataToCSV(false, 0, null, 0.0);
    } catch (Exception e) {
      System.out.println("Exception:- " + e.getMessage());
    }
  }


  void storeUserPortfolioDataToCSV(boolean isFlexible, int recurring,
                                   LocalDate endDate, Double investment) {
    try {
      Path p = Paths.get(userPortfolioFileName);
      boolean isExists = Files.exists(p);
      FileWriter csvWriter = new FileWriter(userPortfolioFileName, true);
      String[] header = {"PortfolioName", "CreatedDate", "StockName", "NumberOfShares", "Price"
              , "isFlexible", "CommissionFees", "Weights", "isRecurrent,Recurring,endDate," +
              "Investment"};
      if (!isExists) {
        appendHeaders(csvWriter, header);
      }
      appendBody(csvWriter, listOfStocks, isFlexible, recurring, endDate, investment);
      csvWriter.flush();
      csvWriter.close();
      reset();
    } catch (IOException io) {
      System.out.println("IO Exception:- " + io.getMessage());
    }
  }

  @Override
  public void deletePortfolio() {
    File f = new File(userPortfolioFileName);
    f.delete();
  }

  @Override
  public HashMap<String, Double> displayPortfolioByDate(LocalDate date, String portfolioName) {
    HashMap<String, Double> result = new HashMap<>();
    if (date.isAfter(LocalDate.now())) {
      result.put("Error: Date should be on or before current date", 0.0);
      return result;
    }
    DayOfWeek day = DayOfWeek.of(date.get(ChronoField.DAY_OF_WEEK));
    LocalDate pastDate = null;
    if (day == DayOfWeek.SATURDAY) {
      pastDate = date.minusDays(1);
    } else if (day == DayOfWeek.SUNDAY) {
      pastDate = date.minusDays(2);
    } else {
      pastDate = date;
    }

    HashMap<String, List<StockModel>> portfolioModelList = getUserPortfolioData(date);
    if (portfolioModelList == null) {
      result.put("Error: Please create a portfolio first.", 0.0);
      return result;
    }
    result.put(portfolioName,
            getAllPortfoliosByNameAndDate(portfolioModelList, pastDate).get(portfolioName));
    return result;
  }

  @Override
  public List<StockModel> displayAvailableStocks() {
    return dataAccessServiceObject.getStockDataByDate(LocalDate.now());
  }

  @Override
  public boolean checkCurrentDayStockPresent() {
    return dataAccessServiceObject.checkCurrentDayStockPresent();
  }

  @Override
  public void reset() {
    this.portfolioName = null;
    this.createdDate = null;
    listOfStocks = new ArrayList<>();
  }

  private DataAccessService getDataServiceObject() {
    DataServiceFactory a = new DataServiceFactory();
    return a.getDataAccessServiceObject(SourceDataTypeEnum.ALPHAVANTAGE);
  }

  void setPortfolioName(String portfolioName) {
    this.portfolioName = portfolioName;
  }

  private HashMap<String, List<StockModel>> getUserPortfolioData(LocalDate date) {
    return getPortfolioData(date);
  }


  private boolean isTickerEmpty(String stockList) {
    if (stockList.isBlank() || stockList.isEmpty()) {
      listOfStocks = new ArrayList<>();
      return true;
    }
    return false;
  }

  private boolean isTickerValid(String[] stockArray) {
    dataAccessServiceObject.getStockDataByDate(LocalDate.now());
    return dataAccessServiceObject.getStockDataByDate(LocalDate.now()).stream()
            .anyMatch(s -> s.getStockName().equals(stockArray[0].toUpperCase()));
  }

  protected boolean isPositiveInteger(String s) {
    try {
      int number = Integer.parseInt(s);
      return number >= 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private String saveStocks(List<StockModel> listOfStocks, String[] stockArray, LocalDate date) {
    if (listOfStocks.stream().anyMatch(s -> s.getStockName().equals(stockArray[0]))) {
      listOfStocks.stream().filter(s -> s.getStockName()
                      .equals(stockArray[0]))
              .map(p -> {
                p.setStockCount(p.getShareCount() + Double.parseDouble(stockArray[1]));
                return p;
              }).collect(Collectors.toList());
      return "Success: Stocks added";
    }
    Double stockPrice = 0.0;
    if (isPositiveInteger(stockArray[1])) {
      stockPrice = dataAccessServiceObject.getStockDataByDate(LocalDate.now())
              .stream().filter(s -> s.getStockName().equals(stockArray[0]))
              .map(s -> s.getPrice()).findFirst().get();
    } else {
      stockPrice = dataAccessServiceObject.getStockDataByDate(date)
              .stream().filter(s -> s.getStockName().equals(stockArray[0]))
              .map(s -> s.getPrice()).findFirst().get();
    }
    StockModel s = null;
    if (isPositiveInteger(stockArray[1])) {
      s = new StockModelImpl(stockArray[0], Integer.parseInt(stockArray[1]), stockPrice,
              LocalDate.parse(stockArray[2], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
              0.0, 0.0);
    } else {
      s = new StockModelImpl(stockArray[0], Double.parseDouble(stockArray[1]), stockPrice,
              LocalDate.parse(stockArray[2], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
              0.0, Double.parseDouble(stockArray[3]));
    }
    listOfStocks.add(s);
    return "Success: Stocks added";
  }

  FileWriter appendBody(FileWriter csvWriter, List<StockModel> listOfStocks,
                        boolean isFlexible, int recurring, LocalDate endDate, Double investment)
          throws IOException {

    //Set current date for inflexible portfolio. Otherwise, set the date specified by user
    if (this.createdDate == null) {
      createdDate = LocalDate.now();
    }
    char isPortfolioFlexible = '0';
    if (isFlexible) {
      isPortfolioFlexible = '1';
    }

    for (StockModel s : listOfStocks) {
      LocalDate date = LocalDate.now();
      if (s.getStockDate() != null) {
        date = s.getStockDate();
      }
      char isRecurring = '0';
      if (recurring != 0) {
        isRecurring = '1';
      }
      csvWriter.append(portfolioName);
      csvWriter.append(",");
      csvWriter.append(String.valueOf(date));
      csvWriter.append(",");
      csvWriter.append(s.getStockName());
      csvWriter.append(",");
      csvWriter.append(String.valueOf(s.getShareCount()));
      csvWriter.append(",");
      csvWriter.append(String.valueOf(s.getPrice()));
      csvWriter.append(",");
      csvWriter.append(isPortfolioFlexible);
      csvWriter.append(",");
      csvWriter.append(String.valueOf(transactionFees));
      csvWriter.append(",");
      csvWriter.append(String.valueOf(s.getStockWeights()));
      csvWriter.append(",");
      csvWriter.append(isRecurring);
      csvWriter.append(",");
      csvWriter.append(String.valueOf(recurring));
      csvWriter.append(",");
      csvWriter.append(String.valueOf(endDate));
      csvWriter.append(",");
      csvWriter.append(String.valueOf(investment));
      csvWriter.append("\n");
    }
    return csvWriter;
  }

  FileWriter appendHeaders(FileWriter csvWriter, String[] header) throws IOException {
    int i = 0;
    while (i < 7) {
      csvWriter.append(header[i]);
      csvWriter.append(",");
      i++;
    }
    csvWriter.append("\n");
    return csvWriter;
  }


  private HashMap<String, Double> getAllPortfoliosByNameAndDate(HashMap<String,
          List<StockModel>> portfolioModelList, LocalDate date) {
    List<StockModel> stockModelList = dataAccessServiceObject.getStockDataByDate(date);
    HashMap<String, Double> result = new HashMap<>();

    for (var portfolio : portfolioModelList.keySet()) {
      List<Double> stockPriceListFilteredByDate = portfolioModelList.get(portfolio).stream()
              .filter(s -> ((date.equals(s.getStockDate()) || date.isAfter(s.getStockDate()))
              ))
              .map(s -> stockModelList.stream()
                      .filter(sm -> sm.getStockName().equals(s.getStockName()))
                      .map(sm -> sm.getPrice() * s.getShareCount())
                      .findFirst().get())
              .collect(Collectors.toList());
      if (stockPriceListFilteredByDate.size() == 0) {
        continue;
      }
      double individualPortfolioSum = stockPriceListFilteredByDate
              .stream().mapToDouble(i -> i).sum();
      result.put(portfolio, individualPortfolioSum);
    }
    return result;
  }


  //@Override
  HashMap<String, List<StockModel>> getPortfolioData(LocalDate date) {
    date = date == null ? LocalDate.now() : date;
    try {
      BufferedReader br = new BufferedReader(new FileReader(userPortfolioFileName));
      String line = br.readLine();
      HashMap<String, List<StockModel>> portfolioMap = new HashMap<>();
      while ((line = br.readLine()) != null) {
        String[] attributes = line.split(",");
        StockModel newStock = new StockModelImpl(attributes[2],
                Double.parseDouble(attributes[3]),
                Double.parseDouble(attributes[4]), parseDate(attributes[1]),
                Double.parseDouble(attributes[6]));

        if (newStock.getStockDate().isAfter(date)) {
          continue;
        }
        //Add key if it doesn't exist
        if (!portfolioMap.containsKey(attributes[0])) {
          portfolioMap.put(attributes[0], new ArrayList<>());
        }

        if (portfolioMap.get(attributes[0]).stream()
                .anyMatch(s -> s.getStockName().equals(newStock.getStockName()))) {
          LocalDate finalDate = date;
          portfolioMap.get(attributes[0]).stream()
                  .filter(s -> s.getStockName().equals(newStock.getStockName())
                          && (s.getStockDate().equals(finalDate)
                          || s.getStockDate().isBefore(finalDate)))
                  .map(s -> s.setStockCount(s.getShareCount() + newStock.getShareCount()))
                  .collect(Collectors.toList());
          continue;
        }

        //Append stockList to portfolio
        portfolioMap.get(attributes[0]).add(newStock);
      }
      return portfolioMap;
    } catch (FileNotFoundException e) {
      return null;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  LocalDate parseDate(String date) {
    try {
      return LocalDate.parse(date);
    } catch (Exception e) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
      return LocalDate.parse(date, formatter);
    }
  }
}
