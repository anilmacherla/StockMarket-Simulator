package stocks.dataaccess;

/**
 * This class represents a factory of service type objects which could be instantiated at runtime
 * based on user's requirement.
 */
public class DataServiceFactory extends AbstractFactory {

  /**
   * Fetches the service type object based on user's requirement.
   *
   * @param serviceType Type of service required
   * @return service object based on service type
   */
  @Override
  public DataAccessService getDataAccessServiceObject(SourceDataTypeEnum serviceType) {
    if (serviceType == SourceDataTypeEnum.CSV) {
      return new CSVDataAccess();
    } else if (serviceType == SourceDataTypeEnum.ALPHAVANTAGE) {
      return new AlphaVantageDataAccess();
    }
    //Add more service types if we need to use other source types in future

    return new CSVDataAccess();
  }
}
