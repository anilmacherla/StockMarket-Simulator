package stocks.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * This interface represents all the operations associated with a portfolio.
 * Using this interface, we can add/display portfolios of a user accordingly.
 */
public interface PortfolioModel {
  /**
   * Creates a new portfolio for the user.
   *
   * @param pName Name of the portfolio
   * @return returns 1 if portfolio is created. Otherwise, returns 0
   */
  int addPortfolioName(String pName);

  /**
   * Displays all the portfolios along with its associated stocks. It also displays the total
   * value of all the portfolios combined.
   *
   * @param name Name of the portfolio to be retrieved
   * @return a hashmap that contains portfolios and its stocks
   */
  HashMap<String, List<StockModel>> displayPortfolioByName(String name);

  /**
   * Adds stock to the portfolio.
   *
   * @param stock Stock object to be added
   * @return A message is returned describing the status of this operation
   */
  String saveStocksToList(String stock, boolean dollarCost, LocalDate date);

  /**
   * Saves the entire portfolio data to a file.
   *
   * @param pModel portfolio data to be saved
   */
  void saveData(PortfolioModel pModel);

  /**
   * Deletes the entire user portfolio data.
   */
  void deletePortfolio();

  /**
   * Displays portfolio data for a specific date.
   *
   * @param date          date for which portfolio should be displayed
   * @param portfolioName portfolio for which total value has to be calculated
   * @return All the portfolios for the date specified
   */
  HashMap<String, Double> displayPortfolioByDate(LocalDate date, String portfolioName);

  /**
   * Displays all the stocks along with their prices for the current day
   * that are allowed by the application.
   *
   * @return List of stocks with its prices
   */
  List<StockModel> displayAvailableStocks();

  /**
   * Checks if the stock is present in the Source Stock data.
   *
   * @return true if stock exists otherwise returns false
   */
  boolean checkCurrentDayStockPresent();

  void reset();

}
