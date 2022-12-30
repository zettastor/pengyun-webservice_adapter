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

package py.console.service.qos.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.common.Constants;
import py.common.PyService;
import py.common.RequestIdBuilder;
import py.console.bean.IoLimitation;
import py.console.bean.IoLimitationEntry;
import py.console.bean.QosMigrationRule;
import py.console.bean.SimpleDriverClientInfo;
import py.console.bean.SimpleDriverMetadata;
import py.console.bean.SimpleInstance;
import py.console.bean.SimpleStoragePool;
import py.console.service.driver.DriverService;
import py.console.service.instance.InstanceService;
import py.console.service.qos.QosService;
import py.console.service.storagepool.StoragePoolService;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.infocenter.client.InformationCenterClientFactory;
import py.instance.InstanceStatus;
import py.thrift.infocenter.service.FailedToTellDriverAboutAccessRulesExceptionThrift;
import py.thrift.infocenter.service.InformationCenter;
import py.thrift.share.AccessPermissionTypeThrift;
import py.thrift.share.AccessRuleNotAppliedThrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.ApplyFailedDueToVolumeIsReadOnlyExceptionThrift;
import py.thrift.share.ApplyIoLimitationsRequest;
import py.thrift.share.ApplyIoLimitationsResponse;
import py.thrift.share.ApplyMigrationRulesRequest;
import py.thrift.share.CancelIoLimitationsRequest;
import py.thrift.share.CancelIoLimitationsResponse;
import py.thrift.share.CancelMigrationRulesRequest;
import py.thrift.share.CreateIoLimitationsRequest;
import py.thrift.share.CreateIoLimitationsResponse;
import py.thrift.share.DeleteIoLimitationsRequest;
import py.thrift.share.DeleteIoLimitationsResponse;
import py.thrift.share.DriverKeyThrift;
import py.thrift.share.DriverMetadataThrift;
import py.thrift.share.DriverTypeThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.GetAppliedStoragePoolsRequest;
import py.thrift.share.GetAppliedStoragePoolsResponse;
import py.thrift.share.GetIoLimitationAppliedDriversRequest;
import py.thrift.share.GetIoLimitationAppliedDriversResponse;
import py.thrift.share.InvalidInputExceptionThrift;
import py.thrift.share.IoLimitationEntryThrift;
import py.thrift.share.IoLimitationThrift;
import py.thrift.share.IoLimitationTimeInterLeavingThrift;
import py.thrift.share.IoLimitationsDuplicateThrift;
import py.thrift.share.LimitTypeThrift;
import py.thrift.share.ListIoLimitationsRequest;
import py.thrift.share.ListIoLimitationsResponse;
import py.thrift.share.ListMigrationRulesRequest;
import py.thrift.share.ListMigrationRulesResponse;
import py.thrift.share.MigrationRuleNotExists;
import py.thrift.share.MigrationRuleThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.StoragePoolThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;
import py.thrift.share.UpdateIoLimitationRulesRequest;
import py.thrift.share.UpdateIoLimitationsResponse;
import py.thrift.share.VolumeBeingDeletedExceptionThrift;
import py.thrift.share.VolumeNotFoundExceptionThrift;

public class QosServiceImpl implements QosService {

  private static final Logger logger = LoggerFactory.getLogger(QosServiceImpl.class);
  private InformationCenterClientFactory infoCenterClientFactory;

  private StoragePoolService poolService;

  private InstanceService instanceService;

  private DriverService driverService;

  public StoragePoolService getPoolService() {
    return poolService;
  }

  public void setPoolService(StoragePoolService poolService) {
    this.poolService = poolService;
  }

  public InstanceService getInstanceService() {
    return instanceService;
  }

  public void setInstanceService(InstanceService instanceService) {
    this.instanceService = instanceService;
  }

  public DriverService getDriverService() {
    return driverService;
  }

  public void setDriverService(DriverService driverService) {
    this.driverService = driverService;
  }

  public InformationCenterClientFactory getInfoCenterClientFactory() {
    return infoCenterClientFactory;
  }

  public void setInfoCenterClientFactory(InformationCenterClientFactory infoCenterClientFactory) {
    this.infoCenterClientFactory = infoCenterClientFactory;
  }

