package stocks;

import org.junit.Test;

import stocks.model.StockModel;
import stocks.model.StockModelImpl;

import static org.junit.Assert.assertEquals;

/**
 * Tests for  StockModelImpl class.
 */
public class StockModelImplTest {

  @Test
  public void testSetStockCount() {
    StockModel stockModel = new StockModelImpl();
    assertEquals(3, stockModel.setStockCount(3).getShareCount());
  }

}