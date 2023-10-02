package stocks.gui;

import java.util.List;
import java.util.Map;

import stocks.view.PortfolioView;

/**
 * This interface contains methods required for GUI of StockMarket simulator.
 */
public interface IView extends PortfolioView {
  /**
   * Sets up events for the features present in the display menu.
   *
   * @param features object with all features
   */
  void addFeatures(Features features);

  /**
   * Sets up events for the features in the Flexible Portfolio features window.
   *
   * @param features object with all features
   */
  void addFlexiblePortfolioFeatures(Features features);

  /**
   * Sets up events required in the Portfolio details window.
   *
   * @param features object with all features
   */
  void addPortfolioDetailsFeatures(Features features);

  /**
   * Sets up events for the features in the cost basis portfolio window.
   *
   * @param features object with all features
   */
  void addCostBasisFeatures(Features features);

  /**
   * Sets up events for the features in the sell stock window.
   *
   * @param features object with all features
   */
  void addSellStockFeatures(Features features);

  /**
   * Sets up events required for the features in the add stock window.
   *
   * @param features object with all features
   */
  void addBuyStockFeatures(Features features);

  /**
   * Sets up events required for the features in the dollar cost window.
   *
   * @param features object with all features
   */
  void addDollarCostFeatures(Features features);

  /**
   * Sets up events for the features in the weighted portfolio window.
   *
   * @param features object with all features
   */
  void addWeightedPortfolioFeatures(Features features);

  /**
   * Sets up UI for the portfolio details window.
   *
   * @param userPortfolioNamesList names of user portfolios
   */
  void setPortfolioDetailsPane(List<String> userPortfolioNamesList);

  /**
   * Sets up UI for the flexible portfolio window.
   *
   * @param listOfStocks list of stocks
   */
  void setFlexiblePortfolioPane(Map<String, Double> listOfStocks);

  /**
   * Sets up UI for the Cost basis portfolio window.
   *
   * @param userPortfolioNamesList names of portfolios of the user
   */
  void setCostBasisPortfolioPane(List<String> userPortfolioNamesList);

  /**
   * Sets up UI for the Menu and Exit buttons.
   *
   * @param features object with all features
   */
  void addMenuAndExitButtons(Features features);

  /**
   * Displays Error message in UI.
   *
   * @param message message to be displayed
   */
  void displayErrorMessage(String message);

  /**
   * Sets up UI for the Sell stocks window.
   *
   * @param userPortfolioNamesList names of user portfolios
   */
  void setSellStocksPane(List<String> userPortfolioNamesList);

  /**
   * Sets UI for the buy stocks window.
   *
   * @param userPortfolioNamesList names of user portfolios
   */
  void setBuyStocksPane(List<String> userPortfolioNamesList);

  /**
   * Clears the fields for Buy stock window.
   */
  void clearBuyStockInputs();

  /**
   * Sets up window for the weighted portfolio window.
   *
   * @param listOfWeightedStocks list of weighted stocks
   */
  void setWeightedPortfolioPane(Map<String, Double> listOfWeightedStocks);

  /**
   * Sets up window for the dollar cost strategy.
   *
   * @param listOfWeightedStocks list of weighted stocks
   */
  void setDollarCostPane(Map<String, Double> listOfWeightedStocks);

}
