package stocks.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import stocks.controller.PortfolioSimulatorCommand;
import stocks.model.DollarCostAverage;
import stocks.model.StockModel;
import stocks.utility.PortfolioUtility;
import stocks.view.PortfolioView;

/**
 * Class that handles weighted stocks command.
 */
public class WeightedStocks implements PortfolioSimulatorCommand {

  protected double investment = 0.0;

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
    pView.askForInvestment();
    investment = input.nextDouble();
    addWeightedStocksToPortfolio(pModel, pView, input, date, investment);
  }

  private void addWeightedStocksToPortfolio(DollarCostAverage pModel, PortfolioView pView,
                                            Scanner input, LocalDate date, double money) {
    List<String> weightedStocksShares = new ArrayList<>();
    String output = "";
    boolean isFlexiblePortfolio = date != null;
    String stockValidation = null;
    if (!isFlexiblePortfolio) {
      date = LocalDate.now();
    }
    List<StockModel> stocksList = pModel.displayStoksForGiveDate(date);
    pView.displayStockList(stocksList);
    String canAddStocks = "Y";
    while (canAddStocks.equals("Y") || canAddStocks.equals("y")) {
      pView.enterListOfStockTickers();
      String weightedStock = input.next();
      stockValidation = validateStocks(weightedStock, stocksList);
      String[] stocks = weightedStock.split(",");
      if (stockValidation != null) {
        pView.displayOutput("Error: Enter a stock ticker from the above list");
        continue;
      }
      pView.askForWeightage();
      String portions = input.next();
      String[] weights = portions.split(",");
      String error = validateWeights(weights, stocks);
      if (!(error.isEmpty())) {
        pView.displayOutput(error);
        continue;
      }
      output = pModel.calculateShares(stocks, weights, money, stocksList,
              date, pModel);
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


  protected String validateWeights(String[] weights, String[] stocks) {
    int sum = 0;
    String error = "";
    if (weights.length != stocks.length) {
      error = "All stocks should be given weights";
    }
    for (int i = 0; i < weights.length; i++) {
      sum += Integer.parseInt(weights[i]);
    }
    if (sum < 100 || sum > 100) {
      error = " The weights sum must be equal to 100";
    }
    return error;
  }

  protected String validateStocks(String weightedStock, List<StockModel> stocksList) {
    String[] stocks = weightedStock.split(",");
    for (String stock : stocks) {
      String finalStock = stock;
      if (stocksList.stream().noneMatch(s -> s.getStockName().equals(finalStock.toUpperCase()))) {
        return "";
      }
    }
    return null;
  }

}
