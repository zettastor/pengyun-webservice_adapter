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

package py.console.utils;

/**
 * error code when exception or error happens, user can get error information from error_code that
 * return by requested action.
 *
 */
public class ErrorCode2 {

  public static String ERROR_0000_SUCCESS = "success";

  public static String ERROR_0001_AuthenticationFailed = "ERROR_0001_AuthenticationFailed";

  public static String ERROR_0002_AccountExists = "ERROR_0002_AccountExists";

  public static String ERROR_0003_AccountNotExists = "ERROR_0003_AccountNotExists";

  public static String ERROR_0004_NoEnoughSpace = "ERROR_0004_NoEnoughSpace";

  public static String ERROR_0005_VolumeBeenDeleted = "ERROR_0005_VolumeBeenDeleted";

  public static String ERROR_0006_VolumeBeingLaunching = "ERROR_0006_VolumeBeingLaunching";

  public static String ERROR_0007_NoVolumeNow = "ERROR_0007_NoVolumeNow";

  public static String ERROR_0008_NoInstanceNow = "ERROR_0008_NoInstanceNow";

  public static String ERROR_0009_OldPasswodNotCorrect = "ERROR_0009_OldPasswodNotCorrect";

  public static String ERROR_0010_InternalError = "ERROR_0010_InternalError";

  public static String ERROR_0011_VolumeNotFound = "ERROR_0011_VolumeNotFound";

  public static String ERROR_0012_VolumeNotAvailable = "ERROR_0012_VolumeNotAvailable";

  public static String ERROR_0013_DriverHasUmounted = "ERROR_0013_DriverHasUmounted";

  public static String ERROR_0014_OperationFailed = "ERROR_0014_OperationFailed";

  public static String ERROR_0015_NoDriverAvailable = "ERROR_0015_NoDriverAvailable";

  public static String ERROR_0016_NoPermission = "ERROR_0016_NoPermission";

  public static String ERROR_0017_InstanceNotExist = "ERROR_0017_InstanceNotExist";

  public static String ERROR_0018_InstanceFailedAleady = "ERROR_0018_InstanceFailedAleady";

  public static String ERROR_0019_SessionOut = "ERROR_0019_SessionOut";

  public static String ERROR_0020_FSCreatingFailed = "ERROR_0020_FSCreatingFailed";

  public static String ERROR_0021_DeleteAccountItselfError = "ERROR_0021_DeleteAccountItselfError";

  public static String ERROR_0022_AccountNotEmptyError = "ERROR_0022_AccountNotEmptyError";

  public static String ERROR_0023_VolumeSizeNotMultipleOfSegmentSize =
      "ERROR_0023_VolumeSizeNotMultipleOfSegmentSize";

  public static String ERROR_0024_NotEnoughAvailableDriverContainer =
      "ERROR_0024_NotEnoughAvailableDriverContainer";

  public static String ERROR_0026_OnlyPartOfDriversLaunched =
      "ERROR_0026_OnlyPartOfDriversLaunched";

  public static String ERROR_0027_DriverLaunchFailed = "ERROR_0027_DriverLaunchFailed";

  public static String ERROR_0028_SameVolumeNameExist = "ERROR_0028_SameVolumeNameExist";

  public static String ERROR_0029_VolumHasBeenDeleted = "ERROR_0029_VolumHasBeenDeleted";

  public static String ERROR_0030_RootVolumeHasBeenDeleted = "ERROR_0030_RootVolumeHasBeenDeleted";

  public static String ERROR_0031_RootVolumeNotFound = "ERROR_0031_RootVolumeNotFound";

  public static String ERROR_0032_VolumeIsExtending = "ERROR_0032_VolumeIsExtending";

  public static String ERROR_0033_InvalidLicense = "ERROR_0033_InvalidLicense";

  public static String ERROR_0034_NoLicenseToBeUpdate = "ERROR_0033_NoLicenseToBeUpdate";

  public static String ERROR_0035_FailedToUmountDriver = "ERROR_0035_FailedToUmountDriver";

  public static String ERROR_0036_ExistsClient = "ERROR_0036_ExistsClient";

  public static String ERROR_0037_TooManyDrivers = "ERROR_0037_TooManyDrivers";

  public static String ERROR_0038_NotRootVolume = "ERROR_0038_NotRootVolume";

  public static String ERROR_0039_ServiceHavingBeenShutdown =
      "ERROR_0039_ServiceHavingBeenShutdown";

  public static String ERROR_0040_InvalidInput = "ERROR_0040_InvalidInput";

  public static String ERROR_0041_DriverIsLaunching = "ERROR_0041_DriverIsLaunching";

  public static String ERROR_0042_DriverTypeConflict = "ERROR_0042_DriverTypeConflict";

  public static String ERROR_0043_DomainExisted = "ERROR_0043_DomainExisted";

