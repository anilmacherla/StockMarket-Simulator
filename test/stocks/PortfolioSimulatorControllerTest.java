package stocks;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stocks.controller.PortfolioSimulatorController;
import stocks.dataaccess.DataAccessService;
import stocks.model.DollarCostAverage;
import stocks.model.PortfolioModel;
import stocks.model.StockModel;
import stocks.model.StockModelImpl;
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the PortfolioSimulatorController.
 */
public class PortfolioSimulatorControllerTest {
  StringBuilder modelLog = new StringBuilder();


  StringBuilder viewLog = new StringBuilder();
  InputStream input = null;


  class MockModel implements DollarCostAverage {

    private final StringBuilder log;

    private final DataAccessService dataAccessServiceObject;


    public MockModel(StringBuilder log) {
      this.log = log;
      dataAccessServiceObject = null;
    }


    public void setPortfolioName() {
      return;
    }

    @Override
    public void saveFlexiblePortfolioData(PortfolioModel pModel) {
      return;
    }

    @Override
    public List<String> getFlexibleUserPortfolioList() {
      return null;
    }

    @Override
    public void setTransactionFees(Double fees) {
      return;
    }

    @Override
    public void selectPortfolioToUpdate(String flexiblePortfolioName) {
      return;
    }

    @Override
    public String buyStocksForFlexiblePortfolio(String stockDetails) {
      return null;
    }

    @Override
    public String sellStocksForFlexiblePortfolio(String stockDetails) {
      return null;
    }

    @Override
    public List<String> getUserPortfolioNamesList() {
      List<String> pNames = new ArrayList<>();
      pNames.add("p1".toUpperCase());
      return pNames;
    }

    @Override
    public Map<String, Double> getCostBasisByPortfolioNameAndDate(String name, LocalDate date) {
      return null;
    }

    @Override
    public Map<String, Double> displayGraph(String pName, LocalDate startDate, LocalDate endDate) {
      return null;
    }

    @Override
    public int addPortfolioName(String pName) {
      modelLog.append("In addPortfolioName and received pName:" + pName);
      return 1;
    }

    @Override
    public HashMap<String, List<StockModel>> displayPortfolioByName(String name) {
      return null;
    }

    @Override
    public String saveStocksToList(String stock, boolean dollarCost, LocalDate date) {
      return "Success: Stocks added";
    }

    @Override
    public void saveData(PortfolioModel pModel) {
      return;
    }

    @Override
    public void deletePortfolio() {
      return;
    }

    @Override
    public HashMap<String, Double> displayPortfolioByDate(LocalDate date, String portfolioName) {
      return null;
    }

    @Override
    public List<StockModel> displayAvailableStocks() {
      List<StockModel> stocks = new ArrayList<StockModel>();
      stocks.add(new StockModelImpl("AAPL", 0,
              123, LocalDate.now(), 0.0));
      return stocks;
    }

    @Override
    public boolean checkCurrentDayStockPresent() {
      return false;
    }

    @Override
    public void reset() {
      return;
    }

    @Override
    public List<StockModel> displayStoksForGiveDate(LocalDate date) {
      return null;
    }

    @Override
    public void saveDollarPortfolioData(DollarCostAverage pModel, int recurring, LocalDate endDate,
                                        Double investment) {
      return;
    }

    @Override
    public String saveRecurringStocks(String stockList, boolean dollarCost, LocalDate date) {
      return null;
    }

    @Override
    public List<StockModel> getPortfolioDataForRecurring(String name) {
      return null;
    }

    @Override
    public List<String> getEndDateAndRecurringTime(String name) {
      return null;
    }

    @Override
    public void checkForStocksData(String pName, DollarCostAverage pModel) {
      return;
    }

    @Override
    public String calculateSharesRecurring(String[] stocks, String[] weights,
                                           double money, LocalDate startDate,
                                           LocalDate endDate, int recurring,
                                           DollarCostAverage pModel) {
      return null;
    }

    @Override
    public String calculateShares(String[] stocks, String[] weights, double money,
                                  List<StockModel> stocksList, LocalDate date,
                                  DollarCostAverage pModel) {
      return null;
    }

  }

