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

package py.console.service.disk.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.archive.ArchiveStatus;
import py.common.Constants;
import py.common.PyService;
import py.common.RequestIdBuilder;
import py.console.bean.DiskInfo;
import py.console.bean.Maintenance;
import py.console.bean.ServerNode;
import py.console.bean.SimpleArchiveMetadata;
import py.console.bean.SimpleDomain;
import py.console.bean.SimpleInstance;
import py.console.bean.SimpleInstanceMetadata;
import py.console.bean.SimpleSensorInfo;
import py.console.bean.SimpleStoragePool;
import py.console.bean.SmartInfo;
import py.console.service.disk.DiskService;
import py.console.service.domain.DomainService;
import py.console.service.instance.InstanceService;
import py.console.service.storagepool.StoragePoolService;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.infocenter.client.InformationCenterClientFactory;
import py.thrift.icshare.GetArchiveRequestThrift;
import py.thrift.icshare.GetArchiveResponseThrift;
import py.thrift.icshare.GetArchivesRequestThrift;
import py.thrift.icshare.GetArchivesResponseThrift;
import py.thrift.icshare.ListArchivesRequestThrift;
import py.thrift.icshare.ListArchivesResponseThrift;
import py.thrift.infocenter.service.CancelMaintenanceRequest;
import py.thrift.infocenter.service.InformationCenter;
import py.thrift.infocenter.service.InstanceMaintainRequest;
import py.thrift.infocenter.service.InstanceMaintenanceThrift;
import py.thrift.infocenter.service.ListInstanceMaintenancesRequest;
import py.thrift.infocenter.service.ListInstanceMaintenancesResponse;
import py.thrift.share.AccessDeniedExceptionThrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.ArchiveManagerNotSupportExceptionThrift;
import py.thrift.share.ArchiveMetadataThrift;
import py.thrift.share.ArchiveTypeThrift;
import py.thrift.share.DatanodeStatusThrift;
import py.thrift.share.DeleteServerNodesRequestThrift;
import py.thrift.share.DiskHasBeenOfflineThrift;
import py.thrift.share.DiskHasBeenOnlineThrift;
import py.thrift.share.DiskIsBusyThrift;
import py.thrift.share.DiskNameIllegalExceptionThrift;
import py.thrift.share.DiskNotBrokenThrift;
import py.thrift.share.DiskNotFoundExceptionThrift;
import py.thrift.share.DiskNotMismatchConfigThrift;
import py.thrift.share.DiskSizeCanNotSupportArchiveTypesThrift;
import py.thrift.share.DiskSmartInfoThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.GetDiskSmartInfoRequestThrift;
import py.thrift.share.GetDiskSmartInfoResponseThrift;
import py.thrift.share.HardDiskInfoThrift;
import py.thrift.share.InstanceMetadataThrift;
import py.thrift.share.InternalErrorThrift;
import py.thrift.share.InvalidInputExceptionThrift;
import py.thrift.share.ListServerNodeByIdRequestThrift;
import py.thrift.share.ListServerNodeByIdResponseThrift;
import py.thrift.share.ListServerNodesRequestThrift;
import py.thrift.share.ListServerNodesResponseThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.NotEnoughSpaceExceptionThrift;
import py.thrift.share.OfflineDiskRequest;
import py.thrift.share.OnlineDiskRequest;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.SensorInfoThrift;
import py.thrift.share.ServerNodeIsUnknownThrift;
import py.thrift.share.ServerNodeNotExistExceptionThrift;
import py.thrift.share.ServerNodePositionIsRepeatExceptionThrift;
import py.thrift.share.ServerNodeThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.SettleArchiveTypeRequest;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;
import py.thrift.share.UpdateServerNodeRequestThrift;
import py.thrift.share.UpdateServerNodeResponseThrift;

/**
 * DiskServiceImpl.
 */
public class DiskServiceImpl implements DiskService {

  private static final Logger logger = LoggerFactory.getLogger(DiskServiceImpl.class);

  private InformationCenterClientFactory infoCenterClientFactory;

  private StoragePoolService poolService;

  public InstanceService instanceService;

  public DomainService domainService;

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

  public DomainService getDomainService() {
    return domainService;
  }

  public void setDomainService(DomainService domainService) {
    this.domainService = domainService;
  }

