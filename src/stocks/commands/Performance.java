package stocks.commands;

import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

import stocks.controller.PortfolioSimulatorCommand;
import stocks.model.DollarCostAverage;
import stocks.utility.PortfolioUtility;
import stocks.view.PortfolioView;

/**
 * Class that handles  performance command.
 */
public class Performance implements PortfolioSimulatorCommand {
  @Override
  public void executeCommand(DollarCostAverage pModel, PortfolioView pView, Scanner input) {
    String portfolioName = PortfolioUtility.getExistingPortfolioNameFromUser(pModel, pView, input);
    while (true) {
      pView.enterPortfolioDurationStartDate();
      LocalDate startDate = PortfolioUtility.getPortfolioDateFromUser(pView, input);
      pView.enterPortfolioDurationEndDate();
      LocalDate endDate = PortfolioUtility.getPortfolioDateFromUser(pView, input);
      if (startDate.isAfter(endDate)) {
        pView.displayDateError();
      }
      Period duration = Period.between(startDate, endDate);
      if (duration.getDays() < 5 && duration.getMonths() == 0 && duration.getYears() == 0) {
        pView.displayErrorDateRangeLessThanFive();
        continue;
      }
      pModel.checkForStocksData(portfolioName.toUpperCase(),pModel);
      pView.displayGraph(pModel.displayGraph(portfolioName.toUpperCase(),
              startDate, endDate), portfolioName, startDate, endDate);
      break;
    }
  }
}
