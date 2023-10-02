package stocks.gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import stocks.controller.PortfolioSimulator;
import stocks.model.DollarCostAverage;
import stocks.view.PortfolioView;

/**
 * This class implements PortfolioSimulator and Features interface.
 */
public class PortfolioUIController implements Features, PortfolioSimulator {
  private final DollarCostAverage model;
  private final JFrameView view;
  private Map<String, Double> listOfStocks = new HashMap<>();
  private Map<String, Double> listOfWeightedStocks = new HashMap<>();

  /**
   * This method initializes the controller with model and view.
   *
   * @param model Portfolio model
   * @param view  Portfolio view
   */
  public PortfolioUIController(DollarCostAverage model, PortfolioView view) {
    this.model = model;
    this.view = (JFrameView) view;
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public void mainMenu() {
    listOfStocks = new HashMap<>();
    listOfWeightedStocks = new HashMap<>();
    view.displayMenu();
    view.addFeatures(this);
  }

  @Override
  public void createFlexiblePortfolio() {
    view.setFlexiblePortfolioPane(listOfStocks);
    view.addFlexiblePortfolioFeatures(this);
  }

  @Override
  public void getCostBasis() {
    view.setCostBasisPortfolioPane(model.getUserPortfolioNamesList());
    view.addCostBasisFeatures(this);
  }

  @Override
  public void calculateCostBasis(String portfolioName, String date) {
    if (date.isBlank()) {
      view.displayErrorMessage("Date is required");
    }

    LocalDate formattedDate = LocalDate.now();
    String output;
    try {
      var result = model.displayPortfolioByDate(formattedDate, portfolioName);
      output = "The details of " + portfolioName + "on " + date + " are:";
      for (String name : result.keySet()) {
        output += name + result.get(name) + "\n";
      }

      view.displayResult(output);
      view.addMenuAndExitButtons(this);
      if (formattedDate.isAfter(LocalDate.now())) {
        output = "Date cannot be after current date";
        view.displayErrorMessage(output);
      }
    } catch (DateTimeParseException e) {
      view.displayErrorMessage("Enter date in valid format");

    }
  }

  @Override
  public void portfolioDetails() {
    view.setPortfolioDetailsPane(model.getUserPortfolioNamesList());
    view.addPortfolioDetailsFeatures(this);
  }

  @Override
  public void getPortfolioDetailsByNameAndDate(String portfolioName, String date) {
    if (date.isBlank()) {
      view.displayErrorMessage("Date is required");
    }

    LocalDate formattedDate = LocalDate.now();
    String output;
    try {
      formattedDate = LocalDate.parse(date,
              DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      var result = model.displayPortfolioByDate(formattedDate, portfolioName);
      output = "The details of " + portfolioName + "on " + date + " are: ";
      for (String name : result.keySet()) {
        output += name + result.get(name) + "\n";
      }

      view.displayResult(output);
      view.addMenuAndExitButtons(this);
      if (formattedDate.isAfter(LocalDate.now())) {
        output = "Date cannot be after current date";
        view.displayErrorMessage(output);
      }
    } catch (DateTimeParseException e) {
      view.displayErrorMessage("Enter date in valid format");
    }
  }

  @Override
  public void buyStocksPane() {
    view.setBuyStocksPane(model.getFlexibleUserPortfolioList());
    view.addBuyStockFeatures(this);
  }

  @Override
  public void buyStock(String portfolioName, String date, String shareName, String shareCount,
                       String commissionFees) {
    if (date.isBlank() || shareName.isBlank() || shareCount.isBlank() || commissionFees.isBlank()) {
      view.displayErrorMessage("All fields are mandatory");
      return;
    }
    String stockInput = shareName + "," + shareCount + "," + date;
    model.selectPortfolioToUpdate(portfolioName);
    double fees = getTransactionFees(commissionFees);
    if (fees == -1) {
      return;
    }
    model.setTransactionFees(fees);
    String output = model.buyStocksForFlexiblePortfolio(stockInput.toUpperCase());
    if (!output.contains("Success")) {
      view.displayErrorMessage(output);
    } else {
      view.displayOutput(shareName + " added to " + portfolioName);
    }
    view.clearBuyStockInputs();
    view.addMenuAndExitButtons(this);
  }

  @Override
  public void startSimulation(DollarCostAverage pm, PortfolioView pv) {
    view.addFeatures(this);
  }

  @Override
  public void addStockToList(String stockName, String shareCount) {
    if (!isPositiveInteger(shareCount)) {
      view.displayErrorMessage("Share count should be a valid positive integer");
      view.clearBuyStockInputs();
      return;
    }

    if (listOfStocks.containsKey(stockName)) {
      double totalShareCount = listOfStocks.get(stockName) + Double.parseDouble(shareCount);
      listOfStocks.put(stockName, totalShareCount);
    } else {
      listOfStocks.put(stockName, Double.parseDouble(shareCount));
    }
    view.displayOutput("Stock added");
    view.clearBuyStockInputs();
    createFlexiblePortfolio();
  }

  @Override
  public void saveFlexiblePortfolio(String portfolioName, String date, String commissionFees) {
    if (portfolioName.isBlank() || portfolioName.isEmpty()
            || savePortfolioName(portfolioName) == 1) {
      view.displayErrorMessage("Enter valid portfolio name");
      return;
    }

    model.selectPortfolioToUpdate(portfolioName);

    double fees = getTransactionFees(commissionFees);
    if (fees == -1) {
      return;
    }
    model.setTransactionFees(fees);

    for (String stockName : listOfStocks.keySet()) {
      String stockDetails = stockName + "," + listOfStocks.get(stockName) + "," + date;
      model.saveStocksToList(stockDetails.toUpperCase(), false, LocalDate.now());
    }
    view.displayOutput("Portfolio Created Successfully");
    listOfStocks = null;
    createFlexiblePortfolio();
  }

  @Override
  public void sellStocksPane() {
    view.setSellStocksPane(model.getFlexibleUserPortfolioList());
    view.addSellStockFeatures(this);
  }

  @Override
  public void sellStock(String portfolioName, String date, String stockName,
                        String shareCount, String commissionFees) {
    if (date.isBlank() || stockName.isBlank() || shareCount.isBlank() || commissionFees.isBlank()) {
      view.displayErrorMessage("All fields are mandatory");
      return;
    }

    String stockInput = stockName + "," + shareCount + "," + date;
    model.selectPortfolioToUpdate(portfolioName);
    double fees = getTransactionFees(commissionFees);
    if (fees == -1) {
      return;
    }
    model.setTransactionFees(fees);
    String output = model.sellStocksForFlexiblePortfolio(stockInput.toUpperCase());
    if (!output.contains("Success")) {
      view.displayErrorMessage(output);
    } else {
      view.displayOutput("Stock sold successfully");
    }
    view.clearBuyStockInputs();
    view.addMenuAndExitButtons(this);
  }

  @Override
  public void weightedPortfolioPane() {
    view.setWeightedPortfolioPane(listOfWeightedStocks);
    view.addWeightedPortfolioFeatures(this);
  }

  @Override
  public void addWeightedStockToList(String stockName, String weight) {
    if (!validateWeight(weight)) {
      return;
    }
    this.listOfWeightedStocks.put(stockName, Double.parseDouble(weight));
    weightedPortfolioPane();
  }

  @Override
  public void addWeightedStocksToDollarCost(String stockName, String weight) {
    if (!validateWeight(weight)) {
      return;
    }
    this.listOfWeightedStocks.put(stockName, Double.parseDouble(weight));
    dollarCostPane();
  }

  @Override
  public void createWeightedPortfolio(String portfolioName, String date, String commissionFees,
                                      String investment) {
    if (getCount() < 100) {
      view.displayErrorMessage("Total Weight should be 100");
      return;
    }
    model.selectPortfolioToUpdate(portfolioName);
    var stocks = listOfWeightedStocks.keySet().toArray();
    var weights = listOfWeightedStocks.values().toArray();
    listOfWeightedStocks = null;
  }

  @Override
  public void dollarCostPane() {
    view.setDollarCostPane(listOfWeightedStocks);
    view.addDollarCostFeatures(this);
  }

  @Override
  public void createDollarCostStrategy(String portfolioName, String startDate, String endDate,
                                       String investment, String dayCount) {
    view.displayOutput("Successfully created!");
    listOfWeightedStocks = new HashMap<>();
  }


  private boolean isPositiveInteger(String s) {
    try {
      int number = Integer.parseInt(s);
      return number >= 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private double getTransactionFees(String commissionFees) {
    double fees;
    try {
      fees = Double.parseDouble(commissionFees);
      if (fees < 0) {
        view.displayErrorMessage("Commission fees should be a positive integer");
        return -1;
      }
    } catch (Exception e) {
      view.displayErrorMessage("Enter valid commission fees");
      return -1;
    }
    return fees;
  }

  private int savePortfolioName(String pName) {
    if (model.addPortfolioName(pName.toUpperCase()) == 0) {
      return 1;
    }
    return 0;
  }

  private double getCount() {
    double count = 0.0;
    for (String stock : listOfWeightedStocks.keySet()) {
      count += listOfWeightedStocks.get(stock);
    }
    return count;
  }

  private boolean validateWeight(String weight) {
    try {
      double value = Double.parseDouble(weight);

      double count = getCount();
      if (count + value > 100.0) {
        view.displayErrorMessage("Weight total exceeded 100. Maximum weight that can be added is:"
                + (100 - count));
        return false;
      }
      return true;
    } catch (Exception e) {
      view.displayErrorMessage("Weight should be a positive number");
    }
    return false;
  }

}