  @Override
  public List<QosMigrationRule> listMigrationRules(long accountId)
      throws ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    ListMigrationRulesRequest request = new ListMigrationRulesRequest();
    ListMigrationRulesResponse response = new ListMigrationRulesResponse();
    request.setAccountId(accountId);
    request.setRequestId(RequestIdBuilder.get());
    InformationCenter.Iface client = null;
    logger.debug("listMigrationRules request is {}", request);
    List<QosMigrationRule> qosMigrationRuleList = new ArrayList<>();
    try {
      client = infoCenterClientFactory.build().getClient();
      response = client.listMigrationRules(request);
      logger.debug("listMigrationRules response is {}", response);
      if (response != null && response.getMigrationRules().size() != 0) {
        for (MigrationRuleThrift ruleThrift : response.getMigrationRules()) {
          QosMigrationRule rule = new QosMigrationRule();
          rule.setRuleId(String.valueOf(ruleThrift.getRuleId()));
          rule.setRuleName(ruleThrift.getMigrationRuleName());
          rule.setMaxMigrationSpeed(String.valueOf(ruleThrift.getMaxMigrationSpeed()));
          rule.setStrategy(ruleThrift.getMigrationStrategy().name());
          rule.setStatus(ruleThrift.getMigrationRuleStatus().name());
          rule.setMode(ruleThrift.getMode().name());
          rule.setStartTime(String.valueOf(ruleThrift.getStartTime()));
          rule.setEndTime(String.valueOf(ruleThrift.getEndTime()));
          rule.setWaitTime(String.valueOf(ruleThrift.getWaitTime()));
          rule.setBuiltInRule(String.valueOf(ruleThrift.isBuiltInRule()));
          rule.setIgnoreMissPagesAndLogs(String.valueOf(ruleThrift.isIgnoreMissPagesAndLogs()));
          qosMigrationRuleList.add(rule);
        }
      }
      logger.debug("qosMigrationRuleList is {}", qosMigrationRuleList);

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
    return qosMigrationRuleList;
  }

