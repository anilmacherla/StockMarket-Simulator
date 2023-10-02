package stocks;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import stocks.model.DollarCostAverage;
import stocks.model.DollarCostAverageImpl;
import stocks.model.StockModel;
import stocks.model.StockModelImpl;

/**
 * Test for DollarCostAverage.
 */
public class DollarCostAverageTest {

  private DollarCostAverage pm;

  @Test
  public void testDollarCostAveragingCostBasis() {
    Random r = new Random();
    String pName = "PORTFOLIO1" + r.nextInt(30000);
    List<StockModel> stocks = new ArrayList<>();
    stocks.add(new StockModelImpl("AAPL", 2, 100.0,
            LocalDate.parse("2022-11-10"),
            5.0, 60.0));
    DollarCostAverage pModel = new DollarCostAverageImpl(pName,
            LocalDate.parse("2022-11-10"), stocks);
    pModel.selectPortfolioToUpdate(pName);
    pModel.buyStocksForFlexiblePortfolio("AAPL,20,2022-11-10");
    int recurring = 2;
    LocalDate endDate = LocalDate.parse("2022-10-11");
    Double investment = 1000.0;
    String[] array = {"jpm", "jnj"};
    String portions = "60,40";
    String[] weights = portions.split(",");
    pModel.calculateSharesRecurring(array, weights, investment,
            LocalDate.parse("2022-10-11"), endDate, recurring, pModel);
    pModel.saveDollarPortfolioData(pModel, recurring, endDate, investment);
    pModel.selectPortfolioToUpdate(pName);
    pModel.buyStocksForFlexiblePortfolio("AAPL,20,2022-11-10");
    Map<String, Double> cost = (pModel
            .getCostBasisByPortfolioNameAndDate(pName, LocalDate.parse("2022-11-21")));
    Assert.assertEquals("2200.0", cost.get("StockValue").toString());
    Assert.assertEquals("10.0", cost.get("Commission").toString());
    Assert.assertEquals("2210.0", cost.get("Total Money Invested").toString());
  }

  @Test
  public void testDollarCostAveragingCostBasisDays() {
    Random r = new Random();
    String pName = "PORTFOLIO1" + r.nextInt(30000);
    List<StockModel> stocks = new ArrayList<>();
    stocks.add(new StockModelImpl("AAPL", 2, 100.0,
            LocalDate.parse("2022-11-10"),
            5.0, 60.0));
    DollarCostAverage pModel = new DollarCostAverageImpl(pName,
            LocalDate.parse("2022-11-10"), stocks);
    pModel.selectPortfolioToUpdate(pName);
    pModel.buyStocksForFlexiblePortfolio("AAPL,20,2022-11-10");
    int recurring = 2;
    LocalDate endDate = LocalDate.parse("2022-12-11");
    Double investment = 1000.0;
    String[] array = {"jpm", "jnj"};
    String portions = "60,40";
    String[] weights = portions.split(",");
    pModel.calculateSharesRecurring(array, weights, investment,
            LocalDate.parse("2022-10-11"), endDate, recurring, pModel);
    pModel.saveDollarPortfolioData(pModel, recurring, endDate, investment);
    pModel.selectPortfolioToUpdate(pName);
    pModel.buyStocksForFlexiblePortfolio("AAPL,20,2022-11-10");
    Map<String, Double> cost = (pModel
            .getCostBasisByPortfolioNameAndDate(pName, LocalDate.parse("2022-11-21")));
    Assert.assertEquals("4000.0", cost.get("StockValue").toString());
    Assert.assertEquals("10.0", cost.get("Commission").toString());
    Assert.assertEquals("4010.0", cost.get("Total Money Invested").toString());
    Map<String, Double> cost1 = (pModel
            .getCostBasisByPortfolioNameAndDate(pName, LocalDate.parse("2022-11-21")));
    Assert.assertEquals("4000.0", cost1.get("StockValue").toString());
    Assert.assertEquals("10.0", cost1.get("Commission").toString());
    Assert.assertEquals("4010.0", cost1.get("Total Money Invested").toString());
  }

  @Test
  public void testValue() {
    Random r = new Random();
    String pName = "PORTFOLIO1" + r.nextInt(30000);
    List<StockModel> stocks = new ArrayList<>();
    stocks.add(new StockModelImpl("AAPL", 2, 100.0,
            LocalDate.parse("2022-11-10"),
            5.0, 60.0));
    DollarCostAverage pModel = new DollarCostAverageImpl(pName,
            LocalDate.parse("2022-11-10"), stocks);
    pModel.selectPortfolioToUpdate(pName);
    int recurring = 2;
    LocalDate endDate = LocalDate.parse("2022-12-11");
    Double investment = 1000.0;
    String[] array = {"jpm", "jnj"};
    String portions = "60,40";
    String[] weights = portions.split(",");
    pModel.calculateSharesRecurring(array, weights, investment,
            LocalDate.parse("2022-10-11"), endDate, recurring, pModel);
    pModel.saveDollarPortfolioData(pModel, recurring, endDate, investment);
    pModel.selectPortfolioToUpdate(pName);
    var value = pModel
            .displayPortfolioByDate(LocalDate.parse("2022-10-11"), pName);
    for (var portfolio : value.entrySet()) {
      Assert.assertEquals("1000.00", portfolio.getValue());
    }
    var value1 = pModel
            .displayPortfolioByDate(LocalDate.parse("2022-10-21"), pName);
    for (var portfolio : value1.entrySet()) {
      Assert.assertEquals("2000.00", portfolio.getValue());
    }
  }
}
