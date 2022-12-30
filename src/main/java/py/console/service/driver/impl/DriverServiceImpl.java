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

package py.console.service.driver.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.common.Constants;
import py.common.PyService;
import py.common.RequestIdBuilder;
import py.console.bean.DriverStatusDescription;
import py.console.bean.ScsiClient;
import py.console.bean.SimpleDomain;
import py.console.bean.SimpleDriverClientInfo;
import py.console.bean.SimpleDriverMetadata;
import py.console.bean.SimpleInstance;
import py.console.bean.SimpleStoragePool;
import py.console.bean.SimpleVolumeMetadata;
import py.console.bean.VolumeForDriverClient;
import py.console.service.domain.DomainService;
import py.console.service.driver.DriverService;
import py.console.service.instance.InstanceService;
import py.console.service.storagepool.StoragePoolService;
import py.console.service.volume.VolumeService;
import py.driver.ScsiDriverDescription;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.infocenter.client.InformationCenterClientFactory;
import py.instance.DcType;
import py.instance.Instance;
import py.instance.InstanceStatus;
import py.instance.InstanceStore;
import py.thrift.icshare.ListAllDriversRequest;
import py.thrift.icshare.ListAllDriversResponse;
import py.thrift.icshare.ScsiClientDescriptionThrift;
import py.thrift.icshare.ScsiClientInfoThrift;
import py.thrift.infocenter.service.CreateScsiClientRequest;
import py.thrift.infocenter.service.DeleteScsiClientRequest;
import py.thrift.infocenter.service.DeleteScsiClientResponse;
import py.thrift.infocenter.service.InformationCenter;
import py.thrift.infocenter.service.ListScsiClientRequest;
import py.thrift.infocenter.service.ListScsiClientResponse;
import py.thrift.share.AccessPermissionTypeThrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.DriverMetadataThrift;
import py.thrift.share.DriverTypeThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.LaunchScsiDriverRequestThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.ParametersIsErrorExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.ScsiClientIsExistExceptionThrift;
import py.thrift.share.ScsiClientIsNotOkExceptionThrift;
import py.thrift.share.ScsiClientOperationExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.SetIscsiChapControlRequestThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;
import py.thrift.share.UmountScsiDriverRequestThrift;
import py.thrift.share.UmountScsiDriverResponseThrift;
import py.thrift.share.VolumeInActionThrift;
import py.thrift.share.VolumeMetadataThrift;

public class DriverServiceImpl implements DriverService {

  private static final Logger logger = LoggerFactory.getLogger(DriverServiceImpl.class);
  private InformationCenterClientFactory infoCenterClientFactory;
  private InstanceService instanceService;
  private DomainService domainService;

  private StoragePoolService poolService;
  private VolumeService volumeService;
  private InstanceStore instanceStore;

  public InformationCenterClientFactory getInfoCenterClientFactory() {
    return infoCenterClientFactory;
  }

  public void setInfoCenterClientFactory(InformationCenterClientFactory infoCenterClientFactory) {
    this.infoCenterClientFactory = infoCenterClientFactory;
  }

  public InstanceStore getInstanceStore() {
    return instanceStore;
  }

  public void setInstanceStore(InstanceStore instanceStore) {
    this.instanceStore = instanceStore;
  }

  public InstanceService getInstanceService() {
    return instanceService;
  }

  public void setInstanceService(InstanceService instanceService) {
    this.instanceService = instanceService;
  }

  public VolumeService getVolumeService() {
    return volumeService;
  }

  public void setVolumeService(VolumeService volumeService) {
    this.volumeService = volumeService;
  }

  public DomainService getDomainService() {
    return domainService;
  }

  public void setDomainService(DomainService domainService) {
    this.domainService = domainService;
  }

  public static Logger getLogger() {
    return logger;
  }

  public StoragePoolService getPoolService() {
    return poolService;
  }

  public void setPoolService(StoragePoolService poolService) {
    this.poolService = poolService;
  }

