package stocks.commands;

import java.time.LocalDate;
import java.util.Scanner;

import stocks.controller.PortfolioSimulatorCommand;
import stocks.model.DollarCostAverage;
import stocks.utility.PortfolioUtility;
import stocks.view.PortfolioView;

/**
 *  class that handles DisplayValue command.
 */
public class DisplayValue implements PortfolioSimulatorCommand {

  @Override
  public void executeCommand(DollarCostAverage pModel, PortfolioView pView, Scanner input) {
    String portfolioName = PortfolioUtility.getExistingPortfolioNameFromUser(pModel, pView, input);
    pModel.checkForStocksData(portfolioName.toUpperCase(),pModel);
    pView.displayToEnterDate();
    LocalDate desiredDate = PortfolioUtility.getPortfolioDateFromUser(pView, input);
    pView.displayTotalPortfolioValueByNameAndDate(pModel.displayPortfolioByDate(desiredDate,
            portfolioName));
  }
}
