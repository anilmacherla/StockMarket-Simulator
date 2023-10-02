package stocks.commands;

import java.time.LocalDate;
import java.util.Scanner;

import stocks.model.DollarCostAverage;
import stocks.controller.PortfolioSimulatorCommand;
import stocks.view.PortfolioView;
import stocks.utility.PortfolioUtility;

/**
 * class that handles CostBasis command.
 */
public class CostBasis implements PortfolioSimulatorCommand {
  @Override
  public void executeCommand(DollarCostAverage pModel, PortfolioView pView, Scanner input) {
    String portfolioName = PortfolioUtility.getExistingPortfolioNameFromUser(pModel, pView, input);
    pView.displayToEnterPortfolioDate();
    LocalDate formattedDate = PortfolioUtility.getPortfolioDateFromUser(pView, input);
    pModel.checkForStocksData(portfolioName.toUpperCase(),pModel);
    pView.displayCostBasisPortfolio(
            pModel.getCostBasisByPortfolioNameAndDate(portfolioName.toUpperCase(), formattedDate));
  }
}
