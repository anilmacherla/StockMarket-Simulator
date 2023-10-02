package stocks.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import stocks.model.FlexiblePortfolioModel;
import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

/**
 * This is a helper class for portfolio.
 */
public class PortfolioUtility {
  /**
   * Saves portfolio name.
   *
   * @param pModel Model object
   * @param pView  View object
   * @param input  name to be saved
   * @return Returns 1 if saved successfully. Else 0
   */
  public static int savePortfolioName(PortfolioModel pModel,
                                      PortfolioView pView, Scanner input) {
    String pName = getPortfolioNameFromUser(pView, input);
    if (pModel.addPortfolioName(pName.toUpperCase()) == 0) {
      pView.displayErrorSavingPortfolioName();
      return 1;
    }
    return 0;
  }

  /**
   * Gets portfolio name from user.
   *
   * @param pView View object
   * @param input user input
   * @return
   */
  public static String getPortfolioNameFromUser(PortfolioView pView, Scanner input) {
    pView.askPortfolioName();
    return input.next();
  }

  /**
   * Gets existing portfolio name from user.
   *
   * @param pModel model object
   * @param pView  view object
   * @param input  user input
   * @return
   */
  public static String getExistingPortfolioNameFromUser(FlexiblePortfolioModel pModel,
                                                        PortfolioView pView, Scanner input) {
    List<String> portfolioNamesList = pModel.getUserPortfolioNamesList();
    String portfolioName;
    while (true) {
      pView.displayAllPortfolioNames(portfolioNamesList);
      pView.askPortfolioName();
      portfolioName = input.next();
      if (!portfolioNamesList.contains(portfolioName.toUpperCase())) {
        pView.displayErrorForInvalidFlexiblePortfolioName();
        continue;
      }
      break;
    }
    return portfolioName.toUpperCase();
  }

  /**
   * Gets portfolio date from user.
   *
   * @param pView View object
   * @param input user's input
   * @return
   */
  public static LocalDate getPortfolioDateFromUser(PortfolioView pView, Scanner input) {
    LocalDate formattedDate;
    while (true) {
      String date = input.next();
      try {
        formattedDate = LocalDate.parse(date,
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (formattedDate.isAfter(LocalDate.now())) {
          pView.displayErrorForFutureDate();
          continue;
        }
      } catch (DateTimeParseException e) {
        pView.displayErrorWhileEnteringDate();
        continue;
      }
      break;
    }
    return formattedDate;
  }

  /**
   * Gets flexible portfolio.
   *
   * @param pView  view object
   * @param pModel model object
   * @param input  user input
   */
  public static void selectFlexiblePortfolio(PortfolioView pView, FlexiblePortfolioModel pModel,
                                             Scanner input) {
    String flexiblePortfolioName;
    List<String> flexiblePortfolioNameList = pModel.getFlexibleUserPortfolioList();
    if (flexiblePortfolioNameList == null || flexiblePortfolioNameList.size() == 0) {
      pView.displayErrorIfNoFlexiblePortfolioExists();
    }
    pView.displayFlexiblePortfolioList(pModel.getFlexibleUserPortfolioList());
    while (true) {
      pView.askPortfolioName();
      flexiblePortfolioName = input.next();
      if (!flexiblePortfolioNameList.contains(flexiblePortfolioName.toUpperCase())) {
        pView.displayErrorForInvalidFlexiblePortfolioName();
        continue;
      }
      pModel.selectPortfolioToUpdate(flexiblePortfolioName.toUpperCase());
      break;
    }
    pView.displayPortfolio(pModel.displayPortfolioByName(flexiblePortfolioName));
    pView.displayStockList(pModel.displayAvailableStocks());
  }


}