  @Override
  public List<SimpleStoragePool> getAppliedStoragePools(long accountId, long ruleId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift, MigrationRuleNotExists,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    GetAppliedStoragePoolsRequest request = new GetAppliedStoragePoolsRequest();
    GetAppliedStoragePoolsResponse response;
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setRuleId(ruleId);
    InformationCenter.Iface client;
    logger.debug("getAppliedStoragePools request is {}", request);
    List<SimpleStoragePool> poolList = new ArrayList<>();
    try {
      client = infoCenterClientFactory.build().getClient();
      response = client.getAppliedStoragePools(request);
      logger.debug("getAppliedStoragePools response is {}", response);
      if (response != null && response.getStoragePoolList() != null) {
        for (StoragePoolThrift poolThrift : response.getStoragePoolList()) {
          SimpleStoragePool pool = new SimpleStoragePool();
          pool.setPoolId(String.valueOf(poolThrift.getPoolId()));
          pool.setPoolName(poolThrift.getPoolName());
          pool.setDomainId(String.valueOf(poolThrift.getDomainId()));
          pool.setDomainName(poolThrift.getDomainName());
          pool.setStrategy(String.valueOf(poolThrift.getStrategy()));
          pool.setStatus(String.valueOf(poolThrift.getStatus()));
          pool.setDescription(poolThrift.getDescription());
          Set<String> volumeIds = new HashSet<>();
          if (poolThrift.volumeIds != null) {
            for (Long volumeId : poolThrift.volumeIds) {
              volumeIds.add(String.valueOf(volumeId));
            }
          }
          pool.setVolumeIds(volumeIds);
          poolList.add(pool);
        }
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
    return poolList;
  }

  @Override
  public List<SimpleStoragePool> getUnAppliedPools(long accountId, long ruleId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift, MigrationRuleNotExists,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    List<SimpleStoragePool> poolList = new ArrayList<>();
    List<SimpleStoragePool> unAppliedPoolList = new ArrayList<>();
    List<SimpleStoragePool> appliedPoolList = new ArrayList<>();
    try {
      appliedPoolList = getAppliedStoragePools(accountId, ruleId);
      logger.debug("appliedPoolList  is {}", appliedPoolList);
      poolList.addAll(poolService.listStoragePools(null, null, accountId));
      logger.debug("poolList  is {}", poolList);
      for (SimpleStoragePool pool : poolList) {
        boolean appliedFlag = false;
        for (SimpleStoragePool appliedPool : appliedPoolList) {
          if (appliedPool.getPoolId().equals(pool.getPoolId())) {
            appliedFlag = true;
          }
        }
        if (!appliedFlag) {
          unAppliedPoolList.add(pool);
        }
      }
      logger.debug("unAppliedPoolList  is {}", unAppliedPoolList);
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }

    return unAppliedPoolList;

  }

  @Override
  public void createIoLimitations(long accountId, IoLimitation ioLimitation)
      throws IoLimitationsDuplicateThrift, InvalidInputExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      ServiceIsNotAvailableThrift, AccountNotFoundExceptionThrift,
      PermissionNotGrantExceptionThrift,
      IoLimitationTimeInterLeavingThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    CreateIoLimitationsRequest request = new CreateIoLimitationsRequest();
    CreateIoLimitationsResponse response = new CreateIoLimitationsResponse();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    IoLimitationThrift ioLimitationThrift = new IoLimitationThrift();
    ioLimitationThrift.setLimitationId(generateId());
    ioLimitationThrift.setLimitationName(ioLimitation.getLimitationName());
    ioLimitationThrift.setLimitType(LimitTypeThrift.valueOf(ioLimitation.getLimitType()));
    List<IoLimitationEntryThrift> entryThrifts = new ArrayList<>();
    for (IoLimitationEntry entry : ioLimitation.getEntries()) {
      IoLimitationEntryThrift entryThrift = new IoLimitationEntryThrift();
      if (!entry.getUpperLimitedIoPs().isEmpty()) {
        entryThrift.setUpperLimitedIoPs(Integer.valueOf(entry.getUpperLimitedIoPs()));
      }
      if (!entry.getLowerLimitedIoPs().isEmpty()) {
        entryThrift.setLowerLimitedIoPs(Integer.valueOf(entry.getLowerLimitedIoPs()));
      }
      if (!entry.getUpperLimitedThroughput().isEmpty()) {
        entryThrift.setUpperLimitedThroughput(Long.valueOf(entry.getUpperLimitedThroughput()));
      }
      if (!entry.getLowerLimitedThroughput().isEmpty()) {
        entryThrift.setLowerLimitedThroughput(Long.valueOf(entry.getLowerLimitedThroughput()));
      }
      entryThrift.setStartTime(entry.getStartTime());
      entryThrift.setEndTime(entry.getEndTime());
      entryThrift.setEntryId(RequestIdBuilder.get());
      entryThrifts.add(entryThrift);
    }
    ioLimitationThrift.setEntries(entryThrifts);
    request.setIoLimitation(ioLimitationThrift);
    logger.debug("createIOLimitations request is {}", request);
    InformationCenter.Iface client;

    try {
      client = infoCenterClientFactory.build().getClient();
      client.createIoLimitations(request);
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
  public void updateIoLimitations(long accountId, IoLimitation ioLimitation)
      throws IoLimitationsDuplicateThrift, InvalidInputExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      ServiceIsNotAvailableThrift, AccountNotFoundExceptionThrift,
      PermissionNotGrantExceptionThrift,
      IoLimitationTimeInterLeavingThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    UpdateIoLimitationRulesRequest request = new UpdateIoLimitationRulesRequest();
    UpdateIoLimitationsResponse response = new UpdateIoLimitationsResponse();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    IoLimitationThrift ioLimitationThrift = new IoLimitationThrift();
    ioLimitationThrift.setLimitationId(Long.valueOf(ioLimitation.getLimitationId()));
    ioLimitationThrift.setLimitationName(ioLimitation.getLimitationName());
    ioLimitationThrift.setLimitType(LimitTypeThrift.valueOf(ioLimitation.getLimitType()));
    List<IoLimitationEntryThrift> entryThrifts = new ArrayList<>();
    for (IoLimitationEntry entry : ioLimitation.getEntries()) {
      IoLimitationEntryThrift entryThrift = new IoLimitationEntryThrift();
      entryThrift.setUpperLimitedIoPs(Integer.valueOf(entry.getUpperLimitedIoPs()));
      entryThrift.setLowerLimitedIoPs(Integer.valueOf(entry.getLowerLimitedIoPs()));
      entryThrift.setUpperLimitedThroughput(Long.valueOf(entry.getUpperLimitedThroughput()));
      entryThrift.setLowerLimitedThroughput(Long.valueOf(entry.getLowerLimitedThroughput()));
      entryThrift.setStartTime(entry.getStartTime());
      entryThrift.setEndTime(entry.getEndTime());
      entryThrift.setEntryId(RequestIdBuilder.get());
      entryThrifts.add(entryThrift);

    }
    ioLimitationThrift.setEntries(entryThrifts);
    request.setIoLimitation(ioLimitationThrift);
    logger.debug("updateIOLimitations request is {}", request);
    InformationCenter.Iface client = null;

    try {
      client = infoCenterClientFactory.build().getClient();
      client.updateIoLimitations(request);
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
  public List<IoLimitation> listIoLimitations(long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    List<IoLimitation> ioLimitationsList = new ArrayList<>();
    ListIoLimitationsRequest request = new ListIoLimitationsRequest();
    ListIoLimitationsResponse response = new ListIoLimitationsResponse();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    logger.debug("listIoLimitations request is {}", request);
    InformationCenter.Iface client = null;

    try {
      client = infoCenterClientFactory.build().getClient();
      response = client.listIoLimitations(request);
      logger.debug("listIoLimitations response is {}", response);
      if (response != null && response.getIoLimitations() != null) {
        for (IoLimitationThrift ioLimitationThrift : response.getIoLimitations()) {
          IoLimitation ioLimitation = new IoLimitation();
          ioLimitation.setLimitationId(String.valueOf(ioLimitationThrift.getLimitationId()));
          ioLimitation.setLimitationName(ioLimitationThrift.getLimitationName());
          ioLimitation.setLimitType(ioLimitationThrift.getLimitType().name());
          ioLimitation.setStatus(ioLimitationThrift.getStatus().name());
          List<IoLimitationEntry> entries = new ArrayList<>();
          for (IoLimitationEntryThrift entryThrift : ioLimitationThrift.getEntries()) {
            IoLimitationEntry entry = new IoLimitationEntry();
            entry.setUpperLimitedIoPs(String.valueOf(entryThrift.getUpperLimitedIoPs()));
            entry.setLowerLimitedIoPs(String.valueOf(entryThrift.getLowerLimitedIoPs()));
            entry.setUpperLimitedThroughput(
                String.valueOf(entryThrift.getUpperLimitedThroughput()));
            entry.setLowerLimitedThroughput(
                String.valueOf(entryThrift.getLowerLimitedThroughput()));
            entry.setStartTime(entryThrift.getStartTime());
            entry.setEndTime(entryThrift.getEndTime());
            entries.add(entry);
          }
          ioLimitation.setEntries(entries);
          ioLimitationsList.add(ioLimitation);
        }
      }
      logger.debug("ioLimitationsList is {}", ioLimitationsList);
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

    return ioLimitationsList;
  }

  @Override
  public Map<String, Object> deleteIoLimitations(long accountId,
      List<IoLimitation> ioLimitationsList, boolean commit)
      throws ServiceHavingBeenShutdownThrift, FailedToTellDriverAboutAccessRulesExceptionThrift,
      ServiceIsNotAvailableThrift, AccountNotFoundExceptionThrift,
      PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      Exception {
    DeleteIoLimitationsRequest request = new DeleteIoLimitationsRequest();
    DeleteIoLimitationsResponse response = new DeleteIoLimitationsResponse();

    Map<String, Object> result = new HashMap<>();
    Map<IoLimitation, Object> beAppliedIoLimitation2Drivers = new HashMap<>();
    try {
      if (!commit) {
        for (IoLimitation ioLimitation : ioLimitationsList) {
          List<SimpleDriverMetadata> driverList = getIoLimitationAppliedDrivers(accountId,
              Long.valueOf(ioLimitation.getLimitationId()));
          if (driverList == null) {
            logger.error(
                "Something wrong to check if the ioLimitation {} is applied to some driver",
                ioLimitation.getLimitationId());
            throw new TException();
          } else {
            if (driverList.size() != 0) {
              beAppliedIoLimitation2Drivers.put(ioLimitation, driverList);
            }
          }
        }
        if (!beAppliedIoLimitation2Drivers.isEmpty() && beAppliedIoLimitation2Drivers.size() != 0) {
          result.put("beAppliedIOLimitation2Drivers", beAppliedIoLimitation2Drivers);
          logger.debug(" beAppliedIoLimitation2Drivers is {}", beAppliedIoLimitation2Drivers);
          return result;
        }
      }

      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      request.setCommit(true);
      List<Long> ids = new ArrayList<>();
      for (IoLimitation ioLimitation : ioLimitationsList) {
        ids.add(Long.valueOf(ioLimitation.getLimitationId()));
      }
      request.setRuleIds(ids);
      InformationCenter.Iface client = null;
      logger.debug("deleteIOLimitations request is {}", request);

      client = infoCenterClientFactory.build().getClient();
      client.deleteIoLimitations(request);

    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (Exception e) {
      logger.error("Exception catch", e);
      throw e;
    }

    return result;
  }

  @Override
  public List<SimpleDriverMetadata> getIoLimitationAppliedDrivers(long accountId, long limitationId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      Exception {
    List<SimpleDriverMetadata> driverMetadataList = new ArrayList<>();
    GetIoLimitationAppliedDriversRequest request = new GetIoLimitationAppliedDriversRequest();
    GetIoLimitationAppliedDriversResponse response = new GetIoLimitationAppliedDriversResponse();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setRuleId(limitationId);
    InformationCenter.Iface client = null;
    logger.debug("getIOLimitationAppliedDrivers request is {}", request);

    try {
      client = infoCenterClientFactory.build().getClient();
      response = client.getIoLimitationAppliedDrivers(request);
      // get all dirverContainer IP
      List<SimpleInstance> allInstance = new ArrayList<>();
      List<SimpleInstance> driverContainersList = new ArrayList<>();

      allInstance = instanceService.getAll(Constants.SUPERADMIN_ACCOUNT_ID);
      for (SimpleInstance instance : allInstance) {
        if (instance.getInstanceName().equals(PyService.DRIVERCONTAINER.getServiceName())
                && instance.getStatus().equals(InstanceStatus.HEALTHY.name())) {
          driverContainersList.add(instance);
        }
      }
      logger.debug("getIOLimitationAppliedDrivers response is {}", response);
      if (response != null && response.getDriverList() != null) {
        for (DriverMetadataThrift driverMetadataThrift : response.getDriverList()) {
          SimpleDriverMetadata simpleDriverMetadata = new SimpleDriverMetadata();
          simpleDriverMetadata.setDriverName(driverMetadataThrift.getDriverName());
          simpleDriverMetadata.setVolumeId(String.valueOf(driverMetadataThrift.getVolumeId()));
          simpleDriverMetadata.setVolumeName(driverMetadataThrift.getVolumeName());
          simpleDriverMetadata.setSnapshotId(String.valueOf(driverMetadataThrift.getSnapshotId()));
          simpleDriverMetadata.setDriverType(driverMetadataThrift.getDriverType().name());
          simpleDriverMetadata.setHost(driverMetadataThrift.getHostName());
          simpleDriverMetadata.setPort(String.valueOf(driverMetadataThrift.getPort()));
          simpleDriverMetadata.setCoordinatorPort(
              String.valueOf(driverMetadataThrift.getCoordinatorPort()));
          simpleDriverMetadata
              .setDriverContainerId(String.valueOf(driverMetadataThrift.getDriverContainerId()));
          for (SimpleInstance instance : driverContainersList) {
            if (instance.getInstanceId().equals(simpleDriverMetadata.getDriverContainerId())) {
              simpleDriverMetadata.setDriverContainerIp(instance.getHost());
              break;
            }
          }
          List<SimpleDriverClientInfo> driverClientInfoList = new ArrayList<>();
          for (Map.Entry<String, AccessPermissionTypeThrift> entry : driverMetadataThrift
              .getClientHostAccessRule().entrySet()) {
            SimpleDriverClientInfo clientInfo = new SimpleDriverClientInfo();
            clientInfo.setHost(entry.getKey());
            clientInfo.setAuthority(entry.getValue().name());
            driverClientInfoList.add(clientInfo);
          }
          logger.debug("driverClientInfoList is {}", driverClientInfoList);
          logger.debug("driverMetadataThrift.getClientHostAccessRule() is {}",
              driverMetadataThrift.getClientHostAccessRule());
          simpleDriverMetadata.setDriverClientInfoList(driverClientInfoList);
          simpleDriverMetadata.setStatus(driverMetadataThrift.getDriverStatus().name());
          String clientAmount = "";
          if (driverMetadataThrift.getClientHostAccessRule() == null) {
            clientAmount = String.format("%d", 0);
          } else {
            clientAmount = String.format("%d",
                driverMetadataThrift.getClientHostAccessRule().size());
          }
          simpleDriverMetadata.setClientAmount(clientAmount);
          if (driverMetadataThrift.isSetMakeUnmountForCsi()) {
            simpleDriverMetadata.setMarkUnmountForCsi(driverMetadataThrift.isMakeUnmountForCsi());
          }
          driverMetadataList.add(simpleDriverMetadata);
        }
        logger.debug("driverMetadataList is {}", driverMetadataList);
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
    } catch (Exception e) {
      logger.error("Exception catch", e);
      throw e;
    }

    return driverMetadataList;
  }

  @Override
  public List<SimpleDriverMetadata> getIoLimitationUnappliedDrivers(long accountId,
      long limitationId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      Exception {

    List<SimpleDriverMetadata> allDriverList = new ArrayList<>();
    List<SimpleDriverMetadata> appliedDriverList = new ArrayList<>();
    List<SimpleDriverMetadata> unappliedDriverList = new ArrayList<>();

    try {
      appliedDriverList = getIoLimitationAppliedDrivers(accountId, limitationId);
      logger.debug("appliedDriverList  is {}", appliedDriverList);
      allDriverList = driverService.listAllDrivers(null);
      for (SimpleDriverMetadata driverMetadata : allDriverList) {
        boolean appliedFlag = false;
        for (SimpleDriverMetadata appliedDriver : appliedDriverList) {
          if (appliedDriver.getDriverContainerId().equals(driverMetadata.getDriverContainerId())
              && appliedDriver.getVolumeId().equals(driverMetadata.getVolumeId()) && appliedDriver
              .getSnapshotId().equals(driverMetadata.getSnapshotId())) {
            appliedFlag = true;
          }
        }
        if (!appliedFlag) {
          unappliedDriverList.add(driverMetadata);
        }
      }
      logger.debug("unappliedDriverList  is {}", unappliedDriverList);
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

    return unappliedDriverList;
  }

  @Override
  public List<SimpleDriverMetadata> applyIoLimitations(long accountId, long limitationId,
      List<SimpleDriverMetadata> driverList)
      throws VolumeNotFoundExceptionThrift, VolumeBeingDeletedExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      FailedToTellDriverAboutAccessRulesExceptionThrift, ServiceIsNotAvailableThrift,
      ApplyFailedDueToVolumeIsReadOnlyExceptionThrift, AccountNotFoundExceptionThrift,
      PermissionNotGrantExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    ApplyIoLimitationsRequest request = new ApplyIoLimitationsRequest();
    ApplyIoLimitationsResponse response = new ApplyIoLimitationsResponse();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setRuleId(limitationId);
    List<DriverKeyThrift> driverKeyThrifts = new ArrayList<>();
    List<SimpleDriverMetadata> failedDriverList = new ArrayList<>();
    logger.debug("driverList is {}", driverList);
    for (SimpleDriverMetadata driver : driverList) {
      DriverKeyThrift driverKeyThrift = new DriverKeyThrift();
      driverKeyThrift.setDriverContainerId(Long.valueOf(driver.getDriverContainerId()));
      driverKeyThrift.setVolumeId(Long.valueOf(driver.getVolumeId()));
      driverKeyThrift.setSnapshotId(Integer.valueOf(driver.getSnapshotId()));
      logger.debug("driver.getDriverType() is {}", driver.getDriverType());
      driverKeyThrift.setDriverType(DriverTypeThrift.valueOf(driver.getDriverType()));
      driverKeyThrifts.add(driverKeyThrift);
    }
    request.setDriverKeys(driverKeyThrifts);
    InformationCenter.Iface client = null;
    logger.debug("applyIOLimitations request is {}", request);

    try {
      client = infoCenterClientFactory.build().getClient();
      response = client.applyIoLimitations(request);
      if (response != null && response.getAirDriverKeyList() != null) {
        for (DriverMetadataThrift driverThrift : response.getAirDriverKeyList()) {
          SimpleDriverMetadata driver = new SimpleDriverMetadata();
          driver.setDriverName(driverThrift.getDriverName());
          driver.setVolumeId(String.valueOf(driverThrift.getVolumeId()));
          driver.setVolumeName(driverThrift.getVolumeName());
          driver.setSnapshotId(String.valueOf(driverThrift.getSnapshotId()));
          driver.setDriverType(driverThrift.getDriverType().name());
          driver.setHost(driverThrift.getHostName());
          driver.setPort(String.valueOf(driverThrift.getPort()));
          driver.setCoordinatorPort(String.valueOf(driverThrift.getCoordinatorPort()));
          driver.setChapControl(String.valueOf(driverThrift.getChapControl()));
          failedDriverList.add(driver);
        }
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
    return failedDriverList;

  }

  @Override
  public void cancelIoLimitations(long accountId, long limitationId,
      List<SimpleDriverMetadata> driverList)
      throws ServiceHavingBeenShutdownThrift, FailedToTellDriverAboutAccessRulesExceptionThrift,
      ServiceIsNotAvailableThrift, AccessRuleNotAppliedThrift, AccountNotFoundExceptionThrift,
      PermissionNotGrantExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    CancelIoLimitationsRequest request = new CancelIoLimitationsRequest();
    CancelIoLimitationsResponse response = new CancelIoLimitationsResponse();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setRuleId(limitationId);
    List<DriverKeyThrift> driverKeyThrifts = new ArrayList<>();
    for (SimpleDriverMetadata driver : driverList) {
      DriverKeyThrift driverKeyThrift = new DriverKeyThrift();
      driverKeyThrift.setDriverContainerId(Long.valueOf(driver.getDriverContainerId()));
      driverKeyThrift.setVolumeId(Long.valueOf(driver.getVolumeId()));
      driverKeyThrift.setSnapshotId(Integer.valueOf(driver.getSnapshotId()));
      driverKeyThrift.setDriverType(DriverTypeThrift.valueOf(driver.getDriverType()));
      driverKeyThrifts.add(driverKeyThrift);
    }
    request.setDriverKeys(driverKeyThrifts);
    InformationCenter.Iface client = null;
    logger.debug("cancelIOLimitations request is {}", request);

    try {
      client = infoCenterClientFactory.build().getClient();
      client.cancelIoLimitations(request);
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
  public void applyMigrationRules(long accountId, List<Long> poolIds, long ruleId)
      throws VolumeNotFoundExceptionThrift, VolumeBeingDeletedExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      FailedToTellDriverAboutAccessRulesExceptionThrift, ServiceIsNotAvailableThrift,
      ApplyFailedDueToVolumeIsReadOnlyExceptionThrift, AccountNotFoundExceptionThrift,
      PermissionNotGrantExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    ApplyMigrationRulesRequest request = new ApplyMigrationRulesRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setStoragePoolIds(poolIds);
    request.setRuleId(ruleId);
    InformationCenter.Iface client = null;
    logger.debug("applyMigrationRules request is {}", request);

    try {
      client = infoCenterClientFactory.build().getClient();
      client.applyMigrationRules(request);
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
  public void cancelMigrationRules(long accountId, List<Long> poolIds, long ruleId)
      throws ServiceHavingBeenShutdownThrift, FailedToTellDriverAboutAccessRulesExceptionThrift,
      ServiceIsNotAvailableThrift, AccessRuleNotAppliedThrift, AccountNotFoundExceptionThrift,
      PermissionNotGrantExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    CancelMigrationRulesRequest request = new CancelMigrationRulesRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setStoragePoolIds(poolIds);
    request.setRuleId(ruleId);
    InformationCenter.Iface client = null;
    logger.debug("cancelMigrationRules request is {}", request);
    try {
      client = infoCenterClientFactory.build().getClient();
      client.cancelMigrationRules(request);
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
