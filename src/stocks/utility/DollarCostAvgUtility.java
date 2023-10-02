package stocks.utility;

import java.time.LocalDate;
import java.util.List;

import stocks.model.DollarCostAverage;
import stocks.model.StockModel;

/**
 * This class handles dollar cost average portfolio shares.
 */
public class DollarCostAvgUtility {

  /**
   * Create the recurring stock if not available.
   *
   * @param stocks     stocks list
   * @param weights    weights of the stock
   * @param shares     total shares
   * @param startDate  start date of the stock
   * @param endDate    end date of the stock
   * @param recurring  recurring time
   * @param pModel     DollarPortfolioModel
   * @param investment total investment
   * @return returns shares
   */
  public static List<String> calculateSharesRecurring(String[] stocks, String[] weights,
                                                      List<String> shares,
                                                      LocalDate startDate, LocalDate endDate,
                                                      int recurring, DollarCostAverage pModel,
                                                      double investment) {
    while (startDate.isBefore(endDate) && startDate.isBefore(LocalDate.now())) {
      List<StockModel> stocksList = pModel.displayStoksForGiveDate(startDate);
      for (int i = 0; i < stocks.length; i++) {
        double price = stocksList.stream().filter(l -> l.getStockName()
                        .equals(stocks[1].toUpperCase()))
                .map(l -> l.getPrice()).findFirst().get();
        double cost = (investment * (Double.valueOf(weights[i]) / 100.0f));
        double shareCount = (1 / price) * cost;
        shares.add(stocks[i] + "," + shareCount + "," + startDate + "," + weights[i]);
      }
      startDate = startDate.plusDays(recurring);
    }
    return shares;
  }


}
