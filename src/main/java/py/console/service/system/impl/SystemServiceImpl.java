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

package py.console.service.system.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.RequestResponseHelper;
import py.common.Constants;
import py.common.PyService;
import py.common.RequestIdBuilder;
import py.console.bean.Capacity;
import py.console.bean.DashboardInfo;
import py.console.bean.NodeCounts;
import py.console.bean.Performance;
import py.console.bean.RebalanceAbsoluteTime;
import py.console.bean.RebalanceRule;
import py.console.bean.SimpleCapacityRecord;
import py.console.bean.SimpleDriverMetadata;
import py.console.bean.SimpleInstance;
import py.console.bean.SimpleStoragePool;
import py.console.bean.SimpleVolumeAccessRule;
import py.console.bean.SimpleVolumeMetadata;
import py.console.bean.TotalIoPsAndThroughput;
import py.console.bean.VolumeCounts;
import py.console.service.access.rule.VolumeAccessRuleService;
import py.console.service.instance.InstanceService;
import py.console.service.performance.PerformanceService;
import py.console.service.system.SystemService;
import py.console.service.volume.VolumeService;
import py.console.service.volume.impl.VolumeServiceImpl;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.icshare.TotalAndUsedCapacity;
import py.infocenter.client.InformationCenterClientFactory;
import py.instance.InstanceStatus;
import py.thrift.icshare.GetDashboardInfoRequest;
import py.thrift.icshare.GetDashboardInfoResponse;
import py.thrift.infocenter.service.InformationCenter;
import py.thrift.share.AbsoluteTimethrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.AddRebalanceRuleRequest;
import py.thrift.share.ApplyRebalanceRuleRequest;
import py.thrift.share.DeleteRebalanceRuleRequest;
import py.thrift.share.DeleteRebalanceRuleResponse;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.GetAppliedRebalanceRulePoolRequest;
import py.thrift.share.GetAppliedRebalanceRulePoolResponse;
import py.thrift.share.GetCapacityRecordRequestThrift;
import py.thrift.share.GetCapacityRecordResponseThrift;
import py.thrift.share.GetCapacityRequest;
import py.thrift.share.GetCapacityResponse;
import py.thrift.share.GetRebalanceRuleRequest;
import py.thrift.share.GetRebalanceRuleResponse;
import py.thrift.share.GetUnAppliedRebalanceRulePoolRequest;
import py.thrift.share.GetUnAppliedRebalanceRulePoolResponse;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.PoolAlreadyAppliedRebalanceRuleExceptionThrift;
import py.thrift.share.RebalanceRuleExistingExceptionThrift;
import py.thrift.share.RebalanceRuleNotExistExceptionThrift;
import py.thrift.share.RebalanceRulethrift;
import py.thrift.share.RelativeTimethrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.StorageEmptyExceptionThrift;
import py.thrift.share.StoragePoolNotExistedExceptionThrift;
import py.thrift.share.StoragePoolThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;
import py.thrift.share.TotalAndUsedCapacityThrift;
import py.thrift.share.UnApplyRebalanceRuleRequest;
import py.thrift.share.UpdateRebalanceRuleRequest;

/**
 * SystemServiceImpl.
 */
public class SystemServiceImpl implements SystemService {