  public static String ERROR_0044_DomainNameExisted = "ERROR_0044_DomainNameExisted";

  public static String ERROR_0045_DomainNameNull = "ERROR_0045_DomainNameNull";

  public static String ERROR_0046_DomainNotExisted = "ERROR_0046_DomainNotExisted";

  public static String ERROR_0035_DiskNotFound = "ERROR_0035_DiskNotFound";

  public static String ERROR_0036_DiskHasBeenLaunched = "ERROR_0036_DiskHasBeenLaunched";

  public static String ERROR_0037_DiskHasBeenUnlaunched = "ERROR_0037_DiskHasBeenUnlaunched";

  public static String ERROR_0038_DiskNotBroken = "ERROR_0038_DiskNotBroken";

  public static String ERROR_0039_DiskNotConfigMismatch = "ERROR_0039_DiskNotConfigMismatch";

  public static String ERROR_0040_DiskBusy = "ERROR_0040_DiskBusy";

  public static String ERROR_0047_TooManyReadWritePermissions =
      "ERROR_0047_TooManyReadWritePermissions";

  public static String ERROR_0048_GenerateLicenseSequenceFailed =
      "ERROR_0048_GenerateLicenseSequenceFailed";

  public static String ERROR_0049_TooManyEndPointFound = "ERROR_0049_TooManyEndPointFound";

  public static String ERROR_0055_VolumeCannotBeRecycled = "ERROR_0055_VolumeCannotBeRecycled";

  public static String ERROR_0056_LaunchedVolumeCannotBeDeleted =
      "ERROR_0056_LaunchedVolumeCannotBeDeleted";

  public static String ERROR_0057_LaunchedVolumeCannotBeDeletedAndDriverUnknown =
      "ERROR_0057_LaunchedVolumeCannotBeDeletedAndDriverUnknown";

  public static String ERROR_0058_AccessRuleNotApplied = "ERROR_0058_AccessRuleNotApplied";

  // begin 60 by zhangjj
  public static String ERROR_0060_SnapshotCountReachMax = "ERROR_0060_SnapshotCountReachMax";
  public static String ERROR_0061_SnapshotNameExist = "ERROR_0061_SnapshotNameExist";
  public static String ERROR_0062_VolumeUnderOperation = "ERROR_0062_VolumeUnderOperation";
  public static String ERROR_0063_SnapshotCreateFailed = "ERROR_0063_SnapshotCreateFailed";
  public static String ERROR_0065_SnapshotDeleteFailed = "ERROR_0065_SnapshotDeleteFailed";
  public static String ERROR_0066_NoDriverLaunch = "ERROR_0066_NoDriverLaunch";
  public static String ERROR_0067_VolumeNotAvailable = "ERROR_0067_VolumeNotAvailable";
  public static String ERROR_0068_SnapshotRollbackFailed = "ERROR_0068_SnapshotRollbackFailed";
  public static String ERROR_0069_SnapshotInputError = "ERROR_0069_SnapshotInputError";

  public static String ERROR_0070_SnapshotNotFound = "ERROR_0070_SnapshotNotFound";
  public static String ERROR_0071_SnapshotBeMounted = "ERROR_0071_SnapshotBeMounted";
  public static String ERROR_0072_VolumeWithDriverNotRollback =
      "ERROR_0072_VolumeWithDriverNotRollback";
  public static String ERROR_0080_VolumeWasRollbacking = "ERROR_0080_VolumeWasRollbacking";
  // end      by zhangjj

  public static String ERROR_IOLimit_InvalidLimitInput = "ERROR_IOLimit_InvalidLimitInput";
  public static String ERROR_IOLimit_VolumeNotFound = "ERROR_IOLimit_VolumeNotFound";
  public static String ERROR_IOLimit_LimitAlreadyExist = "ERROR_IOLimit_LimitAlreadyExist";
  public static String ERROR_IOLimit_DriverNotReady = "ERROR_IOLimit_DriverNotReady";
  public static String ERROR_PerformanceTaskExistedException =
      "ERROR_PerformanceTaskExistedException";
  public static String ERROR_PerformanceCreationException = "ERROR_PerformanceCreationException";
  public static String ERROR_EndPointNotFoundException = "ERROR_EndPointNotFoundException";
  public static String ERROR_TooManyEndPointFoundException = "ERROR_TooManyEndPointFoundException";
  public static String ERROR_GenericThriftClientFactoryException =
      "ERROR_GenericThriftClientFactoryException";

  public static String ERROR_PermissionNotGrantException = "ERROR_PermissionNotGrantException";
  public static String ERROR_CRUDBuiltInRoleException = "ERROR_CRUDBuiltInRoleException";

  public static String ERROR_RebalanceRuleExistingException =
      "ERROR_RebalanceRuleExistingException";
}
