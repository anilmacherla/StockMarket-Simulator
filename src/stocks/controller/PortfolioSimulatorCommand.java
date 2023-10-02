package stocks.controller;

import java.util.Scanner;

import stocks.model.DollarCostAverage;
import stocks.view.PortfolioView;

/**
 * Class that handles user commands.
 */
public interface PortfolioSimulatorCommand {
  /**
   * Method that executes commands from the user.
   * @param pModel DollarCostModel object
   * @param pView TextView object
   * @param input input from the user
   */
  void executeCommand(DollarCostAverage pModel,
                     PortfolioView pView, Scanner input);

}
