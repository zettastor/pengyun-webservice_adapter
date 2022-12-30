/*
 * Copyright (c) 2022. PengYunNetWork
 *
 * This program is free software: you can use, redistribute, and/or modify it
 * under the terms of the GNU Affero General Public License, version 3 or later ("AGPL"),
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 *  You should have received a copy of the GNU Affero General Public License along with
 *  this program. If not, see <http://www.gnu.org/licenses/>.
 */

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
