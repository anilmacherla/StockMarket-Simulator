package stocks.model;

import java.time.LocalDate;

/**
 * This class is an implementation of StockModel interface.
 */
public class StockModelImpl implements StockModel {

  private final String stockName;
  private double shareCount;
  private double price;
  private LocalDate date;

  private Double commissionCharged;

  private double stockWeights;

  @Override
  public String getStockName() {
    return stockName;
  }

  @Override
  public double getShareCount() {
    return shareCount;
  }

  @Override
  public double getPrice() {
    return price;
  }

  @Override
  public Double getCommissionCharged() {
    return this.commissionCharged;
  }

  @Override
  public StockModel setCommissionCharged(Double fees) {
    this.commissionCharged = fees;
    return this;
  }

  public double getStockWeights() {
    return stockWeights;
  }

  public StockModel setStockWeights(double stockWeights) {
    this.stockWeights = stockWeights;
    return this;
  }

  /**
   * Constructs StockModelImpl and sets all the private variables to empty/0.
   */
  public StockModelImpl() {
    stockName = "";
    shareCount = 0.0;
    price = 0.0;
    commissionCharged = 0.0;
    stockWeights = 0.0;
  }

  /**
   * Constructs StockModelImpl by initializing the stock model with the given inputs.
   *
   * @param stockName  Name of the stock
   * @param shareCount Number of shares of the stock
   * @param price      price of the stock
   * @param date       date purchased
   */
  public StockModelImpl(String stockName, double shareCount, double price, LocalDate date,
                        Double commission) {
    this.stockName = stockName;
    this.shareCount = shareCount;
    this.price = price;
    this.date = date;
    this.commissionCharged = commission;
  }

  /**
   * This method initializes StockModelImpl.
   *
   * @param stockName    Name of the stock
   * @param shareCount   Number of shares of the stock
   * @param price        price of the stock
   * @param date         date purchased
   * @param commission   commission fees
   * @param stockWeights weight of the stock
   */
  public StockModelImpl(String stockName, double shareCount, double price, LocalDate date,
                        Double commission, Double stockWeights) {
    this.stockName = stockName;
    this.shareCount = shareCount;
    this.price = price;
    this.date = date;
    this.commissionCharged = commission;
    this.stockWeights = stockWeights;
  }


  @Override
  public String toString() {
    return stockName + " " + shareCount + " " + price;
  }

  @Override
  public LocalDate getStockDate() {
    return this.date;
  }

  @Override
  public StockModel setStockCount(double count) {
    this.shareCount = count;
    return this;
  }
}
