package stocks.commands;

import java.util.Scanner;

import stocks.controller.PortfolioSimulatorCommand;
import stocks.model.DollarCostAverage;
import stocks.utility.PortfolioUtility;
import stocks.view.PortfolioView;

/**
 * Class that handles SellStocks command.
 */
public class SellStocks implements PortfolioSimulatorCommand {
  @Override
  public void executeCommand(DollarCostAverage pModel, PortfolioView pView, Scanner input) {
    PortfolioUtility.selectFlexiblePortfolio(pView, pModel, input);
    String canSellStocks = "Y";
    while (canSellStocks.equals("Y") || canSellStocks.equals("y")) {
      pView.displayToSellStocksFromFlexiblePortfolio();
      String stockInput = input.next();
      String output = pModel.sellStocksForFlexiblePortfolio(stockInput);
      if (output.contains("Success")) {
        pView.displayOutput(output);
        pView.displaySellStocks();
        canSellStocks = input.next();
      } else {
        pView.displayOutput(output);
        canSellStocks = "Y";
      }
    }
  }
}
