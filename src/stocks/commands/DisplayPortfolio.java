package stocks.commands;

import java.util.Scanner;

import stocks.controller.PortfolioSimulatorCommand;
import stocks.model.DollarCostAverage;
import stocks.utility.PortfolioUtility;
import stocks.view.PortfolioView;

/**
 * class that handles DisplayPortfolio command.
 */
public class DisplayPortfolio implements PortfolioSimulatorCommand {

  @Override
  public void executeCommand(DollarCostAverage pModel, PortfolioView pView, Scanner input) {
    String pName = PortfolioUtility.getExistingPortfolioNameFromUser(pModel, pView, input);
    pModel.checkForStocksData(pName.toUpperCase(),pModel);
    pView.displayPortfolio(pModel.displayPortfolioByName(pName));
  }

}
