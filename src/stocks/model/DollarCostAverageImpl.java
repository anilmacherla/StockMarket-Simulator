package stocks.model;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import stocks.utility.DollarCostAvgUtility;

/**
 * This class implements DollarCostAverageImpl and extends FlexiblePortfolioModelImpl.
 */
public class DollarCostAverageImpl extends FlexiblePortfolioModelImpl implements DollarCostAverage {
  /**
   * Constructor that handles dollar cost portfolio.
   *
   * @param portfolioName portfolio Name
   * @param createdDate   create date
   * @param listOfStocks  list of portfolio stocks
   */
  public DollarCostAverageImpl(String portfolioName, LocalDate createdDate,
                               List<StockModel> listOfStocks) {
    super(portfolioName, createdDate, listOfStocks);
  }

  /**
   * DollarCostAverageImpl Constructor to create Object.
   */
  public DollarCostAverageImpl() {
    super();
  }

  @Override
  public List<StockModel> displayStoksForGiveDate(LocalDate date) {
    return dataAccessServiceObject.getStockDataByDate(date);
  }

  @Override
  public void saveDollarPortfolioData(DollarCostAverage pModel, int recurring, LocalDate endDate,
                                      Double investment) {
    try {
      storeUserPortfolioDataToCSV(true, recurring, endDate, investment);
    } catch (Exception e) {
      System.out.println("Exception:- " + e.getMessage());
    }
  }

  @Override
  public String saveRecurringStocks(String stockList, boolean dollarCost, LocalDate date) {

    String result = performStockValidations(stockList, dollarCost);

    if (result.equals("")) {
      String[] stockArray = stockList.split(",");
      result = saveToStockList(listOfStocks, stockArray, date);
    }

    return result;
  }