  @Override
  public List<SimpleDriverMetadata> listAllDrivers(SimpleDriverMetadata driverMetadata)
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
          simpleDriverMetadata.setCreateTime(String.valueOf(driverMetadataThrift.getCreateTime()));
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
  public void setIscsiChapControl(SimpleDriverMetadata driver)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    SetIscsiChapControlRequestThrift request = new SetIscsiChapControlRequestThrift();
    request.setRequestId(RequestIdBuilder.get());
    request.setDriverContainerId(Long.valueOf(driver.getDriverContainerId()));
    request.setVolumeId(Long.valueOf(driver.getVolumeId()));
    request.setSnapshotId(Integer.valueOf(driver.getSnapshotId()));
    request.setDriverType(DriverTypeThrift.valueOf(driver.getDriverType()));
    request.setChapControl(Integer.valueOf(driver.getChapControl()));

    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.setIscsiChapControl(request);
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
  public void createScsiClient(long accountId, String ip, long driverContainerId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      ScsiClientIsExistExceptionThrift,
      PermissionNotGrantExceptionThrift, AccountNotFoundExceptionThrift, TException {
    CreateScsiClientRequest request = new CreateScsiClientRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setIp(ip);
    request.setDriverContainerId(driverContainerId);
    logger.debug("CreateScsiClientRequest is {}", request);

    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.createScsiClient(request);
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
  public Map<String, ScsiClientOperationExceptionThrift> deleteScsiClient(long accountId,
      List<String> ips)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, TException {
    DeleteScsiClientRequest request = new DeleteScsiClientRequest();
    DeleteScsiClientResponse response = new DeleteScsiClientResponse();
    Map<String, ScsiClientOperationExceptionThrift> failedError = new HashMap<>();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setIps(ips);
    logger.debug("DeleteScsiClientRequest is {}", request);
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      response = client.deleteScsiClient(request);
      if (response != null) {
        failedError = response.getError();
      }
      logger.debug("DeleteScsiClientResponse is {}", response);
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

    return failedError;
  }

  @Override
  public Map<String, Object> listScsiClient(long accountId, String ip)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, TException {
    ListScsiClientRequest request = new ListScsiClientRequest();
    ListScsiClientResponse response = new ListScsiClientResponse();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setIp(ip);
    Map<String, Object> result = new HashMap<>();
    logger.debug("ListScsiClientRequest is {}", request);
    try {
      List<SimpleDomain> domainList = domainService
          .listDomains(null, py.console.utils.Constants.SUPER_ADMIN_ACCOUNT_ID);
      Set<SimpleStoragePool> storagePools = poolService
          .listStoragePools(null, null, py.console.utils.Constants.SUPER_ADMIN_ACCOUNT_ID);
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      response = client.listScsiClient(request);
      logger.debug("ListScsiClientResponse is {}", response);
      if (response != null) {
        // 如果参数ip为空则获取所有的客户端，如果IP不为空，则获取对应IP客户端已挂载的卷和可以挂载卷（未挂载）的信息
        if (StringUtils.isEmpty(ip)) {
          List<ScsiClient> scsiClientsList = new ArrayList<>();
          if (response != null & response.getClientDescriptions() != null) {
            for (ScsiClientDescriptionThrift scsiClientInfoThrift :
                response.getClientDescriptions()) {
              ScsiClient scsiClient = new ScsiClient();
              scsiClient.setIp(scsiClientInfoThrift.getIp());
              scsiClient.setStatus(scsiClientInfoThrift.getStatus().name());
              scsiClientsList.add(scsiClient);
            }

          }

          result.put("scsiClientsList", scsiClientsList);
        } else {
          List<VolumeForDriverClient> launchedVolume = new ArrayList<>();
          List<SimpleVolumeMetadata> unlaunchedVolume = new ArrayList<>();
          if (response.getLaunchVolumesForScsi() != null) {
            for (ScsiClientInfoThrift scsiClientInfoThrift : response.getLaunchVolumesForScsi()) {
              VolumeForDriverClient volumeForDriverClient = new VolumeForDriverClient();
              volumeForDriverClient
                  .setId(String.valueOf(scsiClientInfoThrift.getVolume().getVolumeId()));
              volumeForDriverClient.setName(scsiClientInfoThrift.getVolume().getName());
              if (scsiClientInfoThrift.getVolume().getVolumeStatus() != null) {
                volumeForDriverClient
                    .setVolumeStatus(scsiClientInfoThrift.getVolume().getVolumeStatus().name());
              }

              if (scsiClientInfoThrift.getDriverStatus() != null) {
                volumeForDriverClient.setDriverStatus(
                    scsiClientInfoThrift.getDriverStatus().name());
              }

              volumeForDriverClient.setCreateTime(
                  String.valueOf(scsiClientInfoThrift.getVolume().getVolumeCreatedTime()));
              volumeForDriverClient.setLinkStatus(scsiClientInfoThrift.getStatus().name());
              volumeForDriverClient.setPath(scsiClientInfoThrift.getPath());
              volumeForDriverClient
                  .setDomainId(String.valueOf(scsiClientInfoThrift.getVolume().getDomainId()));
              volumeForDriverClient
                  .setPoolId(String.valueOf(scsiClientInfoThrift.getVolume().getStoragePoolId()));
              volumeForDriverClient
                  .setSize(String.valueOf(scsiClientInfoThrift.getVolume().getVolumeSize()));
              // domain name
              String domainName = volumeService
                  .getDomainNameById(scsiClientInfoThrift.getVolume().getDomainId(), domainList);
              volumeForDriverClient.setDomainName(domainName);
              // pool name
              SimpleStoragePool storagePool = volumeService
                  .getStoragePoolById(scsiClientInfoThrift.getVolume().getStoragePoolId(),
                      storagePools);
              volumeForDriverClient.setPoolName(storagePool.getPoolName());
              DriverStatusDescription statusDescription = new DriverStatusDescription();
              if (scsiClientInfoThrift.getStatusDescription() != null) {
                statusDescription.setName(
                    ScsiDriverDescription.findByValue(scsiClientInfoThrift.getStatusDescription())
                        .name());
                statusDescription.setCh(ScsiDriverDescription
                    .findByValue(scsiClientInfoThrift.getStatusDescription())
                    .getChinaDescription());
                statusDescription.setEn(ScsiDriverDescription
                    .findByValue(scsiClientInfoThrift.getStatusDescription())
                    .getEnglishDescription());
                statusDescription.setType(scsiClientInfoThrift.getDescriptionTpye().name());
              }

              volumeForDriverClient.setStatusDescription(statusDescription);
              launchedVolume.add(volumeForDriverClient);

            }
          }

          result.put("launchedVolume", launchedVolume);
          if (response.getUnLaunchVolumesForScsi() != null) {
            for (VolumeMetadataThrift volumeMetadataThrift : response.getUnLaunchVolumesForScsi()) {
              SimpleVolumeMetadata volume = new SimpleVolumeMetadata();
              volume.setVolumeId(String.valueOf(volumeMetadataThrift.getVolumeId()));
              volume.setVolumeName(volumeMetadataThrift.getName());
              volume.setVolumeSize(String.valueOf(volumeMetadataThrift.getVolumeSize()));
              volume.setCreateTime(volumeMetadataThrift.getVolumeCreatedTime());
              volume.setVolumeStoragePoolId(
                  String.valueOf(volumeMetadataThrift.getStoragePoolId()));
              volume.setDomainId(String.valueOf(volumeMetadataThrift.getDomainId()));
              // 根据inAction设置他的中间状态
              if (volumeMetadataThrift.getInAction().equals(VolumeInActionThrift.NULL)) {
                volume.setVolumeStatus(volumeMetadataThrift.getVolumeStatus().name());
              } else {
                volume.setVolumeStatus(volumeMetadataThrift.getInAction().name());
              }
              volume.setVolumeSize(String.valueOf(volumeMetadataThrift.getVolumeSize()));
              // domain name
              String domainName = volumeService
                  .getDomainNameById(volumeMetadataThrift.getDomainId(), domainList);
              volume.setVolumeDomain(domainName);
              // pool name
              SimpleStoragePool storagePool = volumeService
                  .getStoragePoolById(volumeMetadataThrift.getStoragePoolId(), storagePools);
              volume.setStoragePoolName(storagePool.getPoolName());
              unlaunchedVolume.add(volume);
            }
          }

          result.put("unlaunchedVolume", unlaunchedVolume);

        }

        logger.debug("result is ", result);
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

    return result;
  }

  @Override
  public void launchDriverForScsi(long accountId, List<Long> volumeIds, String driverType,
      String scsiIp,
      int driverNum)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, ScsiClientIsNotOkExceptionThrift, TException {
    LaunchScsiDriverRequestThrift request = new LaunchScsiDriverRequestThrift();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    Map<Long, Integer> volumesForLaunch = new HashMap<>();
    if (volumeIds != null) {
      for (long volumeId : volumeIds) {
        volumesForLaunch.put(volumeId, 0);
      }
    }

    request.setVolumesForLaunch(volumesForLaunch);
    request.setDriverType(DriverTypeThrift.valueOf(driverType));
    request.setDriverAmount(driverNum);
    request.setScsiIp(scsiIp);
    logger.debug("LaunchScsiDriverRequestThrift is {}", request);
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.launchDriverForScsi(request);
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
  public Map<String, ScsiClientOperationExceptionThrift> umountDriverForScsi(long accountId,
      List<Long> volumeIds,
      String scsiIp)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, ScsiClientIsNotOkExceptionThrift, TException {
    UmountScsiDriverRequestThrift request = new UmountScsiDriverRequestThrift();
    UmountScsiDriverResponseThrift response = new UmountScsiDriverResponseThrift();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    Map<String, ScsiClientOperationExceptionThrift> failedError = new HashMap<>();
    Map<Long, Integer> volumesForUmount = new HashMap<>();
    if (volumeIds != null) {
      for (long volumeId : volumeIds) {
        volumesForUmount.put(volumeId, 0);
      }
    }

    request.setVolumesForUmount(volumesForUmount);
    request.setScsiClientIp(scsiIp);
    logger.debug("UmountScsiDriverRequestThrift is {}", request);
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      response = client.umountDriverForScsi(request);
      if (response != null) {
        failedError = response.getError();
      }
      logger.debug("UmountScsiDriverResponseThrift is {}", response);
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
    return failedError;

  }

  @Override
  public List<SimpleInstance> availableDriverContainerForClient()
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, TException {
    List<SimpleInstance> availableDriverContainer = new ArrayList<>();
    Map<String, Object> result = new HashMap<>();
    try {
      result = listScsiClient(Constants.SUPERADMIN_ACCOUNT_ID, null);
      List<ScsiClient> scsiClientsList = (List<ScsiClient>) result.get("scsiClientsList");
      Set<Instance> allDriverContainers = instanceStore
          .getAll(PyService.DRIVERCONTAINER.getServiceName(), InstanceStatus.HEALTHY);
      for (Instance instance : allDriverContainers) {
        if (instance.getDcType().equals(DcType.ALLSUPPORT) || instance.getDcType()
            .equals(DcType.SCSISUPPORT)) {
          SimpleInstance simpleInstance = new SimpleInstance(instance);
          boolean usedFlag = false;
          for (ScsiClient scsiClient : scsiClientsList) {
            if (scsiClient.getIp().equals(simpleInstance.getHost())) {
              usedFlag = true;
              break;
            }
          }
          if (!usedFlag) {
            availableDriverContainer.add(simpleInstance);
          }
        }
      }
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }

    return availableDriverContainer;
  }
}
