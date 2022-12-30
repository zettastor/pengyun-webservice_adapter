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

package py.console.service.volume;

import java.util.List;
import java.util.Set;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import py.console.bean.FixVolumeResponse;
import py.console.bean.SimpleDomain;
import py.console.bean.SimpleDriverMetadata;
import py.console.bean.SimpleIoLimitation;
import py.console.bean.SimpleSegmentMetadata;
import py.console.bean.SimpleStoragePool;
import py.console.bean.SimpleVolumeMetadata;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.io.qos.IoLimitation;
import py.storage.StorageConfiguration;
import py.thrift.icshare.GetVolumeResponse;
import py.thrift.share.AccessDeniedExceptionThrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.AlreadyExistStaticLimitationExceptionThrift;
import py.thrift.share.BadLicenseTokenExceptionThrift;
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
import py.thrift.share.DriverNameExistsExceptionThrift;
import py.thrift.share.DriverNotFoundExceptionThrift;
import py.thrift.share.DriverTypeConflictExceptionThrift;
import py.thrift.share.DriverTypeIsConflictExceptionThrift;
import py.thrift.share.DriverUnmountingExceptionThrift;
import py.thrift.share.DynamicIoLimitationTimeInterleavingExceptionThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.ExistsClientExceptionThrift;
import py.thrift.share.ExistsDriverExceptionThrift;
import py.thrift.share.FailedToUmountDriverExceptionThrift;
import py.thrift.share.FrequentFixVolumeRequestThrift;
import py.thrift.share.GetScsiDeviceOperationExceptionThrift;
import py.thrift.share.InternalErrorThrift;
import py.thrift.share.InvalidInputExceptionThrift;
import py.thrift.share.LackDatanodeExceptionThrift;
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
import py.thrift.share.ResourceNotExistsExceptionThrift;
import py.thrift.share.RootVolumeBeingDeletedExceptionThrift;
import py.thrift.share.RootVolumeNotFoundExceptionThrift;
import py.thrift.share.ScsiDeviceIsLaunchExceptionThrift;
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
import py.thrift.share.UnknownIpv4HostExceptionThrift;
import py.thrift.share.UnknownIpv6HostExceptionThrift;
import py.thrift.share.UselessLicenseExceptionThrift;
import py.thrift.share.VolumeBeingDeletedExceptionThrift;
import py.thrift.share.VolumeCanNotLaunchMultiDriversThisTimeExceptionThrift;
import py.thrift.share.VolumeCannotBeRecycledExceptionThrift;
import py.thrift.share.VolumeDeletingExceptionThrift;
import py.thrift.share.VolumeExistingExceptionThrift;
import py.thrift.share.VolumeFixingOperationExceptionThrift;
import py.thrift.share.VolumeInExtendingExceptionThrift;
import py.thrift.share.VolumeInMoveOnlineDoNotHaveOperationExceptionThrift;
import py.thrift.share.VolumeIsBeginMovedExceptionThrift;
import py.thrift.share.VolumeIsCloningExceptionThrift;
import py.thrift.share.VolumeIsCopingExceptionThrift;
import py.thrift.share.VolumeIsMovingExceptionThrift;
import py.thrift.share.VolumeLaunchMultiDriversExceptionThrift;
import py.thrift.share.VolumeNameExistedExceptionThrift;
import py.thrift.share.VolumeNotAvailableExceptionThrift;
import py.thrift.share.VolumeNotFoundExceptionThrift;
import py.thrift.share.VolumeSizeIllegalExceptionThrift;
import py.thrift.share.VolumeSizeNotMultipleOfSegmentSizeThrift;
import py.thrift.share.VolumeUnderOperationExceptionThrift;
import py.thrift.share.VolumeWasRollbackingExceptionThrift;
import py.volume.VolumeMetadata;

/**
 * VolumeService.
 */
public interface VolumeService {

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
      VolumeSizeIllegalExceptionThrift, TException;

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
      VolumeIsMovingExceptionThrift, TException;

