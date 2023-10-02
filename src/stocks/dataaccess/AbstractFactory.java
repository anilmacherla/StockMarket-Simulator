package stocks.dataaccess;

/**
 * This abstracts takes serviceType as argument to get its respective service type (e.g CSV, Json)
 * for retrieving data.
 */
public abstract class AbstractFactory {
  /**
   * Returns the object of the service type to be used.
   *
   * @param serviceType Type of service required
   * @return object of required service
   */
  protected abstract DataAccessService getDataAccessServiceObject(SourceDataTypeEnum serviceType);
}
