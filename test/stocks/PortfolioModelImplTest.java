package stocks;


import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import stocks.model.PortfolioModel;
import stocks.model.PortfolioModelImpl;
import stocks.model.StockModel;
import stocks.model.StockModelImpl;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the PortfolioModelImpl implementation class.
 */
public class PortfolioModelImplTest {

  private PortfolioModel pm;

  /**
   * Initializes the PortfolioModel object.
   */
  @Before
  public void setup() {
    pm = new PortfolioModelImpl();
  }

  @Test
  public void testAddPortfolioNamePortfolioExists() {
    Random r = new Random();
    String pName = "PORTFOLIO1" + r.nextInt(30000);
    List<StockModel> stocks = new ArrayList<>();
    stocks.add(new StockModelImpl("APPLE", 1, 0, LocalDate.now(),0.0));
    PortfolioModel portfolioModel = new PortfolioModelImpl(pName, LocalDate.now(), stocks);
    portfolioModel.saveData(portfolioModel);
    int actual = portfolioModel.addPortfolioName(pName);
    assertEquals(0, actual);
  }

  @Test
  public void testDisplayPortfolio() {
    Random r = new Random();
    String pName = "PORTFOLIO1" + r.nextInt(30000);
    List<StockModel> stocks = new ArrayList<>();
    stocks.add(new StockModelImpl("APPLE", 1, 0, LocalDate.now(),0.0));
    PortfolioModel portfolioModel = new PortfolioModelImpl(pName, LocalDate.now(), stocks);
    portfolioModel.saveData(portfolioModel);
    assertEquals(true, portfolioModel.displayPortfolioByName(pName).containsKey(pName));
  }


  @Test
  public void testSaveStocksToListEmpty() {
    String stockList = "";
    String expected = "Error: Ticker cannot be empty";
    assertEquals(expected, pm.saveStocksToList(stockList,false, null));
  }

  @Test
  public void testSaveStocksToListWrong() {
    String stockList = "asdfvbvcx,23";
    String expected = "Error: Please enter a valid ticker";
    assertEquals(expected, pm.saveStocksToList(stockList,false,null));
  }


  @Test
  public void testDisplayPortfolioByDateAfterDate() {
    Random r = new Random();
    String pName = "PORTFOLIO1" + r.nextInt(30000);
    List<StockModel> stocks = new ArrayList<StockModel>();
    stocks.add(new StockModelImpl("APPLE", 1, 0, LocalDate.now(),0.0));
    PortfolioModel portfolioModel = new PortfolioModelImpl(pName, LocalDate.now(), stocks);
    portfolioModel.saveData(portfolioModel);
    assertEquals(false, portfolioModel.displayPortfolioByDate(LocalDate.now()
            .plusDays(1),pName).containsKey(pName));
  }

}