package stocks.dataaccess;

import java.time.LocalDate;
import java.util.List;

import stocks.model.StockModel;

/**
 * This interface represents the operations that are required to access the source data which could
 * be CSV, JSON or any human-readable file.
 */
public interface DataAccessService {

  /**
   * Fetches Stock data for the date specified.
   *
   * @param date Date for which stock and its associated prices are needed
   * @return List of stocks for the specified date
   */
  List<StockModel> getStockDataByDate(LocalDate date);

  /**
   * Validates if a stock exists in the source file for the current date.
   *
   * @return returns true if stock exists or else returns false
   */
  boolean checkCurrentDayStockPresent();

  /**
   * Returns Stock price for the date specified.
   *
   * @param ticker Stock ticker for which price is required.
   * @param date   date on which price to be fetched
   * @return stock price for the date specified
   */
  Double getStockValueByDate(String ticker, LocalDate date);
}
