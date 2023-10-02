package stocks.commands;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import stocks.model.DollarCostAverage;
import stocks.controller.PortfolioSimulatorCommand;
import stocks.view.PortfolioView;
import stocks.model.StockModel;
import stocks.utility.PortfolioUtility;

/**
 * class that handles CreateFlexiblePortfolio command.
 */

public class CreateFlexiblePortfolio implements PortfolioSimulatorCommand {

  @Override
  public void executeCommand(DollarCostAverage pModel, PortfolioView pView, Scanner input) {
    while (true) {
      if (PortfolioUtility.savePortfolioName(pModel, pView, input) == 1) {
        continue;
      }
      break;
    }
    pView.displayToEnterPortfolioDate();
    LocalDate date = PortfolioUtility.getPortfolioDateFromUser(pView, input);
    addAndSaveStocksToPortfolio(pModel, pView, input, date);
  }

  private void addAndSaveStocksToPortfolio(DollarCostAverage pModel,
                                           PortfolioView pView, Scanner input, LocalDate date) {
    boolean isFlexiblePortfolio = date != null;
    if (!isFlexiblePortfolio) {
      date = LocalDate.now();
    }
    List<StockModel> stocksList = pModel.displayAvailableStocks();
    pView.displayStockList(stocksList);
    String canAddStocks = "Y";
    while (canAddStocks.equals("Y") || canAddStocks.equals("y")) {
      pView.enterStocks();
      String stock = input.next();
      String finalStock = stock;
      if (stocksList.stream().noneMatch(s -> s.getStockName().equals(finalStock.toUpperCase()))) {
        pView.displayOutput("Error: Enter a stock ticker from the above list");
        continue;
      }
      pView.enterShareCount();
      String shares = input.next();
      shares = shares.split(Pattern.quote("."))[0];
      stock = stock + "," + shares + "," + date;
      String output = pModel.saveStocksToList(stock.toUpperCase(),false, null);
      if (output.equals("Success: Stocks added")) {
        pView.displayOutput(output);
        pView.displayAddStocks();
        canAddStocks = input.next();
      } else {
        pView.displayOutput(output);
        canAddStocks = "Y";
      }
    }
    if (isFlexiblePortfolio) {
      pModel.saveFlexiblePortfolioData(pModel);
    } else {
      pModel.saveData(pModel);
    }
    pView.portfolioCreatedSuccessfully();
  }

}
