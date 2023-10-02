package stocks.model;

import java.time.LocalDate;
import java.util.List;

/**
 * This is the interface that handles new strategies for the flexible portfolio.
 */
public interface DollarCostAverage extends FlexiblePortfolioModel {
  /**
   * display stock for given date.
   *
   * @param date given date.
   * @return returns list of stocks
   */
  List<StockModel> displayStoksForGiveDate(LocalDate date);

  /**
   * method to save dollar portfolio.
   *
   * @param pModel     DollarPortfolio model
   * @param recurring  recurring time
   * @param endDate    end date of dollar cost
   * @param investment total investment
   */
  void saveDollarPortfolioData(DollarCostAverage pModel, int recurring, LocalDate endDate,
                               Double investment);


  /**
   * save saveRecurringStocks to the existing ones.
   *
   * @param stockList  stock list
   * @param dollarCost is dollar cost
   * @param date       date of the portfolio
   * @return
   */
  String saveRecurringStocks(String stockList, boolean dollarCost, LocalDate date);

  /**
   * Get the data for dollar cost recurring portfolio.
   *
   * @param name portfolio name
   * @return returns list of stocks
   */
  List<StockModel> getPortfolioDataForRecurring(String name);

  /**
   * Gets end date  and reccuring time of dollar cost portfolio.
   *
   * @param name portfolio name
   * @return return end date and reccuring time
   */
  List<String> getEndDateAndRecurringTime(String name);

  /**
   * check for portfolio updated.
   *
   * @param pName  portfolio name
   * @param pModel portfolio model
   */
  void checkForStocksData(String pName, DollarCostAverage pModel);

  /**
   * Calculates the dollar cost portfolio.
   *
   * @param stocks    List of stock array
   * @param weights   List of respective weights
   * @param money     investment money
   * @param startDate Start date
   * @param endDate   end date
   * @param recurring reccuring time
   * @param pModel    model of dollar cost
   * @return return string that tells its calculated.
   */
  String calculateSharesRecurring(String[] stocks, String[] weights, double money,
                                  LocalDate startDate, LocalDate endDate,
                                  int recurring, DollarCostAverage pModel);

  /**
   * claculate number of shares of the portfolio.
   *
   * @param stocks     List of stock array
   * @param weights    List of respective weights
   * @param money      investment money
   * @param stocksList list of stocks of dollar cost
   * @param date       date to calculate
   * @param pModel     model of dollar cost
   * @return return success string
   */
  String calculateShares(String[] stocks, String[] weights, double money,
                         List<StockModel> stocksList, LocalDate date, DollarCostAverage pModel);

}
