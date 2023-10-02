package stocks.commands;

import java.util.Scanner;

import stocks.model.DollarCostAverage;
import stocks.controller.PortfolioSimulatorCommand;
import stocks.view.PortfolioView;
import stocks.utility.PortfolioUtility;

/**
 * class that handles AddStocksToFlexiblePortfolio command.
 */
public class AddStocksToFlexiblePortfolio implements PortfolioSimulatorCommand {
  @Override
  public void executeCommand(DollarCostAverage pModel, PortfolioView pView, Scanner input) {
    PortfolioUtility.selectFlexiblePortfolio(pView, pModel, input);
    String canAddStocks = "Y";
    while (canAddStocks.equals("Y") || canAddStocks.equals("y")) {
      pView.displayToAddStocksToExistingPortfolio();
      String stockInput = input.next();
      String output = pModel.buyStocksForFlexiblePortfolio(stockInput.toUpperCase());
      if (output.contains("Success")) {
        pView.displayOutput(output);
        pView.displayAddStocks();
        canAddStocks = input.next();
      } else {
        pView.displayOutput(output);
        canAddStocks = "Y";
      }
    }
  }
}
