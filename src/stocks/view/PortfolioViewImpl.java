package stocks.view;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import stocks.model.StockModel;

/**
 * This class implements PortfolioView interface.
 */
public class PortfolioViewImpl implements PortfolioView {

  private final PrintStream out;

  /**
   * This method constructs PortfolioViewImpl object.
   *
   * @param out out of type PrintStream.
   */
  public PortfolioViewImpl(PrintStream out) {
    this.out = out;
  }

  @Override
  public void displayMenu() {
    this.out.println("Please enter your choice: \n 1.Create Portfolio \n 2.Display Portfolio"
            + "\n 3.Display portfolio value at given date in the format YYYY-MM-DD"
            + "\n 4.Create flexible portfolio"
            + "\n 5.Add stock to a flexible portfolio at a desired date"
            + "\n 6.Sell stocks from a portfolio at a desired date"
            + "\n 7.Get cost basis of portfolio based on date"
            + "\n 8.Display Performance of Portfolio"
            + "\n 9.Modify the transaction/commission fees"
            + "\n 10. Create flexible Portfolio with weighted stocks"
            + "\n 11. Create flexible Portfolio with weighted stocks with recurring"
            + "\n 0.Exit");
  }

  @Override
  public void askPortfolioName() {
    this.out.println("Enter Portfolio Name: ");
  }

  @Override
  public void displayStockList(List<StockModel> stocksList) {
    this.out.println("Available Stocks and their respective prices are:- ");
    for (var stock : stocksList) {
      this.out.println(stock.getStockName() + ", $" + stock.getPrice());
    }
  }

  @Override
  public void enterStocks() {
    this.out.println("Enter Stock ticker");
  }

  @Override
  public void displayExit() {
    this.out.println("Do you want to exit - [Y/N] : ");
  }

  @Override
  public void displayAddStocks() {
    this.out.println("Do you want to add another stock to the portfolio - [Y/N]:");
  }

  @Override
  public void displayTotalPortfolioValueByNameAndDate(HashMap<String,
          Double> displayPortfolioByDate) {
    if (displayPortfolioByDate.size() == 0) {
      this.out.println("Cannot get the portfolio value as it is not created"
              + " on or before the given date.");
    } else if (displayPortfolioByDate.size() == 1
            && displayPortfolioByDate.keySet().stream().findFirst().get().contains("Error")) {
      this.out.println(displayPortfolioByDate.keySet().stream().findFirst().get());
    } else {
      for (var portfolio : displayPortfolioByDate.entrySet()) {
        this.out.println("The value of " + portfolio.getKey() + " is: $" + portfolio.getValue());
      }
    }
  }

  @Override
  public void displayToEnterDate() {
    this.out.println("Please enter date in format YYYY-MM-DD");
  }

  @Override
  public void displayErrorWhileEnteringDate() {
    this.out.println("Date is in incorrect format. Please enter in the format YYYY-MM-DD");
  }

  @Override
  public void displayErrorSavingPortfolioName() {
    this.out.println("Portfolio name should be unique. Please try again");
  }

  @Override
  public void displayOutput(String saveStocksToList) {
    this.out.println(saveStocksToList);
  }

  @Override
  public void displayPortfolio(HashMap<String, List<StockModel>> displayPortfolio) {
    this.out.println("----------------------------------------------------------------");
    if (displayPortfolio.isEmpty() || displayPortfolio == null) {
      this.out.println("No Portfolio exists, please create one to display");
      this.out.println("----------------------------------------------------------------");
    } else {
      for (var portfolio : displayPortfolio.entrySet()) {
        this.out.println("Portfolio name: " + portfolio.getKey());
        for (var stock : portfolio.getValue()) {
          this.out.println("Stock name:- " + stock.getStockName() + ", No. of Shares:- "
                  + stock.getShareCount());
        }
        this.out.println("----------------------------------------------------------------");
      }
    }
  }

  @Override
  public void portfolioCreatedSuccessfully() {
    this.out.println("Portfolio created and saved successfully.");
  }

  @Override
  public void clearPortfolioSuccessfully() {
    this.out.println("Portfolio cleared successfully.");
  }

  @Override
  public void displayToEnterPortfolioDate() {
    this.out.println("Enter portfolio date in the format YYYY-MM-DD:");
  }

