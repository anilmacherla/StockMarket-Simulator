package stocks.dataaccess;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import stocks.model.StockModel;
import stocks.model.StockModelImpl;

/**
 * This class implements methods/operations required to access data from CSV file.
 */

public class CSVDataAccess implements DataAccessService {

  private final String stockDataSourceFilePath = "./StockDataSource.csv";

  @Override
  public List<StockModel> getStockDataByDate(LocalDate date) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(stockDataSourceFilePath));
      String line = br.readLine();
      List<StockModel> stockModelList = new ArrayList<>();
      while ((line = br.readLine()) != null) {
        String[] attributes = line.split(",");
        stockModelList.add(new StockModelImpl(attributes[0], 0,
                Double.parseDouble(attributes[1]),
                parseDate(attributes[2]), 0.0));
      }
      return stockModelList.stream()
              .filter(s -> s.getStockDate().equals(date))
              .collect(Collectors.toList());

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public boolean checkCurrentDayStockPresent() {
    try {
      BufferedReader br = new BufferedReader(new FileReader(stockDataSourceFilePath));
      String line = br.readLine();
      while ((line = br.readLine()) != null) {
        String[] attributes = line.split(",");
        if (LocalDate.now().toString().equals(attributes[2])) {
          return true;
        }
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return false;
  }

  @Override
  public Double getStockValueByDate(String ticker, LocalDate date) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(stockDataSourceFilePath));
      String line = br.readLine();
      while ((line = br.readLine()) != null) {
        String[] attributes = line.split(",");
        if (date.equals(attributes[2]) && ticker.equals(attributes[0])) {
          return Double.parseDouble(attributes[1]);
        }
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  LocalDate parseDate(String date) {
    try {
      return LocalDate.parse(date);
    } catch (Exception e) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
      return LocalDate.parse(date, formatter);
    }
  }
}