  private static final Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);

  private InformationCenterClientFactory infoCenterClientFactory;

  private VolumeService volumeService;

  private PerformanceService performanceService;

  private VolumeAccessRuleService volumeAccessRuleService;

  private InstanceService instanceService;

  public InformationCenterClientFactory getInfoCenterClientFactory() {
    return infoCenterClientFactory;
  }

  public void setInfoCenterClientFactory(InformationCenterClientFactory infoCenterClientFactory) {
    this.infoCenterClientFactory = infoCenterClientFactory;
  }

  public VolumeService getVolumeService() {
    return volumeService;
  }

  public void setVolumeService(VolumeServiceImpl volumeService) {
    this.volumeService = volumeService;
  }

  public PerformanceService getPerformanceService() {
    return performanceService;
  }

  public void setPerformanceService(PerformanceService performanceService) {
    this.performanceService = performanceService;
  }

  public InstanceService getInstanceService() {
    return instanceService;
  }

  public void setInstanceService(InstanceService instanceService) {
    this.instanceService = instanceService;
  }

  public VolumeAccessRuleService getVolumeAccessRuleService() {
    return volumeAccessRuleService;
  }

  public void setVolumeAccessRuleService(VolumeAccessRuleService volumeAccessRuleService) {
    this.volumeAccessRuleService = volumeAccessRuleService;
  }

  /**
   * get the node number in the system.
   */
  @Override
  public NodeCounts retrieveNodeCounts(long accountId) throws Exception {
    try {
      NodeCounts nodeCounts = new NodeCounts();
      List<SimpleInstance> instanceList = instanceService.getAll(Constants.SUPERADMIN_ACCOUNT_ID);
      int countOk = 0;
      int countInc = 0;
      int countFailed = 0;
      for (SimpleInstance simpleInstance : instanceList) {
        if (simpleInstance.getInstanceName().equals(PyService.DATANODE.getServiceName())) {
          if (simpleInstance.getStatus().equals(InstanceStatus.HEALTHY.name())) {
            countOk++;
          } else if (simpleInstance.getStatus().equals(InstanceStatus.SICK.name())) {
            countInc++;
          } else {
            countFailed++;
          }
        }
      }
      nodeCounts.setFailedCounts(countFailed);
      nodeCounts.setIncCounts(countInc);
      nodeCounts.setOkCounts(countOk);
      return nodeCounts;
    } catch (Exception e) {
      logger.error("Exception catch", e);
      throw e;
    }
  }

  /**
   * get the volume number in the system.
   */
  @Override
  public VolumeCounts retrieveVolumeCounts(long accountId) throws Exception {
    try {
      VolumeCounts volumeCounts = new VolumeCounts();
      List<SimpleVolumeMetadata> volumeList = volumeService.getMultipleVolumes(accountId, null);
      Set<String> totalClientList = new HashSet<String>();
      int countOk = 0;
      int countDegree = 0;
      int countUnavailable = 0;
      int clientNums = 0;
      for (SimpleVolumeMetadata simpleVolumeMetadata : volumeList) {
        if (simpleVolumeMetadata.getVolumeStatus().equals("Unavailable")) {
          countUnavailable++;
        } else {
          countOk++;
        }
        SimpleVolumeMetadata realVolume = volumeService
            .getVolumeById(Long.parseLong(simpleVolumeMetadata.getVolumeId()), accountId, true);
        // int healthIndex = performanceService.checkHealthStatus(realVolume);
        // if (healthIndex == 100)
        //    countOk++;
        // else if (healthIndex == 0)
        //    countUnavailable++;
        // else
        //    countDegree++;
        if (realVolume.getDriverMetadatas() != null) {
          for (SimpleDriverMetadata simpleDriverMetadata : realVolume.getDriverMetadatas()) {
            if (simpleDriverMetadata.getDriverClientInfoList() != null) {
              clientNums += simpleDriverMetadata.getDriverClientInfoList().size();
            }
          }
        }
      }
      volumeCounts.setDegreeCounts(countDegree);
      volumeCounts.setOkCounts(countOk);
      volumeCounts.setUnavailableCounts(countUnavailable);
      volumeCounts.setConnectedClients(clientNums);
      List<SimpleVolumeAccessRule> volumeAccessRuleList = volumeAccessRuleService
          .listVolumeAccessRules(accountId);
      for (SimpleVolumeAccessRule simpleVolumeAccessRule : volumeAccessRuleList) {
        totalClientList.add(simpleVolumeAccessRule.getRemoteHostName());
      }
      volumeCounts.setTotalClients(totalClientList.size());
      return volumeCounts;
    } catch (Exception e) {
      logger.error("Exception catch", e);
      throw e;
    }
  }

  /**
   * get the total IOPS and Throughput in the system.
   */
  @Override
  public TotalIoPsAndThroughput retrieveTotalIoPsAndThroughput(long accountId) throws Exception {
    try {
      TotalIoPsAndThroughput tit = new TotalIoPsAndThroughput();
      List<Performance> performanceList = performanceService.getAll(accountId);
      long readIoPs = 0;
      long writeIoPs = 0;
      long readThroughput = 0;
      long writeThroughput = 0;
      for (Performance performance : performanceList) {
        readIoPs += performance.getReadIoPs();
        writeIoPs += performance.getWriteIoPs();
        readThroughput += performance.getReadThroughput();
        writeThroughput += performance.getWriteThroughput();
      }
      tit.setReadIoPs(readIoPs);
      tit.setWriteIoPs(writeIoPs);
      tit.setReadThroughput(readThroughput);
      tit.setWriteThroughput(writeThroughput);
      return tit;
    } catch (Exception e) {
      logger.error("Exception catch", e);
      throw e;
    }
  }

  @Override
  public Capacity getSystemCapacity(long accountId) throws Exception {
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      GetCapacityRequest request = new GetCapacityRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      GetCapacityResponse response = client.getCapacity(request);
      Capacity capacity = null;
      if (response != null) {
        capacity = new Capacity();
        if (response.getCapacity() < 0 || response.getLogicalCapacity() < 0
            || response.getFreeSpace() < 0
            || response.getCapacity() < response.getFreeSpace()) {
          logger.warn("system capacity is strange: totalSpace:{} freeSpace:{} availableSpace:{}",
              response.getCapacity(), response.getFreeSpace(), response.getLogicalCapacity());
        }
        // TODO: there is not perfect solution for address the negative used space
        if (response.getCapacity() < response.getFreeSpace()) {
          capacity.setTotalCapacity(String.valueOf(response.getFreeSpace()));
        } else {
          capacity.setTotalCapacity(String.valueOf(response.getCapacity()));
        }

        capacity.setAvailableCapacity(String.valueOf(response.getLogicalCapacity()));
        capacity.setFreeSpace(String.valueOf(response.getFreeSpace()));
      }
      return capacity;
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (StorageEmptyExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }
  }

  @Override
  public SimpleCapacityRecord getCapacityRecord(long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      GetCapacityRecordRequestThrift request = new GetCapacityRecordRequestThrift();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      GetCapacityRecordResponseThrift response = client.getCapacityRecord(request);
      SimpleCapacityRecord simpleCapacityRecord = new SimpleCapacityRecord();
      TreeMap<String, TotalAndUsedCapacity> capacityRecordMap = new TreeMap<String,
          TotalAndUsedCapacity>();
      for (Entry<String, TotalAndUsedCapacityThrift> entry : response.getCapacityRecord()
          .getCapacityRecordMap()
          .entrySet()) {
        TotalAndUsedCapacity capacityInfo = RequestResponseHelper
            .buildTotalAndUsedCapacityFrom(entry.getValue());
        capacityRecordMap.put(entry.getKey(), capacityInfo);
      }
      simpleCapacityRecord.setCapacityRecord(capacityRecordMap);
      return simpleCapacityRecord;
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }
  }

  @Override
  public void startAutoRebalance()
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      TException {
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.startAutoRebalance();
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }

  }

  @Override
  public void pauseAutoRebalance()
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      TException {
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.pauseAutoRebalance();
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }

  }

  @Override
  public boolean rebalanceStarted()
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      TException {
    boolean response;
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      response = client.rebalanceStarted();
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }
    return response;
  }

  @Override
  public DashboardInfo getDashboardInfo(long accountId)
      throws EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      ServiceIsNotAvailableThrift,
      AccountNotFoundExceptionThrift, ServiceHavingBeenShutdownThrift,
      PermissionNotGrantExceptionThrift,
      TException {
    DashboardInfo dashboardInfo = new DashboardInfo();
    GetDashboardInfoRequest request = new GetDashboardInfoRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    GetDashboardInfoResponse response = new GetDashboardInfoResponse();

    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      response = client.getDashboardInfo(request);
      logger.debug("GetDashboardInfoResponse response is {}", response);
      List<String> errorMessage = new ArrayList<>();
      if (response != null) {
        if (response.getCapacity() != null) {
          dashboardInfo.setTotalCapacity(response.getCapacity().getTotalCapacity());
          dashboardInfo.setAvailableCapacity(response.getCapacity().getAvailableCapacity());
          dashboardInfo.setUsedCapacity(response.getCapacity().getUsedCapacity());
          dashboardInfo.setFreeSpace(response.getCapacity().getFreeSpace());
          dashboardInfo.setAvailableCapacityPer(response.getCapacity().getAvailableCapacityPer());
          dashboardInfo.setUsedCapacityPer(response.getCapacity().getUsedCapacityPer());
          dashboardInfo.setTheunUsedUniPerStr(response.getCapacity().getTheunUsedUniPerStr());
          dashboardInfo.setTheunUsedUnitSpace(response.getCapacity().getTheunUsedUnitSpace());
          dashboardInfo.setTheUsedUniPerStr(response.getCapacity().getTheUsedUniPerStr());
          dashboardInfo.setTheUsedUnitSpace(response.getCapacity().getTheUsedUnitSpace());
          if (!StringUtils.isEmpty(response.getCapacity().getMessage())) {
            errorMessage.add("capacity:" + response.getCapacity().getMessage());
          }
        }
        if (response.getVolumeCounts() != null) {
          dashboardInfo.setOkCounts(String.valueOf(response.getVolumeCounts().getOkCounts()));
          dashboardInfo.setDegreeCounts(
              String.valueOf(response.getVolumeCounts().getDegreeCounts()));
          dashboardInfo
              .setUnavailableCounts(
                  String.valueOf(response.getVolumeCounts().getUnavailableCounts()));
          dashboardInfo.setConnectedClients(
              String.valueOf(response.getVolumeCounts().getConnectedClients()));
          if (!StringUtils.isEmpty(response.getVolumeCounts().getMessage())) {
            errorMessage.add("volume:" + response.getVolumeCounts().getMessage());
          }
        }
        if (response.getInstanceStatusStatistics() != null) {
          dashboardInfo.setServiceHealthy(
              String.valueOf(response.getInstanceStatusStatistics().getServiceHealthy()));
          dashboardInfo.setServiceSick(
              String.valueOf(response.getInstanceStatusStatistics().getServiceSick()));
          dashboardInfo.setServiceFailed(
              String.valueOf(response.getInstanceStatusStatistics().getServiceFailed()));
          dashboardInfo
              .setServiceTotal(
                  String.valueOf(response.getInstanceStatusStatistics().getServiceTotal()));
          if (!StringUtils.isEmpty(response.getInstanceStatusStatistics().getMessage())) {
            errorMessage.add("service:" + response.getInstanceStatusStatistics().getMessage());
          }
        }
        if (response.getClientTotal() != null) {
          dashboardInfo.setClientTotal(String.valueOf(response.getClientTotal().getClientTotal()));
          if (!StringUtils.isEmpty(response.getClientTotal().getMessage())) {
            errorMessage.add("client:" + response.getClientTotal().getMessage());
          }
        }

        if (response.getPoolStatistics() != null) {
          dashboardInfo.setPoolHigh(String.valueOf(response.getPoolStatistics().getPoolHigh()));
          dashboardInfo.setPoolMiddle(String.valueOf(response.getPoolStatistics().getPoolMiddle()));
          dashboardInfo.setPoolLow(String.valueOf(response.getPoolStatistics().getPoolLow()));
          dashboardInfo.setPoolTotal(String.valueOf(response.getPoolStatistics().getPoolTotal()));
          if (!StringUtils.isEmpty(response.getPoolStatistics().getMessage())) {
            errorMessage.add("pool:" + response.getPoolStatistics().getMessage());
          }
        }
        if (response.getDiskStatistics() != null) {
          dashboardInfo.setGoodDiskCount(
              String.valueOf(response.getDiskStatistics().getGoodDiskCount()));
          dashboardInfo.setBadDiskCount(
              String.valueOf(response.getDiskStatistics().getBadDiskCount()));
          dashboardInfo.setAllDiskCount(
              String.valueOf(response.getDiskStatistics().getAllDiskCount()));
          if (!StringUtils.isEmpty(response.getDiskStatistics().getMessage())) {
            errorMessage.add("disk:" + response.getDiskStatistics().getMessage());
          }
        }
        if (response.getServerNodeStatistics() != null) {
          dashboardInfo.setOkServerNodeCounts(
              String.valueOf(response.getServerNodeStatistics().getOkServerNodeCounts()));
          dashboardInfo.setUnknownServerNodeCount(
              String.valueOf(response.getServerNodeStatistics().getUnknownServerNodeCount()));
          dashboardInfo.setTotalServerNodeCount(
              String.valueOf(response.getServerNodeStatistics().getTotalServerNodeCount()));
          if (!StringUtils.isEmpty(response.getServerNodeStatistics().getMessage())) {
            errorMessage.add("server:" + response.getServerNodeStatistics().getMessage());
          }

        }
        dashboardInfo.setErrorMessage(errorMessage);
        logger.debug("dashboardInfo is {}", dashboardInfo);
      }
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }
    return dashboardInfo;
  }

  /**
   * delete rebalance rule.
   *
   * @param accountId account id
   * @param ruleIdList rule id list
   * @return rebalance rule list
   * @throws RebalanceRuleNotExistExceptionThrift RebalanceRuleNotExistExceptionThrift
   * @throws EndPointNotFoundExceptionThrift EndPointNotFoundExceptionThrift
   * @throws TooManyEndPointFoundExceptionThrift TooManyEndPointFoundExceptionThrift
   * @throws NetworkErrorExceptionThrift NetworkErrorExceptionThrift
   * @throws TException TException
   */
  public List<RebalanceRule> deleteRebalanceRule(long accountId, List<Long> ruleIdList)
      throws RebalanceRuleNotExistExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {

    List<RebalanceRule> failRules = new ArrayList<>();
    DeleteRebalanceRuleRequest request = new DeleteRebalanceRuleRequest();
    DeleteRebalanceRuleResponse response = new DeleteRebalanceRuleResponse();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setRuleIdList(ruleIdList);
    logger.debug("deleteRebalanceRule request is {}", request);
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      response = client.deleteRebalanceRule(request);
      logger.debug("deleteRebalanceRule response is {}", response);
      if (response.getFailedRuleIdList() != null) {
        for (RebalanceRulethrift rulethrift : response.getFailedRuleIdList()) {
          RebalanceRule rule = new RebalanceRule();
          rule.setRuleId(String.valueOf(rulethrift.getRuleId()));
          rule.setRuleName(rulethrift.getRuleName());
          rule.setWaitTime(String.valueOf(rulethrift.getRelativeTime().getWaitTime()));

          List<RebalanceAbsoluteTime> absoluteTimeList = new ArrayList<>();
          for (AbsoluteTimethrift absoluteTimeThrift : rulethrift.getAbsoluteTimeList()) {
            RebalanceAbsoluteTime absoluteTime = new RebalanceAbsoluteTime();
            absoluteTime.setBeginTime(String.valueOf(absoluteTimeThrift.getBeginTime()));
            absoluteTime.setEndTime(String.valueOf(absoluteTimeThrift.getEndTime()));

            absoluteTimeList.add(absoluteTime);
          }
          rule.setAbsoluteTimeList(absoluteTimeList);

          failRules.add(rule);
        }
      }
      logger.debug("failRules is {}", failRules);
    } catch (RebalanceRuleNotExistExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }

    return failRules;
  }

  @Override
  public List<RebalanceRule> listRebalanceRule(long accountId, List<Long> ruleIdList)
      throws EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    GetRebalanceRuleRequest request = new GetRebalanceRuleRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setRuleIdList(ruleIdList);
    GetRebalanceRuleResponse response = new GetRebalanceRuleResponse();
    logger.debug("GetRebalanceRuleRequest is {}", request);
    List<RebalanceRule> rebalanceRules = new ArrayList<>();
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      response = client.getRebalanceRule(request);
      logger.debug("GetRebalanceRuleResponse is {}", request);
      if (response.getRules() != null) {
        for (RebalanceRulethrift ruleThrift : response.getRules()) {
          RebalanceRule rule = new RebalanceRule();
          rule.setRuleId(String.valueOf(ruleThrift.getRuleId()));
          rule.setRuleName(ruleThrift.getRuleName());
          rule.setWaitTime(String.valueOf(ruleThrift.getRelativeTime().getWaitTime()));

          List<RebalanceAbsoluteTime> absoluteTimeList = new ArrayList<>();
          for (AbsoluteTimethrift absoluteTimeThrift : ruleThrift.getAbsoluteTimeList()) {
            RebalanceAbsoluteTime absoluteTime = new RebalanceAbsoluteTime();
            absoluteTime.setBeginTime(String.valueOf(absoluteTimeThrift.getBeginTime()));
            absoluteTime.setEndTime(String.valueOf(absoluteTimeThrift.getEndTime()));

            absoluteTimeList.add(absoluteTime);
          }
          rule.setAbsoluteTimeList(absoluteTimeList);
          rebalanceRules.add(rule);
        }
      }
      logger.debug("rebalanceRules is {}", rebalanceRules);
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }
    return rebalanceRules;
  }

  @Override
  public void addRebalanceRule(long accountId, RebalanceRule rule)
      throws RebalanceRuleExistingExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    AddRebalanceRuleRequest request = new AddRebalanceRuleRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    RebalanceRulethrift rebalanceRulethrift = new RebalanceRulethrift();
    rebalanceRulethrift.setRuleId(generateId());
    rebalanceRulethrift.setRuleName(rule.getRuleName());

    List<AbsoluteTimethrift> absoluteTimeThriftList = new ArrayList<>();
    for (RebalanceAbsoluteTime absoluteTime : rule.getAbsoluteTimeList()) {
      AbsoluteTimethrift absoluteTimethrift = new AbsoluteTimethrift();
      if (absoluteTime.getBeginTime() != null) {
        absoluteTimethrift.setBeginTime(Long.valueOf(absoluteTime.getBeginTime()));
      }
      if (absoluteTime.getEndTime() != null) {
        absoluteTimethrift.setEndTime(Long.valueOf(absoluteTime.getEndTime()));
      }
      absoluteTimeThriftList.add(absoluteTimethrift);
    }
    rebalanceRulethrift.setAbsoluteTimeList(absoluteTimeThriftList);

    RelativeTimethrift relativeTimeThrift = new RelativeTimethrift();
    if (rule.getWaitTime() != null) {
      relativeTimeThrift.setWaitTime(Long.valueOf(rule.getWaitTime()));
    }
    rebalanceRulethrift.setRelativeTime(relativeTimeThrift);

    request.setRule(rebalanceRulethrift);
    logger.debug("createRebalanceRule request is {}", request);
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.addRebalanceRule(request);
    } catch (PoolAlreadyAppliedRebalanceRuleExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (RebalanceRuleExistingExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }
  }

  @Override
  public void updateRebalanceRule(long accountId, RebalanceRule rule)
      throws RebalanceRuleNotExistExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    UpdateRebalanceRuleRequest request = new UpdateRebalanceRuleRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    RebalanceRulethrift rebalanceRuleThrift = new RebalanceRulethrift();
    if (!StringUtils.isEmpty(rule.getRuleId())) {
      rebalanceRuleThrift.setRuleId(Long.valueOf(rule.getRuleId()));
    } else {
      rebalanceRuleThrift.setRuleId(generateId());
    }
    if (rule.getRuleName() != null) {
      rebalanceRuleThrift.setRuleName(rule.getRuleName());
    }
    if (rule.getWaitTime() != null) {
      RelativeTimethrift relativeTimeThrift = new RelativeTimethrift();
      if (rule.getWaitTime() != null) {
        relativeTimeThrift.setWaitTime(Long.valueOf(rule.getWaitTime()));
      }
      rebalanceRuleThrift.setRelativeTime(relativeTimeThrift);
    }
    if (rule.getAbsoluteTimeList() != null) {
      List<AbsoluteTimethrift> absoluteTimeThriftList = new ArrayList<>();
      for (RebalanceAbsoluteTime absoluteTime : rule.getAbsoluteTimeList()) {
        AbsoluteTimethrift absoluteTimeThrift = new AbsoluteTimethrift();
        if (absoluteTime.getBeginTime() != null) {
          absoluteTimeThrift.setBeginTime(Long.valueOf(absoluteTime.getBeginTime()));
        }
        if (absoluteTime.getEndTime() != null) {
          absoluteTimeThrift.setEndTime(Long.valueOf(absoluteTime.getEndTime()));
        }
        absoluteTimeThriftList.add(absoluteTimeThrift);
      }
      rebalanceRuleThrift.setAbsoluteTimeList(absoluteTimeThriftList);
    }
    request.setRule(rebalanceRuleThrift);
    logger.debug("createRebalanceRule request is {}", request);
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.updateRebalanceRule(request);
    } catch (PoolAlreadyAppliedRebalanceRuleExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (RebalanceRuleNotExistExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }
  }

  @Override
  public List<SimpleStoragePool> getAppliedRebalanceRulePool(long accountId, long ruleId)
      throws RebalanceRuleNotExistExceptionThrift, StoragePoolNotExistedExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    GetAppliedRebalanceRulePoolRequest request = new GetAppliedRebalanceRulePoolRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setRuleId(ruleId);

    List<SimpleStoragePool> appliedPoolList = new ArrayList<>();
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      GetAppliedRebalanceRulePoolResponse response = client.getAppliedRebalanceRulePool(request);
      List<StoragePoolThrift> poolsList = response.getStoragePoolList();
      for (StoragePoolThrift pool : poolsList) {
        SimpleStoragePool simplePool = new SimpleStoragePool();
        simplePool.setDomainId(String.valueOf(pool.getDomainId()));
        simplePool.setDomainName(pool.getDomainName());
        simplePool.setDescription(pool.getDescription());
        simplePool.setPoolId(String.valueOf(pool.getPoolId()));
        simplePool.setPoolName(pool.getPoolName());
        simplePool.setStatus(String.valueOf(pool.getStatus()));
        appliedPoolList.add(simplePool);
      }
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }

    return appliedPoolList;
  }

  @Override
  public List<SimpleStoragePool> getUnAppliedRebalanceRulePool(long accountId)
      throws StoragePoolNotExistedExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    GetUnAppliedRebalanceRulePoolRequest request = new GetUnAppliedRebalanceRulePoolRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);

    List<SimpleStoragePool> unAppliedPoolList = new ArrayList<>();
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      GetUnAppliedRebalanceRulePoolResponse response = client.getUnAppliedRebalanceRulePool(
          request);
      List<StoragePoolThrift> poolsList = response.getStoragePoolList();
      for (StoragePoolThrift pool : poolsList) {
        SimpleStoragePool simplePool = new SimpleStoragePool();
        simplePool.setDomainId(String.valueOf(pool.getDomainId()));
        simplePool.setDomainName(pool.getDomainName());
        simplePool.setDescription(pool.getDescription());
        simplePool.setPoolId(String.valueOf(pool.getPoolId()));
        simplePool.setPoolName(pool.getPoolName());
        simplePool.setStatus(String.valueOf(pool.getStatus()));
        unAppliedPoolList.add(simplePool);
      }
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }

    return unAppliedPoolList;
  }

  @Override
  public void unApplyRebalanceRule(long accountId, long ruleId, List<Long> poolIdList)
      throws StoragePoolNotExistedExceptionThrift, GenericThriftClientFactoryException,
      RebalanceRuleNotExistExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    UnApplyRebalanceRuleRequest request = new UnApplyRebalanceRuleRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    RebalanceRulethrift rebalanceRuleThrift = new RebalanceRulethrift();
    rebalanceRuleThrift.setRuleId(ruleId);
    request.setRule(rebalanceRuleThrift);
    request.setStoragePoolIdList(poolIdList);
    logger.debug("unApplyRebalanceRule request is {}", request);
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.unApplyRebalanceRule(request);
    } catch (NetworkErrorExceptionThrift e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (StoragePoolNotExistedExceptionThrift e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (RebalanceRuleNotExistExceptionThrift e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new GenericThriftClientFactoryException();
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }

  }

  @Override
  public void applyRebalanceRule(long accountId, long ruleId, List<Long> poolIdList)
      throws StoragePoolNotExistedExceptionThrift, PoolAlreadyAppliedRebalanceRuleExceptionThrift,
      RebalanceRuleNotExistExceptionThrift, NetworkErrorExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      TException {
    ApplyRebalanceRuleRequest request = new ApplyRebalanceRuleRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    RebalanceRulethrift rebalanceRuleThrift = new RebalanceRulethrift();
    rebalanceRuleThrift.setRuleId(ruleId);
    request.setRule(rebalanceRuleThrift);
    request.setStoragePoolIdList(poolIdList);
    logger.debug("applyRebalanceRule request is {}", request);
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.applyRebalanceRule(request);
    } catch (PoolAlreadyAppliedRebalanceRuleExceptionThrift e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (StoragePoolNotExistedExceptionThrift e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (RebalanceRuleNotExistExceptionThrift e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }
  }

  /**
   * generate id.
   *
   * @return id
   */
  public long generateId() {
    long id = UUID.randomUUID().getLeastSignificantBits();
    if (id < 0) {
      id = id + Long.MAX_VALUE;
    }

    return id;
  }

}
