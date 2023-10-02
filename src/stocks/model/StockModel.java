package stocks.model;

import java.time.LocalDate;

/**
 * This interface represents all the methods and operations such as adding/displaying the data
 * associated with the stocks.
 */
public interface StockModel {
  /**
   * Gets the current stock name.
   *
   * @return name of the stock
   */
  String getStockName();

  /**
   * Gets the count of number of shares of the stock purchased.
   *
   * @return number of shares
   */
  double getShareCount();

  /**
   * Gets the price of the stock price.
   *
   * @return price of the stock
   */
  double getPrice();

  StockModel setCommissionCharged(Double fees);

  /**
   * Represents the date of the stock stored.
   *
   * @return date of current stock
   */
  LocalDate getStockDate();

  /**
   * Sets the number of shares purchased for the stock.
   *
   * @param count Number of shares bought
   * @return current StockModel object
   */
  StockModel setStockCount(double count);

  Double getCommissionCharged();

  double getStockWeights();

}