  public SimpleVolumeMetadata getVolumeById(long volumeId, long accountId, boolean withSegmentList)
      throws VolumeNotFoundExceptionThrift;

  public SimpleVolumeMetadata viewVolume(long volumeId, long accountId)
      throws VolumeNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, Exception;

  public List<SimpleVolumeMetadata> getVolumesByName(String volumeName, long accountId)
      throws VolumeNotFoundExceptionThrift;

  public List<SimpleVolumeMetadata> getVolumeByAccount(String account);

  public SimpleVolumeMetadata getVolumeNotDeadByName(String volumeName, long accountId)
      throws VolumeNotFoundExceptionThrift;

  public GetVolumeResponse getVolume(long volumeId, long accountId)
      throws InvalidInputExceptionThrift, VolumeNotFoundExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      ServiceIsNotAvailableThrift, TException;

  public List<SimpleSegmentMetadata> getSegmentList(long volumeId, long accountId,
      int startSegmentIndex,
      int endSegmentIndex) throws VolumeNotFoundExceptionThrift;

  public StorageConfiguration getStorageConfiguration();

  public List<SimpleVolumeMetadata> getMultipleVolumes(long accountId, Set<Long> ids)
      throws AccessDeniedExceptionThrift, ResourceNotExistsExceptionThrift,
      InvalidInputExceptionThrift,
      ServiceHavingBeenShutdownThrift, VolumeNotFoundExceptionThrift, ServiceIsNotAvailableThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

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
      VolumeCanNotLaunchMultiDriversThisTimeExceptionThrift;

  public boolean umountVolume(long volumeId, long accountId,
      List<DriverIpTargetThrift> driverIpTargetList,
      String scsiIp, String nodeId, long forDelayUmountTime, boolean forceUmount)
      throws VolumeNotFoundExceptionThrift, ServiceHavingBeenShutdownThrift,
      FailedToUmountDriverExceptionThrift, ExistsClientExceptionThrift,
      DriverIsLaunchingExceptionThrift,
      AccessDeniedExceptionThrift, ServiceIsNotAvailableThrift,
      SnapshotRollingBackExceptionThrift,
      DriverLaunchingExceptionThrift,
      DriverUnmountingExceptionThrift, VolumeDeletingExceptionThrift,
      VolumeUnderOperationExceptionThrift,
      InvalidInputExceptionThrift, DriverIsUpgradingExceptionThrift, TransportExceptionThrift,
      DriverContainerIsIncExceptionThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      NoDriverLaunchExceptionThrift, TException;

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
      VolumeSizeIllegalExceptionThrift, TException;

  public boolean modifyIoLimit(long volumeId, IoLimitation ioLimitation, long accountId)
      throws VolumeNotFoundExceptionThrift;

  public boolean recycleVolume(long volumeId, long accountId)
      throws AccessDeniedExceptionThrift, NotEnoughSpaceExceptionThrift,
      VolumeNotFoundExceptionThrift,
      VolumeCannotBeRecycledExceptionThrift, ServiceHavingBeenShutdownThrift,
      VolumeInExtendingExceptionThrift,
      ExistsDriverExceptionThrift, ServiceIsNotAvailableThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, VolumeWasRollbackingExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public List<SimpleVolumeMetadata> getOrphanVolume(long accountId)
      throws TTransportException, EndPointNotFoundException, TooManyEndPointFoundException,
      AccessDeniedExceptionThrift, InternalErrorThrift, InvalidInputExceptionThrift,
      GenericThriftClientFactoryException, TException;

  public SimpleStoragePool getStoragePoolById(long poolId, Set<SimpleStoragePool> storagePools);

  public String getDomainNameById(long domainId, List<SimpleDomain> domainList);


  public List<SimpleIoLimitation> listIoLimits(long accountId, SimpleDriverMetadata driver)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      DriverNotFoundExceptionThrift, ServiceIsNotAvailableThrift, TException;

