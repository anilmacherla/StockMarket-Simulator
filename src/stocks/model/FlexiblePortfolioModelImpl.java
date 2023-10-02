package stocks.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * This class implements FlexiblePortfolioModel and extends PortfolioModelImpl.
 */
public class FlexiblePortfolioModelImpl extends PortfolioModelImpl
        implements FlexiblePortfolioModel {

  /**
   * Constructor that handles flexible portfolio.
   * @param portfolioName portfolio Name
   * @param createdDate create date
   * @param listOfStocks list of portfolio stocks
   */
  public FlexiblePortfolioModelImpl(String portfolioName,
                                    LocalDate createdDate, List<StockModel> listOfStocks) {
    super(portfolioName, createdDate, listOfStocks);
  }

  /**
   * FlexiblePortfolioModelImpl constructor.
   */
  public FlexiblePortfolioModelImpl() {
    super();
  }

  @Override
  public void saveFlexiblePortfolioData(PortfolioModel pModel) {
    try {
      storeUserPortfolioDataToCSV(true,0,null,0.0);
    } catch (Exception e) {
      System.out.println("Exception:- " + e.getMessage());
    }
  }

  @Override
  public Map<String, Double> getCostBasisByPortfolioNameAndDate(String name, LocalDate date) {
    var stockList = getUserPurchasedStocksByDate(date).get(name)
            .stream()
            .filter(s -> s.getStockDate().isBefore(date) || s.getStockDate().equals(date))
            .collect(Collectors.toList());
    double totalPrice = 0.0;
    double commission = 0.0;

    for (var stock : stockList) {
      if (stock.getShareCount() >= 0) {
        totalPrice += stock.getPrice() * stock.getShareCount();
      }
      commission += stock.getCommissionCharged();
    }

    Map<String, Double> result = new TreeMap<>();
    result.put("StockValue", totalPrice);
    result.put("Commission", commission);
    result.put("Total Money Invested", totalPrice + commission);
    return result;
  }


  @Override
  public void selectPortfolioToUpdate(String flexiblePortfolioName) {
    setPortfolioName(flexiblePortfolioName);
  }

  @Override
  public List<String> getFlexibleUserPortfolioList() {
    try {
      BufferedReader br = new BufferedReader(new FileReader(userPortfolioFileName));
      String line = br.readLine();
      List<String> portfolioNames = new ArrayList<>();
      while ((line = br.readLine()) != null) {
        String[] attributes = line.split(",");
        if (attributes[5].equals("1") && !portfolioNames.contains(attributes[0])) {
          portfolioNames.add(attributes[0]);
        }
      }
      return portfolioNames;
    } catch (FileNotFoundException e) {
      return null;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<String> getUserPortfolioNamesList() {
    try {
      BufferedReader br = new BufferedReader(new FileReader(userPortfolioFileName));
      String line = br.readLine();
      List<String> portfolioNames = new ArrayList<>();
      while ((line = br.readLine()) != null) {
        String[] attributes = line.split(",");
        if (!portfolioNames.contains(attributes[0])) {
          portfolioNames.add(attributes[0]);
        }
      }
      return portfolioNames;
    } catch (FileNotFoundException e) {
      return null;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String buyStocksForFlexiblePortfolio(String stockDetails) {
    stockDetails = stockDetails.toUpperCase();
    String result = performStockValidations(stockDetails,false);
    //If any errors, inform the user
    if (!result.equals("")) {
      return result;
    }
    String[] attributes = stockDetails.split(",");
    LocalDate formattedDate;
    try {
      formattedDate = LocalDate.parse(attributes[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    } catch (Exception e) {
      return "Error: Enter a valid date";
    }
    List<StockModel> userPortfolioStockData =  getPortfolioData(null)
            .get(this.portfolioName);
    LocalDate userPortfolioCreatedDate = userPortfolioStockData.stream()
            .map(s -> s.getStockDate()).min(LocalDate::compareTo).get();
    if (userPortfolioCreatedDate.isAfter(formattedDate)) {
      return "Error: Cannot add stock for the specified date as it is before the portfolio " +
              "created date : " + userPortfolioCreatedDate;
    }

    if (formattedDate.isAfter(LocalDate.now())) {
      return "Cannot add stocks with a date beyond the current date";
    }
    attributes[1] = attributes[1].split(Pattern.quote("."))[0];
    StockModel newStock = new StockModelImpl(attributes[0], Double.parseDouble(attributes[1]),
            0.0, formattedDate, 0.0);
    this.listOfStocks = new ArrayList<>();
    this.listOfStocks.add(newStock);
    storeUserPortfolioDataToCSV(true,0,null,0.0);
    return "Success";
  }

  @Override
  public String sellStocksForFlexiblePortfolio(String stockDetails) {
    String result;
    stockDetails = stockDetails.toUpperCase();
    result = performStockValidations(stockDetails,false);

    //If any errors, inform the user
    if (!result.equals("")) {
      return result;
    }

    String[] attributes = stockDetails.split(",");

    LocalDate formattedDate;
    try {
      formattedDate = LocalDate.parse(attributes[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      if (formattedDate.isAfter(LocalDate.now())) {
        return "Error: Entered Date should be on or before the current date";
      }

      DayOfWeek day = DayOfWeek.of(formattedDate.get(ChronoField.DAY_OF_WEEK));
      if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
        return "Error: Cannot sell stocks on weekends";
      }

    } catch (Exception e) {
      return "Error: Enter a valid date";
    }
    List<StockModel> userPortfolioStockData = getPortfolioData(null).get(this.portfolioName);

    boolean isStockExists = userPortfolioStockData.stream()
            .anyMatch(s -> s.getStockName().equals(attributes[0]));
    if (!isStockExists) {
      return "Error: Stock doesn't exist in the portfolio";
    }
    LocalDate stockPurchasedDate = userPortfolioStockData.stream()
            .filter(s -> s.getStockName().equals(attributes[0]))
            .map(s -> s.getStockDate())
            .min(LocalDate::compareTo).get();

    if (formattedDate.isBefore(stockPurchasedDate)) {
      return "Cannot sell a stock on the date which is before it's purchasing date";
    }

    double totalPurchasedStocks = userPortfolioStockData.stream()
            .filter(s -> s.getStockDate().isBefore(formattedDate)
                    || s.getStockDate().equals(formattedDate))
            .map(s -> s.getShareCount()).reduce(0.00, Double::sum);

    if (totalPurchasedStocks < Double.parseDouble(attributes[1])) {
      return "Cannot sell stocks greater than the purchased stocks. Maximum stock that you can " +
              "sell is :" + totalPurchasedStocks;
    }
    attributes[1] = attributes[1].split(Pattern.quote("."))[0];
    StockModel newStock = new StockModelImpl(attributes[0], -Double.parseDouble(attributes[1]),
            0.0, formattedDate, 0.0);
    this.listOfStocks = new ArrayList<>();
    this.listOfStocks.add(newStock);
    storeUserPortfolioDataToCSV(true,0,null,0.0);
    return "Success";
  }

  @Override
  public Map<String, Double> displayGraph(String pName, LocalDate startDate,
                                          LocalDate endDate) {
    Map<String, Double> graph = new TreeMap<>();
    LocalDate date;
    Period duration = Period.between(startDate, endDate);
    int totalDays = duration.getDays() + duration.getYears() * 365 + duration.getMonths() * 30;
    if (totalDays > 30) {
      int equalDistribution = totalDays % 30;
      if (equalDistribution == 0) {
        int scale = totalDays / 30;
        for (int i = 0; i < 30; i++) {
          startDate = startDate.plusDays(scale);
          date = startDate;
          if (date.plusDays(scale).isAfter(endDate)) {
            date = endDate;
          }
          graph.put(date.toString(), displayPortfolioByDate(date, pName).get(pName));
        }
      } else {
        int scale = totalDays / 29;
        for (int i = 0; i < 30; i++) {
          startDate = startDate.plusDays(scale);
          if (i == 29) {
            date = startDate.plusDays(totalDays % 29);
            if (date.plusDays(totalDays % 29).isAfter(endDate)) {
              date = endDate;
            }
            graph.put(date.toString(), displayPortfolioByDate(date, pName).get(pName));
            return graph;
          }
          date = startDate;
          graph.put(date.toString(), displayPortfolioByDate(date, pName).get(pName));
        }
      }
    } else if (totalDays >= 5 && totalDays <= 30) {
      for (int i = 0; i <= totalDays; i++) {
        date = startDate;
        startDate = startDate.plusDays(1);
        graph.put(date.toString(), displayPortfolioByDate(date, pName).get(pName));
      }
    }
    return graph;
  }

  @Override
  public void setTransactionFees(Double fees) {
    this.transactionFees = fees;
  }

  private HashMap<String, List<StockModel>> getUserPurchasedStocksByDate(LocalDate date) {
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
          portfolioMap.put(attributes[0], new ArrayList<StockModel>());
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


}