  class MockView implements PortfolioView {

    private final PrintStream log;

    public MockView(PrintStream log) {
      this.log = log;
    }

    @Override
    public void displayMenu() {
      //intentionally left empty
      return;
    }

    @Override
    public void askPortfolioName() {
      this.log.println("In to the askPortfolio Name Method");
    }

    @Override
    public void displayStockList(List<StockModel> stocksList) {
      return;
    }

    @Override
    public void enterStocks() {
      return;
    }

    @Override
    public void displayExit() {
      return;
    }

    @Override
    public void displayAddStocks() {
      return;
    }

    @Override
    public void displayTotalPortfolioValueByNameAndDate(HashMap<String,
            Double> displayPortfolioByDate) {
      return;
    }

    @Override
    public void displayToEnterDate() {
      return;
    }

    @Override
    public void displayErrorWhileEnteringDate() {
      return;
    }

    @Override
    public void displayErrorSavingPortfolioName() {
      return;
    }

    @Override
    public void displayOutput(String saveStocksToList) {
      this.log.println("Success: Stocks added");
    }

    @Override
    public void displayPortfolio(HashMap<String, List<StockModel>> displayPortfolio) {
      return;
    }

    @Override
    public void portfolioCreatedSuccessfully() {
      this.log.println("Portfolio created and saved successfully.");
    }

    @Override
    public void clearPortfolioSuccessfully() {
      return;
    }

    @Override
    public void displayToEnterPortfolioDate() {
      return;
    }

    @Override
    public void displayToAddStocksToExistingPortfolio() {
      return;
    }

    @Override
    public void displayFlexiblePortfolioList(List<String> flexibleUserPortfolioList) {
      return;
    }

    @Override
    public void displayErrorForInvalidFlexiblePortfolioName() {
      return;
    }

    @Override
    public void displayErrorIfNoFlexiblePortfolioExists() {
      return;
    }

    @Override
    public void displayToSellStocksFromFlexiblePortfolio() {
      return;
    }

    @Override
    public void displaySellStocks() {
      return;
    }

    @Override
    public void displayToEnterTransactionFees() {
      this.log.println("Enter commission fees:");
    }

    @Override
    public void displayErrorForTransactionFees() {
      viewLog.append("Transaction fees should be a positive number.");
      this.log.println("Transaction fees should be a positive number.");

    }

    @Override
    public void displayAllPortfolioNames(List<String> userPortfolioNamesList) {
      return;
    }

    @Override
    public void displayCostBasisPortfolio(Map<String, Double> costBasisByPortfolioNameAndDate) {
      return;
    }

    @Override
    public void enterPortfolioDurationStartDate() {
      return;
    }

    @Override
    public void enterPortfolioDurationEndDate() {
      return;
    }

    @Override
    public void displayGraph(Map<String, Double> portfolio, String portfolioName,
                             LocalDate startDate, LocalDate endDate) {
      return;
    }

    @Override
    public void displayErrorDateRangeLessThanFive() {
      return;
    }

    @Override
    public void displayErrorForFutureDate() {
      return;
    }

    @Override
    public void enterShareCount() {
      return;
    }

    @Override
    public void displayTransactionFeesSaved() {
      return;
    }

    @Override
    public void displayDateError() {
      return;
    }

    @Override
    public void askForInvestment() {
      return;
    }

    @Override
    public void enterListOfStockTickers() {
      return;
    }

    @Override
    public void askForWeightage() {
      return;
    }

    @Override
    public void enterRecurring() {
      return;
    }
  }

  @Test
  public void testPortfolioController1() {
    DollarCostAverage pm = new MockModel(modelLog);
    PortfolioView pv = new MockView(System.out);
    String inputString = "1\np1\nAAPL\n1\nN\n0";
    String expectedLog = "In addPortfolioName and received pName:P1";
    input = new ByteArrayInputStream(inputString.getBytes());
    PortfolioSimulatorController controller = new PortfolioSimulatorController(input);
    controller.startSimulation(pm, pv);
    pv.displayMenu();
    assertEquals(expectedLog, modelLog.toString());
  }

