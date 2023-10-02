package stocks;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import stocks.controller.PortfolioSimulator;
import stocks.controller.PortfolioSimulatorController;
import stocks.gui.JFrameView;
import stocks.gui.PortfolioUIController;
import stocks.model.DollarCostAverage;
import stocks.model.DollarCostAverageImpl;
import stocks.model.StockModel;
import stocks.model.StockModelImpl;
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

/**
 * The entry point of the program where we can simulate stock portfolios and manage them.
 */
public class Main {

  /**
   * Main method that starts the application.
   *
   * @param args accepts a single argument of type array
   */
  public static void main(String[] args) {
    PortfolioSimulator portfolio;
    DollarCostAverage pm;
    PortfolioView pv;
    Scanner sc = new Scanner(System.in);
    System.out.println("Press 1 for text based UI.\nPress 2 for GUI UI");
    int input = sc.nextInt();
    if (input == 1) {
      pm = new DollarCostAverageImpl();
      pv = new PortfolioViewImpl(System.out);
      portfolio = new PortfolioSimulatorController(System.in);

    } else {
      pm = new DollarCostAverageImpl();
      pv = new JFrameView("Portfolio Simulator");
      portfolio = new PortfolioUIController(pm, pv);
    }

    if (!pm.checkCurrentDayStockPresent()) {
      createStockData();
    }
    portfolio.startSimulation(pm, pv);
  }

  private static void createStockData() {
    try {
      FileWriter csvWriter = new FileWriter("./StockDataSource.csv", true);

      for (StockModel s : setStocks()) {
        csvWriter.append(s.getStockName());
        csvWriter.append(",");
        csvWriter.append(String.valueOf(s.getPrice()));
        csvWriter.append(",");
        csvWriter.append(s.getStockDate().toString());
        csvWriter.append("\n");
      }
      csvWriter.flush();
      csvWriter.close();
    } catch (IOException io) {
      System.out.println("IO Exception:- " + io.getMessage());
    }
  }

  private static List<StockModel> setStocks() {
    List<StockModel> stocks = new ArrayList<StockModel>();
    var stock = new String[]{"AAPL", "GOOGL", "TSLA", "META", "COST", "AMZN"
            , "MSFT", "UNH", "JNJ", "JPM"};
    int count = 0;
    Random rand = new Random();
    int upperBound = 300;
    while (count < 10) {
      stocks.add(new StockModelImpl(stock[count],
              0, rand.nextInt(upperBound), LocalDate.now(), 0.0));
      count++;
    }
    return stocks;
  }
}
