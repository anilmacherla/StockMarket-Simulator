package stocks.commands;

import java.util.Scanner;

import stocks.model.DollarCostAverage;
import stocks.controller.PortfolioSimulatorCommand;
import stocks.view.PortfolioView;

/**
 * class that handles Commission command.
 */
public class Commission implements PortfolioSimulatorCommand {
  @Override
  public void executeCommand(DollarCostAverage pModel, PortfolioView pView, Scanner input) {
    while (true) {
      pView.displayToEnterTransactionFees();
      try {
        Double fees = Double.parseDouble(input.next());
        if (fees < 0) {
          pView.displayErrorForTransactionFees();
          continue;
        }
        pModel.setTransactionFees(fees);
        pView.displayTransactionFeesSaved();
      } catch (Exception e) {
        pView.displayErrorForTransactionFees();
      }
      break;
    }
  }
}