  @Override
  public void onlineDisk(InstanceMetadataThrift instanceMetadata,
      ArchiveMetadataThrift archiveMetadata,
      long accountId)
      throws DiskNotFoundExceptionThrift, DiskHasBeenOnlineThrift, ServiceHavingBeenShutdownThrift,
      AccessDeniedExceptionThrift, InternalErrorThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift, AccountNotFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift, TException {
    logger.debug(
        "DiskServiceImpl launchDisk().params are:instanceMetadata:[{}],archiveMetadata:[{}],"
            + "accountId:[{}]",
        instanceMetadata, archiveMetadata, accountId);
    InformationCenter.Iface client;
    try {
      client = infoCenterClientFactory.build().getClient();
      OnlineDiskRequest request = new OnlineDiskRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      request.setInstance(instanceMetadata);
      request.setOnlineArchive(archiveMetadata);
      logger.debug("online disk request is {}", request);
      client.onlineDisk(request);
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
    return;
  }

  /**
   * settleArchiveType is instead of online disk.
   *
   * @param accountId account id
   * @param archiveId archive id
   * @param instanceMetadata instance metadata
   */
  @Override
  public void settleArchiveType(long accountId, long archiveId, String diskName,
      InstanceMetadataThrift instanceMetadata, List<ArchiveTypeThrift> archiveTypes)
      throws DiskNotFoundExceptionThrift, DiskSizeCanNotSupportArchiveTypesThrift,
      ServiceHavingBeenShutdownThrift, ArchiveManagerNotSupportExceptionThrift,
      DiskHasBeenOfflineThrift,
      ServiceIsNotAvailableThrift, PermissionNotGrantExceptionThrift, NetworkErrorExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift, TException {
    logger.debug(
        "DiskServiceImpl settleArchiveType().params are:instanceMetadata:[{}],archiiveId:[{}],"
            + "accountId:[{}]",
        instanceMetadata, archiveId, accountId);

    SettleArchiveTypeRequest request = new SettleArchiveTypeRequest();
    request.setAccountId(accountId);
    request.setArchiveId(archiveId);
    request.setInstance(instanceMetadata);
    request.setArchiveTypes(archiveTypes);
    request.setDevName(diskName);

    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.settleArchiveType(request);
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
  public void offlineDisk(InstanceMetadataThrift instanceMetadata,
      ArchiveMetadataThrift archiveMetadata,
      long accountId)
      throws DiskNotFoundExceptionThrift, DiskHasBeenOfflineThrift, ServiceHavingBeenShutdownThrift,
      AccessDeniedExceptionThrift, DiskIsBusyThrift, NetworkErrorExceptionThrift,
      ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift, AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, TException {
    logger.debug(
        "DiskServiceImpl unlaunchDisk().params are:instanceMetadata:[{}],archiveMetadata:[{}],"
            + "accountId:[{}]",
        instanceMetadata, archiveMetadata, accountId);
    InformationCenter.Iface client = null;
    try {
      client = infoCenterClientFactory.build().getClient();
      OfflineDiskRequest request = new OfflineDiskRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      request.setInstance(instanceMetadata);
      request.setOfflineArchive(archiveMetadata);
      logger.debug("offline disk request is {}", request);
      client.offlineDisk(request);
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
    return;
  }

  @Override
  public List<SimpleInstanceMetadata> listInstanceMetadata()
      throws TTransportException, InternalErrorThrift, NotEnoughSpaceExceptionThrift,
      GenericThriftClientFactoryException, EndPointNotFoundException,
      TooManyEndPointFoundException, TException {
    InformationCenter.Iface clientIc = null;
    try {
      clientIc = infoCenterClientFactory.build().getClient();
      ListArchivesRequestThrift request = new ListArchivesRequestThrift();
      request.setRequestId(RequestIdBuilder.get());

      ListArchivesResponseThrift response = clientIc.listArchives(request);
      logger.debug("listInstanceMetadata response {}", response);
      return buildSimpleInstanceMetadataList(response);
    } catch (TTransportException e) {
      logger.error("Exception catch", e);
    } catch (InternalErrorThrift e) {
      logger.error("Exception catch", e);
    } catch (InvalidInputExceptionThrift e) {
      logger.error("Exception catch", e);
    } catch (NotEnoughSpaceExceptionThrift e) {
      logger.error("Exception catch", e);
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
    } catch (TException e) {
      logger.error("Exception catch", e);
    }
    return null;
  }

  @Override
  public List<SimpleArchiveMetadata> listAllDisks(long accountId)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift, TException {
    InformationCenter.Iface client = null;
    ListArchivesRequestThrift request = new ListArchivesRequestThrift();
    request.setRequestId(RequestIdBuilder.get());
    ListArchivesResponseThrift response = new ListArchivesResponseThrift();
    List<SimpleArchiveMetadata> diskList = new ArrayList<>();
    Set<SimpleStoragePool> poolSet = new HashSet<>();
    try {
      client = infoCenterClientFactory.build().getClient();
      response = client.listArchives(request);
      poolSet = poolService.listStoragePools(null, null, accountId);
      if (response.getInstanceMetadata() != null) {
        for (InstanceMetadataThrift instanceMetadataThrift : response.getInstanceMetadata()) {
          for (ArchiveMetadataThrift archiveThrift : instanceMetadataThrift.getArchiveMetadata()) {
            SimpleArchiveMetadata disk = new SimpleArchiveMetadata();
            disk.setArchiveId(String.valueOf(archiveThrift.getArchiveId()));
            disk.setDeviceName(archiveThrift.getDevName());
            disk.setStatus(ArchiveStatus.valueOf(archiveThrift.getStatus().name()));
            disk.setSerialNumber(archiveThrift.getSerialNumber());
            disk.setLogicalSpace(archiveThrift.getLogicalSpace());
            disk.setLogicalFreeSpace(archiveThrift.getLogicalFreeSpace());
            disk.setSlotNumber(archiveThrift.getSlotNo());
            disk.setDataSizeMb(String.valueOf(archiveThrift.getDataSizeMb()));

            disk.setArchiveType(String.valueOf(archiveThrift.getType().name()));
            disk.setStorageType(archiveThrift.getStoragetype().name());
            disk.setDataNodeEndPoint(instanceMetadataThrift.getEndpoint());
            disk.setDatanodeId(String.valueOf(instanceMetadataThrift.getInstanceId()));
            double migrationRatio = 100.0;
            if (archiveThrift.getTotalPageToMigrate() != 0) {
              migrationRatio =
                  archiveThrift.getAlreadyMigratedPage() * 1.0
                      / archiveThrift.getTotalPageToMigrate()
                      * 100;
            }
            disk.setMigrationRatio(String.valueOf(migrationRatio));
            disk.setMigrationSpeed(String.valueOf(archiveThrift.getMigrationSpeed()));

            if (archiveThrift.isSetStoragePoolId()) {
              disk.setStoragePool(String.valueOf(archiveThrift.getStoragePoolId()));
              for (SimpleStoragePool pool : poolSet) {
                logger.debug("pool.getPoolId() is {}, archiveThrift.getStoragePoolId()is {}",
                    pool.getPoolId(), archiveThrift.getStoragePoolId());
                if (pool.getPoolId().equals(String.valueOf(archiveThrift.getStoragePoolId()))) {
                  disk.setPoolName(pool.getPoolName());
                  disk.setDomainId(pool.getDomainId());
                  disk.setDomainName(pool.getDomainName());
                }
              }
            }
            diskList.add(disk);
          }
        }
      }

    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (ServiceHavingBeenShutdownThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (ServiceIsNotAvailableThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }

    return diskList;
  }

  private List<SimpleInstanceMetadata> buildSimpleInstanceMetadataList(
      ListArchivesResponseThrift response) {
    List<SimpleInstanceMetadata> instanceMetadataList = new ArrayList<>();
    List<InstanceMetadataThrift> instanceMetadataListInThrift = response.getInstanceMetadata();
    for (InstanceMetadataThrift instanceMetadata : instanceMetadataListInThrift) {
      SimpleInstanceMetadata simpleData = new SimpleInstanceMetadata();
      simpleData.setCapacity(instanceMetadata.getCapacity());
      simpleData.setFreeSpace(instanceMetadata.getFreeSpace());
      simpleData.setLogicalCapacity(instanceMetadata.getLogicalCapacity());
      simpleData.setInstanceId(String.valueOf(instanceMetadata.getInstanceId()));
      simpleData.setEndPoint(instanceMetadata.getEndpoint());
      simpleData.setArchives(buildSimpleArchiveMetadataList(instanceMetadata.getArchiveMetadata(),
          instanceMetadata.getEndpoint()));
      if (instanceMetadata.isSetInstanceDomain() && instanceMetadata.getInstanceDomain()
          .isSetDomianId()) {
        simpleData.setDomainId(instanceMetadata.getInstanceDomain().getDomianId());
      }
      instanceMetadataList.add(simpleData);
    }

    return instanceMetadataList;
  }

  @Override
  public List<SimpleArchiveMetadata> getDiskDetail(List<Long> diskIds, long accountId)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift, TException {
    InformationCenter.Iface client = null;
    GetArchiveRequestThrift request = new GetArchiveRequestThrift();
    request.setRequestId(RequestIdBuilder.get());
    request.setArchiveIds(diskIds);
    GetArchiveResponseThrift response = new GetArchiveResponseThrift();
    List<SimpleArchiveMetadata> diskList = new ArrayList<>();
    Set<SimpleStoragePool> poolSet = new HashSet<>();
    logger.debug("request {}", request);
    try {
      client = infoCenterClientFactory.build().getClient();
      response = client.getArchive(request);
      poolSet = poolService.listStoragePools(null, null, Constants.SUPERADMIN_ACCOUNT_ID);
      if (response.getInstanceMetadata() != null) {
        for (InstanceMetadataThrift instanceMetadataThrift : response.getInstanceMetadata()) {
          for (ArchiveMetadataThrift archiveThrift : instanceMetadataThrift.getArchiveMetadata()) {
            SimpleArchiveMetadata disk = new SimpleArchiveMetadata();
            disk.setArchiveId(String.valueOf(archiveThrift.getArchiveId()));
            disk.setDeviceName(archiveThrift.getDevName());
            disk.setStatus(ArchiveStatus.valueOf(archiveThrift.getStatus().name()));
            disk.setSerialNumber(archiveThrift.getSerialNumber());
            disk.setLogicalSpace(archiveThrift.getLogicalSpace());
            disk.setLogicalFreeSpace(archiveThrift.getLogicalFreeSpace());

            disk.setArchiveType(String.valueOf(archiveThrift.getType().name()));
            disk.setStorageType(archiveThrift.getStoragetype().name());
            disk.setDataNodeEndPoint(instanceMetadataThrift.getEndpoint());
            disk.setDatanodeId(String.valueOf(instanceMetadataThrift.getInstanceId()));
            double migrationRatio = 100.0;
            if (archiveThrift.getTotalPageToMigrate() != 0) {
              migrationRatio =
                  archiveThrift.getAlreadyMigratedPage() * 1.0
                      / archiveThrift.getTotalPageToMigrate()
                      * 100;
            }
            disk.setMigrationRatio(String.valueOf(migrationRatio));
            disk.setMigrationSpeed(String.valueOf(archiveThrift.getMigrationSpeed()));

            if (archiveThrift.isSetStoragePoolId()) {
              disk.setStoragePool(String.valueOf(archiveThrift.getStoragePoolId()));
              for (SimpleStoragePool pool : poolSet) {
                logger.debug("pool.getPoolId() is {}, archiveThrift.getStoragePoolId()is {}",
                    pool.getPoolId(), archiveThrift.getStoragePoolId());
                if (pool.getPoolId().equals(String.valueOf(archiveThrift.getStoragePoolId()))) {
                  disk.setPoolName(pool.getPoolName());
                  disk.setDomainId(pool.getDomainId());
                  disk.setDomainName(pool.getDomainName());
                }
              }
            }
            diskList.add(disk);
          }
        }
      }

    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (ServiceHavingBeenShutdownThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (ServiceIsNotAvailableThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }

    return diskList;
  }

  @Override
  public List<SimpleArchiveMetadata> getArchives(long instanceId)
      throws TTransportException, InternalErrorThrift, InvalidInputExceptionThrift,
      NotEnoughSpaceExceptionThrift, GenericThriftClientFactoryException, EndPointNotFoundException,
      TooManyEndPointFoundException, TException {
    InformationCenter.Iface clientIc = null;
    try {
      clientIc = infoCenterClientFactory.build().getClient();

      GetArchivesRequestThrift request = new GetArchivesRequestThrift();
      request.setRequestId(RequestIdBuilder.get());
      request.setInstanceId(instanceId);
      GetArchivesResponseThrift response = clientIc.getArchives(request);
      logger.debug("get archives response {}", response);
      return buildSimpleArchiveMetadataList(response.getInstanceMetadata().archiveMetadata,
          response.getInstanceMetadata().getEndpoint());
    } catch (TTransportException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (InternalErrorThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (InvalidInputExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (NotEnoughSpaceExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }
  }

  /**
   * generate smart info.
   *
   * @return smart info list
   */
  public List<SmartInfo> generateSmartInfo() {

    List<SmartInfo> smartInfoList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      SmartInfo smartInfo = new SmartInfo();
      smartInfo.setId("00001" + i);
      smartInfo.setAttributeNameEn("hello" + i);
      smartInfo.setAttributeNameCn("您好" + i);
      smartInfo.setFlag("flag");
      smartInfo.setValue("123");
      smartInfo.setWorst("100");
      smartInfo.setThresh("12");
      smartInfo.setType("type");
      smartInfo.setUpdated("update");
      smartInfo.setWhenFailed("whenfailed");
      smartInfo.setRawValue("rawValue");
      smartInfoList.add(smartInfo);
    }

    return smartInfoList;
  }

  @Override
  public List<ServerNode> listServernodes(long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, Exception {
    InformationCenter.Iface client = null;
    ListServerNodesRequestThrift request = new ListServerNodesRequestThrift();
    ListServerNodesResponseThrift response = new ListServerNodesResponseThrift();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setLimit(100000);
    List<ServerNode> serverNodeList = new ArrayList<>();
    logger.debug("listServernodes request is {}", request);
    try {
      client = infoCenterClientFactory.build().getClient();
      response = client.listServerNodes(request);
      logger.debug("listServernodes response is {}", response);
      if (response != null && response.getServerNodesList().size() != 0) {
        List<SimpleDomain> domainList = domainService.listDomains(null,
            Constants.SUPERADMIN_ACCOUNT_ID);
        List<SimpleInstanceMetadata> dataNodeList = new ArrayList<>();
        dataNodeList = listInstanceMetadata();
        logger.debug("dataNodeList is {}", dataNodeList);
        for (ServerNodeThrift nodeThrift : response.getServerNodesList()) {
          ServerNode node = new ServerNode();
          node.setServerId(nodeThrift.getServerId());
          node.setModelInfo(nodeThrift.getModelInfo());
          node.setCpuInfo(nodeThrift.getCpuInfo());
          node.setMemoryInfo(nodeThrift.getMemoryInfo());
          node.setDiskInfo(nodeThrift.getDiskInfo());
          node.setNetworkCardInfo(nodeThrift.getNetworkCardInfo());
          node.setManageIp(nodeThrift.getManageIp());
          node.setGatewayIp(nodeThrift.getGatewayIp());
          node.setStoreIp(nodeThrift.getStoreIp());
          node.setRackNo(nodeThrift.getRackNo());
          node.setSlotNo(nodeThrift.getSlotNo());
          node.setStatus(nodeThrift.getStatus());

          List<SimpleSensorInfo> simpleSensorInfos = new ArrayList<>();
          for (SensorInfoThrift sensorInfoThrift : nodeThrift.getSensorInfos()) {
            SimpleSensorInfo simpleSensorInfo = new SimpleSensorInfo();
            simpleSensorInfo.setName(sensorInfoThrift.getName());
            simpleSensorInfo.setStatus(sensorInfoThrift.getStatus());
            simpleSensorInfo.setValue(sensorInfoThrift.getValue());
            simpleSensorInfos.add(simpleSensorInfo);
          }
          node.setSensorInfos(simpleSensorInfos);
          // 如果datanode的状态是被隔离，那么服务器的状态也改成被隔离

          if (nodeThrift.getDatanodeStatus() != null && nodeThrift.getDatanodeStatus().name()
              .equals(DatanodeStatusThrift.SEPARATED.name())) {
            node.setStatus(nodeThrift.getDatanodeStatus().name());
          }
          node.setChildFramNo(nodeThrift.getChildFramNo());
          node.setHostName(nodeThrift.getHostName());
          List<DiskInfo> diskNotInDataNodeList = new ArrayList<>();
          List<SimpleArchiveMetadata> diskInDataNodeList = new ArrayList<>();
          boolean serverNodeHasDataNodeDiskFlag = false;
          for (SimpleInstanceMetadata dataNode : dataNodeList) {
            logger.debug("node.getNetworkCardInfo() is {},dataNode.getEndPoint() is {}",
                node.getNetworkCardInfo(), dataNode.getEndPoint());
            if (node.getNetworkCardInfo().contains(dataNode.getEndPoint().split(":")[0])) {
              diskInDataNodeList = dataNode.getArchives();
              logger.debug("diskInDataNodeList is {}", diskInDataNodeList);
              serverNodeHasDataNodeDiskFlag = true;
              for (HardDiskInfoThrift diskInfoThrift : nodeThrift.getDiskInfoSet()) {
                boolean diskIsInDataNodeFlag = false;
                for (SimpleArchiveMetadata archiveMetadata : diskInDataNodeList) {
                  logger.debug(
                      "archiveMetadata.getDeviceName() is {},diskInfoThrift.getName() is {}",
                      archiveMetadata.getDeviceName(), diskInfoThrift.getName());
                  if (archiveMetadata.getDeviceName().equals(diskInfoThrift.getName())) {
                    archiveMetadata.setModel(diskInfoThrift.getModel());
                    archiveMetadata.setVendor(diskInfoThrift.getVendor());
                    archiveMetadata.setRate(String.valueOf(diskInfoThrift.getRate()));
                    archiveMetadata.setWwn(diskInfoThrift.getWwn());
                    archiveMetadata.setControllerId(diskInfoThrift.getControllerId());
                    archiveMetadata.setSlotNumber(diskInfoThrift.getSlotNumber());
                    archiveMetadata.setEnclosureId(diskInfoThrift.getEnclosureId());
                    archiveMetadata.setCardType(diskInfoThrift.getCardType());
                    archiveMetadata.setSwith(diskInfoThrift.getSwith());
                    archiveMetadata.setDiskSerialNumber(diskInfoThrift.getSerialNumber());
                    diskIsInDataNodeFlag = true;
                    break;
                  }
                }
                if (!diskIsInDataNodeFlag) {
                  DiskInfo disk = new DiskInfo();
                  disk.setName(diskInfoThrift.getName());
                  disk.setVendor(diskInfoThrift.getVendor());
                  disk.setModel(diskInfoThrift.getModel());
                  disk.setSn(diskInfoThrift.getSn());
                  disk.setSsdOrHdd(diskInfoThrift.getSsdOrHdd());
                  disk.setRate(String.valueOf(diskInfoThrift.getRate()));
                  disk.setSize(diskInfoThrift.getSize());
                  disk.setWwn(diskInfoThrift.getWwn());
                  disk.setControllerId(diskInfoThrift.getControllerId());
                  disk.setSlotNumber(diskInfoThrift.getSlotNumber());
                  disk.setEnclosureId(diskInfoThrift.getEnclosureId());
                  disk.setCardType(diskInfoThrift.getCardType());
                  disk.setSwith(diskInfoThrift.getSwith());
                  disk.setSerialNumber(diskInfoThrift.getSerialNumber());
                  diskNotInDataNodeList.add(disk);
                }

              }
              // get domain
              SimpleDomain domain = new SimpleDomain();
              Long domainId = dataNode.getDomainId();
              if (domainId != null) {
                domain = domainService.getDomainByIdFromDomainList(domainId, domainList);
              }
              logger.debug("setDomain is {}", domain);
              node.setDomain(domain);

            }
          }
          if (!serverNodeHasDataNodeDiskFlag) {
            for (HardDiskInfoThrift diskInfoThrift : nodeThrift.getDiskInfoSet()) {
              DiskInfo disk = new DiskInfo();
              disk.setName(diskInfoThrift.getName());
              disk.setVendor(diskInfoThrift.getVendor());
              disk.setModel(diskInfoThrift.getModel());
              disk.setSn(diskInfoThrift.getSn());
              disk.setSsdOrHdd(diskInfoThrift.getSsdOrHdd());
              disk.setRate(String.valueOf(diskInfoThrift.getRate()));
              disk.setSize(diskInfoThrift.getSize());
              disk.setWwn(diskInfoThrift.getWwn());
              disk.setControllerId(diskInfoThrift.getControllerId());
              disk.setSlotNumber(diskInfoThrift.getSlotNumber());
              disk.setEnclosureId(diskInfoThrift.getEnclosureId());
              disk.setCardType(diskInfoThrift.getCardType());
              disk.setSwith(diskInfoThrift.getSwith());
              disk.setSerialNumber(diskInfoThrift.getSerialNumber());

              diskNotInDataNodeList.add(disk);
            }
          }
          List<SimpleArchiveMetadata> diskCahe = new ArrayList<>();
          List<SimpleArchiveMetadata> diskRaw = new ArrayList<>();
          for (SimpleArchiveMetadata disk : diskInDataNodeList) {
            if (disk.getArchiveType().equals("RAW_DISK")) {
              diskRaw.add(disk);
            } else {
              diskCahe.add(disk);
            }
          }
          node.setDiskCache(diskCahe);
          node.setDiskRaw(diskRaw);
          node.setDiskNotInDataNode(diskNotInDataNodeList);

          serverNodeList.add(node);
        }
        List<InstanceMaintenanceThrift> maintenanceListThrift = new ArrayList<>();
        try {
          maintenanceListThrift = getMaintenanceList(Constants.SUPERADMIN_ACCOUNT_ID);
          logger.debug("maintenanceListThrift is {}", maintenanceListThrift);

        } catch (Exception e) {
          logger.error("Exception catch ", e);
        }
        // get service instance on this node
        List<SimpleInstance> instanceTmpList = new ArrayList<>();

        instanceTmpList = instanceService.getAll(Constants.SUPERADMIN_ACCOUNT_ID);

        for (ServerNode node : serverNodeList) {
          List<SimpleInstance> instancesOnNode = new ArrayList<>();
          for (SimpleInstance instance : instanceTmpList) {
            //                    if (Pattern.matches(instance.getHost() + "[^\\d]", node
            //                    .getNetworkCardInfo()))
            if (node.getNetworkCardInfo().contains(instance.getHost())) {

              instancesOnNode.add(instance);
              // if the node has datanode service, then we should set this node maintenance info.
              if (instance.getInstanceName().equals(PyService.DATANODE.getServiceName())) {
                for (InstanceMaintenanceThrift maintenanceThrift : maintenanceListThrift) {

                  if (instance.getInstanceId()
                      .equals(String.valueOf(maintenanceThrift.getInstanceId()))) {
                    Maintenance maintenance = new Maintenance();
                    maintenance.setStartTime(String.valueOf(maintenanceThrift.getStartTime()));
                    maintenance.setEndTime(String.valueOf(maintenanceThrift.getEndTime()));
                    maintenance.setDuration(String.valueOf(maintenanceThrift.getDuration()));
                    maintenance.setMessage("success");
                    maintenance.setCurrentTime(String.valueOf(maintenanceThrift.getCurrentTime()));
                    node.setMaintenance(maintenance);

                  }
                }
              }
            }
          }
          node.setInstances(instancesOnNode);

        }
      }

      logger.debug("serverNodeList is {}", serverNodeList);

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

    return serverNodeList;
  }

  @Override
  public ServerNode getServerNode(long accountId, String serverNodeId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, Exception {
    ListServerNodeByIdRequestThrift request = new ListServerNodeByIdRequestThrift();
    ListServerNodeByIdResponseThrift response = new ListServerNodeByIdResponseThrift();
    request.setRequestId(RequestIdBuilder.get());
    request.setServerId(serverNodeId);
    InformationCenter.Iface client = null;
    logger.debug("getServerNode request is {}", request);
    ServerNode node = new ServerNode();
    try {
      client = infoCenterClientFactory.build().getClient();
      response = client.listServerNodeById(request);
      if (response != null) {
        List<SimpleInstanceMetadata> dataNodeList = new ArrayList<>();
        dataNodeList = listInstanceMetadata();

        logger.debug("dataNodeList is {}", dataNodeList);
        ServerNodeThrift nodeThrift = response.getServerNode();
        node.setServerId(nodeThrift.getServerId());
        node.setModelInfo(nodeThrift.getModelInfo());
        node.setCpuInfo(nodeThrift.getCpuInfo());
        node.setMemoryInfo(nodeThrift.getMemoryInfo());
        node.setDiskInfo(nodeThrift.getDiskInfo());
        node.setNetworkCardInfo(nodeThrift.getNetworkCardInfo());
        node.setManageIp(nodeThrift.getManageIp());
        node.setGatewayIp(nodeThrift.getGatewayIp());
        node.setStoreIp(nodeThrift.getStoreIp());
        node.setRackNo(nodeThrift.getRackNo());
        node.setSlotNo(nodeThrift.getSlotNo());
        node.setStatus(nodeThrift.getStatus());
        // 如果datanode的状态是被隔离，那么服务器的状态也改成被隔离

        if (nodeThrift.getDatanodeStatus() != null && nodeThrift.getDatanodeStatus().name()
            .equals(DatanodeStatusThrift.SEPARATED.name())) {
          node.setStatus(nodeThrift.getDatanodeStatus().name());
        }
        node.setChildFramNo(nodeThrift.getChildFramNo());
        node.setHostName(nodeThrift.getHostName());
        List<DiskInfo> diskNotInDataNodeList = new ArrayList<>();
        List<SimpleArchiveMetadata> diskInDataNodeList = new ArrayList<>();
        boolean serverNodeHasDataNodeDiskFlag = false;
        for (SimpleInstanceMetadata dataNode : dataNodeList) {
          logger.debug("node.getNetworkCardInfo() is {},dataNode.getEndPoint() is {}",
              node.getNetworkCardInfo(), dataNode.getEndPoint());
          //                    if (Pattern.matches(dataNode.getEndPoint().split(":")[0] +
          //                    "[^\\d]", node.getNetworkCardInfo()))
          if (node.getNetworkCardInfo().contains(dataNode.getEndPoint().split(":")[0])) {

            diskInDataNodeList = dataNode.getArchives();
            logger.debug("diskInDataNodeList is {}", diskInDataNodeList);
            serverNodeHasDataNodeDiskFlag = true;
            for (HardDiskInfoThrift diskInfoThrift : nodeThrift.getDiskInfoSet()) {
              boolean diskIsInDataNodeFlag = false;
              for (SimpleArchiveMetadata archiveMetadata : diskInDataNodeList) {
                logger.debug(
                    "archiveMetadata.getDeviceName() is {},diskInfoThrift.getName() is {}",
                    archiveMetadata.getDeviceName(), diskInfoThrift.getName());
                if (archiveMetadata.getDeviceName().equals(diskInfoThrift.getName())) {
                  archiveMetadata.setModel(diskInfoThrift.getModel());
                  archiveMetadata.setVendor(diskInfoThrift.getVendor());
                  archiveMetadata.setRate(String.valueOf(diskInfoThrift.getRate()));
                  archiveMetadata.setWwn(diskInfoThrift.getWwn());
                  archiveMetadata.setControllerId(diskInfoThrift.getControllerId());
                  archiveMetadata.setSlotNumber(diskInfoThrift.getSlotNumber());
                  archiveMetadata.setEnclosureId(diskInfoThrift.getEnclosureId());
                  archiveMetadata.setCardType(diskInfoThrift.getCardType());
                  archiveMetadata.setSwith(diskInfoThrift.getSwith());
                  archiveMetadata.setDiskSerialNumber(diskInfoThrift.getSerialNumber());
                  if (dataNode.getDomainId() != null) {
                    archiveMetadata.setDomainId(String.valueOf(dataNode.getDomainId()));
                  }
                  // set smart info
                  List<SmartInfo> smartInfoList = new ArrayList<>();
                  if (diskInfoThrift.getSmartInfo() != null) {
                    for (DiskSmartInfoThrift diskSmartInfoThrift : diskInfoThrift
                        .getSmartInfo()) {
                      SmartInfo smartInfo = new SmartInfo();
                      smartInfo.setId(String.valueOf(diskSmartInfoThrift.getId()));
                      smartInfo.setAttributeNameCn(diskSmartInfoThrift.getAttributeNameCn());
                      smartInfo.setAttributeNameEn(diskSmartInfoThrift.getAttributeNameEn());
                      smartInfo.setFlag(diskSmartInfoThrift.getFlag());
                      smartInfo.setValue(diskSmartInfoThrift.getValue());
                      smartInfo.setWorst(diskSmartInfoThrift.getWorst());
                      smartInfo.setThresh(diskSmartInfoThrift.getThresh());
                      smartInfo.setType(diskSmartInfoThrift.getType());
                      smartInfo.setUpdated(diskSmartInfoThrift.getUpdated());
                      smartInfo.setWhenFailed(diskSmartInfoThrift.getWhenFailed());
                      smartInfo.setRawValue(diskSmartInfoThrift.getRawValue());
                      smartInfoList.add(smartInfo);
                    }
                  }
                  smartInfoList.addAll(generateSmartInfo()); // 填充假数据
                  archiveMetadata.setSmartInfos(smartInfoList);

                  diskIsInDataNodeFlag = true;
                  break;
                }
              }
              if (!diskIsInDataNodeFlag) {
                DiskInfo disk = new DiskInfo();
                disk.setName(diskInfoThrift.getName());
                disk.setVendor(diskInfoThrift.getVendor());
                disk.setModel(diskInfoThrift.getModel());
                disk.setSn(diskInfoThrift.getSn());
                disk.setSsdOrHdd(diskInfoThrift.getSsdOrHdd());
                disk.setRate(String.valueOf(diskInfoThrift.getRate()));
                disk.setSize(diskInfoThrift.getSize());
                disk.setWwn(diskInfoThrift.getWwn());
                disk.setControllerId(diskInfoThrift.getControllerId());
                disk.setSlotNumber(diskInfoThrift.getSlotNumber());
                disk.setEnclosureId(diskInfoThrift.getEnclosureId());
                disk.setCardType(diskInfoThrift.getCardType());
                disk.setSwith(diskInfoThrift.getSwith());
                disk.setSerialNumber(diskInfoThrift.getSerialNumber());
                // set smart info
                List<SmartInfo> smartInfoList = new ArrayList<>();
                if (diskInfoThrift.getSmartInfo() != null) {
                  for (DiskSmartInfoThrift diskSmartInfoThrift : diskInfoThrift.getSmartInfo()) {
                    SmartInfo smartInfo = new SmartInfo();
                    smartInfo.setId(String.valueOf(diskSmartInfoThrift.getId()));
                    smartInfo.setAttributeNameCn(diskSmartInfoThrift.getAttributeNameCn());
                    smartInfo.setAttributeNameEn(diskSmartInfoThrift.getAttributeNameEn());
                    smartInfo.setFlag(diskSmartInfoThrift.getFlag());
                    smartInfo.setValue(diskSmartInfoThrift.getValue());
                    smartInfo.setWorst(diskSmartInfoThrift.getWorst());
                    smartInfo.setThresh(diskSmartInfoThrift.getThresh());
                    smartInfo.setType(diskSmartInfoThrift.getType());
                    smartInfo.setUpdated(diskSmartInfoThrift.getUpdated());
                    smartInfo.setWhenFailed(diskSmartInfoThrift.getWhenFailed());
                    smartInfo.setRawValue(diskSmartInfoThrift.getRawValue());
                    smartInfoList.add(smartInfo);
                  }
                }
                smartInfoList.addAll(generateSmartInfo()); // 填充假数据
                disk.setSmartInfos(smartInfoList);
                logger.debug("smartInfoList is {}", smartInfoList);
                diskNotInDataNodeList.add(disk);
              }

            }
            // get domain
            SimpleDomain domain = new SimpleDomain();
            Long domainId = dataNode.getDomainId();
            if (domainId != null) {
              List<Long> ids = new ArrayList<>();
              ids.add(Long.valueOf(domainId));
              domain = domainService.listDomains(ids, Constants.SUPERADMIN_ACCOUNT_ID).get(0);
            }
            logger.debug("setDomain is {}", domain);
            node.setDomain(domain);

          }
        }
        if (!serverNodeHasDataNodeDiskFlag) {
          for (HardDiskInfoThrift diskInfoThrift : nodeThrift.getDiskInfoSet()) {
            DiskInfo disk = new DiskInfo();
            disk.setName(diskInfoThrift.getName());
            disk.setVendor(diskInfoThrift.getVendor());
            disk.setModel(diskInfoThrift.getModel());
            disk.setSn(diskInfoThrift.getSn());
            disk.setSsdOrHdd(diskInfoThrift.getSsdOrHdd());
            disk.setRate(String.valueOf(diskInfoThrift.getRate()));
            disk.setSize(diskInfoThrift.getSize());
            disk.setWwn(diskInfoThrift.getWwn());
            disk.setControllerId(diskInfoThrift.getControllerId());
            disk.setSlotNumber(diskInfoThrift.getSlotNumber());
            disk.setEnclosureId(diskInfoThrift.getEnclosureId());
            disk.setCardType(diskInfoThrift.getCardType());
            disk.setSwith(diskInfoThrift.getSwith());
            disk.setSerialNumber(diskInfoThrift.getSerialNumber());
            // set smart info
            List<SmartInfo> smartInfoList = new ArrayList<>();
            if (diskInfoThrift.getSmartInfo() != null) {
              for (DiskSmartInfoThrift diskSmartInfoThrift : diskInfoThrift.getSmartInfo()) {
                SmartInfo smartInfo = new SmartInfo();
                smartInfo.setId(String.valueOf(diskSmartInfoThrift.getId()));
                smartInfo.setAttributeNameCn(diskSmartInfoThrift.getAttributeNameCn());
                smartInfo.setAttributeNameEn(diskSmartInfoThrift.getAttributeNameEn());
                smartInfo.setFlag(diskSmartInfoThrift.getFlag());
                smartInfo.setValue(diskSmartInfoThrift.getValue());
                smartInfo.setWorst(diskSmartInfoThrift.getWorst());
                smartInfo.setThresh(diskSmartInfoThrift.getThresh());
                smartInfo.setType(diskSmartInfoThrift.getType());
                smartInfo.setUpdated(diskSmartInfoThrift.getUpdated());
                smartInfo.setWhenFailed(diskSmartInfoThrift.getWhenFailed());
                smartInfo.setRawValue(diskSmartInfoThrift.getRawValue());
                smartInfoList.add(smartInfo);
              }
            }
            smartInfoList.addAll(generateSmartInfo()); // 填充假数据
            disk.setSmartInfos(smartInfoList);

            diskNotInDataNodeList.add(disk);
          }

        }
        // sort by slot num
        Collections.sort(diskInDataNodeList, new Comparator<SimpleArchiveMetadata>() {
          @Override
          public int compare(SimpleArchiveMetadata o1, SimpleArchiveMetadata o2) {
            if (!StringUtils.isEmpty(o1.getSlotNumber()) && !StringUtils.isEmpty(o2.getSlotNumber())
                && !"unknown".equals(o1.getSlotNumber()) && !"unknown".equals(o2.getSlotNumber())) {
              return Integer.valueOf(o1.getSlotNumber()) - Integer.valueOf(o2.getSlotNumber());
            } else {
              String s1 = "";
              String s2 = "";
              if (!StringUtils.isEmpty(o1.getSlotNumber()) && !"unknown".equals(
                  o1.getSlotNumber())) {
                s1 = o1.getSlotNumber();
              }
              if (!StringUtils.isEmpty(o2.getSlotNumber()) && !"unknown".equals(
                  o2.getSlotNumber())) {
                s2 = o2.getSlotNumber();
              }
              return s1.compareToIgnoreCase(s2);

            }
          }
        });
        List<SimpleArchiveMetadata> diskCahe = new ArrayList<>();
        List<SimpleArchiveMetadata> diskRaw = new ArrayList<>();
        List<SimpleArchiveMetadata> diskNonfunction = new ArrayList<>();
        for (SimpleArchiveMetadata disk : diskInDataNodeList) {
          if (disk.getArchiveType().equals("RAW_DISK")) {
            diskRaw.add(disk);
          } else if (disk.getArchiveType().equals("UNSETTLED_DISK")) {
            diskNonfunction.add(disk);
          } else {
            diskCahe.add(disk);
          }
        }
        node.setDiskCache(diskCahe);
        node.setDiskRaw(diskRaw);
        node.setDiskNonfunction(diskNonfunction);
        // sort by slot num
        Collections.sort(diskNotInDataNodeList, new Comparator<DiskInfo>() {
          @Override
          public int compare(DiskInfo o1, DiskInfo o2) {
            if (!StringUtils.isEmpty(o1.getSlotNumber()) && !StringUtils.isEmpty(o2.getSlotNumber())
                && !"unknown".equals(o1.getSlotNumber()) && !"unknown".equals(o2.getSlotNumber())) {
              return Integer.valueOf(o1.getSlotNumber()) - Integer.valueOf(o2.getSlotNumber());
            } else {
              String s1 = "";
              String s2 = "";
              if (!StringUtils.isEmpty(o1.getSlotNumber()) && !"unknown".equals(
                  o1.getSlotNumber())) {
                s1 = o1.getSlotNumber();
              }
              if (!StringUtils.isEmpty(o2.getSlotNumber()) && !"unknown".equals(
                  o2.getSlotNumber())) {
                s2 = o2.getSlotNumber();
              }
              return s1.compareToIgnoreCase(s2);

            }

          }
        });
        node.setDiskNotInDataNode(diskNotInDataNodeList);
        // get maintenance list
        Maintenance maintenance = new Maintenance();
        List<InstanceMaintenanceThrift> maintenanceListThrift = new ArrayList<>();
        try {
          maintenanceListThrift = getMaintenanceList(Constants.SUPERADMIN_ACCOUNT_ID);
          logger.debug("maintenanceListThrift is {}", maintenanceListThrift);

        } catch (PermissionNotGrantExceptionThrift e) {
          logger.error("Exception catch ", e);
          maintenance.setMessage("ERROR_PermissionNotGrantException");
        } catch (ServiceIsNotAvailableThrift e) {
          logger.error("Exception catch ", e);
          maintenance.setMessage("ERROR_0065_ServiceIsNotAvailable");
        } catch (ServiceHavingBeenShutdownThrift e) {
          logger.error("Exception catch ", e);
          maintenance.setMessage("ERROR_0039_ServiceHavingBeenShutdown");
        } catch (Exception e) {
          logger.error("Exception catch ", e);
          maintenance.setMessage("ERROR_NetworkErrorException");
        }

        // get service instance on this node
        List<SimpleInstance> instanceTmpList = new ArrayList<>();
        List<SimpleInstance> instancesOnNode = new ArrayList<>();
        instanceTmpList = instanceService.getAll(Constants.SUPERADMIN_ACCOUNT_ID);
        for (SimpleInstance instance : instanceTmpList) {
          //                    if (Pattern.matches(instance.getHost() + "[^\\d]", node
          //                    .getNetworkCardInfo()))
          if (node.getNetworkCardInfo().contains(instance.getHost())) {

            instancesOnNode.add(instance);
            // if the node has datanode service, then we should set this node maintenance info.
            if (instance.getInstanceName().equals(PyService.DATANODE.getServiceName())) {
              for (InstanceMaintenanceThrift maintenanceThrift : maintenanceListThrift) {

                if (instance.getInstanceId()
                    .equals(String.valueOf(maintenanceThrift.getInstanceId()))) {
                  maintenance.setStartTime(String.valueOf(maintenanceThrift.getStartTime()));
                  maintenance.setEndTime(String.valueOf(maintenanceThrift.getEndTime()));
                  maintenance.setDuration(String.valueOf(maintenanceThrift.getDuration()));
                  maintenance.setMessage("success");
                  maintenance.setCurrentTime(String.valueOf(maintenanceThrift.getCurrentTime()));
                  node.setMaintenance(maintenance);

                }
              }
            }
          }
        }
        node.setInstances(instancesOnNode);

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

    return node;
  }

  @Override
  public void updateServernode(ServerNode node, long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, ServerNodePositionIsRepeatExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    InformationCenter.Iface client = null;
    UpdateServerNodeRequestThrift request = new UpdateServerNodeRequestThrift();
    UpdateServerNodeResponseThrift response = new UpdateServerNodeResponseThrift();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);

    if (node.getServerId() != null) {
      request.setServerId(node.getServerId());
    }
    if (node.getHostName() != null) {
      request.setHostname(node.getHostName());
    }
    if (node.getManageIp() != null) {
      request.setManageIp(node.getManageIp());
    }
    if (node.getRackNo() != null) {
      request.setRackNo(node.getRackNo());
    }
    if (node.getSlotNo() != null) {
      request.setSlotNo(node.getSlotNo());
    }
    if (node.getStoreIp() != null) {
      request.setStoreIp(node.getStoreIp());
    }
    if (node.getChildFramNo() != null) {
      request.setChildFramNo(node.getChildFramNo());
    }

    if (node.getDiskInfo() != null) {
      request.setDiskInfo(node.getDiskInfo());
    }
    if (node.getCpuInfo() != null) {
      request.setCpuInfo(node.getCpuInfo());
    }
    if (node.getGatewayIp() != null) {
      request.setGatewayIp(node.getGatewayIp());
    }
    if (node.getModelInfo() != null) {
      request.setModelInfo(node.getModelInfo());
    }
    if (node.getMemoryInfo() != null) {
      request.setMemoryInfo(node.getMemoryInfo());
    }
    if (node.getNetworkCardInfo() != null) {
      request.setNetworkCardInfo(node.getNetworkCardInfo());
    }
    logger.debug("updateServernode request is {}", request);
    try {
      client = infoCenterClientFactory.build().getClient();
      client.updateServerNode(request);
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
  public void deleteServerNodes(long accountId, List<String> serverIds)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      ServerNodeIsUnknownThrift,
      PermissionNotGrantExceptionThrift, AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    InformationCenter.Iface client = null;
    DeleteServerNodesRequestThrift request = new DeleteServerNodesRequestThrift();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setServerIds(serverIds);
    logger.debug("deleteServerNodes request is {}", request);

    try {
      client = infoCenterClientFactory.build().getClient();
      client.deleteServerNodes(request);
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

  private List<SimpleArchiveMetadata> buildSimpleArchiveMetadataList(
      List<ArchiveMetadataThrift> archiveMetadataThrifts, String endpoint) {
    List<SimpleArchiveMetadata> archiveList = new ArrayList<SimpleArchiveMetadata>();
    for (ArchiveMetadataThrift archiveThrift : archiveMetadataThrifts) {
      SimpleArchiveMetadata simpleArchive = new SimpleArchiveMetadata();
      simpleArchive.setArchiveId(String.valueOf(archiveThrift.getArchiveId()));
      simpleArchive.setDeviceName(archiveThrift.getDevName());
      simpleArchive.setStatus(ArchiveStatus.valueOf(archiveThrift.getStatus().name()));
      simpleArchive.setSerialNumber(archiveThrift.getSerialNumber());
      simpleArchive.setLogicalSpace(archiveThrift.getLogicalSpace());
      simpleArchive.setLogicalFreeSpace(archiveThrift.getLogicalFreeSpace());
      simpleArchive.setRate(String.valueOf(archiveThrift.getRate()));

      simpleArchive.setArchiveType(String.valueOf(archiveThrift.getType().name()));
      simpleArchive.setStorageType(archiveThrift.getStoragetype().name());
      simpleArchive.setDataNodeEndPoint(endpoint);
      double migrationRatio = 100.0;
      if (archiveThrift.getTotalPageToMigrate() != 0) {
        migrationRatio =
            archiveThrift.getAlreadyMigratedPage() * 1.0 / archiveThrift.getTotalPageToMigrate()
                * 100;
      }
      simpleArchive.setMigrationRatio(String.valueOf(migrationRatio));
      simpleArchive.setMigrationSpeed(String.valueOf(archiveThrift.getMigrationSpeed()));

      if (archiveThrift.isSetStoragePoolId()) {
        simpleArchive.setStoragePool(String.valueOf(archiveThrift.getStoragePoolId()));
      }
      archiveList.add(simpleArchive);
    }
    return archiveList;
  }

  public InformationCenterClientFactory getInfoCenterClientFactory() {
    return infoCenterClientFactory;
  }

  public void setInfoCenterClientFactory(InformationCenterClientFactory infoCenterClientFactory) {
    this.infoCenterClientFactory = infoCenterClientFactory;
  }

  @Override
  public void fixBrokenDisk(InstanceMetadataThrift instanceMetadata,
      ArchiveMetadataThrift archiveMetadata,
      long accountId)
      throws DiskNotFoundExceptionThrift, DiskNotBrokenThrift, ServiceHavingBeenShutdownThrift,
      AccessDeniedExceptionThrift, AccountNotFoundExceptionThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift, NetworkErrorExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, TException {
    logger.debug(
        "DiskServiceImpl reuseBrokenDisk.params are:instanceMetadata:[{}],archiveMetadata:[{}],"
            + "accountId:[{}]",
        instanceMetadata, archiveMetadata, accountId);
    InformationCenter.Iface client = null;
    try {
      client = infoCenterClientFactory.build().getClient();
      OnlineDiskRequest request = new OnlineDiskRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      request.setInstance(instanceMetadata);
      request.setOnlineArchive(archiveMetadata);
      logger.debug("fix broken disk request is {}", request);
      client.fixBrokenDisk(request);
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
    return;
  }

  @Override
  public void fixConfigMismatchedDisk(InstanceMetadataThrift instanceMetadata,
      ArchiveMetadataThrift archiveMetadata, long accountId)
      throws DiskNotFoundExceptionThrift, DiskNotMismatchConfigThrift,
      ServiceHavingBeenShutdownThrift,
      AccessDeniedExceptionThrift, AccountNotFoundExceptionThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift, NetworkErrorExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TException {
    logger.debug(
        "DiskServiceImpl reuseConfigMismatchedDisk.params are:instanceMetadata:[{}],"
            + "archiveMetadata:[{}],accountId:",
        instanceMetadata, archiveMetadata, accountId);
    InformationCenter.Iface client;
    try {
      client = infoCenterClientFactory.build().getClient();
      OnlineDiskRequest request = new OnlineDiskRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      request.setInstance(instanceMetadata);
      request.setOnlineArchive(archiveMetadata);
      logger.debug("fix config mismatch disk request is {}", request);
      client.fixConfigMismatchDisk(request);
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
    return;
  }

  @Override
  public List<InstanceMaintenanceThrift> getMaintenanceList(long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    ListInstanceMaintenancesRequest request = new ListInstanceMaintenancesRequest();
    ListInstanceMaintenancesResponse response = new ListInstanceMaintenancesResponse();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    List<InstanceMaintenanceThrift> instanceMaintenanceList = new ArrayList<>();
    logger.debug("listInstanceMaintenances request is {}", request);

    // 获取维护的list
    try {
      InformationCenter.Iface ccClient = infoCenterClientFactory.build().getClient();
      response = ccClient.listInstanceMaintenances(request);
      instanceMaintenanceList = response.getInstanceMaintenances();
      logger.debug("listInstanceMaintenances response is {}", response);
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

    return instanceMaintenanceList;
  }

  @Override
  public void cancelInstanceMaintenance(long accountId, long instanceId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    CancelMaintenanceRequest request = new CancelMaintenanceRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setInstanceId(instanceId);

    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.cancelInstanceMaintenance(request);
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
  public List<SmartInfo> obtainDiskSmartInfo(String serverId, String diskName)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, DiskNameIllegalExceptionThrift,
      ServerNodeNotExistExceptionThrift,
      TException {
    List<SmartInfo> smartInfoList = new ArrayList<>();
    GetDiskSmartInfoRequestThrift request = new GetDiskSmartInfoRequestThrift();
    request.setRequestId(RequestIdBuilder.get());
    request.setServerId(serverId);
    request.setDiskName(diskName);
    GetDiskSmartInfoResponseThrift response = new GetDiskSmartInfoResponseThrift();

    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      response = client.getDiskSmartInfo(request);
      if (response != null && response.getSmartInfo() != null) {
        for (DiskSmartInfoThrift diskSmartInfoThrift : response.getSmartInfo()) {
          SmartInfo smartInfo = new SmartInfo();
          smartInfo.setId(String.valueOf(diskSmartInfoThrift.getId()));
          smartInfo.setAttributeNameCn(diskSmartInfoThrift.getAttributeNameCn());
          smartInfo.setAttributeNameEn(diskSmartInfoThrift.getAttributeNameEn());
          smartInfo.setFlag(diskSmartInfoThrift.getFlag());
          smartInfo.setValue(diskSmartInfoThrift.getValue());
          smartInfo.setWorst(diskSmartInfoThrift.getWorst());
          smartInfo.setThresh(diskSmartInfoThrift.getThresh());
          smartInfo.setType(diskSmartInfoThrift.getType());
          smartInfo.setUpdated(diskSmartInfoThrift.getUpdated());
          smartInfo.setWhenFailed(diskSmartInfoThrift.getWhenFailed());
          smartInfo.setRawValue(diskSmartInfoThrift.getRawValue());
          smartInfoList.add(smartInfo);
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
    return smartInfoList;
  }

  @Override
  public void markInstanceMaintenance(long accountId, long instanceId, String instanceIp,
      long durationInMinutes)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    InstanceMaintainRequest request = new InstanceMaintainRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setDurationInMinutes(durationInMinutes);
    request.setInstanceId(instanceId);
    request.setIp(instanceIp);

    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.markInstanceMaintenance(request);
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
