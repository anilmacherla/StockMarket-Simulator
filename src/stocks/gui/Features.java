package stocks.gui;

/**
 * This interface describes all the features that are supported by the stock simulator.
 */
public interface Features {
  /**
   * Exits and closes the application.
   */
  void exitProgram();

  /**
   * Displays main menu with the list of operations supported.
   */
  void mainMenu();

  /**
   * Displays flexible portfolio window.
   */
  void createFlexiblePortfolio();

  /**
   * Displays cost basis window.
   */
  void getCostBasis();

  /**
   * Calculates cost basis of a portfolio.
   *
   * @param portfolioName Name of the portfolio
   * @param date          date on which cost basis is required
   */
  void calculateCostBasis(String portfolioName, String date);

  /**
   * Displays Portfolio details window.
   */
  void portfolioDetails();

  /**
   * Gets the details of a portfolio for a given date.
   *
   * @param portfolioName Name of portfolio
   * @param date          data for which portfolio to be retrieved
   */
  void getPortfolioDetailsByNameAndDate(String portfolioName, String date);

  /**
   * Displays window to buy stocks.
   */
  void buyStocksPane();

  /**
   * Adds a stock to an existing portfolio.
   *
   * @param portfolioName  Name of portfolio
   * @param date           date on which stock is purchased
   * @param stockName      name of the stock
   * @param shareCount     no of shares to be purchased
   * @param commissionFees commission fees charged
   */
  void buyStock(String portfolioName, String date, String stockName, String shareCount,
                String commissionFees);

  /**
   * Adds a stock to the list.
   *
   * @param stockName  Name of the stock
   * @param shareCount Number of shares
   */
  void addStockToList(String stockName, String shareCount);

  /**
   * Saves flexible portfolio.
   *
   * @param portfolioName  Name of the portfolio
   * @param date           date on which portfolio is created
   * @param commissionFees commission to be charged
   */
  void saveFlexiblePortfolio(String portfolioName, String date, String commissionFees);

  /**
   * Displays window to sell shares of a stock.
   */
  void sellStocksPane();

  /**
   * Sells stock from a flexible portfolio.
   *
   * @param portfolioName  Name of the portfolio
   * @param date           Date on which stock is to be sold
   * @param stockName      Name of the stock
   * @param shareCount     Number of shares to be sold
   * @param commissionFees commission fees charged
   */
  void sellStock(String portfolioName, String date, String stockName, String shareCount,
                 String commissionFees);

  /**
   * Displays window to create a weighted portfolio.
   */
  void weightedPortfolioPane();

  /**
   * Adds weighted stocks to list.
   *
   * @param stockName Name of the stock
   * @param weight    weight of the stock
   */
  void addWeightedStockToList(String stockName, String weight);

  /**
   * Adds weighted stocks for dollar cost averaging.
   *
   * @param stockName Name of the stock
   * @param weight    weight of the stock
   */
  void addWeightedStocksToDollarCost(String stockName, String weight);

  /**
   * Creates a weighted portfolio.
   *
   * @param portfolioName  Name of the portfolio
   * @param date           created date
   * @param commissionFees commission to be charged
   * @param investment     amount to be invested
   */
  void createWeightedPortfolio(String portfolioName, String date, String commissionFees,
                               String investment);

  /**
   * Displays window for dollar cost strategy.
   */
  void dollarCostPane();

  /**
   * Creates dollar cost strategy for a portfolio.
   *
   * @param portfolioName Name of the portfolio
   * @param startDate     start date of investment
   * @param endDate       end date of investment
   * @param investment    amount to be invested
   * @param dayCount      duration of the strategy in days
   */
  void createDollarCostStrategy(String portfolioName, String startDate, String endDate,
                                String investment, String dayCount);
}
