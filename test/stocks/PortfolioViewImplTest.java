package stocks;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import stocks.model.StockModel;
import stocks.model.StockModelImpl;
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * Tests for PortfolioViewImpl class.
 */
public class PortfolioViewImplTest {

  private OutputStream outputStream;
  private PortfolioView pv;

  /**
   * Sets data for the PortfolioViewImplTest.
   */
  @Before
  public void setup() {
    outputStream = new ByteArrayOutputStream();
    pv = new PortfolioViewImpl(new PrintStream(outputStream));
  }

  @Test
  public void testDisplayMenu() {
    pv.displayMenu();
    String expected = "Please enter your choice: \n 1.Create Portfolio \n 2.Display Portfolio"
            + "\n 3.Display portfolio value at given date in the format YYYY-MM-DD"
            + "\n 4.Create flexible portfolio"
            + "\n 5.Add stock to a flexible portfolio at a desired date"
            + "\n 6.Sell stocks from a portfolio at a desired date"
            + "\n 7.Get cost basis of portfolio based on date"
            + "\n 8.Display Performance of Portfolio"
            + "\n 9.Modify the transaction/commission fees"
            + "\n 0.Exit\r\n";
    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testEnterPortfolioName() {
    pv.askPortfolioName();
    String expected = "Enter Portfolio Name: \r\n";
    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testDisplayStockList() {
    List<StockModel> stocks = new ArrayList<StockModel>();
    stocks.add(new StockModelImpl("APPLE", 0, 123, LocalDate.now(),0.0));
    pv.displayStockList(stocks);
    String expected = "Available Stocks and their respective prices are:- \r\n"
            + "APPLE, 123.0\r\n";
    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testEnterStocks() {
    pv.enterStocks();
    String expected = "Enter Stock ticker and number of shares in the following pattern: "
            + "stockTicker,NumberOfShares (e.g: MSFT,46)\r\n";
    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testDisplayExit() {
    pv.displayExit();
    String expected = "Do you want to exit - [Y/N] : \r\n";
    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testDisplayAddStocks() {
    pv.displayAddStocks();
    String expected = "Do you want to add another stock to the portfolio - [Y/N]:\r\n";
    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void displayTotalPortfolioValue() {
    HashMap<String, Double> result = new HashMap<>();
    result.put("Portfolio1", 4914.0);
    pv.displayTotalPortfolioValueByNameAndDate(result);
    String expected = "Value of each portfolio: \r\n"
            + "Portfolio1: 4914.0\r\n"
            + "Total Portfolio value: 4914.0\r\n";
    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void displayTotalPortfolioValueNull() {
    HashMap<String, Double> result = new HashMap<>();
    pv.displayTotalPortfolioValueByNameAndDate(result);
    String expected = "Cannot get the portfolio value as it is not created on"
            + " or before the given date.\r\n";
    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void displayTotalPortfolioValueError() {
    HashMap<String, Double> result = new HashMap<>();
    result.put("Error: Source stock data is not available at the given date", 0.0);
    pv.displayTotalPortfolioValueByNameAndDate(result);
    String expected = "Error: Source stock data is not available at the given date\r\n";
    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testDisplayToEnterDate() {
    pv.displayToEnterDate();
    String expected = "Please enter date in format YYYY-MM-DD\r\n";
    assertEquals(expected, outputStream.toString());

  }

  @Test
  public void testDisplayErrorWhileEnteringDate() {
    pv.displayErrorWhileEnteringDate();
    String expected = "Date is in incorrect format. Please try again\r\n";
    assertEquals(expected, outputStream.toString());

  }

  @Test
  public void testDisplayErrorSavingPortfolioName() {
    pv.displayErrorSavingPortfolioName();
    String expected = "Portfolio name should be unique. Please try again\r\n";
    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testDisplayOutput() {
    pv.displayOutput("Stocks added");
    String expected = "Stocks added\r\n";
    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testDisplayPortfolio() {
    HashMap<String, List<StockModel>> portfolio = new HashMap<String, List<StockModel>>();
    List<StockModel> stocks = new ArrayList<StockModel>();
    stocks.add(new StockModelImpl("APPLE", 1, 123, LocalDate.now(),0.0));
    portfolio.put("P1", stocks);
    pv.displayPortfolio(portfolio);
    String expected = "----------------------------------------------------------------\r\n"
            + "Portfolio name: P1\r\n"
            + "Stock name:- APPLE, No. of Shares:- 1\r\n"
            + "----------------------------------------------------------------\r\n";
    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testDisplayPortfolioNull() {
    pv.displayPortfolio(null);
    String expected = "----------------------------------------------------------------\r\n"
            + "No Portfolio exists, please create one to display\r\n"
            + "----------------------------------------------------------------\r\n";
    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testPortfolioCreatedSuccessfully() {
    pv.portfolioCreatedSuccessfully();
    String expected = "Portfolio created and saved successfully.\r\n";
    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testClearPortfolioSuccessfully() {
    pv.clearPortfolioSuccessfully();
    String expected = "Portfolio cleared successfully.\r\n";
    assertEquals(expected, outputStream.toString());

  }

}