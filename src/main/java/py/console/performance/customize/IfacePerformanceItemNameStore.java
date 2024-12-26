

package py.console.performance.customize;

import java.util.UUID;
import py.monitor.exception.AlreadyExistedException;
import py.monitor.exception.EmptyStoreException;
import py.monitor.exception.NotExistedException;

/**
 * IPerformanceItemNameStore.
 */
public interface IfacePerformanceItemNameStore {

  public void load() throws EmptyStoreException, Exception;

  public void commit() throws Exception;

  public void add(PerformanceItemName itemName) throws AlreadyExistedException, Exception;

  public PerformanceItemName getItemByCustomizedName(String customizedName)
      throws NotExistedException, Exception;

  public PerformanceItemName getItemByBeanName(String beanName)
      throws NotExistedException, Exception;

  public String getCustomizedNameByBeanName(String beanName) throws NotExistedException, Exception;

  public String getBeannameByCustomizedName(String customizedName)
      throws NotExistedException, Exception;

  public String getBeannameById(UUID id) throws NotExistedException, Exception;

  public UUID getIdByBeanName(String beanName) throws NotExistedException, Exception;
}
