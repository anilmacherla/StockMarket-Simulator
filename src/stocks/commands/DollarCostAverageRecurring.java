package stocks.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import stocks.controller.PortfolioSimulatorCommand;
import stocks.model.DollarCostAverage;
import stocks.model.StockModel;
import stocks.utility.PortfolioUtility;
import stocks.view.PortfolioView;

/**
 * Class that handles DollarCostAverageRecurring command.
 */
public class DollarCostAverageRecurring extends WeightedStocks
        implements PortfolioSimulatorCommand {
  @Override
  public void executeCommand(DollarCostAverage pModel, PortfolioView pView, Scanner input) {
    LocalDate startDate = null;
    LocalDate endDate = null;
    while (true) {
      if (PortfolioUtility.savePortfolioName(pModel, pView, input) == 1) {
        continue;
      }
      break;
    }
    while (true) {
      pView.enterPortfolioDurationStartDate();
      startDate = PortfolioUtility.getPortfolioDateFromUser(pView, input);
      pView.enterPortfolioDurationEndDate();
      endDate = dateValid(pView, input);
      if (startDate.isAfter(endDate)) {
        pView.displayDateError();
        continue;
      }
      break;
    }
    pView.askForInvestment();
    investment = input.nextDouble();
    pView.enterRecurring();
    int recurring = input.nextInt();
    addWeightedStocksToPortfolio(pModel, pView, input, startDate, endDate, recurring, investment);
  }

  private void addWeightedStocksToPortfolio(DollarCostAverage pModel, PortfolioView pView,
                                            Scanner input, LocalDate startDate,
                                            LocalDate endDate, int recurring, double money) {
    boolean isRecurring = true;
    String output = "";
    boolean isFlexiblePortfolio = startDate != null;
    String stockValidation = null;
    if (!isFlexiblePortfolio) {
      startDate = LocalDate.now();
    }
    List<StockModel> stocksList = pModel.displayStoksForGiveDate(startDate);
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
      output = pModel.calculateSharesRecurring(stocks, weights, money,
              startDate, endDate, recurring, pModel);
      if (output.equals("Success: Stocks added")) {
        pView.displayOutput(output);
        pView.displayAddStocks();
        canAddStocks = input.next();
      } else {
        pView.displayOutput(output);
        canAddStocks = "Y";
      }
    }
    if (isRecurring) {
      pModel.saveDollarPortfolioData(pModel, recurring, endDate, investment);
    } else {
      pModel.saveData(pModel);
    }
    pView.portfolioCreatedSuccessfully();
  }


  private LocalDate dateValid(PortfolioView pView, Scanner input) {
    LocalDate formattedDate;
    while (true) {
      String date = input.next();
      try {
        formattedDate = LocalDate.parse(date,
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      } catch (DateTimeParseException e) {
        pView.displayErrorWhileEnteringDate();
        continue;
      }
      break;
    }
    return formattedDate;
  }
}
