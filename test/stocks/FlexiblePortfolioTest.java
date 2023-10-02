package stocks;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import stocks.model.FlexiblePortfolioModel;
import stocks.model.FlexiblePortfolioModelImpl;
import stocks.model.StockModel;
import stocks.model.StockModelImpl;

/**
 * Test for FlexiblePortfolio class.
 */
public class FlexiblePortfolioTest {

  private FlexiblePortfolioModel pm;


  @Test
  public void TestSaveFlexiblePortfolioData() {
    Random r = new Random();
    String pName = "PORTFOLIO1" + r.nextInt(30000);
    List<StockModel> stocks = new ArrayList<>();
    stocks.add(new StockModelImpl("AAPL", 1, 0,
            LocalDate.parse("2022-11-10"),
            0.0));
    FlexiblePortfolioModel pModel = new FlexiblePortfolioModelImpl(pName,
            LocalDate.parse("2022-11-10"), stocks);
    pModel.saveFlexiblePortfolioData(pModel);
    String stockName = pModel.displayPortfolioByName(pName).get(pName).get(0).getStockName();
    double shareCount = pModel.displayPortfolioByName(pName).get(pName).get(0).getShareCount();
    LocalDate date = pModel.displayPortfolioByName(pName).get(pName).get(0).getStockDate();
    double price = pModel.displayPortfolioByName(pName).get(pName).get(0).getPrice();
    Assert.assertEquals(stocks.get(0).getStockName(), stockName);
    Assert.assertEquals(stocks.get(0).getShareCount(), shareCount);
    Assert.assertEquals(stocks.get(0).getStockDate(), date);
    Assert.assertEquals(stocks.get(0).getPrice(), price, 0);
  }

  @Test
  public void TestGetCostBasisByPortfolioNameAndDate() {
    Random r = new Random();
    String pName = "PORTFOLIO1" + r.nextInt(30000);
    List<StockModel> stocks = new ArrayList<>();
    stocks.add(new StockModelImpl("AAPL", 2, 100.0,
            LocalDate.parse("2022-11-10"),
            5.0));
    FlexiblePortfolioModel pModel = new FlexiblePortfolioModelImpl(pName,
            LocalDate.parse("2022-11-10"), stocks);
    pModel.saveFlexiblePortfolioData(pModel);
    pModel.selectPortfolioToUpdate(pName);
    pModel.buyStocksForFlexiblePortfolio("AAPL,20,2022-11-10");
    Map<String, Double> cost = (pModel
            .getCostBasisByPortfolioNameAndDate(pName, LocalDate.parse("2022-11-12")));
    Assert.assertEquals("2200.0", cost.get("StockValue").toString());
    Assert.assertEquals("10.0", cost.get("Commission").toString());
    Assert.assertEquals("2210.0", cost.get("Total Money Invested").toString());
  }

  @Test
  public void TestGetFlexibleUserPortfolioList() {
    Random r = new Random();
    String pName = "PORTFOLIO1" + r.nextInt(30000);
    List<StockModel> stocks = new ArrayList<>();
    stocks.add(new StockModelImpl("AAPL", 1, 0,
            LocalDate.parse("2022-11-10"),
            0.0));
    FlexiblePortfolioModel pModel = new FlexiblePortfolioModelImpl(pName,
            LocalDate.parse("2022-11-10"), stocks);
    pModel.saveFlexiblePortfolioData(pModel);
    List<String> portfolioList = pModel.getFlexibleUserPortfolioList();
    Assert.assertEquals(pName, portfolioList.get(portfolioList.size() - 1));
    Assert.assertEquals(true, portfolioList.contains(pName));
  }

  @Test
  public void TestGetUserPortfolioNameList() {
    Random r = new Random();
    String pName = "PORTFOLIO1" + r.nextInt(30000);
    List<StockModel> stocks = new ArrayList<>();
    stocks.add(new StockModelImpl("AAPL", 1, 0,
            LocalDate.parse("2022-11-10"),
            0.0));
    FlexiblePortfolioModel pModel = new FlexiblePortfolioModelImpl(pName,
            LocalDate.parse("2022-11-10"), stocks);
    pModel.saveFlexiblePortfolioData(pModel);
    List<String> pNames = pModel.getUserPortfolioNamesList();
    Assert.assertEquals(pName, pNames.get(pNames.size() - 1));
    Assert.assertEquals(true, pNames.contains(pName));
  }

  @Test
  public void TestAddStocksToFlexiblePortfolio() {
    Random r = new Random();
    String pName = "PORTFOLIO1" + r.nextInt(30000);
    List<StockModel> stocks = new ArrayList<>();
    stocks.add(new StockModelImpl("AAPL", 1, 0,
            LocalDate.parse("2022-11-10"),
            0.0));
    FlexiblePortfolioModel pModel = new FlexiblePortfolioModelImpl(pName,
            LocalDate.parse("2022-11-10"), stocks);
    pModel.saveFlexiblePortfolioData(pModel);
    pModel.selectPortfolioToUpdate(pName);
    String message = pModel.buyStocksForFlexiblePortfolio("AAPL,20,2022-11-12");
    String stockName = pModel.displayPortfolioByName(pName).get(pName).get(0).getStockName();
    double shares = pModel.displayPortfolioByName(pName).get(pName).get(0).getShareCount();
    Assert.assertEquals(stocks.get(0).getStockName(), stockName);
    Assert.assertEquals(21, shares);
  }

