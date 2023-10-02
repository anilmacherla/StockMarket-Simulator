package stocks.gui;

import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JOptionPane;


import stocks.model.StockModel;

/**
 * This class implements IView interface and extends JFrame.
 */
public class JFrameView extends JFrame implements IView {

  private JLabel display;
  private JButton exitButton;
  private JButton mainMenuButton;
  private JButton createFlexiblePortfolioButton;
  private JButton submitButton;
  private JButton buyStockButton;
  private JButton sellStockButton;
  private JButton costBasisButton;
  private JButton portfolioDetailsButton;
  private JButton calculateCostBasisButton;
  private JButton fetchPortfolioDetails;
  private JButton buyStockPaneButton;
  private JButton weightedPortfolioButton;
  private JButton dollarCostAvgButton;
  private JButton updateWeightedPortfolioButton;
  private JTextField portfolioNameField;
  private JTextField shareCountField;
  private JTextField enteredDateField;
  private JTextField commissionFeesField;
  private JTextField investmentAmountField;
  private JTextField weightCountField;
  private JTextField endDateField;
  private JTextField dayCountField;

  private JComboBox stockNameField;
  private JComboBox portfolioNameListField;

  public JFrameView(String caption) {
    super(caption);
    displayMenu();
  }

  @Override
  public void displayCostBasisPortfolio(Map<String, Double> costBasisByPortfolioNameAndDate) {
    String result = "";
    for (String key : costBasisByPortfolioNameAndDate.keySet()) {
      result += key + ": " + costBasisByPortfolioNameAndDate.get(key) + "\n";
    }
    //displayResult(result);
    this.getContentPane().removeAll();
    JPanel panel = new JPanel();
    JTextArea output = new JTextArea(result);
    mainMenuButton = new JButton("Main Menu");
    exitButton = new JButton("Exit");
    mainMenuButton.setBounds(200, 180, 100, 30);
    exitButton.setBounds(50, 180, 100, 30);
    panel.add(output);
    panel.add(mainMenuButton);
    panel.add(exitButton);

    this.getContentPane().add(panel);

    pack();
    setVisible(true);
  }


  @Override
  public void addFeatures(Features features) {
    exitButton.addActionListener(evt -> features.exitProgram());
    createFlexiblePortfolioButton.addActionListener(evt -> features.createFlexiblePortfolio());
    costBasisButton.addActionListener(e -> features.getCostBasis());
    portfolioDetailsButton.addActionListener(e -> features.portfolioDetails());
    buyStockButton.addActionListener(e -> features.buyStocksPane());
    sellStockButton.addActionListener(e -> features.sellStocksPane());
    weightedPortfolioButton.addActionListener(e -> features.weightedPortfolioPane());
    dollarCostAvgButton.addActionListener(e -> features.dollarCostPane());
    updateWeightedPortfolioButton.addActionListener(e -> features.weightedPortfolioPane());
  }

  @Override
  public void addFlexiblePortfolioFeatures(Features features) {
    buyStockButton.addActionListener(evt -> features
            .addStockToList(stockNameField.getSelectedItem().toString(),
                    shareCountField.getText()));
    submitButton.addActionListener(evt ->
            features.saveFlexiblePortfolio(portfolioNameField.getText(),
                    enteredDateField.getText(), commissionFeesField.getText()));
    addMenuAndExitButtons(features);
  }

  @Override
  public void addMenuAndExitButtons(Features features) {
    exitButton.addActionListener(evt -> features.exitProgram());
    mainMenuButton.addActionListener(evt -> features.mainMenu());
  }

  @Override
  public void addCostBasisFeatures(Features features) {
    exitButton.addActionListener(evt -> features.exitProgram());
    mainMenuButton.addActionListener(evt -> features.mainMenu());
    calculateCostBasisButton.addActionListener(evt ->
            features.calculateCostBasis(portfolioNameListField.getSelectedItem().toString(),
                    enteredDateField.getText()));
  }

