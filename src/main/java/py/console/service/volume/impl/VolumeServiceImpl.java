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

package py.console.service.volume.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.RequestResponseHelper;
import py.archive.segment.SegmentMetadata;
import py.archive.segment.SegmentUnitMetadata;
import py.archive.segment.SegmentUnitStatus;
import py.common.PyService;
import py.common.RequestIdBuilder;
import py.console.bean.FixVolumeResponse;
import py.console.bean.SimpleDomain;
import py.console.bean.SimpleDriverClientInfo;
import py.console.bean.SimpleDriverMetadata;
import py.console.bean.SimpleInstance;
import py.console.bean.SimpleIoLimitation;
import py.console.bean.SimpleSegUnit;
import py.console.bean.SimpleSegUnit.UnitType;
import py.console.bean.SimpleSegmentMetadata;
import py.console.bean.SimpleSegmentVersion;
import py.console.bean.SimpleStoragePool;
import py.console.bean.SimpleVolumeMetadata;
import py.console.service.domain.DomainService;
import py.console.service.instance.InstanceService;
import py.console.service.storagepool.StoragePoolService;
import py.console.service.volume.VolumeService;
import py.console.utils.Constants;
import py.console.utils.Utils;
import py.driver.DriverMetadata;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.infocenter.client.InformationCenterClientFactory;
import py.infocenter.client.InformationCenterClientWrapper;
import py.infocenter.client.VolumeMetadataAndDrivers;
import py.informationcenter.AccessPermissionType;
import py.instance.InstanceStatus;
import py.io.qos.IoLimitation;
import py.membership.SegmentMembership;
import py.storage.StorageConfiguration;
import py.thrift.icshare.AddOrModifyIoLimitRequest;
import py.thrift.icshare.AddOrModifyIoLimitResponse;
import py.thrift.icshare.CancelDeleteVolumeDelayRequest;
import py.thrift.icshare.CreateVolumeRequest;
import py.thrift.icshare.CreateVolumeResponse;
import py.thrift.icshare.DeleteIoLimitRequest;
import py.thrift.icshare.DeleteIoLimitResponse;
import py.thrift.icshare.DeleteVolumeDelayRequest;
import py.thrift.icshare.DeleteVolumeRequest;
import py.thrift.icshare.DeleteVolumeResponse;
import py.thrift.icshare.GetLimitsRequest;
import py.thrift.icshare.GetLimitsResponse;
import py.thrift.icshare.GetSegmentListRequest;
import py.thrift.icshare.GetSegmentListResponse;
import py.thrift.icshare.GetVolumeRequest;
import py.thrift.icshare.GetVolumeResponse;
import py.thrift.icshare.ListRecycleVolumeInfoRequest;
import py.thrift.icshare.ListRecycleVolumeInfoResponse;
import py.thrift.icshare.ListVolumesRequest;
import py.thrift.icshare.ListVolumesResponse;
import py.thrift.icshare.MoveVolumeToRecycleRequest;
import py.thrift.icshare.OrphanVolumeRequest;
import py.thrift.icshare.OrphanVolumeResponse;
import py.thrift.icshare.RecycleVolumeRequest;
import py.thrift.icshare.RecycleVolumeResponse;
import py.thrift.icshare.RecycleVolumeToNormalRequest;
import py.thrift.icshare.StartDeleteVolumeDelayRequest;
import py.thrift.icshare.StopDeleteVolumeDelayRequest;
import py.thrift.icshare.UpdateVolumeDescriptionRequest;
import py.thrift.infocenter.service.ExtendVolumeRequest;
import py.thrift.infocenter.service.ExtendVolumeResponse;
import py.thrift.infocenter.service.InformationCenter;
import py.thrift.infocenter.service.MarkVolumesReadWriteRequest;
import py.thrift.share.AccessDeniedExceptionThrift;
import py.thrift.share.AccessPermissionTypeThrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.AlreadyExistStaticLimitationExceptionThrift;
import py.thrift.share.BadLicenseTokenExceptionThrift;
import py.thrift.share.ConfirmFixVolumeRequestThrift;
import py.thrift.share.ConfirmFixVolumeResponseThrift;
import py.thrift.share.ConnectPydDeviceOperationExceptionThrift;
import py.thrift.share.CreateBackstoresOperationExceptionThrift;
import py.thrift.share.CreateLoopbackLunsOperationExceptionThrift;
import py.thrift.share.CreateLoopbackOperationExceptionThrift;
import py.thrift.share.DomainIsDeletingExceptionThrift;
import py.thrift.share.DomainNotExistedExceptionThrift;
import py.thrift.share.DriverAmountAndHostNotFitThrift;
import py.thrift.share.DriverContainerIsIncExceptionThrift;
import py.thrift.share.DriverHostCannotUseThrift;
import py.thrift.share.DriverIpTargetThrift;
import py.thrift.share.DriverIsLaunchingExceptionThrift;
import py.thrift.share.DriverIsUpgradingExceptionThrift;
import py.thrift.share.DriverLaunchingExceptionThrift;
import py.thrift.share.DriverMetadataThrift;
import py.thrift.share.DriverNameExistsExceptionThrift;
import py.thrift.share.DriverNotFoundExceptionThrift;
import py.thrift.share.DriverTypeConflictExceptionThrift;
import py.thrift.share.DriverTypeIsConflictExceptionThrift;
import py.thrift.share.DriverTypeThrift;
import py.thrift.share.DriverUnmountingExceptionThrift;
import py.thrift.share.DynamicIoLimitationTimeInterleavingExceptionThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.ExistsClientExceptionThrift;
import py.thrift.share.ExistsDriverExceptionThrift;
import py.thrift.share.FailedToUmountDriverExceptionThrift;
import py.thrift.share.FixVolumeRequestThrift;
import py.thrift.share.FixVolumeResponseThrift;
import py.thrift.share.FrequentFixVolumeRequestThrift;
import py.thrift.share.GetScsiDeviceOperationExceptionThrift;
import py.thrift.share.InternalErrorThrift;
import py.thrift.share.InvalidInputExceptionThrift;
import py.thrift.share.IoLimitationThrift;
import py.thrift.share.LackDatanodeExceptionThrift;
import py.thrift.share.LaunchDriverRequestThrift;
import py.thrift.share.LaunchDriverResponseThrift;
import py.thrift.share.LaunchedVolumeCannotBeDeletedExceptionThrift;
import py.thrift.share.LicenseExceptionThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.NoDriverLaunchExceptionThrift;
import py.thrift.share.NoEnoughPydDeviceExceptionThrift;
import py.thrift.share.NotEnoughGroupExceptionThrift;
import py.thrift.share.NotEnoughLicenseTokenExceptionThrift;
import py.thrift.share.NotEnoughNormalGroupExceptionThrift;
import py.thrift.share.NotEnoughSpaceExceptionThrift;
import py.thrift.share.NotRootVolumeExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.ReadWriteTypeThrift;
import py.thrift.share.ResourceNotExistsExceptionThrift;
import py.thrift.share.RootVolumeBeingDeletedExceptionThrift;
import py.thrift.share.RootVolumeNotFoundExceptionThrift;
import py.thrift.share.ScsiDeviceIsLaunchExceptionThrift;
import py.thrift.share.SegmentMembershipThrift;
import py.thrift.share.SegmentMetadataThrift;
import py.thrift.share.SegmentUnitMetadataThrift;
import py.thrift.share.SegmentUnitStatusThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.SnapshotRollingBackExceptionThrift;
import py.thrift.share.StoragePoolIsDeletingExceptionThrift;
import py.thrift.share.StoragePoolNotExistInDoaminExceptionThrift;
import py.thrift.share.StoragePoolNotExistedExceptionThrift;
import py.thrift.share.SystemCpuIsNotEnoughThrift;
import py.thrift.share.SystemMemoryIsNotEnoughThrift;
import py.thrift.share.TooManyDriversExceptionThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;
import py.thrift.share.TransportExceptionThrift;
import py.thrift.share.UmountDriverRequestThrift;
import py.thrift.share.UmountDriverResponseThrift;
import py.thrift.share.UnknownIpv4HostExceptionThrift;
import py.thrift.share.UnknownIpv6HostExceptionThrift;
import py.thrift.share.UselessLicenseExceptionThrift;
import py.thrift.share.VolumeBeingDeletedExceptionThrift;
import py.thrift.share.VolumeCanNotLaunchMultiDriversThisTimeExceptionThrift;
import py.thrift.share.VolumeCannotBeRecycledExceptionThrift;
import py.thrift.share.VolumeDeleteDelayInformationThrift;
import py.thrift.share.VolumeDeletingExceptionThrift;
import py.thrift.share.VolumeExistingExceptionThrift;
import py.thrift.share.VolumeFixingOperationExceptionThrift;
import py.thrift.share.VolumeInActionThrift;
import py.thrift.share.VolumeInExtendingExceptionThrift;
import py.thrift.share.VolumeInMoveOnlineDoNotHaveOperationExceptionThrift;
import py.thrift.share.VolumeIsBeginMovedExceptionThrift;
import py.thrift.share.VolumeIsCloningExceptionThrift;
import py.thrift.share.VolumeIsCopingExceptionThrift;
import py.thrift.share.VolumeIsMovingExceptionThrift;
import py.thrift.share.VolumeLaunchMultiDriversExceptionThrift;
import py.thrift.share.VolumeMetadataThrift;
import py.thrift.share.VolumeNameExistedExceptionThrift;
import py.thrift.share.VolumeNotAvailableExceptionThrift;
import py.thrift.share.VolumeNotFoundExceptionThrift;
import py.thrift.share.VolumeOriginalSizeNotMatchExceptionThrift;
import py.thrift.share.VolumeRecycleInformationThrift;
import py.thrift.share.VolumeSizeIllegalExceptionThrift;
import py.thrift.share.VolumeSizeNotMultipleOfSegmentSizeThrift;
import py.thrift.share.VolumeStatusThrift;
import py.thrift.share.VolumeTypeThrift;
import py.thrift.share.VolumeUnderOperationExceptionThrift;
import py.thrift.share.VolumeWasRollbackingExceptionThrift;
import py.volume.VolumeInAction;
import py.volume.VolumeMetadata;
import py.volume.VolumeStatus;

/**
 * volume service.
 *
 */
public class VolumeServiceImpl implements VolumeService {