  @Override
  public void displayToAddStocksToExistingPortfolio() {
    this.out.println("Enter ticker with number of shares and date to add to the portfolio in the "
            + "format - TICKER,Count,YYYY-MM-DD");
  }

  @Override
  public void displayToSellStocksFromFlexiblePortfolio() {
    this.out.println("Enter ticker with number of shares and date to sell from the portfolio in "
            + "format - TICKER,Count,YYYY-MM-DD");
  }

  @Override
  public void displaySellStocks() {
    this.out.println("Do you want to sell more stocks?");
  }

  @Override
  public void displayToEnterTransactionFees() {
    this.out.println("Enter commission fees:");
  }

  @Override
  public void displayErrorForTransactionFees() {
    this.out.println("Transaction fees should be a positive number.");
  }

  @Override
  public void displayAllPortfolioNames(List<String> userPortfolioNamesList) {
    this.out.println("Select portfolio from the following list: ");
    for (String name : userPortfolioNamesList) {
      System.out.println(name);
    }
  }

  @Override
  public void displayFlexiblePortfolioList(List<String> flexibleUserPortfolioList) {
    this.out.println("Please select a flexible portfolio from the following list:-");
    for (String portfolioName : flexibleUserPortfolioList) {
      this.out.println(portfolioName);
    }
  }

  @Override
  public void displayErrorForInvalidFlexiblePortfolioName() {
    this.out.println("Please enter valid existing portfolio");
  }

  @Override
  public void displayErrorIfNoFlexiblePortfolioExists() {
    this.out.println("Please add a flexible portfolio first to add a stock");
  }

  @Override
  public void displayCostBasisPortfolio(Map<String, Double> costBasisByPortfolioNameAndDate) {
    for (String value : costBasisByPortfolioNameAndDate.keySet()) {
      this.out.println(value + ": $" + costBasisByPortfolioNameAndDate.get(value));
    }
  }

  @Override
  public void enterPortfolioDurationStartDate() {
    this.out.println("Enter start date in format - "
            + " YYYY-MM-DD");
  }

  @Override
  public void enterPortfolioDurationEndDate() {
    this.out.println("Enter end date in format - "
            + " YYYY-MM-DD");
  }


  @Override
  public void displayGraph(Map<String, Double> portfolio, String portfolioName,
                           LocalDate startDate, LocalDate endDate) {
    int numberOfStars = 0;
    double max = 0.0;
    double min = 0.0;
    if (portfolio.isEmpty()) {
      this.out.println("No portfolio exists in the given duration");
    }
    for (Map.Entry<String, Double> entry : portfolio.entrySet()) {
      if (entry.getValue() == null) {
        entry.setValue(0.0);
      }
    }

    max = Collections.max(portfolio.values());
    min = Collections.min(portfolio.values());
    int scale = (int) (max - min) / 50;
    this.out.println("Performance of the portfolio :" + portfolioName
            + " from " + startDate.toString() + " to " + endDate.toString());
    for (Map.Entry<String, Double> entry : portfolio.entrySet()) {
      numberOfStars = (int) (entry.getValue() / scale);
      if (numberOfStars < 1) {
        numberOfStars = 1;
      }
      this.out.println(entry.getKey() + ":" + Stream.generate(() -> "*")
              .limit(numberOfStars).collect(Collectors.joining()));

    }
    this.out.println("Scale   : * = $" + scale);
  }

  @Override
  public void displayErrorDateRangeLessThanFive() {
    this.out.println("Duration between two dates should be at least 5 days");
  }

  @Override
  public void displayErrorForFutureDate() {
    this.out.println("Entered date should be on or before current date. Please enter date again");
  }

  @Override
  public void enterShareCount() {
    this.out.println("Enter number of shares");
  }

  @Override
  public void displayTransactionFeesSaved() {
    this.out.println("Transaction fees saved.");
  }

  @Override
  public void displayDateError() {
    this.out.println("Start date should not exceed end Date");
  }

  @Override
  public void askForInvestment() {
    this.out.println("Please enter total investment");
  }

  @Override
  public void enterListOfStockTickers() {
    this.out.println("Please enter list of stocks you want to buy in format - "
            + "TICKER1,TICKER2,TICKER3");
  }

  @Override
  public void askForWeightage() {
    this.out.println("Please provide the relative portions of weights for stocks you want to buy"
            + "in format- w1,w2,w3");
  }

  @Override
  public void enterRecurring() {
    this.out.println("Please enter how many days after you want to invest");
  }


}