  @Test
  public void TestAddStocksToFlexiblePortfolioInValidDate() {
    Random r = new Random();
    String pName = "PORTFOLIO1" + r.nextInt(30000);
    List<StockModel> stocks = new ArrayList<>();
    stocks.add(new StockModelImpl("AAPL", 1, 0,
            LocalDate.parse("2022-11-10"),
            0.0));
    FlexiblePortfolioModel pModel = new FlexiblePortfolioModelImpl(pName,
            LocalDate.parse("2022-11-10"), stocks);
    pModel.saveFlexiblePortfolioData(pModel);
    pModel.selectPortfolioToUpdate(pName);
    String date = LocalDate.now().plusDays(30).toString();
    String message = pModel.buyStocksForFlexiblePortfolio("AAPL,20," + date);
    Assert.assertEquals("Cannot add stocks with a date beyond the current date", message);
  }

  @Test
  public void TestSellStocksForFlexiblePortfolio() {
    Random r = new Random();
    String pName = "PORTFOLIO1" + r.nextInt(30000);
    List<StockModel> stocks = new ArrayList<>();
    stocks.add(new StockModelImpl("AAPL", 1, 0,
            LocalDate.parse("2022-11-10"),
            0.0));
    FlexiblePortfolioModel pModel = new FlexiblePortfolioModelImpl(pName,
            LocalDate.parse("2022-11-10"), stocks);
    pModel.saveFlexiblePortfolioData(pModel);
    pModel.selectPortfolioToUpdate(pName);
    String message = pModel.buyStocksForFlexiblePortfolio("AAPL,20,2022-11-12");
    pModel.selectPortfolioToUpdate(pName);
    String sellMessage = pModel.sellStocksForFlexiblePortfolio("AAPL,2,2022-11-13");
    String stockName = pModel.displayPortfolioByName(pName).get(pName).get(0).getStockName();
    double shares = pModel.displayPortfolioByName(pName).get(pName).get(0).getShareCount();
    Assert.assertEquals(stocks.get(0).getStockName(), stockName);
    Assert.assertEquals(19, shares);
  }

  @Test
  public void TestSellStocksForFlexiblePortfolioNotThereInPortfolio() {
    Random r = new Random();
    String pName = "PORTFOLIO1" + r.nextInt(30000);
    List<StockModel> stocks = new ArrayList<>();
    stocks.add(new StockModelImpl("AAPL", 1, 0,
            LocalDate.parse("2022-11-10"),
            0.0));
    FlexiblePortfolioModel pModel = new FlexiblePortfolioModelImpl(pName,
            LocalDate.parse("2022-11-10"), stocks);
    pModel.saveFlexiblePortfolioData(pModel);
    pModel.selectPortfolioToUpdate(pName);
    String message = pModel.buyStocksForFlexiblePortfolio("AAPL,20,2022-11-12");
    pModel.selectPortfolioToUpdate(pName);
    String sellMessage = pModel.sellStocksForFlexiblePortfolio("AAPL,40,2022-11-13");
    Assert.assertEquals("Cannot sell stocks greater than the purchased stocks"
            + ". Maximum stock that you can sell is :21", sellMessage);
  }

  @Test
  public void TestSellStocksForFlexiblePortfolioDateInvalid() {
    Random r = new Random();
    String pName = "PORTFOLIO1" + r.nextInt(30000);
    List<StockModel> stocks = new ArrayList<>();
    stocks.add(new StockModelImpl("AAPL", 1, 0,
            LocalDate.parse("2022-11-10"),
            0.0));
    FlexiblePortfolioModel pModel = new FlexiblePortfolioModelImpl(pName,
            LocalDate.parse("2022-11-10"), stocks);
    pModel.saveFlexiblePortfolioData(pModel);
    pModel.selectPortfolioToUpdate(pName);
    String message = pModel.buyStocksForFlexiblePortfolio("AAPL,20,2022-11-12");
    pModel.selectPortfolioToUpdate(pName);
    String date = LocalDate.now().plusDays(30).toString();
    String sellMessage = pModel.sellStocksForFlexiblePortfolio("AAPL,2," + date);
    Assert.assertEquals("Error: Entered Date should be on or before the current date",
            sellMessage);
  }


  @Test
  public void TestDisplayGraph() {
    Random r = new Random();
    String pName = "PORTFOLIO1" + r.nextInt(30000);
    List<StockModel> stocks = new ArrayList<>();
    stocks.add(new StockModelImpl("AAPL", 1, 0,
            LocalDate.parse("2022-11-10"),
            0.0));
    FlexiblePortfolioModel pModel = new FlexiblePortfolioModelImpl(pName,
            LocalDate.parse("2022-11-10"), stocks);
    pModel.saveFlexiblePortfolioData(pModel);
    Map<String, Double> graph = pModel.displayGraph(pName,
            LocalDate.parse("2022-11-10"), LocalDate.parse("2022-11-16"));
    Assert.assertEquals(7, graph.size());
    LocalDate startDate = LocalDate.parse("2022-11-10");
    for (Map.Entry<String, Double> entry : graph.entrySet()) {
      Assert.assertEquals(startDate.toString(), entry.getKey());
      Assert.assertEquals(false, entry.getValue().isNaN());
      startDate = startDate.plusDays(1);
    }
  }
}
