package stocks.controller;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

import stocks.commands.DollarCostAverageRecurring;
import stocks.commands.Performance;
import stocks.commands.WeightedStocks;
import stocks.model.DollarCostAverage;
import stocks.view.PortfolioView;
import stocks.commands.AddStocksToFlexiblePortfolio;
import stocks.commands.Commission;
import stocks.commands.CostBasis;
import stocks.commands.CreateFlexiblePortfolio;
import stocks.commands.DisplayPortfolio;
import stocks.commands.DisplayValue;
import stocks.commands.SellStocks;
import stocks.commands.CreatePortfolio;


/**
 * This class implements PortfolioSimulator.
 */
public class PortfolioSimulatorController implements PortfolioSimulator {
  final InputStream in;

  /**
   * This method constructs PortfolioSimulatorController object.
   *
   * @param in input of type InputStream
   */
  public PortfolioSimulatorController(InputStream in) {
    this.in = in;
  }

  @Override
  public void startSimulation(DollarCostAverage pModel, PortfolioView pView) {
    PortfolioSimulatorCommand cmd = null;
    Scanner input = new Scanner(this.in);
    boolean canExit = false;
    while (!canExit) {
      pModel.reset();
      pView.displayMenu();
      String response = getResponseFromUser(input);

      if (response.length() > 2) {
        pView.displayOutput(response);
        continue;
      }
      switch (response) {
        case "1":
          cmd = new CreatePortfolio();
          cmd.executeCommand(pModel, pView, input);
          break;
        case "2":
          cmd = new DisplayPortfolio();
          cmd.executeCommand(pModel, pView, input);
          break;
        case "3":
          cmd = new DisplayValue();
          cmd.executeCommand(pModel, pView, input);
          break;
        case "4":
          cmd = new CreateFlexiblePortfolio();
          cmd.executeCommand(pModel, pView, input);
          break;
        case "5":
          cmd = new AddStocksToFlexiblePortfolio();
          cmd.executeCommand(pModel, pView, input);
          break;
        case "6":
          cmd = new SellStocks();
          cmd.executeCommand(pModel, pView, input);
          break;
        case "7":
          cmd = new CostBasis();
          cmd.executeCommand(pModel, pView, input);
          break;
        case "8":
          cmd = new Performance();
          cmd.executeCommand(pModel, pView, input);
          break;
        case "9":
          cmd = new Commission();
          cmd.executeCommand(pModel, pView, input);
          break;
        case "10":
          cmd = new WeightedStocks();
          cmd.executeCommand(pModel, pView, input);
          break;
        case "11":
          cmd = new DollarCostAverageRecurring();
          cmd.executeCommand(pModel, pView, input);
          break;
        case "0":
          canExit = true;
          break;
        default:
          //do nothing
      }
    }
  }

  private String getResponseFromUser(Scanner input) {
    String response = input.next();
    Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    if (response.length() > 2 || !pattern.matcher(response).matches()) {
      return "Please enter a valid integer";
    } else {
      int inputDigit = Integer.parseInt(response);
      if (inputDigit < 0 || inputDigit > 11) {
        return "Input should be between 0-10";
      }
    }
    return response;
  }

}
