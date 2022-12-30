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

package py.console.service.storagepool.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.archive.ArchiveStatus;
import py.common.Constants;
import py.common.PyService;
import py.common.RequestIdBuilder;
import py.console.bean.SimpleArchiveMetadata;
import py.console.bean.SimpleDriverClientInfo;
import py.console.bean.SimpleDriverMetadata;
import py.console.bean.SimpleInstance;
import py.console.bean.SimpleStoragePool;
import py.console.service.driver.DriverService;
import py.console.service.instance.InstanceService;
import py.console.service.storagepool.StoragePoolService;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.infocenter.client.InformationCenterClientFactory;
import py.instance.InstanceStatus;
import py.thrift.icshare.ListAllDriversRequest;
import py.thrift.icshare.ListAllDriversResponse;
import py.thrift.infocenter.service.InformationCenter;
import py.thrift.share.AccessDeniedExceptionThrift;
import py.thrift.share.AccessPermissionTypeThrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.ArchiveIsUsingExceptionThrift;
import py.thrift.share.ArchiveMetadataThrift;
import py.thrift.share.ArchiveNotFoundExceptionThrift;
import py.thrift.share.ArchiveNotFreeToUseExceptionThrift;
import py.thrift.share.CreateStoragePoolRequestThrift;
import py.thrift.share.CreateStoragePoolResponseThrift;
import py.thrift.share.DeleteStoragePoolRequestThrift;
import py.thrift.share.DomainIsDeletingExceptionThrift;
import py.thrift.share.DomainNotExistedExceptionThrift;
import py.thrift.share.DriverMetadataThrift;
import py.thrift.share.DriverTypeThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.FailToRemoveArchiveFromStoragePoolExceptionThrift;
import py.thrift.share.InvalidInputExceptionThrift;
import py.thrift.share.ListStoragePoolCapacityRequestThrift;
import py.thrift.share.ListStoragePoolCapacityResponseThrift;
import py.thrift.share.ListStoragePoolRequestThrift;
import py.thrift.share.ListStoragePoolResponseThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.OneStoragePoolDisplayThrift;
import py.thrift.share.ParametersIsErrorExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.RemoveArchiveFromStoragePoolRequestThrift;
import py.thrift.share.ResourceNotExistsExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.StillHaveVolumeExceptionThrift;
import py.thrift.share.StoragePoolCapacityThrift;
import py.thrift.share.StoragePoolExistedExceptionThrift;
import py.thrift.share.StoragePoolIsDeletingExceptionThrift;
import py.thrift.share.StoragePoolNameExistedExceptionThrift;
import py.thrift.share.StoragePoolNotExistedExceptionThrift;
import py.thrift.share.StoragePoolStrategyThrift;
import py.thrift.share.StoragePoolThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;
import py.thrift.share.UpdateStoragePoolRequestThrift;

/**
 * StoragePoolServiceImpl.
 */
public class StoragePoolServiceImpl implements StoragePoolService {

  private static final Logger logger = LoggerFactory.getLogger(StoragePoolServiceImpl.class);
  private InformationCenterClientFactory infoCenterClientFactory;
  private InstanceService instanceService;

  public InstanceService getInstanceService() {
    return instanceService;
  }

  public void setInstanceService(InstanceService instanceService) {
    this.instanceService = instanceService;
  }

  public DriverService driverService;

  public InformationCenterClientFactory getInfoCenterClientFactory() {
    return infoCenterClientFactory;
  }

  public void setInfoCenterClientFactory(InformationCenterClientFactory infoCenterClientFactory) {
    this.infoCenterClientFactory = infoCenterClientFactory;
  }


