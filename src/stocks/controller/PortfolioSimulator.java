package stocks.controller;

import stocks.model.DollarCostAverage;
import stocks.view.PortfolioView;

/**
 * This interface contains start-up method to begin the simulator.
 */
public interface PortfolioSimulator {
  /**
   * Starts simulation of Portfolio Simulator application.
   *
   * @param pm portfolio's model object
   * @param pv portfolio's view object
   */
  void startSimulation(DollarCostAverage pm, PortfolioView pv);
}