  @Override
  public void setPortfolioDetailsPane(List<String> userPortfolioNamesList) {
    this.getContentPane().removeAll();
    this.setMinimumSize(new Dimension(700, 600));
    JPanel panel = new JPanel(null);
    panel.setBorder(BorderFactory.createTitledBorder("Get Portfolio Details on a specified date:"));

    JLabel portfolioNameLabel = new JLabel("Portfolio name");
    portfolioNameLabel.setBounds(50, 50, 100, 30);

    portfolioNameListField = new JComboBox(userPortfolioNamesList.toArray());
    portfolioNameListField.setBounds(150, 50, 400, 30);

    JLabel enterDateLabel = new JLabel("Enter date in YYYY-MM-DD");
    enterDateLabel.setBounds(50, 90, 100, 30);

    enteredDateField = new JTextField(30);
    enteredDateField.setBounds(150, 90, 400, 30);

    fetchPortfolioDetails = new JButton("Get Details");
    fetchPortfolioDetails.setBounds(400, 150, 150, 30);

    exitButton = new JButton("Exit Button");
    exitButton.setBounds(50, 190, 100, 30);

    mainMenuButton = new JButton("Main Menu");
    mainMenuButton.setBounds(200, 190, 120, 30);

    panel.add(portfolioNameLabel);
    panel.add(enterDateLabel);
    panel.add(portfolioNameListField);
    panel.add(enteredDateField);
    panel.add(fetchPortfolioDetails);
    panel.add(exitButton);
    panel.add(mainMenuButton);

    this.getContentPane().add(panel);
    pack();
    setVisible(true);
  }

  @Override
  public void setFlexiblePortfolioPane(Map<String, Double> listOfStocks) {
    String portfolioName = "";
    String date = "";
    if (listOfStocks.size() > 0) {
      portfolioName = portfolioNameField.getText();
      date = enteredDateField.getText();
    }
    this.getContentPane().removeAll();
    this.setMinimumSize(new Dimension(800, 600));

    setDefaultCloseOperation(EXIT_ON_CLOSE);

    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createTitledBorder("Create Flexible Portfolio"));
    panel.setLayout(null);

    JLabel portfolioNameLabel = new JLabel("Enter Portfolio name");
    portfolioNameLabel.setBounds(50, 50, 200, 30);

    portfolioNameField = new JTextField(30);
    portfolioNameField.setText(portfolioName);
    portfolioNameField.setBounds(300, 50, 200, 30);

    JLabel enterDateLabel = new JLabel("Enter date in YYYY-MM-DD format");
    enterDateLabel.setBounds(50, 90, 400, 30);

    enteredDateField = new JTextField(30);
    enteredDateField.setText(date);
    enteredDateField.setBounds(300, 90, 200, 30);

    JLabel stockNameLabel = new JLabel("Enter stock name");
    stockNameLabel.setBounds(50, 130, 200, 30);

    var stock = new String[]{"AAPL", "GOOGL", "TSLA", "META", "COST", "AMZN"
            , "MSFT", "UNH", "JNJ", "JPM"};
    stockNameField = new JComboBox(stock);
    stockNameField.setBounds(300, 130, 200, 30);

    JLabel shareCountLabel = new JLabel("Enter number of shares");
    shareCountLabel.setBounds(50, 170, 200, 30);

    shareCountField = new JTextField(30);
    shareCountField.setBounds(300, 170, 200, 30);

    JLabel commissionFeesLabel = new JLabel("Enter commission fees");
    commissionFeesLabel.setBounds(50, 210, 200, 30);

    commissionFeesField = new JTextField("0");
    commissionFeesField.setBounds(300, 210, 200, 30);

    if (listOfStocks.size() > 0) {
      String stocksList = "Stocks selected are : ";
      for (String stockName : listOfStocks.keySet()) {
        stocksList += stockName + "(" + listOfStocks.get(stockName) + ") ";
      }
      JTextArea stocksListTextArea = new JTextArea(stocksList);
      stocksListTextArea.setBackground(SystemColor.LIGHT_GRAY);
      stocksListTextArea.setEditable(false);
      stocksListTextArea.setBounds(50, 240, 600, 30);
      panel.add(stocksListTextArea);
    }

    buyStockButton = new JButton("Add Stock");
    buyStockButton.setBounds(350, 280, 100, 30);
    submitButton = new JButton("Create Portfolio");
    submitButton.setBounds(100, 320, 150, 30);

    exitButton = new JButton("Exit Button");
    exitButton.setBounds(300, 320, 100, 30);

    mainMenuButton = new JButton("Main Menu");
    mainMenuButton.setBounds(450, 320, 120, 30);