  @Override
  public SimpleStoragePool createStoragePool(SimpleStoragePool simpleStoragepool, long accountId)
      throws InvalidInputExceptionThrift, ServiceHavingBeenShutdownThrift,
      StoragePoolExistedExceptionThrift,
      StoragePoolNameExistedExceptionThrift, ServiceIsNotAvailableThrift,
      ArchiveNotFreeToUseExceptionThrift,
      ArchiveNotFoundExceptionThrift, DomainNotExistedExceptionThrift,
      ArchiveIsUsingExceptionThrift,
      DomainIsDeletingExceptionThrift, PermissionNotGrantExceptionThrift,
      AccessDeniedExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    if (simpleStoragepool == null) {
      logger.warn("Invalid input params");
      throw new InvalidInputExceptionThrift();
    }
    if (simpleStoragepool.getPoolName() == null) {
      logger.error("storagepool name can not be null");
      throw new InvalidInputExceptionThrift();
    }
    CreateStoragePoolRequestThrift createStoragePoolRequest = new CreateStoragePoolRequestThrift();
    createStoragePoolRequest.setRequestId(RequestIdBuilder.get());
    createStoragePoolRequest.setAccountId(accountId);
    StoragePoolThrift storagePoolThrift = new StoragePoolThrift();
    storagePoolThrift.setPoolId(Long.parseLong(simpleStoragepool.getPoolId()));
    storagePoolThrift.setDomainId(Long.parseLong(simpleStoragepool.getDomainId()));
    storagePoolThrift.setPoolName(simpleStoragepool.getPoolName());
    storagePoolThrift.setDescription(simpleStoragepool.getDescription());
    storagePoolThrift.setStrategy(
        StoragePoolStrategyThrift.valueOf(simpleStoragepool.getStrategy()));
    logger.debug("storagePoolThrift:[{}]", storagePoolThrift);