  @Test
  public void testPortfolioController() {
    DollarCostAverage pm = new MockModel(modelLog);
    PortfolioView pv = new MockView(System.out);
    String inputString = "9\n-1\n2\n0";
    String expectedLog = "In addPortfolioName and received pName:P1";
    input = new ByteArrayInputStream(inputString.getBytes());
    PortfolioSimulatorController controller = new PortfolioSimulatorController(input);
    controller.startSimulation(pm, pv);
    pv.displayMenu();
    assertEquals("Transaction fees should be a positive number.", viewLog.toString());
    assertEquals("", modelLog.toString());
  }

  @Test
  public void testPortfolioController2() {
    DollarCostAverage pm = new MockModel(modelLog);
    PortfolioView pv = new PortfolioViewImpl(System.out);
    String inputString = "2\np1\n0";
    String expectedLog = "In displayPortfolio Method";
    input = new ByteArrayInputStream(inputString.getBytes());
    PortfolioSimulatorController controller = new PortfolioSimulatorController(input);
    controller.startSimulation(pm, pv);
    assertEquals(expectedLog, modelLog.toString());
  }

  @Test
  public void testPortfolioController3() {
    DollarCostAverage pm = new MockModel(modelLog);
    PortfolioView pv = new PortfolioViewImpl(System.out);
    String inputString = "3\n" + LocalDate.now() + "\n0";
    String expectedLog = "In the displayPortfolioByDate Method, Received date: "
            + LocalDate.now();
    input = new ByteArrayInputStream(inputString.getBytes());
    PortfolioSimulatorController controller = new PortfolioSimulatorController(input);
    controller.startSimulation(pm, pv);
    assertEquals(expectedLog, modelLog.toString());
  }


  @Test
  public void testPortfolioController0() {
    DollarCostAverage pm = new MockModel(modelLog);
    PortfolioView pv = new PortfolioViewImpl(System.out);
    String inputString = "0";
    input = new ByteArrayInputStream(inputString.getBytes());
    PortfolioSimulatorController controller = new PortfolioSimulatorController(input);
    controller.startSimulation(pm, pv);
    assertEquals("", modelLog.toString());
  }

  @Test
  public void testPortfolioControllerWrongDate() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    DollarCostAverage pm = new MockModel(modelLog);
    PortfolioView pv = new PortfolioViewImpl(new PrintStream(outputStream));
    String inputString = "3\n10,234,\n0";
    String expectedLog = "Date is in incorrect format. Please try again\r";
    input = new ByteArrayInputStream(inputString.getBytes());
    PortfolioSimulatorController controller = new PortfolioSimulatorController(input);
    controller.startSimulation(pm, pv);
    List<String> actual = getOutStreamValues(outputStream.toString());
    assertEquals(expectedLog, actual.get(6));
  }

  @Test
  public void testWrongOption() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    DollarCostAverage pm = new MockModel(modelLog);
    PortfolioView pv = new PortfolioViewImpl(new PrintStream(outputStream));
    String inputString = "3.\n0";
    String expectedLog = "Please enter a valid integer\r";
    input = new ByteArrayInputStream(inputString.getBytes());
    PortfolioSimulatorController controller = new PortfolioSimulatorController(input);
    controller.startSimulation(pm, pv);
    List<String> actual = getOutStreamValues(outputStream.toString());
    assertEquals(expectedLog, actual.get(5));
  }

  @Test
  public void testWrongOptionGreaterThan4() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    DollarCostAverage pm = new MockModel(modelLog);
    PortfolioView pv = new PortfolioViewImpl(new PrintStream(outputStream));
    String inputString = "5\n0";
    String expectedLog = "Input should be between 0-3\r";
    input = new ByteArrayInputStream(inputString.getBytes());
    PortfolioSimulatorController controller = new PortfolioSimulatorController(input);
    controller.startSimulation(pm, pv);
    List<String> actual = getOutStreamValues(outputStream.toString());
    assertEquals(expectedLog, actual.get(5));
  }

  private List<String> getOutStreamValues(String outStream) {
    return Arrays.asList(outStream.split("\n", -1));
  }
}

