package stocks.view;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stocks.model.StockModel;

/**
 * This interface contains methods for all the views that could be used in the simulator.
 */
public interface PortfolioView {


  /**
   * Displays menu containing all the operations supported by the application.
   */
  void displayMenu();

  /**
   * Displays message requesting user to input the portfolio name.
   */
  void askPortfolioName();

  /**
   * Displays List of stocks that are supported by the application.
   *
   * @param stocksList Stocks to be displayed
   */
  void displayStockList(List<StockModel> stocksList);

  /**
   * Displays message requesting the user to enter the stocks and share count.
   */
  void enterStocks();

  /**
   * Displays message asking the user if to exit after entering the stocks.
   */
  void displayExit();

  /**
   * Displays message asking the user to add stocks.
   */
  void displayAddStocks();

  /**
   * Displays entire portfolio and, it's value for the requested date.
   *
   * @param displayPortfolioByDate portfolio data for the date requested
   */
  void displayTotalPortfolioValueByNameAndDate(HashMap<String, Double> displayPortfolioByDate);

  /**
   * Displays message asking the user to enter the date for which the portfolio data is required.
   */
  void displayToEnterDate();

  /**
   * Displays error message for entering invalid date.
   */
  void displayErrorWhileEnteringDate();

  /**
   * Displays error message for giving duplicate portfolio name.
   */
  void displayErrorSavingPortfolioName();

  /**
   * Displays message about the status of add stocks operation.
   *
   * @param saveStocksToList stocks' list to be saved
   */
  void displayOutput(String saveStocksToList);

  /**
   * Displays all the portfolios associated with the user.
   *
   * @param displayPortfolio portfolio object to be displayed
   */
  void displayPortfolio(HashMap<String, List<StockModel>> displayPortfolio);

  /**
   * Displays success message upon creation of portfolio name successfully.
   */
  void portfolioCreatedSuccessfully();

  /**
   * Displays success message upon deletion of portfolio user data successfully.
   */
  void clearPortfolioSuccessfully();

  /**
   * Display prompt to enter portfolio date.
   */
  void displayToEnterPortfolioDate();

  /**
   * Display prompt to add stock to existing portfolio.
   */
  void displayToAddStocksToExistingPortfolio();

  /**
   * Displays names of flexible portfolios.
   *
   * @param flexibleUserPortfolioList names to be displayed
   */
  void displayFlexiblePortfolioList(List<String> flexibleUserPortfolioList);

  /**
   * Displays Error for invalid flexible portfolio name.
   */
  void displayErrorForInvalidFlexiblePortfolioName();

  /**
   * Displays error if no flexible portfolio exists.
   */
  void displayErrorIfNoFlexiblePortfolioExists();

  /**
   * Displays prompt to enter stock ticker, stock count and date from flexible portfolio.
   */
  void displayToSellStocksFromFlexiblePortfolio();

  /**
   * Display prompt to ask user if to sell more stocks.
   */
  void displaySellStocks();

  /**
   * Display to enter transaction fees.
   */
  void displayToEnterTransactionFees();

  /**
   * Display error for incorrect transaction fees.
   */
  void displayErrorForTransactionFees();

  /**
   * Display all the portfolio names.
   *
   * @param userPortfolioNamesList names to be displayed
   */
  void displayAllPortfolioNames(List<String> userPortfolioNamesList);

  /**
   * Displays prompt to enter portfolio duration start date.
   */
  void enterPortfolioDurationStartDate();

  /**
   * Displays prompt to enter portfolio duration end date.
   */
  void enterPortfolioDurationEndDate();

  /**
   * Displays graph of the portfolio.
   *
   * @param portfolio portfolio to be displayed
   */
  void displayGraph(Map<String, Double> portfolio, String portfolioName,
                    LocalDate startDate, LocalDate endDate);

  /**
   * Displays error when portfolio duration range is less than five days.
   */
  void displayErrorDateRangeLessThanFive();

  /**
   * Displays error if future date is given.
   */
  void displayErrorForFutureDate();

  /**
   * Display to prompt user to enter share count.
   */
  void enterShareCount();

  /**
   * Display success message on saving transaction fees.
   */
  void displayTransactionFeesSaved();

  /**
   * Display date error.
   */
  void displayDateError();

  /**
   * Display ask for investment.
   */
  void askForInvestment();

  /**
   * Display to enter list of stock and tickers.
   */
  void enterListOfStockTickers();

  /**
   * Display to ask for weights.
   */
  void askForWeightage();

  /**
   * Display ask for reccuring time.
   */
  void enterRecurring();

  void displayCostBasisPortfolio(Map<String, Double> costBasisByPortfolioNameAndDate);


}
