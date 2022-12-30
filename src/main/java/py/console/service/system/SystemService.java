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

package py.console.service.system;

import java.util.List;
import org.apache.thrift.TException;
import py.console.bean.Capacity;
import py.console.bean.DashboardInfo;
import py.console.bean.NodeCounts;
import py.console.bean.RebalanceRule;
import py.console.bean.SimpleCapacityRecord;
import py.console.bean.SimpleStoragePool;
import py.console.bean.TotalIoPsAndThroughput;
import py.console.bean.VolumeCounts;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.PoolAlreadyAppliedRebalanceRuleExceptionThrift;
import py.thrift.share.RebalanceRuleExistingExceptionThrift;
import py.thrift.share.RebalanceRuleNotExistExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.StoragePoolNotExistedExceptionThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;

/**
 * SystemService.
 */
public interface SystemService {

  public Capacity getSystemCapacity(long accountId) throws Exception;

  public NodeCounts retrieveNodeCounts(long accountId) throws Exception;

  public VolumeCounts retrieveVolumeCounts(long accountId) throws Exception;

  public TotalIoPsAndThroughput retrieveTotalIoPsAndThroughput(long accountId) throws Exception;

  public SimpleCapacityRecord getCapacityRecord(long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public void startAutoRebalance()
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      TException;

  public void pauseAutoRebalance()
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      TException;

  public boolean rebalanceStarted()
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      TException;

  public DashboardInfo getDashboardInfo(long accountId)
      throws EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      ServiceIsNotAvailableThrift,
      AccountNotFoundExceptionThrift, ServiceHavingBeenShutdownThrift,
      PermissionNotGrantExceptionThrift,
      TException;

  public void addRebalanceRule(long accountId, RebalanceRule rule)
      throws RebalanceRuleExistingExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public void updateRebalanceRule(long accountId, RebalanceRule rule)
      throws RebalanceRuleNotExistExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public List<SimpleStoragePool> getAppliedRebalanceRulePool(long accountId, long ruleId)
      throws RebalanceRuleNotExistExceptionThrift, StoragePoolNotExistedExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public List<SimpleStoragePool> getUnAppliedRebalanceRulePool(long accountId)
      throws StoragePoolNotExistedExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public void applyRebalanceRule(long accountId, long ruleId, List<Long> poolIdList)
      throws StoragePoolNotExistedExceptionThrift, PoolAlreadyAppliedRebalanceRuleExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, RebalanceRuleNotExistExceptionThrift,
      TException;

  public void unApplyRebalanceRule(long accountId, long ruleId, List<Long> poolIdList)
      throws StoragePoolNotExistedExceptionThrift, GenericThriftClientFactoryException,
      RebalanceRuleNotExistExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public List<RebalanceRule> deleteRebalanceRule(long accountId, List<Long> ruleIdList)
      throws RebalanceRuleNotExistExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public List<RebalanceRule> listRebalanceRule(long accountId, List<Long> ruleIdList)
      throws EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

}