  private String saveToStockList(List<StockModel> listOfStocks, String[] stockArray,
                                 LocalDate date) {

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

  @Override
  public List<StockModel> getPortfolioDataForRecurring(String name) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(userPortfolioFileName));
      String line = br.readLine();
      List<StockModel> portfolioMap = new ArrayList<StockModel>();
      while ((line = br.readLine()) != null) {
        String[] attributes = line.split(",");
        if (attributes[0].equals(name) && attributes[8].equals("1")) {
          StockModel newStock = new StockModelImpl(attributes[2],
                  Double.parseDouble(attributes[3]),
                  Double.parseDouble(attributes[4]), parseDate(attributes[1]),
                  Double.parseDouble(attributes[6]), Double.parseDouble(attributes[7]));
          portfolioMap.add(newStock);
        }
      }
      return portfolioMap;
    } catch (FileNotFoundException e) {
      return null;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<String> getEndDateAndRecurringTime(String name) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(userPortfolioFileName));
      String line = br.readLine();
      List<String> required = new ArrayList<>();
      while ((line = br.readLine()) != null) {
        String[] attributes = line.split(",");
        if (attributes[0].equals(name) && attributes[8].equals("1")) {
          required.add(attributes[9]);
          required.add(attributes[10]);
          required.add(attributes[11]);
          break;
        }
      }
      return required;
    } catch (FileNotFoundException e) {
      return null;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void checkForStocksData(String name, DollarCostAverage pModel) {
    name = name.toUpperCase();
    List<String> required = getEndDateAndRecurringTime(name);
    HashMap<String, List<StockModel>> result = new HashMap<>();
    if (!required.isEmpty()) {
      result.put(name, getPortfolioDataForRecurring(name));
      if (LocalDate.now().isBefore(LocalDate.parse(required.get(1)))) {
        LocalDate startDate = getStartDate(result.get(name));
        LocalDate endDate = LocalDate.parse(required.get(1));
        if (startDate.plusDays(Integer.parseInt(required.get(0))).isBefore(endDate)
                && startDate.plusDays(Integer.parseInt(required.get(0)))
                .isBefore(LocalDate.now())) {
          createDollarCostAvg(startDate, endDate, required, result, pModel, name);
        }
      }
    }
  }

  @Override
  public String calculateSharesRecurring(String[] stocks, String[] weights,
                                         double money, LocalDate startDate, LocalDate endDate,
                                         int recurring, DollarCostAverage pModel) {
    String output = "";
    List<String> shares = new ArrayList<>();
    while (startDate.isBefore(endDate) && startDate.isBefore(LocalDate.now())) {
      List<StockModel> stocksList = pModel.displayStoksForGiveDate(startDate);
      for (int i = 0; i < stocks.length; i++) {
        double price = stocksList.stream().filter(l -> l.getStockName()
                        .equals(stocks[1].toUpperCase()))
                .map(l -> l.getPrice()).findFirst().get();
        double cost = (money * (Double.valueOf(weights[i]) / 100.0f));
        double shareCount = (1 / price) * cost;
        shares.add(stocks[i] + "," + shareCount + "," + startDate + "," + weights[i]);
      }
      startDate = startDate.plusDays(recurring);
    }
    for (String flexStocks : shares) {
      startDate = LocalDate.parse(flexStocks.split(",")[2]);
      output = pModel.saveRecurringStocks(flexStocks.toUpperCase(), true, startDate);
    }
    return output;
  }

  @Override
  public String calculateShares(String[] stocks, String[] weights, double money,
                                List<StockModel> stocksList,
                                LocalDate date, DollarCostAverage pModel) {
    List<String> shares = new ArrayList<>();
    String output = "";
    for (int i = 0; i < stocks.length; i++) {
      double price = stocksList.stream().filter(l -> l.getStockName()
                      .equals(stocks[1].toUpperCase()))
              .map(l -> l.getPrice()).findFirst().get();
      double cost = (money * (Double.valueOf(weights[i]) / 100.0f));
      double shareCount = (1 / price) * cost;
      shares.add(stocks[i] + "," + shareCount + "," + date + "," + weights[i]);
    }
    for (String flexStocks : shares) {
      output = pModel.saveStocksToList(flexStocks.toUpperCase(), true, date);
    }
    return output;
  }

  private static void createDollarCostAvg(LocalDate startDate, LocalDate endDate,
                                          List<String> required,
                                          HashMap<String, List<StockModel>> result,
                                          DollarCostAverage pModel,
                                          String pName) {
    var uniqueObject = new HashMap<String, Double>();
    String stocks = "";
    String weights = "";
    for (String name : result.keySet()) {
      var unique = result.get(name).stream().map(s -> s.getStockName()).distinct()
              .collect(Collectors.toList());
      for (String stockName : unique) {
        uniqueObject.put(stockName, result.get(name).stream()
                .filter(s -> s.getStockName().equals(stockName))
                .map(s -> s.getStockWeights()).findFirst().get());
        stocks = stockName + ",";
        weights = (uniqueObject.get(stockName)) + ",";
      }
    }
    String[] stocksArray = stocks.split(",");
    String[] weightArray = weights.split(",");
    List<String> weightedStocksShares = new ArrayList<>();
    pModel.selectPortfolioToUpdate(pName.toUpperCase());
    weightedStocksShares = DollarCostAvgUtility.calculateSharesRecurring(stocksArray, weightArray,
            weightedStocksShares, startDate, endDate, Integer.parseInt(required.get(0)), pModel,
            Double.parseDouble(required.get(2)));
    for (String flexStocks : weightedStocksShares) {
      startDate = LocalDate.parse(flexStocks.split(",")[2]);
      pModel.saveRecurringStocks(flexStocks.toUpperCase(), true, startDate);
      pModel.saveDollarPortfolioData(pModel, Integer.parseInt(required.get(0)), endDate,
              Double.parseDouble(required.get(2)));
    }

  }

  private static LocalDate getStartDate(List<StockModel> stockModels) {
    LocalDate date = null;
    for (StockModel r : stockModels) {
      if (date == null) {
        date = r.getStockDate();
      } else {
        if (r.getStockDate().isAfter(date)) {
          date = r.getStockDate();
        }
      }

    }
    return date;
  }

}