    createStoragePoolRequest.setStoragePool(storagePoolThrift);
    CreateStoragePoolResponseThrift response = new CreateStoragePoolResponseThrift();
    SimpleStoragePool pool = new SimpleStoragePool();
    try {
      InformationCenter.Iface iface = infoCenterClientFactory.build().getClient();
      response = iface.createStoragePool(createStoragePoolRequest);
      StoragePoolThrift poolthrift = response.getStoragePoolThrift();
      if (poolthrift != null) {
        pool.setPoolId(String.valueOf(poolthrift.poolId));
        pool.setPoolName(poolthrift.getPoolName());

        pool.setDomainId(String.valueOf(poolthrift.domainId));
        pool.setDomainName(poolthrift.getDomainName());
        pool.setStrategy(String.valueOf(poolthrift.strategy));
        pool.setStatus(String.valueOf(poolthrift.status));
        pool.setDescription(poolthrift.description);
        Set<String> volumeIds = new HashSet<>();
        if (poolthrift.volumeIds != null) {
          for (Long volumeId : poolthrift.volumeIds) {
            volumeIds.add(String.valueOf(volumeId));
          }
        }
        pool.setVolumeIds(volumeIds);
        pool.setMigrationSpeed(String.valueOf(poolthrift.getMigrationSpeed()));
        pool.setMigrationRatio(String.valueOf(poolthrift.getMigrationRatio()));
        pool.setLogicalPssFreeSpace(String.valueOf(poolthrift.getLogicalPssFreeSpace()));
        pool.setLogicalPsaFreeSpace(String.valueOf(poolthrift.getLogicalPsaFreeSpace()));
        pool.setTotalSpace(String.valueOf(poolthrift.getTotalSpace()));
        pool.setFreeSpace(String.valueOf(poolthrift.getFreeSpace()));
        pool.setSecurityLevel(poolthrift.getStoragePoolLevel());
        pool.setStoragePoolLevel(poolthrift.getStoragePoolLevel());
        pool.setUsedSpace(String.valueOf(poolthrift.getTotalSpace() - poolthrift.getFreeSpace()));
        if (poolthrift.getMigrationRule() != null) {
          pool.setMigrationStrategy(poolthrift.getMigrationRule().getMigrationRuleName());
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
    return pool;
  }

  @Override
  public List<SimpleStoragePool> listStoragePoolCapacity(String domainId, List<Long> storagePoolIds,
      long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      InvalidInputExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    List<SimpleStoragePool> storagePoolsList = new ArrayList<>();
    ListStoragePoolCapacityRequestThrift request = new ListStoragePoolCapacityRequestThrift();
    request.setRequestId(RequestIdBuilder.get());
    request.setDomainId(Long.parseLong(domainId));
    request.setAccountId(accountId);
    logger.debug("domainId:[{}]", domainId);
    if (storagePoolIds != null && storagePoolIds.size() != 0) {
      request.setStoragePoolIdList(storagePoolIds);
    }
    try {
      InformationCenter.Iface iface = infoCenterClientFactory.build().getClient();
      ListStoragePoolCapacityResponseThrift response = iface.listStoragePoolCapacity(request);
      logger.debug("response:[{}]", response);
      List<StoragePoolCapacityThrift> storagePoolCapacityThrifts =
          response.getStoragePoolCapacityList();
      for (StoragePoolCapacityThrift storagePoolCapacity : storagePoolCapacityThrifts) {
        SimpleStoragePool pool = new SimpleStoragePool();
        pool.setDomainId(String.valueOf(storagePoolCapacity.getDomainId()));
        pool.setPoolId(String.valueOf(storagePoolCapacity.getStoragePoolId()));
        pool.setPoolName(storagePoolCapacity.getStoragePoolName());
        pool.setFreeSpace(String.valueOf(storagePoolCapacity.getFreeSpace()));
        pool.setTotalSpace(String.valueOf(storagePoolCapacity.getTotalSpace()));
        pool.setUsedSpace(String.valueOf(storagePoolCapacity.getUsedSpace()));
        logger.debug("pool is :[{}]", pool);
        storagePoolsList.add(pool);
      }
      logger.debug("storagePoolsList:[{}]", storagePoolsList);

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

    return storagePoolsList;
  }

  private List<SimpleDriverMetadata> listAllDrivers(SimpleDriverMetadata driverMetadata)
      throws ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift,
      ParametersIsErrorExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      Exception {
    ListAllDriversRequest request = new ListAllDriversRequest();
    ListAllDriversResponse response = new ListAllDriversResponse();
    request.setRequestId(RequestIdBuilder.get());
    List<SimpleDriverMetadata> driverList = new ArrayList<>();
    if (driverMetadata != null) {
      if (!StringUtils.isEmpty(driverMetadata.getVolumeId())) {
        request.setVolumeId(Long.valueOf(driverMetadata.getVolumeId()));
      }
      if (!StringUtils.isEmpty(driverMetadata.getSnapshotId())) {
        request.setSnapshotId(Integer.valueOf(driverMetadata.getSnapshotId()));
      }
      if (!StringUtils.isEmpty(driverMetadata.getDriverContainerIp())) {
        request.setDrivercontainerHost(driverMetadata.getDriverContainerIp());
      }
      if (!StringUtils.isEmpty(driverMetadata.getHost())) {
        request.setDriverHost(driverMetadata.getHost());
      }
      if (!StringUtils.isEmpty(driverMetadata.getDriverType())) {
        request.setDriverType(DriverTypeThrift.valueOf(driverMetadata.getDriverType()));
      }
    }

    logger.debug("listAllDrivers request is {}", request);
    InformationCenter.Iface client = null;
    try {
      client = infoCenterClientFactory.build().getClient();
      response = client.listAllDrivers(request);
      logger.debug("listAllDrivers response is {}", response);
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

      if (response != null && response.getDriverMetadatasthrift() != null) {
        for (DriverMetadataThrift driverMetadataThrift : response.getDriverMetadatasthrift()) {
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
          simpleDriverMetadata.setChapControl(
              String.valueOf(driverMetadataThrift.getChapControl()));
          simpleDriverMetadata.setIpv6Addr(driverMetadataThrift.getIpv6Addr());
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
          driverList.add(simpleDriverMetadata);
        }
        logger.debug("driverList is {}", driverList);

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
    return driverList;
  }

  @Override

  public boolean isPoolHasPerformanceData(String poolId) throws Exception {
    /*该函数为：判断当前的存储池是否含有挂载驱动的卷，用来判断是否展示存储池的性能数据*/
    List<Long> ids = new ArrayList<>();
    ids.add(Long.valueOf(poolId));
    try {
      Set<SimpleStoragePool> storagePools = listStoragePools(null, ids,
          Constants.SUPERADMIN_ACCOUNT_ID);
      List<SimpleDriverMetadata> drivers = listAllDrivers(null);
      if (drivers == null || storagePools == null) {
        return false;
      }
      for (SimpleDriverMetadata driver : drivers) {
        for (SimpleStoragePool storagePool : storagePools) {
          if (storagePool.getVolumeIds() != null) {
            for (String volumeId : storagePool.getVolumeIds()) {
              if (volumeId.equals(driver.getVolumeId())) {
                return true;
              }

            }
          }
        }
      }
    } catch (Exception e) {
      logger.error("Exception catch", e);
      throw e;
    }
    return false;
  }

  @Override
  public Set<SimpleStoragePool> listStoragePools(String domainId, List<Long> storagePoolIds,
      long accountId)
      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      ServiceIsNotAvailableThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, ResourceNotExistsExceptionThrift, TException {
    ListStoragePoolRequestThrift listStoragePoolRequestThrift = new ListStoragePoolRequestThrift();
    listStoragePoolRequestThrift.setRequestId(RequestIdBuilder.get());
    listStoragePoolRequestThrift.setAccountId(accountId);
    if (!StringUtils.isEmpty(domainId)) {
      logger.debug("domainId:[{}]", domainId);
      listStoragePoolRequestThrift.setDomainId(Long.parseLong(domainId));
    }

    if (storagePoolIds != null && !storagePoolIds.isEmpty()) {
      listStoragePoolRequestThrift.setStoragePoolIds(storagePoolIds);
    }
    Set<SimpleStoragePool> storagePoolsList = new HashSet<SimpleStoragePool>();
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      ListStoragePoolResponseThrift listStoragePoolResponseThrift = client
          .listStoragePools(listStoragePoolRequestThrift);
      logger.debug("listStoragePoolResponseThrift:[{}]", listStoragePoolResponseThrift);
      List<OneStoragePoolDisplayThrift> storagePoolDisplays = listStoragePoolResponseThrift
          .getStoragePoolDisplays();
      for (OneStoragePoolDisplayThrift storagePoolDisplay : storagePoolDisplays) {
        SimpleStoragePool simpleStoragePool = new SimpleStoragePool();
        StoragePoolThrift storagePoolThrift = storagePoolDisplay.getStoragePoolThrift();
        List<ArchiveMetadataThrift> arhiveThriftListInStoragePool =
            storagePoolDisplay.getArchiveThrifts();
        logger.debug("arhiveThriftListInStoragePool:[{}]", arhiveThriftListInStoragePool);
        simpleStoragePool.setPoolId(String.valueOf(storagePoolThrift.poolId));
        simpleStoragePool.setPoolName(storagePoolThrift.getPoolName());

        simpleStoragePool.setDomainId(String.valueOf(storagePoolThrift.domainId));
        simpleStoragePool.setDomainName(storagePoolThrift.getDomainName());
        simpleStoragePool.setStrategy(String.valueOf(storagePoolThrift.strategy));
        simpleStoragePool.setStatus(String.valueOf(storagePoolThrift.status));
        simpleStoragePool.setDescription(storagePoolThrift.description);
        Set<String> volumeIds = new HashSet<>();
        if (storagePoolThrift.volumeIds != null) {
          for (Long volumeId : storagePoolThrift.volumeIds) {
            volumeIds.add(String.valueOf(volumeId));
          }
        }
        simpleStoragePool.setVolumeIds(volumeIds);
        simpleStoragePool.setMigrationSpeed(String.valueOf(storagePoolThrift.getMigrationSpeed()));
        simpleStoragePool.setMigrationRatio(String.valueOf(storagePoolThrift.getMigrationRatio()));
        simpleStoragePool
            .setTotalMigrateDataSizeMb(
                String.valueOf(storagePoolThrift.getTotalMigrateDataSizeMb()));
        simpleStoragePool.setLogicalPssFreeSpace(
            String.valueOf(storagePoolThrift.getLogicalPssFreeSpace()));
        simpleStoragePool.setLogicalPsaFreeSpace(
            String.valueOf(storagePoolThrift.getLogicalPsaFreeSpace()));
        simpleStoragePool.setTotalSpace(String.valueOf(storagePoolThrift.getTotalSpace()));
        simpleStoragePool.setFreeSpace(String.valueOf(storagePoolThrift.getFreeSpace()));
        simpleStoragePool.setStoragePoolLevel(storagePoolThrift.getStoragePoolLevel());
        if (storagePoolThrift.getMigrationRule() != null) {
          simpleStoragePool.setMigrationStrategy(
              storagePoolThrift.getMigrationRule().getMigrationRuleName());
        }

        Map<Long, Set<Long>> archivesInDatanodeIdMap = storagePoolThrift.archivesInDatanode;
        if (archivesInDatanodeIdMap != null && archivesInDatanodeIdMap.size() != 0) {
          Map<SimpleInstance, Set<SimpleArchiveMetadata>> archivesInDatanode =
              new HashMap<SimpleInstance, Set<SimpleArchiveMetadata>>();
          logger.debug("archives in datanode:[{}]", storagePoolThrift.archivesInDatanode);
          for (Entry<Long, Set<Long>> datanodeIdMapArchiveIds :
              archivesInDatanodeIdMap.entrySet()) {
            long datanodeIdLong = datanodeIdMapArchiveIds.getKey();
            SimpleInstance simpleInstance = instanceService.getInstances(datanodeIdLong);
            if (simpleInstance == null) {
              continue;
            }
            logger.debug("simpleInstance in datanode:[{}]", simpleInstance);
            Set<Long> archiveIds = datanodeIdMapArchiveIds.getValue();
            Set<SimpleArchiveMetadata> simpleArchives = new HashSet<SimpleArchiveMetadata>();
            for (long id : archiveIds) {
              boolean findFlag = false;
              for (ArchiveMetadataThrift archiveThrift : arhiveThriftListInStoragePool) {
                if (id == archiveThrift.getArchiveId()) {
                  findFlag = true;
                  SimpleArchiveMetadata archiveTmp = new SimpleArchiveMetadata();
                  archiveTmp.setArchiveId(String.valueOf(archiveThrift.getArchiveId()));
                  archiveTmp.setDeviceName(archiveThrift.getDevName());
                  archiveTmp.setLogicalSpace(archiveThrift.getLogicalSpace());
                  archiveTmp.setSerialNumber(archiveThrift.getSerialNumber());
                  archiveTmp.setStatus(ArchiveStatus.valueOf(archiveThrift.getStatus().name()));
                  archiveTmp.setStoragePool(String.valueOf(archiveThrift.getStoragePoolId()));
                  archiveTmp.setStorageType(String.valueOf(archiveThrift.getStoragetype()));
                  archiveTmp.setRate(String.valueOf(archiveThrift.getRate()));
                  double migrationRatio = 100.0;
                  if (archiveThrift.getTotalPageToMigrate() != 0) {
                    migrationRatio = archiveThrift.getAlreadyMigratedPage() * 1.0 / archiveThrift
                        .getTotalPageToMigrate() * 100;
                  }
                  archiveTmp.setMigrationRatio(String.valueOf(migrationRatio));
                  archiveTmp.setMigrationSpeed(String.valueOf(archiveThrift.getMigrationSpeed()));
                  simpleArchives.add(archiveTmp);
                  break;
                }
              }
              if (!findFlag) {
                SimpleArchiveMetadata unknowDisk = new SimpleArchiveMetadata();
                unknowDisk.setStoragePool(String.valueOf(storagePoolThrift.poolId));
                unknowDisk.setArchiveId(String.valueOf(id));
                unknowDisk.setDatanodeId(String.valueOf(datanodeIdLong));
                // for now don't display unknown archive cause network sub healthy
                simpleArchives.add(unknowDisk);
              }
            }
            archivesInDatanode.put(simpleInstance, simpleArchives);

          }
          simpleStoragePool.setArchivesInDatanode(archivesInDatanode);

        }
        storagePoolsList.add(simpleStoragePool);

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
    return storagePoolsList;
  }

  @Override
  public void updateStoragePool(SimpleStoragePool simpleStoragePool, long accountId)

      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      ServiceIsNotAvailableThrift,
      ArchiveNotFreeToUseExceptionThrift, ArchiveNotFoundExceptionThrift,
      StoragePoolNotExistedExceptionThrift,
      DomainNotExistedExceptionThrift, ArchiveIsUsingExceptionThrift,
      StoragePoolIsDeletingExceptionThrift,
      PermissionNotGrantExceptionThrift, AccessDeniedExceptionThrift,
      AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    if (simpleStoragePool == null || simpleStoragePool.getPoolId() == null) {
      logger.error("Invalid input params");
      throw new InvalidInputExceptionThrift();
    }
    if (simpleStoragePool.getPoolName() == null) {
      logger.error("Storage pool name can not be null");
      throw new InvalidInputExceptionThrift();
    }
    UpdateStoragePoolRequestThrift updateStoragePoolRequestThrift =
        new UpdateStoragePoolRequestThrift();
    updateStoragePoolRequestThrift.setRequestId(RequestIdBuilder.get());
    updateStoragePoolRequestThrift.setAccountId(accountId);
    StoragePoolThrift storagePoolThrift = new StoragePoolThrift();
    storagePoolThrift.setPoolId(Long.parseLong(simpleStoragePool.getPoolId()));
    storagePoolThrift.setPoolName(simpleStoragePool.getPoolName());
    storagePoolThrift.setDomainId(Long.parseLong(simpleStoragePool.getDomainId()));
    if (simpleStoragePool.getDescription() != null) {
      storagePoolThrift.setDescription(simpleStoragePool.getDescription());
    }
    storagePoolThrift.setStrategy(
        StoragePoolStrategyThrift.valueOf(simpleStoragePool.getStrategy()));
    Map<Long, Set<Long>> archiveInDatanode = new HashMap<>();
    for (Entry<SimpleInstance, Set<SimpleArchiveMetadata>> entry :
        simpleStoragePool.getArchivesInDatanode()
        .entrySet()) {
      long datanodeId = Long.parseLong(entry.getKey().getInstanceId());
      Set<Long> archiveIdSet = new HashSet<Long>();
      for (SimpleArchiveMetadata archive : entry.getValue()) {
        archiveIdSet.add(Long.parseLong(archive.getArchiveId()));
      }
      archiveInDatanode.put(datanodeId, archiveIdSet);
    }
    storagePoolThrift.setArchivesInDatanode(archiveInDatanode);
    updateStoragePoolRequestThrift.setStoragePool(storagePoolThrift);
    logger.debug("updateStoragePoolRequestThrift:[{}]", updateStoragePoolRequestThrift);
    try {
      InformationCenter.Iface iface = infoCenterClientFactory.build().getClient();
      iface.updateStoragePool(updateStoragePoolRequestThrift);
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
  public void removeArchiveFormStoragePool(String storagePoolId, String domainId, String datanodeId,
      String archiveId,
      long accountId)
      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      ServiceIsNotAvailableThrift,
      FailToRemoveArchiveFromStoragePoolExceptionThrift, ArchiveNotFoundExceptionThrift,
      StoragePoolNotExistedExceptionThrift, DomainNotExistedExceptionThrift,
      StoragePoolIsDeletingExceptionThrift, PermissionNotGrantExceptionThrift,
      AccessDeniedExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    if (storagePoolId == null || storagePoolId.equals("") || domainId == null || domainId.equals("")
        || archiveId == null || archiveId.equals("") || datanodeId == null || datanodeId.equals(
        "")) {
      logger.error("Invalid input params");
      throw new InvalidInputExceptionThrift();
    }
    RemoveArchiveFromStoragePoolRequestThrift request =
        new RemoveArchiveFromStoragePoolRequestThrift();
    request.setRequestId(RequestIdBuilder.get());
    request.setStoragePoolId(Long.parseLong(storagePoolId));
    request.setDomainId(Long.parseLong(domainId));
    request.setDatanodeInstanceId(Long.parseLong(datanodeId));
    request.setArchiveId(Long.parseLong(archiveId));
    request.setAccountId(accountId);
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.removeArchiveFromStoragePool(request);
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
  public void deleteStoragePool(String domainId, String storagePoolId, long accountId)

      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      StoragePoolNotExistedExceptionThrift, ServiceIsNotAvailableThrift,
      StillHaveVolumeExceptionThrift,
      DomainNotExistedExceptionThrift, StoragePoolIsDeletingExceptionThrift,
      ResourceNotExistsExceptionThrift,
      PermissionNotGrantExceptionThrift, AccessDeniedExceptionThrift,
      AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    if (domainId == null || domainId == "" || storagePoolId == null || storagePoolId == "") {
      logger.error("Invalid input params");
      throw new InvalidInputExceptionThrift();
    }
    DeleteStoragePoolRequestThrift request = new DeleteStoragePoolRequestThrift();
    request.setRequestId(RequestIdBuilder.get());
    request.setDomainId(Long.parseLong(domainId));
    request.setStoragePoolId(Long.parseLong(storagePoolId));
    request.setAccountId(accountId);

    try {
      InformationCenter.Iface iface = infoCenterClientFactory.build().getClient();
      iface.deleteStoragePool(request);
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

}
