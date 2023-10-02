package stocks.dataaccess;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import stocks.model.StockModel;
import stocks.model.StockModelImpl;

/**
 * This class implements methods/operations required to access data from AlphaVantage API.
 */
public class AlphaVantageDataAccess implements DataAccessService {

  @Override
  public List<StockModel> getStockDataByDate(LocalDate date) {
    var stocks = new String[]{"AAPL", "GOOGL", "TSLA", "META", "COST", "AMZN"
            , "MSFT", "UNH", "JNJ", "JPM"};

    List<StockModel> stocksList = new ArrayList<>();
    for (String stock : stocks) {
      Double stockPrice = getStockValueByDate(stock, date);
      stocksList.add(new StockModelImpl(stock, 0, stockPrice, date, 0.0));
    }
    return stocksList;
  }

  /**
   * Checks if current day stock is present in the local storage.
   *
   * @return true if data exists
   */
  @Override
  public boolean checkCurrentDayStockPresent() {
    return true;
  }

  /**
   * Returns Stock price for the date specified.
   *
   * @param ticker Stock ticker for which price is required.
   * @param date   date on which price to be fetched
   * @return stock price for the date specified
   */
  @Override
  public Double getStockValueByDate(String ticker, LocalDate date) {
    double stockPrice = 0;
    String apiKey = "E36KK81HAVZLQERV";
    URL url;

    DataAccessService data = new CSVDataAccess();
    Double price = data.getStockValueByDate(ticker, date);
    if (price != null) {
      return price;
    }

    try {
      url = new URL("https://www.alphavantage.co/query?function="
              + "TIME_SERIES_DAILY&outputsize=full&symbol"
              + "=" + ticker + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + "ticker");
    }
    String[] result = output.toString().split("\n");
    double recentStockValue = Double.parseDouble(result[1].split(",")[4]);

    for (int i = 1; i < result.length; i++) {
      String[] str1 = result[i].split(",");
      if (date.isEqual(LocalDate.parse(str1[0], DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
        stockPrice = Double.parseDouble(str1[4]);
        break;
      }
    }
    stockPrice = stockPrice == 0 ? recentStockValue : stockPrice;
    saveToCSV(ticker, date, stockPrice);

    return stockPrice;
  }

  private void saveToCSV(String ticker, LocalDate date, double price) {
    try {
      FileWriter csvWriter = new FileWriter("./StockDataSource.csv", true);
      csvWriter.append(ticker);
      csvWriter.append(",");
      csvWriter.append(String.valueOf(price));
      csvWriter.append(",");
      csvWriter.append(date.toString());
      csvWriter.append("\n");
      csvWriter.flush();
      csvWriter.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}