    panel.add(portfolioNameLabel);
    panel.add(portfolioNameField);
    panel.add(enterDateLabel);
    panel.add(enteredDateField);
    panel.add(stockNameLabel);
    panel.add(stockNameField);
    panel.add(shareCountLabel);
    panel.add(shareCountField);
    panel.add(submitButton);
    panel.add(exitButton);
    panel.add(mainMenuButton);
    panel.add(buyStockButton);
    panel.add(commissionFeesField);
    panel.add(commissionFeesLabel);

    this.getContentPane().add(panel);
    pack();
    setVisible(true);
  }


  @Override
  public void displayMenu() {
    this.getContentPane().removeAll();
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(14, 2, 2, 2));
    //panel.setLocation(500, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setMinimumSize(new Dimension(400, 600));

    display = new JLabel("Select options from below");
    panel.add(display);

    //Create FlexiblePortfolio
    createFlexiblePortfolioButton = new JButton("Create Flexible Portfolio");
    createFlexiblePortfolioButton.setActionCommand("Create Flexible Portfolio");
    panel.add(createFlexiblePortfolioButton);

    //BuyStocks
    buyStockButton = new JButton("Buy Stocks");
    buyStockButton.setActionCommand("Buy Stocks");
    panel.add(buyStockButton);

    //SellStocks
    sellStockButton = new JButton("Sell Stocks");
    sellStockButton.setActionCommand("Sell Stocks");
    panel.add(sellStockButton);

    //Cost Basis
    costBasisButton = new JButton("Cost Basis");
    costBasisButton.setActionCommand("Cost Basis");
    panel.add(costBasisButton);

    //Portfolio Details
    portfolioDetailsButton = new JButton("Portfolio Details");
    portfolioDetailsButton.setActionCommand("Portfolio Details");
    panel.add(portfolioDetailsButton);

    //Weighted portfolio
    weightedPortfolioButton = new JButton("Create Weighted Portfolio");
    weightedPortfolioButton.setActionCommand("Create Weighted Portfolio");
    panel.add(weightedPortfolioButton);

    dollarCostAvgButton = new JButton("Dollar cost Averaging");
    dollarCostAvgButton.setActionCommand("Dollar cost Averaging");
    panel.add(dollarCostAvgButton);

    updateWeightedPortfolioButton = new JButton("Update Weighted Portfolio");
    updateWeightedPortfolioButton.setActionCommand("Update Weighted Portfolio");
    panel.add(updateWeightedPortfolioButton);

    //Exit
    exitButton = new JButton("Exit");
    exitButton.setActionCommand("Exit");
    panel.add(exitButton);

    this.getContentPane().add(panel);
    pack();
    setVisible(true);
  }

  @Override
  public void askPortfolioName() {
    return;
  }

  @Override
  public void displayStockList(List<StockModel> stocksList) {
    return;

  }

  @Override
  public void enterStocks() {
    return;

  }

  @Override
  public void displayExit() {
    return;
  }

  @Override
  public void displayAddStocks() {
    return;
  }

  @Override
  public void displayTotalPortfolioValueByNameAndDate(HashMap<String,
          Double> displayPortfolioByDate) {
    return;
  }

  @Override
  public void displayToEnterDate() {
    return;
  }

  @Override
  public void displayErrorWhileEnteringDate() {
    return;
  }

  @Override
  public void displayErrorSavingPortfolioName() {
    return;
  }

  @Override
  public void setCostBasisPortfolioPane(List<String> userPortfolioNamesList) {
    this.getContentPane().removeAll();
    this.setMinimumSize(new Dimension(700, 600));
    JPanel panel = new JPanel(null);
    panel.setBorder(BorderFactory.createTitledBorder("Cost Basis of Portfolio"));

    JLabel portfolioNameLabel = new JLabel("Portfolio name");
    portfolioNameLabel.setBounds(50, 50, 200, 30);

    portfolioNameListField = new JComboBox(userPortfolioNamesList.toArray());
    portfolioNameListField.setBounds(300, 50, 200, 30);

    JLabel enterDateLabel = new JLabel("Enter date in YYYY-MM-DD");
    enterDateLabel.setBounds(50, 90, 200, 30);

    enteredDateField = new JTextField(30);
    enteredDateField.setBounds(300, 90, 200, 30);

    calculateCostBasisButton = new JButton("Calculate cost basis");
    calculateCostBasisButton.setBounds(350, 130, 200, 30);

    mainMenuButton = new JButton("Main Menu");
    mainMenuButton.setBounds(200, 180, 100, 30);
    exitButton.setBounds(50, 180, 100, 30);
    panel.add(portfolioNameLabel);
    panel.add(portfolioNameListField);
    panel.add(enterDateLabel);
    panel.add(enteredDateField);
    panel.add(calculateCostBasisButton);
    panel.add(exitButton);
    panel.add(mainMenuButton);
    this.getContentPane().add(panel);

    pack();
    setVisible(true);
  }

  /**
   * Displays result in UI.
   *
   * @param output result to be displayed
   */
  public void displayResult(String output) {
    this.getContentPane().removeAll();
    JPanel panel = new JPanel(null);
    JTextArea area = new JTextArea(output);
    area.setBounds(10, 30, 600, 300);
    mainMenuButton = new JButton("Main Menu");
    mainMenuButton.setActionCommand("Main Menu");
    mainMenuButton.setBounds(30, 350, 100, 30);
    exitButton = new JButton("Exit");
    exitButton.setActionCommand("Exit");
    exitButton.setBounds(150, 350, 100, 30);
    JScrollPane scroll = new JScrollPane(display);
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    panel.add(scroll);
    panel.add(area);
    panel.add(mainMenuButton);
    panel.add(exitButton);

    this.getContentPane().add(panel);
    pack();
    setVisible(true);
  }

  @Override
  public void addPortfolioDetailsFeatures(Features features) {
    buyStockButton.addActionListener(evt ->
            features.addStockToList(stockNameField.getSelectedItem().toString(),
                    shareCountField.getText()));
    fetchPortfolioDetails.addActionListener(evt ->
            features.getPortfolioDetailsByNameAndDate(
                    portfolioNameListField.getSelectedItem().toString(),
                    enteredDateField.getText()));
    addMenuAndExitButtons(features);
  }

  @Override
  public void addBuyStockFeatures(Features features) {
    buyStockPaneButton.addActionListener(e -> features.buyStock(
            portfolioNameListField.getSelectedItem().toString(),
            enteredDateField.getText(),
            stockNameField.getSelectedItem().toString(),
            shareCountField.getText(),
            commissionFeesField.getText()));
    addMenuAndExitButtons(features);
  }

  @Override
  public void clearBuyStockInputs() {
    //enteredDateField.setText("");
    shareCountField.setText("");
    commissionFeesField.setText("0");
  }

  @Override
  public void addSellStockFeatures(Features features) {
    addMenuAndExitButtons(features);
    buyStockPaneButton.addActionListener(e -> features.sellStock(
            portfolioNameListField.getSelectedItem().toString(),
            enteredDateField.getText(),
            stockNameField.getSelectedItem().toString(),
            shareCountField.getText(),
            commissionFeesField.getText()
    ));
  }

  @Override
  public void setWeightedPortfolioPane(Map<String, Double> listOfStocks) {
    this.getContentPane().removeAll();
    this.setMinimumSize(new Dimension(700, 600));

    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createTitledBorder("Create Weighted Portfolio"));
    panel.setLayout(null);

    JLabel portfolioNameLabel = new JLabel("Enter Portfolio name");
    portfolioNameLabel.setBounds(50, 50, 200, 30);

    portfolioNameField = new JTextField(30);
    portfolioNameField.setBounds(300, 50, 200, 30);

    JLabel enterDateLabel = new JLabel("Enter date in YYYY-MM-DD format");
    enterDateLabel.setBounds(50, 90, 400, 30);

    enteredDateField = new JTextField(30);
    enteredDateField.setBounds(300, 90, 200, 30);

    JLabel investmentAmountLabel = new JLabel("Enter investment amount");
    investmentAmountLabel.setBounds(50, 130, 200, 30);

    investmentAmountField = new JTextField();
    investmentAmountField.setBounds(300, 130, 200, 30);

    JLabel stockNameLabel = new JLabel("Enter stock name");
    stockNameLabel.setBounds(50, 170, 200, 30);

    var stock = new String[]{"AAPL", "GOOGL", "TSLA", "META", "COST", "AMZN"
            , "MSFT", "UNH", "JNJ", "JPM"};
    stockNameField = new JComboBox(stock);
    stockNameField.setBounds(300, 170, 200, 30);

    JLabel weightLabel = new JLabel("Enter weight");
    weightLabel.setBounds(50, 210, 200, 30);

    weightCountField = new JTextField(30);
    weightCountField.setBounds(300, 210, 200, 30);

    JLabel commissionFieldLabel = new JLabel("Commission Fees");
    commissionFieldLabel.setBounds(50, 240, 200, 30);
    commissionFeesField = new JTextField("0");
    commissionFeesField.setBounds(300, 240, 200, 30);
    buyStockButton = new JButton("Add Stock");
    buyStockButton.setBounds(350, 320, 100, 30);

    if (listOfStocks.size() > 0) {
      String stocksList = "Stocks selected are : ";
      for (String stockName : listOfStocks.keySet()) {
        stocksList += stockName + "(" + listOfStocks.get(stockName) + ") ";
      }
      JTextArea stocksListTextArea = new JTextArea(stocksList);
      stocksListTextArea.setBackground(SystemColor.LIGHT_GRAY);
      stocksListTextArea.setEditable(false);
      stocksListTextArea.setBounds(50, 280, 600, 30);
      panel.add(stocksListTextArea);
    }

    submitButton = new JButton("Create Portfolio");
    submitButton.setBounds(100, 360, 150, 30);

    exitButton = new JButton("Exit Button");
    exitButton.setBounds(300, 360, 100, 30);

    mainMenuButton = new JButton("Main Menu");
    mainMenuButton.setBounds(450, 360, 120, 30);
    panel.add(portfolioNameLabel);
    panel.add(portfolioNameField);
    panel.add(enterDateLabel);
    panel.add(enteredDateField);
    panel.add(investmentAmountLabel);
    panel.add(investmentAmountField);
    panel.add(stockNameLabel);
    panel.add(stockNameField);
    panel.add(weightLabel);
    panel.add(weightCountField);
    panel.add(buyStockButton);
    panel.add(submitButton);
    panel.add(exitButton);
    panel.add(mainMenuButton);
    panel.add(commissionFieldLabel);
    panel.add(commissionFeesField);
    this.getContentPane().add(panel);
    pack();
    setVisible(true);
  }

  @Override
  public void addWeightedPortfolioFeatures(Features features) {
    buyStockButton.addActionListener(evt ->
            features.addWeightedStockToList(stockNameField.getSelectedItem().toString(),
                    weightCountField.getText()));
    submitButton.addActionListener(evt ->
            features.createWeightedPortfolio(portfolioNameField.getText(),
                    enteredDateField.getText(), commissionFeesField.getText(),
                    investmentAmountField.getText()));
    addMenuAndExitButtons(features);
  }

  @Override
  public void setDollarCostPane(Map<String, Double> listOfStocks) {
    this.getContentPane().removeAll();
    this.setMinimumSize(new Dimension(700, 600));
    JPanel panel = new JPanel(null);
    panel.setBorder(BorderFactory.createTitledBorder("Dollar cost strategy"));

    JLabel portfolioNameLabel = new JLabel("Enter Portfolio name");
    portfolioNameLabel.setBounds(50, 50, 200, 30);

    portfolioNameField = new JTextField(30);
    portfolioNameField.setBounds(400, 50, 200, 30);

    JLabel enterDateLabel = new JLabel("Enter start date in YYYY-MM-DD format");
    enterDateLabel.setBounds(50, 90, 300, 30);

    enteredDateField = new JTextField(30);
    enteredDateField.setBounds(400, 90, 200, 30);

    JLabel enterEndDateLabel = new JLabel("Enter end date in YYYY-MM-DD format");
    enterEndDateLabel.setBounds(50, 130, 400, 30);

    endDateField = new JTextField(30);
    endDateField.setBounds(400, 130, 200, 30);

    JLabel investmentAmountLabel = new JLabel("Enter investment amount");
    investmentAmountLabel.setBounds(50, 170, 200, 30);

    investmentAmountField = new JTextField();
    investmentAmountField.setBounds(400, 170, 200, 30);

    JLabel dayCountLabel = new JLabel("Enter duration in days to auto invest");
    dayCountLabel.setBounds(50, 210, 300, 30);

    dayCountField = new JTextField();
    dayCountField.setBounds(400, 210, 200, 30);

    JLabel stockNameLabel = new JLabel("Enter stock name");
    stockNameLabel.setBounds(50, 250, 200, 30);

    var stock = new String[]{"AAPL", "GOOGL", "TSLA", "META", "COST", "AMZN"
            , "MSFT", "UNH", "JNJ", "JPM"};
    stockNameField = new JComboBox(stock);
    stockNameField.setBounds(400, 250, 200, 30);

    JLabel weightLabel = new JLabel("Enter weight");
    weightLabel.setBounds(50, 290, 200, 30);

    weightCountField = new JTextField(30);
    weightCountField.setBounds(400, 290, 200, 30);

    buyStockButton = new JButton("Add Stock");
    buyStockButton.setBounds(500, 380, 100, 30);

    if (listOfStocks.size() > 0) {
      String stocksList = "Stocks selected are : ";
      for (String stockName : listOfStocks.keySet()) {
        stocksList += stockName + "(" + listOfStocks.get(stockName) + ") ";
      }
      JTextArea stocksListTextArea = new JTextArea(stocksList);
      stocksListTextArea.setBackground(SystemColor.LIGHT_GRAY);
      stocksListTextArea.setEditable(false);
      stocksListTextArea.setBounds(50, 330, 600, 30);
      panel.add(stocksListTextArea);
    }
    submitButton = new JButton("Create Strategy");
    submitButton.setBounds(100, 420, 150, 30);

    exitButton = new JButton("Exit Button");
    exitButton.setBounds(300, 420, 100, 30);

    mainMenuButton = new JButton("Main Menu");
    mainMenuButton.setBounds(450, 420, 120, 30);
    panel.add(portfolioNameLabel);
    panel.add(portfolioNameField);
    panel.add(enterDateLabel);
    panel.add(enteredDateField);
    panel.add(investmentAmountLabel);
    panel.add(investmentAmountField);
    panel.add(stockNameLabel);
    panel.add(stockNameField);
    panel.add(weightLabel);
    panel.add(weightCountField);
    panel.add(buyStockButton);
    panel.add(submitButton);
    panel.add(exitButton);
    panel.add(mainMenuButton);
    panel.add(endDateField);
    panel.add(enterDateLabel);
    panel.add(dayCountLabel);
    panel.add(dayCountField);
    panel.add(enterEndDateLabel);
    this.getContentPane().add(panel);
    pack();
    setVisible(true);
  }

  @Override
  public void addDollarCostFeatures(Features features) {
    buyStockButton.addActionListener(evt ->
            features.addWeightedStocksToDollarCost(stockNameField.getSelectedItem().toString(),
                    weightCountField.getText()));
    submitButton.addActionListener(evt ->
            features.createDollarCostStrategy(portfolioNameField.getText(),
                    enteredDateField.getText(), endDateField.getText(),
                    investmentAmountField.getText(), dayCountField.getText()));
    addMenuAndExitButtons(features);
  }

  @Override
  public void displayErrorMessage(String message) {
    JOptionPane.showMessageDialog(null, message,
            "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void displayOutput(String message) {
    JOptionPane.showMessageDialog(null,
            message);
  }

  @Override
  public void displayPortfolio(HashMap<String, List<StockModel>> displayPortfolio) {
    return;
  }

  @Override
  public void portfolioCreatedSuccessfully() {
    return;
  }

  @Override
  public void clearPortfolioSuccessfully() {
    return;
  }

  @Override
  public void displayToEnterPortfolioDate() {
    return;
  }

  @Override
  public void displayToAddStocksToExistingPortfolio() {
    return;
  }

  @Override
  public void displayFlexiblePortfolioList(List<String> flexibleUserPortfolioList) {
    return;
  }

  @Override
  public void displayErrorForInvalidFlexiblePortfolioName() {
    return;
  }

  @Override
  public void displayErrorIfNoFlexiblePortfolioExists() {
    return;
  }

  @Override
  public void displayToSellStocksFromFlexiblePortfolio() {
    return;
  }

  @Override
  public void displaySellStocks() {
    return;
  }

  @Override
  public void displayToEnterTransactionFees() {
    return;
  }

  @Override
  public void displayErrorForTransactionFees() {
    return;
  }

  @Override
  public void displayAllPortfolioNames(List<String> userPortfolioNamesList) {
    return;
  }

  @Override
  public void enterPortfolioDurationStartDate() {
    return;
  }

  @Override
  public void enterPortfolioDurationEndDate() {
    return;
  }

  @Override
  public void displayGraph(Map<String, Double> portfolio, String portfolioName,
                           LocalDate startDate, LocalDate endDate) {
    return;
  }

  @Override
  public void displayErrorDateRangeLessThanFive() {
    return;
  }

  @Override
  public void displayErrorForFutureDate() {
    return;
  }

  @Override
  public void enterShareCount() {
    return;
  }

  @Override
  public void displayTransactionFeesSaved() {
    return;
  }

  @Override
  public void displayDateError() {
    return;
  }

  @Override
  public void askForInvestment() {
    return;
  }

  @Override
  public void enterListOfStockTickers() {
    return;
  }

  @Override
  public void askForWeightage() {
    return;
  }

  @Override
  public void enterRecurring() {
    return;
  }

  @Override
  public void setSellStocksPane(List<String> userPortfolioNamesList) {
    this.getContentPane().removeAll();
    this.setMinimumSize(new Dimension(700, 600));
    JPanel panel = new JPanel(null);
    panel.setBorder(BorderFactory.createTitledBorder("Sell stock from flexible portfolio"));
    panel = setLayoutForBuyOrSell(panel, userPortfolioNamesList, "Sell Stock");
    this.getContentPane().add(panel);
    pack();
    setVisible(true);
  }

  @Override
  public void setBuyStocksPane(List<String> userPortfolioNamesList) {
    this.getContentPane().removeAll();
    this.setMinimumSize(new Dimension(700, 600));
    JPanel panel = new JPanel(null);
    panel.setBorder(BorderFactory.createTitledBorder("Add stock to flexible portfolio"));
    panel = setLayoutForBuyOrSell(panel, userPortfolioNamesList, "Buy Stock");
    this.getContentPane().add(panel);
    pack();
    setVisible(true);
  }

  private JPanel setLayoutForBuyOrSell(JPanel panel, List<String> userPortfolioNamesList,
                                       String label) {

    JLabel portfolioNameLabel = new JLabel("Portfolio name");
    portfolioNameLabel.setBounds(50, 50, 100, 30);

    portfolioNameListField = new JComboBox(userPortfolioNamesList.toArray());
    portfolioNameListField.setBounds(300, 50, 200, 30);

    JLabel enterDateLabel = new JLabel("Enter date in YYYY-MM-DD");
    enterDateLabel.setBounds(50, 90, 400, 30);

    enteredDateField = new JTextField(30);
    enteredDateField.setBounds(300, 90, 200, 30);

    JLabel stockNameLabel = new JLabel("Enter stock name");
    stockNameLabel.setBounds(50, 130, 200, 30);

    var stock = new String[]{"AAPL", "GOOGL", "TSLA", "META", "COST", "AMZN"
            , "MSFT", "UNH", "JNJ", "JPM"};
    stockNameField = new JComboBox<String>(stock);
    stockNameField.setBounds(300, 130, 200, 30);

    JLabel shareCountLabel = new JLabel("Enter number of shares");
    shareCountLabel.setBounds(50, 170, 200, 30);

    shareCountField = new JTextField(30);
    shareCountField.setBounds(300, 170, 200, 30);
    JLabel commissionFeesLabel = new JLabel("Commission fees");
    commissionFeesLabel.setBounds(50, 210, 200, 30);
    commissionFeesField = new JTextField("0");
    commissionFeesField.setBounds(300, 210, 200, 30);

    panel = addMainMenuAndExitButtons(panel);
    exitButton.setBounds(50, 250, 100, 30);
    buyStockPaneButton = new JButton(label);
    buyStockPaneButton.setBounds(350, 250, 100, 30);
    mainMenuButton.setBounds(200, 250, 100, 30);
    panel.add(portfolioNameLabel);
    panel.add(portfolioNameListField);
    panel.add(buyStockPaneButton);
    panel.add(enterDateLabel);
    panel.add(enteredDateField);
    panel.add(stockNameLabel);
    panel.add(stockNameField);
    panel.add(shareCountLabel);
    panel.add(shareCountField);
    panel.add(commissionFeesField);
    panel.add(commissionFeesLabel);
    return panel;
  }

  private JPanel addMainMenuAndExitButtons(JPanel panel) {
    mainMenuButton = new JButton("Main Menu");
    mainMenuButton.setActionCommand("Main Menu");
    exitButton = new JButton("Exit");
    exitButton.setActionCommand("Exit");
    panel.add(mainMenuButton);
    panel.add(exitButton);
    return panel;
  }


}