  @Override
  public List<SimpleVolumeMetadata> getOrphanVolume(long accountId)
      throws TTransportException, EndPointNotFoundException, TooManyEndPointFoundException,
      AccessDeniedExceptionThrift, InternalErrorThrift, InvalidInputExceptionThrift,
      GenericThriftClientFactoryException, TException {
    InformationCenter.Iface client = null;
    List<SimpleVolumeMetadata> orphanVolumeList = new ArrayList<SimpleVolumeMetadata>();
    try {
      List<SimpleDomain> domainList = domainService.listDomains(null, accountId);

      client = infoCenterClientFactory.build().getClient();
      OrphanVolumeRequest request = new OrphanVolumeRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      OrphanVolumeResponse response = client.listOrphanVolume(request);
      for (VolumeMetadataThrift volumeThrift : response.getOrphanVolumes()) {
        SimpleVolumeMetadata orphanVolume = new SimpleVolumeMetadata();
        orphanVolume.setVolumeId(String.valueOf(volumeThrift.getVolumeId()));
        orphanVolume.setAccountId(volumeThrift.getAccountId());
        orphanVolume.setVolumeSize(Utils.volumeSizeBuilder(volumeThrift.getVolumeSize()));
        orphanVolume.setVolumeName(volumeThrift.getName());
        orphanVolume.setVolumeStatus(volumeThrift.getVolumeStatus().name());
        orphanVolume.setVolumeType(volumeThrift.getVolumeType().name());
        orphanVolume.setReadWrite(volumeThrift.getReadWrite().name());
        orphanVolume.setSimpleConfiguration(volumeThrift.isSimpleConfiguration() ? "Yes" : "No");

        if (volumeThrift.getInAction().equals(VolumeInActionThrift.NULL)) {
          orphanVolume.setVolumeStatus(volumeThrift.getVolumeStatus().name());
        } else {
          orphanVolume.setVolumeStatus(volumeThrift.getInAction().name());
        }

        if (volumeThrift.getLastExtendedTime() != 0) {
          orphanVolume.setExtendFlag("YES");
        } else {
          orphanVolume.setExtendFlag("NO");
        }
        orphanVolume.setVolumeType(volumeThrift.getVolumeType().name());
        orphanVolume.setCreateTime(volumeThrift.getVolumeCreatedTime());
        orphanVolume.setVolumeBuildType(String.valueOf(volumeThrift.getVolumeSource()));

        // domain name
        String domainName = getDomainNameById(volumeThrift.getDomainId(), domainList);
        orphanVolume.setVolumeDomain(domainName);
        orphanVolumeList.add(orphanVolume);

        //                String storagePoolName = getStoragePoolNameById(volumeThrift
        //                .getDomainId(),
        //                        volumeThrift.getStoragePoolId(), accountId);
        //                orphanVolume.setStoragePoolName(storagePoolName);
      }

    } catch (TTransportException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (AccessDeniedExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (InternalErrorThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (InvalidInputExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }
    return orphanVolumeList;
  }

  private static final Logger logger = LoggerFactory.getLogger(VolumeServiceImpl.class);

  private InformationCenterClientFactory infoCenterClientFactory;

  private StorageConfiguration storageConfiguration;

  private DomainService domainService;

  private InstanceService instanceService;

  private StoragePoolService storagePoolService;
  private String volumeDetailShowFlag;

  public String getVolumeDetailShowFlag() {
    return volumeDetailShowFlag;
  }

  public void setVolumeDetailShowFlag(String volumeDetailShowFlag) {
    this.volumeDetailShowFlag = volumeDetailShowFlag;
  }

  public InformationCenterClientFactory getInfoCenterClientFactory() {
    return infoCenterClientFactory;
  }

  public void setInfoCenterClientFactory(InformationCenterClientFactory infoCenterClientFactory) {
    this.infoCenterClientFactory = infoCenterClientFactory;
  }

  public StorageConfiguration getStorageConfiguration() {
    return storageConfiguration;
  }

  public void setStorageConfiguration(StorageConfiguration storageConfiguration) {
    this.storageConfiguration = storageConfiguration;
  }

  public InstanceService getInstanceService() {
    return instanceService;
  }

  public void setInstanceService(InstanceService instanceService) {
    this.instanceService = instanceService;
  }

  @Override
  public VolumeMetadata createVolume(VolumeMetadata volumeMetadata)
      throws NotEnoughSpaceExceptionThrift, NetworkErrorExceptionThrift,
      InvalidInputExceptionThrift,
      AccessDeniedExceptionThrift, ServiceHavingBeenShutdownThrift,
      VolumeSizeNotMultipleOfSegmentSizeThrift,
      VolumeExistingExceptionThrift, VolumeNameExistedExceptionThrift,
      BadLicenseTokenExceptionThrift,
      UselessLicenseExceptionThrift, NotEnoughLicenseTokenExceptionThrift,
      ServiceIsNotAvailableThrift,
      StoragePoolNotExistInDoaminExceptionThrift, DomainNotExistedExceptionThrift,
      StoragePoolNotExistedExceptionThrift, DomainIsDeletingExceptionThrift,
      StoragePoolIsDeletingExceptionThrift, NotEnoughGroupExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      LicenseExceptionThrift, VolumeNotFoundExceptionThrift, NotEnoughNormalGroupExceptionThrift,
      VolumeSizeIllegalExceptionThrift, TException {
    InformationCenter.Iface client = null;
    try {
      client = infoCenterClientFactory.build().getClient();
      CreateVolumeRequest request = new CreateVolumeRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(volumeMetadata.getAccountId());
      request.setVolumeId(volumeMetadata.getVolumeId());
      request.setName(volumeMetadata.getName());
      request.setVolumeDescription(volumeMetadata.getVolumeDescription());
      request.setVolumeSize(volumeMetadata.getVolumeSize());
      request.setVolumeType(VolumeTypeThrift.valueOf(volumeMetadata.getVolumeType().name()));
      request.setEnableLaunchMultiDrivers(volumeMetadata.isEnableLaunchMultiDrivers());
      if (volumeMetadata.getDomainId() != null) {
        request.setDomainId(volumeMetadata.getDomainId());
      }
      if (volumeMetadata.getStoragePoolId() != null) {
        request.setStoragePoolId(volumeMetadata.getStoragePoolId());

      }

      // The old way of simple configuration. It has been deprecated but still works
      //            request.setNotCreateAllSegmentAtBegining(volumeMetadata
      //            .isNotCreateAllSegmentAtBeginning());
      //            request.setLeastSegmentUnitCount(volumeMetadata.getSegmentNumToCreateEachTime
      //            ());
      logger.debug("create volume request is {}", request);
      CreateVolumeResponse response = client.createVolume(request);
      if (response != null) {
        volumeMetadata.setVolumeId(response.getVolumeId());
        return volumeMetadata;
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
    return null;
  }

  @Override
  public boolean deleteVolume(long volumeId, String volumeName, long accountId)
      throws AccessDeniedExceptionThrift, NotEnoughSpaceExceptionThrift,
      VolumeNotFoundExceptionThrift,
      VolumeBeingDeletedExceptionThrift, ServiceHavingBeenShutdownThrift,
      VolumeInExtendingExceptionThrift,
      LaunchedVolumeCannotBeDeletedExceptionThrift, ServiceIsNotAvailableThrift,
      VolumeUnderOperationExceptionThrift,
      SnapshotRollingBackExceptionThrift, DriverLaunchingExceptionThrift,
      DriverUnmountingExceptionThrift,
      VolumeDeletingExceptionThrift, VolumeWasRollbackingExceptionThrift,
      InvalidInputExceptionThrift,
      VolumeIsCloningExceptionThrift, ResourceNotExistsExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, VolumeIsCopingExceptionThrift, ExistsDriverExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      EndPointNotFoundExceptionThrift,
      VolumeInMoveOnlineDoNotHaveOperationExceptionThrift, VolumeIsBeginMovedExceptionThrift,
      VolumeIsMovingExceptionThrift, TException {
    InformationCenter.Iface client;
    logger.debug("@console going to delete volume_id {}", volumeId);
    try {
      client = infoCenterClientFactory.build().getClient();
      DeleteVolumeRequest request = new DeleteVolumeRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      request.setVolumeId(volumeId);
      request.setVolumeName(volumeName);
      DeleteVolumeResponse response = client.deleteVolume(request);
      if (response != null) {
        return true;
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
    return false;
  }

  @Override
  public boolean recycleVolume(long volumeId, long accountId)
      throws AccessDeniedExceptionThrift, NotEnoughSpaceExceptionThrift,
      VolumeNotFoundExceptionThrift,
      VolumeCannotBeRecycledExceptionThrift, ServiceHavingBeenShutdownThrift,
      VolumeInExtendingExceptionThrift,
      ExistsDriverExceptionThrift, ServiceIsNotAvailableThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, VolumeWasRollbackingExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    InformationCenter.Iface client;
    try {
      client = infoCenterClientFactory.build().getClient();
      RecycleVolumeRequest request = new RecycleVolumeRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      request.setVolumeId(volumeId);
      RecycleVolumeResponse response = client.recycleVolume(request);
      if (response != null) {
        return true;
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
    return false;
  }

  @Override
  public SimpleVolumeMetadata getVolumeById(long volumeId, long accountId, boolean withSegmentList)
      throws VolumeNotFoundExceptionThrift {
    InformationCenter.Iface clientIc = null;
    try {
      clientIc = infoCenterClientFactory.build().getClient();
      GetVolumeRequest request = new GetVolumeRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      request.setVolumeId(volumeId);
      request.setWithOutSegmentList(!withSegmentList);
      request.setContainDeadVolume(false);
      GetVolumeResponse response = clientIc.getVolume(request);
      List<SimpleDomain> domainList = domainService.listDomains(null, accountId);
      SimpleVolumeMetadata simpleVolume = buildSimpleVolumeMetadata(response, accountId);
      if (simpleVolume == null) {
        return simpleVolume;
      }
      String domainName = getDomainNameById(response.getVolumeMetadata().getDomainId(), domainList);
      logger.debug("domain name is {}", domainName);
      logger.debug("simpleVolume  is {}", simpleVolume);
      simpleVolume.setVolumeDomain(domainName);
      return simpleVolume;
    } catch (TTransportException e) {
      logger.error("Exception catch", e);
    } catch (InternalErrorThrift e) {
      logger.error("Exception catch", e);
    } catch (InvalidInputExceptionThrift e) {
      logger.error("Exception catch", e);
    } catch (NotEnoughSpaceExceptionThrift e) {
      logger.error("Exception catch", e);
    } catch (VolumeNotFoundExceptionThrift e) {
      logger.error("Exception catch", e);
      throw new VolumeNotFoundExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
    } catch (IOException e) {
      logger.error("Exception catch", e);
    }
    return null;
  }

  @Override
  public SimpleVolumeMetadata viewVolume(long volumeId, long accountId)
      throws VolumeNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, Exception {
    InformationCenterClientWrapper clientWrapper;
    SimpleVolumeMetadata simpleVolume = new SimpleVolumeMetadata();
    try {
      clientWrapper = infoCenterClientFactory.build();
      VolumeMetadataAndDrivers response = clientWrapper.getVolumeByPagination(volumeId, accountId);
      VolumeMetadata volume = response.getVolumeMetadata();
      simpleVolume.setAccountId(volume.getAccountId());
      simpleVolume.setVolumeId(String.valueOf(volume.getVolumeId()));
      simpleVolume.setVolumeName(volume.getName());
      simpleVolume.setDescription(volume.getVolumeDescription());
      simpleVolume.setClientLastConnectTime(String.valueOf(volume.getClientLastConnectTime()));
      simpleVolume.setVolumeSize(Utils.volumeSizeBuilder(volume.getVolumeSize()));
      simpleVolume.setFreeSpaceRatio(volume.getFreeSpaceRatio());
      simpleVolume.setReadWrite(volume.getReadWrite().name());
      simpleVolume.setRebalanceRatio(String.valueOf(volume.getRebalanceInfo().getRebalanceRatio()));
      simpleVolume
          .setRebalanceVersion(String.valueOf(volume.getRebalanceInfo().getRebalanceVersion()));
      logger.warn("volume.getFreeSpaceRatio() is {}", volume.getFreeSpaceRatio());
      Validate.isTrue(volume.getVolumeStatus() != VolumeStatus.Dead);
      if (volume.getInAction().equals(VolumeInAction.NULL)) {
        simpleVolume.setVolumeStatus(volume.getVolumeStatus().name());
      } else {
        simpleVolume.setVolumeStatus(volume.getInAction().name());
      }

      if (volume.getLastExtendedTime() != null && volume.getLastExtendedTime().getTime() != 0) {
        simpleVolume.setExtendFlag("YES");
      } else {
        simpleVolume.setExtendFlag("NO");
      }
      simpleVolume.setVolumeType(volume.getVolumeType().name());
      simpleVolume.setCreateTime(volume.getVolumeCreatedTime().getTime());
      simpleVolume.setVolumeBuildType(String.valueOf(volume.getVolumeSource()));
      simpleVolume.setEnableLaunchMultiDrivers(String.valueOf(volume.isEnableLaunchMultiDrivers()));

      Set<SimpleStoragePool> storagePools = new HashSet<>();
      try {
        storagePools = storagePoolService
            .listStoragePools(null, null, Constants.SUPER_ADMIN_ACCOUNT_ID);
      } catch (Exception e) {
        logger.error("catch an Exception", e);
      }

      SimpleStoragePool storagePool = getStoragePoolById(
          response.getVolumeMetadata().getStoragePoolId(),
          storagePools);
      String storagePoolName = storagePool.getPoolName();
      simpleVolume.setStoragePoolName(storagePoolName);
      String poolId = storagePool.getPoolId();
      simpleVolume.setPoolId(poolId);
      List<SimpleSegmentMetadata> segList = new ArrayList<SimpleSegmentMetadata>();
      for (SegmentMetadata segmentMetadata : volume.getSegments()) {
        SimpleSegmentMetadata simpleSeg = new SimpleSegmentMetadata();
        simpleSeg.setSegId(segmentMetadata.getIndex());
        simpleSeg.setUnitSize(segmentMetadata.getSegmentUnitCount());
        List<SimpleSegUnit> unitList = new ArrayList<SimpleSegUnit>();
        for (SegmentUnitMetadata segmentUnitMetadata : segmentMetadata.getSegmentUnits()) {
          SimpleSegUnit segUnit = new SimpleSegUnit();
          segUnit.setInstanceId(String.valueOf(segmentUnitMetadata.getInstanceId().getId()));
          segUnit.setStatus(segmentUnitMetadata.getStatus().name());
          segUnit
              .setStatusDisplay(Utils.segmentStatusConvert(segmentUnitMetadata.getStatus().name()));
          if (segmentUnitMetadata.getStatus().equals(SegmentUnitStatus.PreSecondary)) {
            segUnit.setRatioMigration(segmentUnitMetadata.getRatioMigration());
          }
          segUnit.setDiskName(segmentUnitMetadata.getDiskName());
          segUnit.setOffset(String.valueOf(segmentUnitMetadata.getPhysicalDataOffset()));
          SegmentMembership membership = segmentUnitMetadata.getMembership();
          if (membership.getPrimary().getId() == Long.valueOf(segUnit.getInstanceId())) {
            segUnit.setUnitType(UnitType.Primary.name());
          } else {
            segUnit.setUnitType(UnitType.Secondary.name());
          }
          logger.debug("segment version {} epoch = {} generation = {}",
              segmentUnitMetadata.getSegId(),
              membership.getSegmentVersion().getEpoch(),
              membership.getSegmentVersion().getGeneration());
          SimpleSegmentVersion simpleSegmentVersion = new SimpleSegmentVersion();
          simpleSegmentVersion.setEpoch(membership.getSegmentVersion().getEpoch());
          simpleSegmentVersion.setGeneration(membership.getSegmentVersion().getGeneration());
          segUnit.setSimpleSegmentVersion(simpleSegmentVersion);
          unitList.add(segUnit);
        }
        sortUnitList(unitList);
        simpleSeg.setUnitList(unitList);
        segList.add(simpleSeg);
      }
      Collections.sort(segList);
      simpleVolume.setSegmentList(segList);

      // get all dirverContainer IP
      List<SimpleInstance> allInstance = new ArrayList<>();
      List<SimpleInstance> driverContainersList = new ArrayList<>();
      try {
        allInstance = instanceService.getAll(Constants.SUPER_ADMIN_ACCOUNT_ID);
        for (SimpleInstance instance : allInstance) {
          if (instance.getInstanceName().equals(PyService.DRIVERCONTAINER.getServiceName())
                  && instance.getStatus().equals(InstanceStatus.HEALTHY.name())) {
            driverContainersList.add(instance);
          }
        }

      } catch (Exception e) {
        logger.error("catch an Exception", e);
      }

      // get drivers
      List<DriverMetadata> driverMetadatas = response.getDriverMetadatas();
      if (driverMetadatas != null && driverMetadatas.size() > 0) {
        List<SimpleDriverMetadata> simpleDriverMetadatas = new ArrayList<SimpleDriverMetadata>();
        for (DriverMetadata driverMetadata : driverMetadatas) {
          SimpleDriverMetadata simpleDriverMetadata = new SimpleDriverMetadata();
          simpleDriverMetadata.setVolumeId(String.valueOf(driverMetadata.getVolumeId()));
          simpleDriverMetadata.setSnapshotId(String.valueOf(driverMetadata.getSnapshotId()));
          simpleDriverMetadata.setDriverType(driverMetadata.getDriverType().name());
          simpleDriverMetadata.setHost(driverMetadata.getHostName());
          simpleDriverMetadata.setPort(String.valueOf(driverMetadata.getPort()));
          simpleDriverMetadata
              .setCoordinatorPort(String.valueOf(driverMetadata.getCoordinatorPort()));
          simpleDriverMetadata
              .setDriverContainerId(String.valueOf(driverMetadata.getDriverContainerId()));
          simpleDriverMetadata.setDriverName(driverMetadata.getDriverName());
          simpleDriverMetadata.setVolumeName(driverMetadata.getVolumeName());
          simpleDriverMetadata.setChapControl(String.valueOf(driverMetadata.getChapControl()));
          for (SimpleInstance instance : driverContainersList) {
            if (instance.getInstanceId().equals(simpleDriverMetadata.getDriverContainerId())) {
              simpleDriverMetadata.setDriverContainerIp(instance.getHost());
              break;
            }
          }
          List<SimpleDriverClientInfo> driverClientInfoList = new ArrayList<>();
          for (Entry<String, AccessPermissionType> entry : driverMetadata.getClientHostAccessRule()
              .entrySet()) {
            SimpleDriverClientInfo clientInfo = new SimpleDriverClientInfo();
            clientInfo.setHost(entry.getKey());
            clientInfo.setAuthority(entry.getValue().name());
            driverClientInfoList.add(clientInfo);
          }
          logger.debug("driverClientInfoList is {}", driverClientInfoList);
          logger.debug("driverMetadataThrift.getClientHostAccessRule() is {}",
              driverMetadata.getClientHostAccessRule());
          simpleDriverMetadata.setDriverClientInfoList(driverClientInfoList);
          simpleDriverMetadata.setStatus(driverMetadata.getDriverStatus().name());
          String clientAmount = "";
          if (driverMetadata.getClientHostAccessRule() == null) {
            clientAmount = String.format("%d", 0);
          } else {
            clientAmount = String.format("%d", driverMetadata.getClientHostAccessRule().size());
          }
          simpleDriverMetadata.setClientAmount(clientAmount);
          // set the driver umount info
          simpleDriverMetadata.setMarkUnmountForCsi(driverMetadata.isMakeUnmountForCsi());
          simpleDriverMetadatas.add(simpleDriverMetadata);
        }
        simpleVolume.setDriverMetadatas(simpleDriverMetadatas);
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

    return simpleVolume;
  }

  @Override
  public List<SimpleVolumeMetadata> getVolumesByName(String volumeName, long accountId)
      throws VolumeNotFoundExceptionThrift {
    InformationCenter.Iface clientIc = null;
    List<SimpleVolumeMetadata> volumeList = new ArrayList<SimpleVolumeMetadata>();
    try {
      List<SimpleDomain> domainList = domainService.listDomains(null, accountId);
      clientIc = infoCenterClientFactory.build().getClient();
      ListVolumesRequest request = new ListVolumesRequest();
      request.setAccountId(accountId);
      request.setRequestId(RequestIdBuilder.get());
      // ListVolumesResponse response = client.listVolumes(request);
      ListVolumesResponse response = clientIc.listVolumes(request);
      for (VolumeMetadataThrift volumeThrift : response.getVolumes()) {
        SimpleVolumeMetadata volume = new SimpleVolumeMetadata();

        logger.debug("volumeThrift.getName() is {},volumeName is {}", volumeThrift.getName(),
            volumeName);
        if (volumeThrift.getName().contains(volumeName)) {
          volume.setVolumeId(String.valueOf(volumeThrift.getVolumeId()));
          volume.setAccountId(volumeThrift.getAccountId());
          volume.setVolumeSize(Utils.volumeSizeBuilder(volumeThrift.getVolumeSize()));
          volume.setVolumeName(volumeThrift.getName());
          volume.setDescription(volumeThrift.getVolumeDescription());
          volume.setClientLastConnectTime(String.valueOf(volumeThrift.getClientLastConnectTime()));
          volume.setVolumeStatus(volumeThrift.getVolumeStatus().name());
          volume.setVolumeType(volumeThrift.getVolumeType().name());
          volume.setReadWrite(volumeThrift.getReadWrite().name());
          volume.setFreeSpaceRatio(volumeThrift.getFreeSpaceRatio());
          volume.setEnableLaunchMultiDrivers(
              String.valueOf(volumeThrift.isEnableLaunchMultiDrivers()));
          volume.setRebalanceRatio(String.valueOf(volumeThrift.getRebalanceRatio()));
          volume.setRebalanceVersion(String.valueOf(volumeThrift.getRebalanceVersion()));
          if (volumeThrift.getInAction().equals(VolumeInActionThrift.NULL)) {
            volume.setVolumeStatus(volumeThrift.getVolumeStatus().name());
          } else {
            volume.setVolumeStatus(volumeThrift.getInAction().name());
          }

          if (volumeThrift.getLastExtendedTime() != 0) {
            volume.setExtendFlag("YES");
          } else {
            volume.setExtendFlag("NO");
          }
          volume.setCreateTime(volumeThrift.getVolumeCreatedTime());
          volume.setVolumeBuildType(String.valueOf(volumeThrift.getVolumeSource()));
          String domainName = getDomainNameById(volumeThrift.getDomainId(), domainList);
          volume.setVolumeDomain(domainName);

          volumeList.add(volume);
        }
      }
    } catch (TTransportException e) {
      logger.error("Exception catch", e);
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
    } catch (AccessDeniedExceptionThrift e) {
      logger.error("Exception catch", e);
    } catch (InternalErrorThrift e) {
      logger.error("Exception catch", e);
    } catch (InvalidInputExceptionThrift e) {
      logger.error("Exception catch", e);
    } catch (TException e) {
      logger.error("Exception catch", e);
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
    }
    return volumeList;
  }

  @Override
  public List<SimpleVolumeMetadata> getVolumeByAccount(String account) {
    return null;
  }

  @Override
  public SimpleVolumeMetadata getVolumeNotDeadByName(String volumeName, long accountId)
      throws VolumeNotFoundExceptionThrift {
    InformationCenter.Iface clientIc = null;
    try {
      // client = infoCenterClientFactory.build().getClient();
      clientIc = infoCenterClientFactory.build().getClient();
      GetVolumeRequest request = new GetVolumeRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setName(volumeName);
      // GetVolumeResponse response =
      // client.getVolumeNotDeadByName(request);
      GetVolumeResponse response = clientIc.getVolumeNotDeadByName(request);
      return buildSimpleVolumeMetadata(response, accountId);
    } catch (TTransportException e) {
      logger.error("Exception catch", e);
    } catch (InternalErrorThrift e) {
      logger.error("Exception catch", e);
    } catch (InvalidInputExceptionThrift e) {
      logger.error("Exception catch", e);
    } catch (NotEnoughSpaceExceptionThrift e) {
      logger.error("Exception catch", e);
    } catch (VolumeNotFoundExceptionThrift e) {
      logger.info("Exception catch", e);
      throw new VolumeNotFoundExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
    } catch (IOException e) {
      logger.error("Exception catch", e);
    }
    return null;
  }

  /**
   * get domain name by id.
   *
   * @param domainId domain id
   * @param domainList domain list
   * @return domain name
   */
  public String getDomainNameById(long domainId, List<SimpleDomain> domainList) {
    String domainName = null;
    logger.debug("getDomainNameById: domainList is {}", domainList);
    for (SimpleDomain domain : domainList) {
      logger.debug("domain.getDomainId() is {}, filter domainId(): {}", domain.getDomainId(),
          domainId);
      if (domain.getDomainId().equals(String.valueOf(domainId))) {
        logger.debug("domainName is {}", domain.getDomainName());
        domainName = domain.getDomainName();
      }
    }
    return domainName;
  }

  @Override
  public SimpleStoragePool getStoragePoolById(long poolId, Set<SimpleStoragePool> storagePools) {
    List<Long> storagePoolIds = new ArrayList<>();
    SimpleStoragePool pool = null;
    logger.debug("storagePools: {}", storagePools);
    for (SimpleStoragePool storagePool : storagePools) {
      if (storagePool.getPoolId().equals(String.valueOf(poolId))) {
        logger.debug("storage pool name is {}", storagePool.getPoolName());
        pool = storagePool;
      }
    }

    return pool;
  }

  @Override
  public List<SimpleVolumeMetadata> getMultipleVolumes(long accountId, Set<Long> ids)
      throws AccessDeniedExceptionThrift, ResourceNotExistsExceptionThrift,
      InvalidInputExceptionThrift, TooManyEndPointFoundExceptionThrift,
      ServiceHavingBeenShutdownThrift, VolumeNotFoundExceptionThrift, ServiceIsNotAvailableThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    InformationCenter.Iface clientIc = null;
    List<SimpleVolumeMetadata> volumeList = new ArrayList<SimpleVolumeMetadata>();
    try {
      List<SimpleDomain> domainList = domainService
          .listDomains(null, Constants.SUPER_ADMIN_ACCOUNT_ID);
      Set<SimpleStoragePool> storagePools = storagePoolService
          .listStoragePools(null, null, Constants.SUPER_ADMIN_ACCOUNT_ID);
      clientIc = infoCenterClientFactory.build().getClient();
      ListVolumesRequest request = new ListVolumesRequest();
      request.setAccountId(accountId);
      request.setRequestId(RequestIdBuilder.get());
      request.setContainDeadVolume(false);
      if (ids != null && !ids.isEmpty()) {
        request.setVolumesCanBeList(ids);
      }
      logger.debug("list volumes request is {}", request);
      ListVolumesResponse response = clientIc.listVolumes(request);
      logger.debug("list volumes response is {}", response);
      Map<Long, VolumeDeleteDelayInformationThrift> volumeDeleteDelayInfoMap =
          response.getVolumeDeleteDelayInfo();

      for (VolumeMetadataThrift volumeThrift : response.getVolumes()) {
        SimpleVolumeMetadata volume = buildVolumeWithoutSegment(volumeThrift);

        // set deley info
        if (volumeDeleteDelayInfoMap != null) {
          for (Entry<Long, VolumeDeleteDelayInformationThrift> entry :
              volumeDeleteDelayInfoMap.entrySet()) {
            if (volumeThrift.getVolumeId() == entry.getKey()) {
              volume.setStopDelay(String.valueOf(entry.getValue().isStopDelay()));
              volume.setTimeForDelay(String.valueOf(entry.getValue().getTimeForDelay()));
              break;
            }
          }
        }

        // domain name
        String domainName = getDomainNameById(volumeThrift.getDomainId(), domainList);
        volume.setVolumeDomain(domainName);
        // set pool
        SimpleStoragePool storagePool = getStoragePoolById(volumeThrift.getStoragePoolId(),
            storagePools);
        String storagePoolName = storagePool.getPoolName();
        volume.setStoragePoolName(storagePoolName);
        String poolId = storagePool.getPoolId();
        volume.setPoolId(poolId);
        // 隐藏迁移过程中正在删除的源卷
        if (!volume.getVolumeName()
            .contains(py.informationcenter.Utils.MOVE_VOLUME_APPEND_STRING_FOR_ORIGINAL_VOLUME)) {
          volumeList.add(volume);
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
    return volumeList;
  }

  /**
   * build volume without segment.
   *
   * @param volumeThrift volume thrift
   * @return simple volume metadata
   */
  public SimpleVolumeMetadata buildVolumeWithoutSegment(VolumeMetadataThrift volumeThrift) {
    logger.debug("get volumeThrift is {}", volumeThrift);
    SimpleVolumeMetadata volume = new SimpleVolumeMetadata();
    volume.setVolumeId(String.valueOf(volumeThrift.getVolumeId()));
    volume.setAccountId(volumeThrift.getAccountId());
    volume.setVolumeSize(Utils.volumeSizeBuilder(volumeThrift.getVolumeSize()));
    volume.setVolumeName(volumeThrift.getName());
    volume.setDescription(volumeThrift.getVolumeDescription());
    volume.setClientLastConnectTime(String.valueOf(volumeThrift.getClientLastConnectTime()));
    volume.setVolumeType(volumeThrift.getVolumeType().name());
    volume.setFreeSpaceRatio(volumeThrift.getFreeSpaceRatio());
    volume.setReadWrite(volumeThrift.getReadWrite().name());
    volume.setMigrationRatio(String.valueOf(volumeThrift.getMigrationRatio()));
    volume.setMigrationSpeed(String.valueOf(volumeThrift.getMigrationSpeed()));
    volume.setDomainId(String.valueOf(volumeThrift.getDomainId()));
    volume.setVolumeStoragePoolId(String.valueOf(volumeThrift.getStoragePoolId()));
    volume
        .setEnableLaunchMultiDrivers(String.valueOf(volumeThrift.isEnableLaunchMultiDrivers()));
    volume.setRebalanceRatio(String.valueOf(volumeThrift.getRebalanceRatio()));
    volume.setRebalanceVersion(String.valueOf(volumeThrift.getRebalanceVersion()));
    volume.setTotalPhysicalSpace(Utils.volumeSizeBuilder(volumeThrift.getTotalPhysicalSpace()));
    volume.setSrcSnapshotNameWithClone(volumeThrift.getSrcSnapshotNameWithClone());
    volume.setSrcVolumeNameWithClone(volumeThrift.getSrcVolumeNameWithClone());
    volume.setCsiLaunchCount(String.valueOf(volumeThrift.getCsiLaunchCount()));
    if (volumeThrift.isSetReadOnlyForCsi()) {
      volume.setReadOnlyForCsi(String.valueOf(volumeThrift.isReadOnlyForCsi()));
    } else {
      volume.setReadOnlyForCsi("");
    }
    if (volumeThrift.isSetUsedSpaceForCsi()) {
      volume.setUsedSpaceForCsi(String.valueOf(volumeThrift.getUsedSpaceForCsi()));
    } else {
      // just for order
      volume.setUsedSpaceForCsi("0");
    }
    volume.setTotalSpaceFroCsi(String.valueOf(volumeThrift.getTotalSpaceFroCsi()));
    // logger.warn("volumeThrift.getFreeSpaceRatio() is {}", volumeThrift.getFreeSpaceRatio());
    volume.setSimpleConfiguration(volumeThrift.isSimpleConfiguration() ? "Yes" : "No");
    // 根据inAction设置他的中间状态
    if (volumeThrift.getInAction().equals(VolumeInActionThrift.NULL)) {
      volume.setVolumeStatus(volumeThrift.getVolumeStatus().name());
    } else {
      volume.setVolumeStatus(volumeThrift.getInAction().name());
    }

    if (volumeThrift.getLastExtendedTime() != 0) {
      volume.setExtendFlag("YES");
    } else {
      volume.setExtendFlag("NO");
    }
    volume.setCreateTime(volumeThrift.getVolumeCreatedTime());
    volume.setVolumeBuildType(String.valueOf(volumeThrift.getVolumeSource()));

    return volume;

  }


  @Override
  public List<String> launchVolume(String driverName, long volumeId, int snapshotId,
      String driverType,
      int driverAmount,
      long accountId, String host, String scsiIp, boolean volumeCanNotLaunchMultiDriversThisTime,
      boolean forCsi, String nodeId, boolean forAutoFixLaunchWithCsi)
      throws VolumeNotFoundExceptionThrift, VolumeNotAvailableExceptionThrift,
      TooManyDriversExceptionThrift,
      NotRootVolumeExceptionThrift, ServiceHavingBeenShutdownThrift,
      VolumeBeingDeletedExceptionThrift,
      DriverTypeConflictExceptionThrift, AccessDeniedExceptionThrift, InvalidInputExceptionThrift,
      ServiceIsNotAvailableThrift, VolumeUnderOperationExceptionThrift,
      SnapshotRollingBackExceptionThrift,
      DriverLaunchingExceptionThrift,
      DriverUnmountingExceptionThrift, VolumeDeletingExceptionThrift,
      VolumeWasRollbackingExceptionThrift,
      SystemMemoryIsNotEnoughThrift, DriverAmountAndHostNotFitThrift, DriverHostCannotUseThrift,
      DriverIsUpgradingExceptionThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift,
      DriverTypeIsConflictExceptionThrift, DriverNameExistsExceptionThrift,
      ExistsDriverExceptionThrift,
      VolumeLaunchMultiDriversExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      VolumeInMoveOnlineDoNotHaveOperationExceptionThrift, UnknownIpv4HostExceptionThrift,
      UnknownIpv6HostExceptionThrift, ConnectPydDeviceOperationExceptionThrift,
      SystemCpuIsNotEnoughThrift,
      CreateLoopbackOperationExceptionThrift, GetScsiDeviceOperationExceptionThrift,
      ScsiDeviceIsLaunchExceptionThrift, CreateBackstoresOperationExceptionThrift,
      NoEnoughPydDeviceExceptionThrift, CreateLoopbackLunsOperationExceptionThrift, TException,
      VolumeCanNotLaunchMultiDriversThisTimeExceptionThrift {
    InformationCenter.Iface client = null;
    try {
      client = infoCenterClientFactory.build().getClient();
      LaunchDriverRequestThrift request = new LaunchDriverRequestThrift();
      request.setRequestId(RequestIdBuilder.get());
      request.setDriverName(driverName);
      request.setVolumeId(volumeId);
      request.setAccountId(accountId);
      request.setSnapshotId(snapshotId);
      request.setDriverType(DriverTypeThrift.valueOf(driverType));
      request.setDriverAmount(driverAmount);
      request.setVolumeCanNotLaunchMultiDriversThisTime(volumeCanNotLaunchMultiDriversThisTime);
      if (!StringUtils.isEmpty(host)) {
        request.setHostName(host);
      }
      logger
          .info("launchVolume for volume :{}, LaunchDriverRequestThrift is {}", volumeId, request);
      LaunchDriverResponseThrift response = client.launchDriver(request);
      List<String> driverNameLaunchThisTime = new ArrayList<>();
      if (response.getDriverNameLaunchThisTime() != null
          && !response.getDriverNameLaunchThisTime().isEmpty()) {
        driverNameLaunchThisTime.addAll(response.getDriverNameLaunchThisTime());
      }
      return driverNameLaunchThisTime;
    } catch (EndPointNotFoundException e) {
      logger.error("launchVolume for volume :{} Exception catch", volumeId, e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (VolumeCanNotLaunchMultiDriversThisTimeExceptionThrift e) {
      logger.error("launchVolume for volume :{} Exception catch", volumeId, e);
      throw new VolumeCanNotLaunchMultiDriversThisTimeExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("launchVolume for volume :{} Exception catch", volumeId, e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("launchVolume for volume :{} Exception catch", volumeId, e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("launchVolume for volume :{} Exception catch", volumeId, e);
      throw e;
    }
  }

  /**
   * build simple volume metadata.
   *
   * @param response response
   * @param accountId account id
   * @return simple volume metadata
   * @throws IOException IOException
   */
  public SimpleVolumeMetadata buildSimpleVolumeMetadata(GetVolumeResponse response, long accountId)
      throws IOException {
    logger.debug("response is {}", response);
    SimpleVolumeMetadata simpleVolume = new SimpleVolumeMetadata();
    if (!response.isSetVolumeMetadata()) {
      logger.debug("is not set metadata");
      return null;
    }
    VolumeMetadataThrift volumeMetadataThrift = response.getVolumeMetadata();
    logger.debug("get the volumeMetadataThrift is: {}", volumeMetadataThrift);
    VolumeMetadata volume = RequestResponseHelper.buildVolumeFrom(volumeMetadataThrift);

    simpleVolume.setAccountId(volume.getAccountId());
    simpleVolume.setVolumeId(String.valueOf(volume.getVolumeId()));
    simpleVolume.setVolumeName(volume.getName());
    simpleVolume.setDescription(volume.getVolumeDescription());
    simpleVolume.setClientLastConnectTime(String.valueOf(volume.getClientLastConnectTime()));
    simpleVolume.setVolumeSize(Utils.volumeSizeBuilder(volume.getVolumeSize()));
    simpleVolume.setFreeSpaceRatio(volume.getFreeSpaceRatio());
    simpleVolume.setReadWrite(volume.getReadWrite().name());
    simpleVolume.setRebalanceRatio(String.valueOf(volume.getRebalanceInfo().getRebalanceRatio()));
    simpleVolume
        .setRebalanceVersion(String.valueOf(volume.getRebalanceInfo().getRebalanceVersion()));
    logger.warn("volume.getFreeSpaceRatio() is {}", volume.getFreeSpaceRatio());
    simpleVolume.setSimpleConfiguration(
        response.getVolumeMetadata().isSimpleConfiguration() ? "Yes" : "No");
    Validate.isTrue(volume.getVolumeStatus() != VolumeStatus.Dead);
    if (volume.getInAction().equals(VolumeInAction.NULL)) {
      simpleVolume.setVolumeStatus(volume.getVolumeStatus().name());
    } else {
      simpleVolume.setVolumeStatus(volume.getInAction().name());
    }

    if (volume.getLastExtendedTime() != null && volume.getLastExtendedTime().getTime() != 0) {
      simpleVolume.setExtendFlag("YES");
    } else {
      simpleVolume.setExtendFlag("NO");
    }
    simpleVolume.setVolumeType(volume.getVolumeType().name());
    simpleVolume.setCreateTime(volume.getVolumeCreatedTime().getTime());
    simpleVolume.setVolumeBuildType(String.valueOf(volume.getVolumeSource()));
    simpleVolume.setEnableLaunchMultiDrivers(String.valueOf(volume.isEnableLaunchMultiDrivers()));
    // list the snapshot metadata

    /* // list the snapshot metadata
    ByteBuffer snapshotManagerBuff = volumeMetadataThrift.snapshotManagerInBinary;
    logger.debug("volumeMetadataThrift is {}",volumeMetadataThrift);
    logger.debug("snapshotManagerBuff is {}",snapshotManagerBuff);
    logger.debug("snapshotManagerBuff.array() is {}",snapshotManagerBuff.array());
    DistributedVolumeSnapshotManager snapshotManager = null;
    try {
        snapshotManager = DistributedVolumeSnapshotManagerImpl
                .parseFromByteArray(snapshotManagerBuff.array(), volume.getVolumeId());
    } catch (IOException e) {
        logger.error("Exception catch", e);
        throw e;
    }
    List<SimpleSnapshotMetadata> snapshotsList = new ArrayList<>();
    for (SnapshotMetadata node : snapshotManager.listSnapshots()) {
        SimpleSnapshotMetadata snapshot = new SimpleSnapshotMetadata(node.getSnapshotId(), node
        .getSnapshotName(),
                node.getSnapshotDescription(), node.getCreatedTime());

        //:TODO snapshot is not set

        snapshotsList.add(snapshot);
    }
    simpleVolume.setSnapshotMetadatas(snapshotsList); */

    // get pool
    Set<SimpleStoragePool> storagePools = new HashSet<>();
    try {
      storagePools = storagePoolService
          .listStoragePools(null, null, Constants.SUPER_ADMIN_ACCOUNT_ID);
    } catch (Exception e) {
      logger.error("catch an Exception", e);
    }

    SimpleStoragePool storagePool = getStoragePoolById(
        response.getVolumeMetadata().getStoragePoolId(),
        storagePools);
    String storagePoolName = storagePool.getPoolName();
    simpleVolume.setStoragePoolName(storagePoolName);
    String poolId = storagePool.getPoolId();
    simpleVolume.setPoolId(poolId);

    // get all dirverContainer IP
    List<SimpleInstance> allInstance = new ArrayList<>();
    List<SimpleInstance> driverContainersList = new ArrayList<>();
    try {
      allInstance = instanceService.getAll(Constants.SUPER_ADMIN_ACCOUNT_ID);
      for (SimpleInstance instance : allInstance) {
        if (instance.getInstanceName().equals(PyService.DRIVERCONTAINER.getServiceName())
                && instance.getStatus().equals(InstanceStatus.HEALTHY.name())) {
          driverContainersList.add(instance);
        }
      }

    } catch (Exception e) {
      logger.error("catch an Exception", e);
    }

    if (response.getDriverMetadatas() != null && response.getDriverMetadatas().size() > 0) {
      List<SimpleDriverMetadata> simpleDriverMetadatas = new ArrayList<SimpleDriverMetadata>();
      for (DriverMetadataThrift driverMetadataThrift : response.getDriverMetadatas()) {
        SimpleDriverMetadata simpleDriverMetadata = new SimpleDriverMetadata();
        simpleDriverMetadata.setVolumeId(String.valueOf(driverMetadataThrift.getVolumeId()));
        simpleDriverMetadata.setSnapshotId(String.valueOf(driverMetadataThrift.getSnapshotId()));
        simpleDriverMetadata.setDriverType(driverMetadataThrift.getDriverType().name());
        simpleDriverMetadata.setHost(driverMetadataThrift.getHostName());
        simpleDriverMetadata.setPort(String.valueOf(driverMetadataThrift.getPort()));
        simpleDriverMetadata
            .setCoordinatorPort(String.valueOf(driverMetadataThrift.getCoordinatorPort()));
        simpleDriverMetadata
            .setDriverContainerId(String.valueOf(driverMetadataThrift.getDriverContainerId()));
        simpleDriverMetadata.setDriverName(driverMetadataThrift.getDriverName());
        simpleDriverMetadata.setVolumeName(driverMetadataThrift.getVolumeName());
        simpleDriverMetadata.setChapControl(String.valueOf(driverMetadataThrift.getChapControl()));
        for (SimpleInstance instance : driverContainersList) {
          if (instance.getInstanceId().equals(simpleDriverMetadata.getDriverContainerId())) {
            simpleDriverMetadata.setDriverContainerIp(instance.getHost());
            break;
          }
        }
        List<SimpleDriverClientInfo> driverClientInfoList = new ArrayList<>();
        for (Entry<String, AccessPermissionTypeThrift> entry : driverMetadataThrift
            .getClientHostAccessRule()
            .entrySet()) {
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
          clientAmount = String
              .format("%d", driverMetadataThrift.getClientHostAccessRule().size());
        }
        simpleDriverMetadata.setClientAmount(clientAmount);
        // set the driver umount info
        if (driverMetadataThrift.isSetMakeUnmountForCsi()) {
          simpleDriverMetadata.setMarkUnmountForCsi(driverMetadataThrift.isMakeUnmountForCsi());
        }

        simpleDriverMetadatas.add(simpleDriverMetadata);
      }
      simpleVolume.setDriverMetadatas(simpleDriverMetadatas);
    }
    List<SimpleSegmentMetadata> segList = new ArrayList<SimpleSegmentMetadata>();
    for (SegmentMetadata segmentMetadata : volume.getSegments()) {
      SimpleSegmentMetadata simpleSeg = new SimpleSegmentMetadata();
      simpleSeg.setSegId(segmentMetadata.getIndex());
      simpleSeg.setUnitSize(segmentMetadata.getSegmentUnitCount());
      List<SimpleSegUnit> unitList = new ArrayList<SimpleSegUnit>();
      for (SegmentUnitMetadata segmentUnitMetadata : segmentMetadata.getSegmentUnits()) {
        SimpleSegUnit segUnit = new SimpleSegUnit();
        segUnit.setInstanceId(String.valueOf(segmentUnitMetadata.getInstanceId().getId()));
        segUnit.setStatus(segmentUnitMetadata.getStatus().name());
        segUnit
            .setStatusDisplay(Utils.segmentStatusConvert(segmentUnitMetadata.getStatus().name()));
        if (segmentUnitMetadata.getStatus().equals(SegmentUnitStatus.PreSecondary)) {
          segUnit.setRatioMigration(segmentUnitMetadata.getRatioMigration());
        }
        segUnit.setDiskName(segmentUnitMetadata.getDiskName());
        segUnit.setOffset(String.valueOf(segmentUnitMetadata.getPhysicalDataOffset()));
        SegmentMembership membership = segmentUnitMetadata.getMembership();
        if (membership.getPrimary().getId() == Long.valueOf(segUnit.getInstanceId())) {
          segUnit.setUnitType(UnitType.Primary.name());
        } else {
          segUnit.setUnitType(UnitType.Secondary.name());
        }
        logger
            .debug("segment version {} epoch = {} generation = {}", segmentUnitMetadata.getSegId(),
                membership.getSegmentVersion().getEpoch(),
                membership.getSegmentVersion().getGeneration());
        SimpleSegmentVersion simpleSegmentVersion = new SimpleSegmentVersion();
        simpleSegmentVersion.setEpoch(membership.getSegmentVersion().getEpoch());
        simpleSegmentVersion.setGeneration(membership.getSegmentVersion().getGeneration());
        segUnit.setSimpleSegmentVersion(simpleSegmentVersion);
        unitList.add(segUnit);
      }
      sortUnitList(unitList);
      simpleSeg.setUnitList(unitList);
      segList.add(simpleSeg);
    }
    Collections.sort(segList);
    simpleVolume.setSegmentList(segList);
    return simpleVolume;
  }

  /**
   * sort unit list.
   *
   * @param unitList unit list
   */
  public void sortUnitList(List<SimpleSegUnit> unitList) {
    if (unitList.size() < 2) {
      return;
    }
    Collections.sort(unitList);
    for (int i = 0; i < unitList.size(); i++) {
      if (unitList.get(i).getUnitType().equals(UnitType.Primary.name()) && i != 0) {
        Collections.swap(unitList, 0, i);
      }
    }
  }

  @Override
  public boolean umountVolume(long volumeId, long accountId, List<DriverIpTargetThrift> driTarget,
      String scsiIp, String nodeId, long forDelayUmountTime, boolean forceUmount)
      throws VolumeNotFoundExceptionThrift, ServiceHavingBeenShutdownThrift,
      FailedToUmountDriverExceptionThrift, ExistsClientExceptionThrift,
      DriverIsLaunchingExceptionThrift,
      AccessDeniedExceptionThrift, ServiceIsNotAvailableThrift, SnapshotRollingBackExceptionThrift,
      DriverLaunchingExceptionThrift,
      DriverUnmountingExceptionThrift, VolumeDeletingExceptionThrift,
      VolumeUnderOperationExceptionThrift,
      InvalidInputExceptionThrift, DriverIsUpgradingExceptionThrift, TransportExceptionThrift,
      DriverContainerIsIncExceptionThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      NoDriverLaunchExceptionThrift, TException {
    logger.debug("umount volume binding driver on host {}", driTarget);
    UmountDriverRequestThrift request = new UmountDriverRequestThrift();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setVolumeId(volumeId);
    request.setDriverIpTargetList(driTarget);
    request.setForceUmount(forceUmount);
    logger.debug("UmountDriverRequestThrift is {}", request);
    InformationCenter.Iface client = null;
    try {
      client = infoCenterClientFactory.build(60000).getClient();
      UmountDriverResponseThrift response = client.umountDriver(request);
      if (response != null) {
        return true;
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
    return false;
  }

  @Override
  public GetVolumeResponse getVolume(long volumeId, long accountId)
      throws InvalidInputExceptionThrift, VolumeNotFoundExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      ServiceIsNotAvailableThrift, TException {
    InformationCenter.Iface clientIc;
    try {
      clientIc = infoCenterClientFactory.build().getClient();
      GetVolumeRequest request = new GetVolumeRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      request.setVolumeId(volumeId);
      request.setContainDeadVolume(false);
      GetVolumeResponse response = clientIc.getVolume(request);
      Validate.isTrue(response.getVolumeMetadata().getVolumeStatus() != VolumeStatusThrift.Dead);
      return response;
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (InternalErrorThrift e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }
  }

  @Override
  public boolean extendVolume(long volumeId, long extendSize, long originalSize, long accountId)
      throws NotEnoughSpaceExceptionThrift, EndPointNotFoundExceptionThrift,
      InvalidInputExceptionThrift,
      AccessDeniedExceptionThrift, ServiceHavingBeenShutdownThrift,
      VolumeSizeNotMultipleOfSegmentSizeThrift,
      RootVolumeBeingDeletedExceptionThrift, RootVolumeNotFoundExceptionThrift,
      ServiceIsNotAvailableThrift,
      VolumeWasRollbackingExceptionThrift, NotEnoughGroupExceptionThrift,
      VolumeIsCloningExceptionThrift,
      PermissionNotGrantExceptionThrift, AccountNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, DomainNotExistedExceptionThrift,
      NotEnoughLicenseTokenExceptionThrift,
      LicenseExceptionThrift, UselessLicenseExceptionThrift, VolumeNotFoundExceptionThrift,
      BadLicenseTokenExceptionThrift, VolumeIsCopingExceptionThrift, VolumeExistingExceptionThrift,
      StoragePoolNotExistedExceptionThrift, StoragePoolNotExistInDoaminExceptionThrift,
      DomainIsDeletingExceptionThrift, StoragePoolIsDeletingExceptionThrift,
      VolumeInMoveOnlineDoNotHaveOperationExceptionThrift, NotEnoughNormalGroupExceptionThrift,
      VolumeSizeIllegalExceptionThrift, VolumeOriginalSizeNotMatchExceptionThrift, TException {
    InformationCenter.Iface client = null;
    try {
      client = infoCenterClientFactory.build().getClient();
      ExtendVolumeRequest request = new ExtendVolumeRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setVolumeId(volumeId);
      request.setAccountId(accountId);
      request.setExtendSize(extendSize);
      request.setOriginalSize(originalSize);
      ExtendVolumeResponse response = client.extendVolume(request);
      if (response != null) {
        return true;
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
    return false;
  }

  public DomainService getDomainService() {
    return domainService;
  }

  public void setDomainService(DomainService domainService) {
    this.domainService = domainService;
  }

  /**
   * modify to limit.
   *
   * @param volumeId volume id
   * @param ioLimitation IO limitation
   * @param accountId account id
   * @return bool
   * @throws VolumeNotFoundExceptionThrift VolumeNotFoundExceptionThrift
   */
  @Deprecated
  public boolean modifyIoLimit(long volumeId, IoLimitation ioLimitation, long accountId)
      throws VolumeNotFoundExceptionThrift {
    InformationCenter.Iface cclientIc = null;
    try {
      cclientIc = infoCenterClientFactory.build().getClient();
      AddOrModifyIoLimitRequest request = new AddOrModifyIoLimitRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setVolumeId(volumeId);
      request.setIoLimitation(RequestResponseHelper.buildThriftIoLimitationFrom(ioLimitation));
      request.setAccountId(accountId);
      AddOrModifyIoLimitResponse response = cclientIc.addOrModifyIoLimit(request);
      if (response == null) {
        return false;
      } else {
        return true;
      }
    } catch (TTransportException e) {
      logger.error("Exception catch", e);
    } catch (InternalErrorThrift e) {
      logger.error("Exception catch", e);
    } catch (InvalidInputExceptionThrift e) {
      logger.error("Exception catch", e);
    } catch (VolumeNotFoundExceptionThrift e) {
      logger.error("Exception catch", e);
      throw new VolumeNotFoundExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
    }
    return false;
  }

  @Override
  public List<SimpleSegmentMetadata> getSegmentList(long volumeId, long accountId,
      int startSegmentIndex,
      int endSegmentIndex) throws VolumeNotFoundExceptionThrift {
    InformationCenter.Iface clientIc = null;
    logger.info("now to get segment list index {}:{}", startSegmentIndex, endSegmentIndex);
    try {
      clientIc = infoCenterClientFactory.build().getClient();
      GetSegmentListRequest request = new GetSegmentListRequest();
      request.setVolumeId(volumeId);
      request.setAccountId(accountId);
      request.setStartSegmentIndex(startSegmentIndex);
      request.setEndSegmentIndex(endSegmentIndex);
      GetSegmentListResponse response = clientIc.getSegmentList(request);
      List<SimpleSegmentMetadata> segList = new ArrayList<SimpleSegmentMetadata>();
      for (SegmentMetadataThrift segmentMetadataThrift : response.getSegments()) {
        SimpleSegmentMetadata simpleSeg = new SimpleSegmentMetadata();
        simpleSeg.setSegId(segmentMetadataThrift.getIndexInVolume());
        simpleSeg.setUnitSize(segmentMetadataThrift.getSegmentUnitsSize());
        List<SimpleSegUnit> unitList = new ArrayList<SimpleSegUnit>();
        for (SegmentUnitMetadataThrift segUnitMetaThrift : segmentMetadataThrift
            .getSegmentUnits()) {
          SimpleSegUnit segUnit = new SimpleSegUnit();
          segUnit.setInstanceId(String.valueOf(segUnitMetaThrift.getInstanceId()));
          segUnit.setStatus(segUnitMetaThrift.getStatus().name());
          segUnit
              .setStatusDisplay(Utils.segmentStatusConvert(segUnitMetaThrift.getStatus().name()));
          if (segUnitMetaThrift.getStatus().equals(SegmentUnitStatusThrift.PreSecondary)) {
            if (segUnitMetaThrift.isSetRatioMigration()) {
              segUnit.setRatioMigration(segUnitMetaThrift.getRatioMigration());
            }
          }
          segUnit.setDiskName(segUnitMetaThrift.getDiskName());
          segUnit.setOffset(String.valueOf(segUnitMetaThrift.getOffset()));
          SimpleInstance simpleInstance = instanceService
              .getInstances(segUnitMetaThrift.getInstanceId());
          segUnit.setInstanceIp(simpleInstance.getHost());
          SegmentMembershipThrift membershipThrift = segUnitMetaThrift.getMembership();
          if (membershipThrift.getPrimary() == Long.valueOf(segUnit.getInstanceId())) {
            segUnit.setUnitType(UnitType.Primary.name());
          } else {
            segUnit.setUnitType(UnitType.Secondary.name());
          }
          logger.debug("segment version {} epoch = {} generation = {}",
              segUnitMetaThrift.getSegIndex(),
              membershipThrift.getEpoch(), membershipThrift.getGeneration());
          SimpleSegmentVersion simpleSegmentVersion = new SimpleSegmentVersion();
          simpleSegmentVersion.setEpoch(membershipThrift.getEpoch());
          simpleSegmentVersion.setGeneration(membershipThrift.getGeneration());
          segUnit.setSimpleSegmentVersion(simpleSegmentVersion);
          unitList.add(segUnit);
        }
        sortUnitList(unitList);
        simpleSeg.setUnitList(unitList);
        segList.add(simpleSeg);
      }
      Collections.sort(segList);
      return segList;
    } catch (Exception e) {
      logger.error("Exception catch", e);
    }
    return null;
  }

  public StoragePoolService getStoragePoolService() {
    return storagePoolService;
  }

  public void setStoragePoolService(StoragePoolService storagePoolService) {
    this.storagePoolService = storagePoolService;
  }

  @Override
  @Deprecated
  public List<SimpleIoLimitation> listIoLimits(long accountId, SimpleDriverMetadata driver)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      DriverNotFoundExceptionThrift, ServiceIsNotAvailableThrift, TException {
    InformationCenter.Iface ccClient;

    try {
      ccClient = infoCenterClientFactory.build().getClient();
      GetLimitsRequest request = new GetLimitsRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setVolumeId(Long.valueOf(driver.getVolumeId()));
      request.setSnapshotId(Integer.valueOf(driver.getSnapshotId()));
      request.setDriverContainerId(Long.valueOf(driver.getDriverContainerId()));
      request.setDriverType(DriverTypeThrift.valueOf(driver.getDriverType()));
      logger.debug("listIOLimits request is {}", request);
      GetLimitsResponse response = ccClient.getLimits(request);
      logger.debug("listIOLimits response is {}", response);
      List<SimpleIoLimitation> limitList = new ArrayList<>();
      for (IoLimitationThrift limitationThrift : response.getIoLimitations()) {
        SimpleIoLimitation ioLimitation = new SimpleIoLimitation();
        ioLimitation.setLimitationId(String.valueOf(limitationThrift.getLimitationId()));
        // TODO ioLimitationEntry
        //                ioLimitation.setUpperLimitedIOPS(limitationThrift.getUpperLimitedIOPS());
        //                ioLimitation.setLowerLimitedIOPS(limitationThrift.getLowerLimitedIOPS());
        //                ioLimitation.setUpperLimitedThroughput(limitationThrift
        //                .getUpperLimitedThroughput());
        //                ioLimitation.setLowerLimitedThroughput(limitationThrift
        //                .getLowerLimitedThroughput());
        //                ioLimitation.setLimitType(String.valueOf(limitationThrift.getLimitType
        //                ()));
        //                ioLimitation.setStartTime(String.valueOf(limitationThrift.getStartTime
        //                ()));
        //                ioLimitation.setEndTime(String.valueOf(limitationThrift.getEndTime()));
        limitList.add(ioLimitation);
      }
      return limitList;
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (DriverNotFoundExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (ServiceIsNotAvailableThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }

  }

  @Override
  public void addOrModifyIoLimit(long accountId, IoLimitation ioLimitation,
      SimpleDriverMetadata driver)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      DriverNotFoundExceptionThrift, ServiceIsNotAvailableThrift, AccountNotFoundExceptionThrift,
      AlreadyExistStaticLimitationExceptionThrift, AccessDeniedExceptionThrift,
      DynamicIoLimitationTimeInterleavingExceptionThrift,
      InvalidInputExceptionThrift, PermissionNotGrantExceptionThrift, TException {
    InformationCenter.Iface ccClient = null;

    try {
      ccClient = infoCenterClientFactory.build().getClient();
      AddOrModifyIoLimitRequest request = new AddOrModifyIoLimitRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      request.setVolumeId(Long.valueOf(driver.getVolumeId()));
      request.setSnapshotId(Integer.valueOf(driver.getSnapshotId()));
      request.setDriverContainerId(Long.valueOf(driver.getDriverContainerId()));
      request.setDriverType(DriverTypeThrift.valueOf(driver.getDriverType()));
      request.setIoLimitation(RequestResponseHelper.buildThriftIoLimitationFrom(ioLimitation));
      logger.debug("addOrModifyIOLimit request is {}", request);
      AddOrModifyIoLimitResponse response = ccClient.addOrModifyIoLimit(request);
      logger.debug("addOrModifyIOLimit response is {}", response);
      request.setIoLimitation(RequestResponseHelper.buildThriftIoLimitationFrom(ioLimitation));
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (IllegalArgumentException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (DriverNotFoundExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (AccountNotFoundExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (AlreadyExistStaticLimitationExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (DynamicIoLimitationTimeInterleavingExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (ServiceIsNotAvailableThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (InvalidInputExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (PermissionNotGrantExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (AccessDeniedExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }

  }

  @Deprecated
  @Override
  public boolean deleteIoLimit(long accountId, long limitId, SimpleDriverMetadata driver)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      InvalidInputExceptionThrift, ServiceIsNotAvailableThrift, AccountNotFoundExceptionThrift,
      DriverNotFoundExceptionThrift, AccessDeniedExceptionThrift, PermissionNotGrantExceptionThrift,
      TException {
    InformationCenter.Iface ccClient = null;
    try {
      ccClient = infoCenterClientFactory.build().getClient();
      DeleteIoLimitRequest request = new DeleteIoLimitRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      request.setLimitId(limitId);
      request.setVolumeId(Long.valueOf(driver.getVolumeId()));
      request.setSnapshotId(Integer.valueOf(driver.getSnapshotId()));
      request.setDriverContainerId(Long.valueOf(driver.getDriverContainerId()));
      request.setDriverType(DriverTypeThrift.valueOf(driver.getDriverType()));
      logger.debug("deleteIOLimit request is {}", request);
      DeleteIoLimitResponse response = ccClient.deleteIoLimit(request);
      logger.debug("deleteIOLimit response is {}", response);
      return true;
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (InvalidInputExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (ServiceIsNotAvailableThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (AccountNotFoundExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (DriverNotFoundExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (AccessDeniedExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (PermissionNotGrantExceptionThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }
  }

  @Override
  public FixVolumeResponse fixvolume(long volumeId, long accountId)
      throws AccountNotFoundExceptionThrift, VolumeNotFoundExceptionThrift,
      AccessDeniedExceptionThrift,
      ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      VolumeInMoveOnlineDoNotHaveOperationExceptionThrift, TException {
    InformationCenter.Iface clientCc = null;
    FixVolumeRequestThrift request = new FixVolumeRequestThrift();
    FixVolumeResponseThrift responseThrift = new FixVolumeResponseThrift();
    FixVolumeResponse response = new FixVolumeResponse();
    try {
      clientCc = infoCenterClientFactory.build().getClient();
      request.setAccountId(accountId);
      request.setVolumeId(volumeId);
      logger.debug("fixvolume request is {}", request);
      responseThrift = clientCc.fixVolume(request);
      logger.debug("fixvolume responseThrift is {}", responseThrift);
      response.setNeedFixVolume(responseThrift.isNeedFixVolume());
      response.setFixVolumeCompletely(responseThrift.isFixVolumeCompletely());
      List<SimpleInstance> lostDatanodeList;
      List<Long> datanodeIds = new ArrayList<>();
      datanodeIds.addAll(responseThrift.getLostDatanodes());
      lostDatanodeList = domainService.listInstancesByIds(datanodeIds);
      response.setLostDatanodes(lostDatanodeList);
      logger.debug("fixvolume response is {}", response);
      return response;
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
  public boolean confirmFixVolume(long accountId, long volumeId, List<Long> lostDatanodes)
      throws InternalErrorThrift, VolumeNotFoundExceptionThrift, AccessDeniedExceptionThrift,
      LackDatanodeExceptionThrift, NotEnoughSpaceExceptionThrift, InvalidInputExceptionThrift,
      ServiceIsNotAvailableThrift, VolumeFixingOperationExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      FrequentFixVolumeRequestThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    InformationCenter.Iface clientCc = null;
    ConfirmFixVolumeRequestThrift request = new ConfirmFixVolumeRequestThrift();
    ConfirmFixVolumeResponseThrift response = new ConfirmFixVolumeResponseThrift();
    try {
      clientCc = infoCenterClientFactory.build().getClient();
      request.setAccountId(accountId);
      request.setVolumeId(volumeId);
      if (lostDatanodes != null) {
        Set<Long> lostDatanodesSet = new HashSet<>();
        lostDatanodesSet.addAll(lostDatanodes);
        logger.debug("lostDatanodesSet is {}", lostDatanodesSet);
        request.setLostDatanodes(lostDatanodesSet);
      }
      logger.debug("confirmFixVolume is {}", request);
      response = clientCc.confirmFixVolume(request);

      logger.debug("confirmFixVolume response is {}", response);
      return response.isConfirmFixVolumeSucess();
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
  public void markVolumesReadWrite(List<Long> volumeIds, long accountId, String readWrite)
      throws ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, VolumeNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    MarkVolumesReadWriteRequest request = new MarkVolumesReadWriteRequest();
    InformationCenter.Iface clientCc = null;
    Set<Long> volumeIdsSet = new HashSet<>();
    volumeIdsSet.addAll(volumeIds);

    try {
      clientCc = infoCenterClientFactory.build().getClient();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      request.setVolumeIds(volumeIdsSet);
      request.setReadWrite(ReadWriteTypeThrift.valueOf(readWrite));
      clientCc.markVolumesReadWrite(request);
      logger.debug("markVolumesReadWrite request is {}", request);
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
  public String obtainVolumeDetailShowFlag() {
    return volumeDetailShowFlag;
  }

  @Override
  public void deleteVolumeDelay(long accountId, long volumeId,
      String volumeName, int delayDate) throws EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    DeleteVolumeDelayRequest request = new DeleteVolumeDelayRequest();
    InformationCenter.Iface client = null;
    request.setRequestId(RequestIdBuilder.get());
    request.setVolumeId(volumeId);
    request.setAccountId(accountId);
    request.setVolumeName(volumeName);
    request.setDelaydate(delayDate);
    logger.debug("DeleteVolumeDelayRequest request is {}", request);
    try {
      client = infoCenterClientFactory.build().getClient();
      client.deleteVolumeDelay(request);
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
  public void stopDeleteVolumeDelay(long accountId, long volumeId)
      throws EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    StopDeleteVolumeDelayRequest request = new StopDeleteVolumeDelayRequest();
    InformationCenter.Iface client = null;
    request.setRequestId(RequestIdBuilder.get());
    request.setVolumeId(volumeId);
    request.setAccountId(accountId);

    logger.debug("StopDeleteVolumeDelayRequest request is {}", request);
    try {
      client = infoCenterClientFactory.build().getClient();
      client.stopDeleteVolumeDelay(request);
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
  public void startDeleteVolumeDelay(long accountId, long volumeId)
      throws EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    StartDeleteVolumeDelayRequest request = new StartDeleteVolumeDelayRequest();
    InformationCenter.Iface client = null;
    request.setRequestId(RequestIdBuilder.get());
    request.setVolumeId(volumeId);
    request.setAccountId(accountId);

    logger.debug("StartDeleteVolumeDelayRequest request is {}", request);
    try {
      client = infoCenterClientFactory.build().getClient();
      client.startDeleteVolumeDelay(request);
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
  public void cancelDeleteVolumeDelay(long accountId, long volumeId)
      throws EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    CancelDeleteVolumeDelayRequest request = new CancelDeleteVolumeDelayRequest();
    InformationCenter.Iface client = null;
    request.setRequestId(RequestIdBuilder.get());
    request.setVolumeId(volumeId);
    request.setAccountId(accountId);

    logger.debug("CancelDeleteVolumeDelayRequest request is {}", request);
    try {
      client = infoCenterClientFactory.build().getClient();
      client.cancelDeleteVolumeDelay(request);
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
  public void moveVolumeToRecycle(long accountId, long volumeId)
      throws VolumeInMoveOnlineDoNotHaveOperationExceptionThrift, VolumeIsMovingExceptionThrift,
      VolumeIsCopingExceptionThrift,
      AccessDeniedExceptionThrift, VolumeIsCloningExceptionThrift,
      VolumeInExtendingExceptionThrift, DriverLaunchingExceptionThrift,
      DriverUnmountingExceptionThrift, ServiceHavingBeenShutdownThrift,
      VolumeNotFoundExceptionThrift,
      VolumeBeingDeletedExceptionThrift, VolumeIsBeginMovedExceptionThrift,
      PermissionNotGrantExceptionThrift,
      VolumeDeletingExceptionThrift, VolumeWasRollbackingExceptionThrift,
      LaunchedVolumeCannotBeDeletedExceptionThrift,
      AccountNotFoundExceptionThrift, ServiceIsNotAvailableThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    MoveVolumeToRecycleRequest request = new MoveVolumeToRecycleRequest();
    InformationCenter.Iface client = null;
    request.setRequestId(RequestIdBuilder.get());
    request.setVolumeId(volumeId);
    request.setAccountId(accountId);
    try {
      client = infoCenterClientFactory.build().getClient();
      client.moveVolumeToRecycle(request);
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
  public void recycleVolumeToNormal(long accountId, long volumeId)
      throws ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift,
      VolumeBeingDeletedExceptionThrift,
      AccessDeniedExceptionThrift, VolumeNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    RecycleVolumeToNormalRequest request = new RecycleVolumeToNormalRequest();
    InformationCenter.Iface client = null;
    request.setRequestId(RequestIdBuilder.get());
    request.setVolumeId(volumeId);
    request.setAccountId(accountId);
    try {
      client = infoCenterClientFactory.build().getClient();
      client.recycleVolumeToNormal(request);
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
  public List<SimpleVolumeMetadata> listRecycleVolumeInfo(long accountId)
      throws AccessDeniedExceptionThrift, ResourceNotExistsExceptionThrift,
      InvalidInputExceptionThrift,
      ServiceHavingBeenShutdownThrift, VolumeNotFoundExceptionThrift, ServiceIsNotAvailableThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    InformationCenter.Iface clientIc = null;
    List<SimpleVolumeMetadata> volumeList = new ArrayList<SimpleVolumeMetadata>();
    try {
      List<SimpleDomain> domainList = domainService
          .listDomains(null, Constants.SUPER_ADMIN_ACCOUNT_ID);
      Set<SimpleStoragePool> storagePools = storagePoolService
          .listStoragePools(null, null, Constants.SUPER_ADMIN_ACCOUNT_ID);
      clientIc = infoCenterClientFactory.build().getClient();
      ListRecycleVolumeInfoRequest request = new ListRecycleVolumeInfoRequest();
      request.setAccountId(accountId);
      request.setRequestId(RequestIdBuilder.get());

      logger.debug("ListRecycleVolumeInfoRequest is {}", request);
      ListRecycleVolumeInfoResponse response = clientIc.listRecycleVolumeInfo(request);
      logger.debug("ListRecycleVolumeInfoResponse is {}", response);

      for (VolumeMetadataThrift volumeThrift : response.getVolumes()) {
        SimpleVolumeMetadata volume = buildVolumeWithoutSegment(volumeThrift);

        Map<Long, VolumeRecycleInformationThrift> volumeRecycleInformationMap =
            response.getVolumeRecycleInformationMap();

        // set recycle  info
        if (volumeRecycleInformationMap != null) {
          for (Entry<Long, VolumeRecycleInformationThrift> entry :
              volumeRecycleInformationMap.entrySet()) {
            if (volumeThrift.getVolumeId() == entry.getKey()) {
              volume.setTimeForRecycle(String.valueOf(entry.getValue().getTimeInRecycle()));
              break;
            }
          }
        }

        // domain name
        String domainName = getDomainNameById(volumeThrift.getDomainId(), domainList);
        volume.setVolumeDomain(domainName);
        // set pool
        SimpleStoragePool storagePool = getStoragePoolById(volumeThrift.getStoragePoolId(),
            storagePools);
        String storagePoolName = storagePool.getPoolName();
        volume.setStoragePoolName(storagePoolName);
        String poolId = storagePool.getPoolId();
        volume.setPoolId(poolId);
        // 隐藏迁移过程中正在删除的源卷
        if (!volume.getVolumeName()
            .contains(py.informationcenter.Utils.MOVE_VOLUME_APPEND_STRING_FOR_ORIGINAL_VOLUME)) {
          volumeList.add(volume);
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
    return volumeList;
  }

  @Override
  public void updateVolumeDescription(long accountId, long volumeId, String volumeDescription)
      throws AccessDeniedExceptionThrift, VolumeBeingDeletedExceptionThrift,
      ServiceHavingBeenShutdownThrift, VolumeNotFoundExceptionThrift, ServiceIsNotAvailableThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    UpdateVolumeDescriptionRequest request = new UpdateVolumeDescriptionRequest();
    InformationCenter.Iface client = null;
    request.setRequestId(RequestIdBuilder.get());
    request.setVolumeId(volumeId);
    request.setAccountId(accountId);
    request.setVolumeDescription(volumeDescription);
    try {
      client = infoCenterClientFactory.build().getClient();
      client.updateVolumeDescription(request);
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