  public void addOrModifyIoLimit(long accountId, IoLimitation ioLimitation,
      SimpleDriverMetadata driver)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      DriverNotFoundExceptionThrift, ServiceIsNotAvailableThrift, AccountNotFoundExceptionThrift,
      AlreadyExistStaticLimitationExceptionThrift, InvalidInputExceptionThrift,
      DynamicIoLimitationTimeInterleavingExceptionThrift,
      PermissionNotGrantExceptionThrift, AccessDeniedExceptionThrift, TException;

  public boolean deleteIoLimit(long accountId, long limitId, SimpleDriverMetadata driver)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException, PermissionNotGrantExceptionThrift,
      InvalidInputExceptionThrift, ServiceIsNotAvailableThrift, AccountNotFoundExceptionThrift,
      DriverNotFoundExceptionThrift, AccessDeniedExceptionThrift, TException;

  public FixVolumeResponse fixvolume(long volumeId, long accountId)
      throws AccountNotFoundExceptionThrift, VolumeNotFoundExceptionThrift,
      AccessDeniedExceptionThrift,
      ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      VolumeInMoveOnlineDoNotHaveOperationExceptionThrift, TException;

  public boolean confirmFixVolume(long accountId, long volumeId, List<Long> lostDatanodes)
      throws InternalErrorThrift, VolumeNotFoundExceptionThrift, AccessDeniedExceptionThrift,
      LackDatanodeExceptionThrift, NotEnoughSpaceExceptionThrift, InvalidInputExceptionThrift,
      ServiceIsNotAvailableThrift, VolumeFixingOperationExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      FrequentFixVolumeRequestThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public void markVolumesReadWrite(List<Long> volumeIds, long accountId, String readWrite)
      throws ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, VolumeNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public String obtainVolumeDetailShowFlag();

  public void deleteVolumeDelay(long accountId, long volumeId, String volumeName, int delayDate)
      throws EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public void stopDeleteVolumeDelay(long accountId, long volumeId)
      throws EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public void startDeleteVolumeDelay(long accountId, long volumeId)
      throws EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public void cancelDeleteVolumeDelay(long accountId, long volumeId)
      throws EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public void moveVolumeToRecycle(long accountId, long volumeId)
      throws VolumeInMoveOnlineDoNotHaveOperationExceptionThrift, VolumeIsMovingExceptionThrift,
      VolumeIsCopingExceptionThrift,
      AccessDeniedExceptionThrift, VolumeIsCloningExceptionThrift,
      VolumeInExtendingExceptionThrift, DriverLaunchingExceptionThrift,
      DriverUnmountingExceptionThrift, ServiceHavingBeenShutdownThrift,
      VolumeNotFoundExceptionThrift, VolumeBeingDeletedExceptionThrift,
      VolumeIsBeginMovedExceptionThrift, PermissionNotGrantExceptionThrift,
      VolumeDeletingExceptionThrift,
      VolumeWasRollbackingExceptionThrift, LaunchedVolumeCannotBeDeletedExceptionThrift,
      AccountNotFoundExceptionThrift,
      ServiceIsNotAvailableThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public void recycleVolumeToNormal(long accountId, long volumeId)
      throws ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift,
      VolumeBeingDeletedExceptionThrift,
      AccessDeniedExceptionThrift, VolumeNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;


  public List<SimpleVolumeMetadata> listRecycleVolumeInfo(long accountId)
      throws AccessDeniedExceptionThrift, ResourceNotExistsExceptionThrift,
      InvalidInputExceptionThrift,
      ServiceHavingBeenShutdownThrift, VolumeNotFoundExceptionThrift, ServiceIsNotAvailableThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;


  public void updateVolumeDescription(long accountId, long volumeId, String volumeDescription)
      throws AccessDeniedExceptionThrift, VolumeBeingDeletedExceptionThrift,
      ServiceHavingBeenShutdownThrift, VolumeNotFoundExceptionThrift, ServiceIsNotAvailableThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

}
