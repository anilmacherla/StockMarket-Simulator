package stocks.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * This interface describes all the methods/operations required to support a flexible portfolio
 * such as modify/display existing portfolios.
 */
public interface FlexiblePortfolioModel extends PortfolioModel {

  /**
   * Saves a flexible portfolio.
   *
   * @param pModel data to be saved as flexible portfolio
   */
  void saveFlexiblePortfolioData(PortfolioModel pModel);

  /**
   * Gets all the names of the portfolios present in flexible portfolios.
   *
   * @return Names of flexible portfolios
   */
  List<String> getFlexibleUserPortfolioList();

  /**
   * Sets the commission fees of the broker.
   *
   * @param fees fees to be charged per transaction
   */
  void setTransactionFees(Double fees);

  /**
   * Selects flexible portfolio to update.
   *
   * @param flexiblePortfolioName portfolio name for which portfolio has to be updated
   */
  void selectPortfolioToUpdate(String flexiblePortfolioName);

  /**
   * Buys stocks and adds to an existing flexible portfolio.
   *
   * @param stockDetails Stock to be bought
   * @return Returns Success/Error based as status message of the operation.
   */
  String buyStocksForFlexiblePortfolio(String stockDetails);

  /**
   * Sells a stock from an existing portfolio.
   *
   * @param stockDetails Stock to be sold
   * @return Returns Success/Error based as status message of the operation.
   */
  String sellStocksForFlexiblePortfolio(String stockDetails);

  /**
   * Gets the names of all the portfolios.
   *
   * @return names of portfolios
   */
  List<String> getUserPortfolioNamesList();

  /**
   * Gets cost basis of specified portfolio at a specified date.
   *
   * @param name Name of the portfolio
   * @param date date at which cost basis is to be fetched
   * @return Cost basis of portfolio
   */
  Map<String, Double> getCostBasisByPortfolioNameAndDate(String name, LocalDate date);

  /**
   * Displays portfolio performance over a period of time.
   *
   * @param pName     name of the portfolio
   * @param startDate start date of the portfolio analysis
   * @param endDate   end date of the portfolio analysis
   * @return portfolio performance along with its respective time period
   */
  Map<String, Double> displayGraph(String pName, LocalDate startDate,
                                   LocalDate endDate);
}
