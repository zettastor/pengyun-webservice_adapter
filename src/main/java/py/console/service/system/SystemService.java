/**
* Copyright (C) 2013-2024 Nanjing Pengyun Network Technology Co., Ltd.
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
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
