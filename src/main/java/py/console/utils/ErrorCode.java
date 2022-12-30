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
public class ErrorCode {

  public static String ERROR_0000_SUCCESS_EN = "success";

  public static String ERROR_0001_AuthenticationFailed_EN = "Incorrect username or password !";

  public static String ERROR_0002_AccountExists_EN = "Account already exists";

  public static String ERROR_0003_AccountNotExists_EN = "Account not exists";

  public static String ERROR_0004_NoEnoughSpace_EN = "Not enough space";

  public static String ERROR_0005_VolumeBeenDeleted_EN = "The volume has been deleted";

  public static String ERROR_0006_VolumeBeingLaunching_EN = "The volume is being launching by "
      + "other operators";

  public static String ERROR_0007_NoVolumeNow_EN = "No volume now";

  public static String ERROR_0008_NoInstanceNow_EN = "No instance now";

  public static String ERROR_0009_OldPasswordNotCorrect_EN = "Old password not correctly";

  public static String ERROR_0010_InternalError_EN = "Internal error, operation failed";

  public static String ERROR_0011_VolumeNotFound_EN = "The volume not exists";

  public static String ERROR_0012_VolumeNotAvailable_EN = "The volume not available";

  public static String ERROR_0013_DriverHasMounted_EN = "The volume has been umounted";

  public static String ERROR_0014_OperationFailed_EN = "Operation Failed";

  public static String ERROR_0015_NoDriverAvailable_EN = "No Driver Available";

  public static String ERROR_0016_NoPermission_EN = "You have no privilege to do this operation";

  public static String ERROR_0017_InstanceNotExist_EN = "Instance not exists";

  public static String ERROR_0018_InstanceFailedAleady_EN = "Instance is already failed";

  public static String ERROR_0023_VolumeSizeNotMultipleOfSegmentSize_EN = "volume size is not "
      + "integral multiple of segment size";

  public static String ERROR_0033_InvalidLicense_EN = "invalid license file";

  public static String ERROR_0000_SUCCESS_ZH = "操作成功";

  public static String ERROR_0001_AuthenticationFailed_ZH = "账户名或密码错误 !";

  public static String ERROR_0002_AccountExists_ZH = "账户已经存在";

  public static String ERROR_0003_AccountNotExists_ZH = "账户不存在";

  public static String ERROR_0004_NoEnoughSpace_ZH = "系统空间不足";

  public static String ERROR_0005_VolumeBeenDeleted_ZH = "磁盘已经被删除";

  public static String ERROR_0006_VolumeBeingLaunching_ZH = "磁盘正在被其他人挂载";

  public static String ERROR_0007_NoVolumeNow_ZH = "现在没有磁盘";

  public static String ERROR_0008_NoInstanceNow_ZH = "现在没有实例";

  public static String ERROR_0009_OldPasswordNotCorrect_ZH = "旧密码不正确";

  public static String ERROR_0010_InternalError_ZH = "内部错误，操作失败";

  public static String ERROR_0011_VolumeNotFound_ZH = "磁盘不存在";

  public static String ERROR_0012_VolumeNotAvailable_ZH = "磁盘不可用";

  public static String ERROR_0013_DriverHasMounted_ZH = "磁盘已经被挂载了";

  public static String ERROR_0014_OperationFailed_ZH = "操作失败";

  public static String ERROR_0015_NoDriverAvailable_ZH = "没有合适的驱动";

  public static String ERROR_0016_NoPermission_ZH = "您没有权限进行此次操作";

  public static String ERROR_0017_InstanceNotExist_ZH = "实例不存在";

  public static String ERROR_0018_InstanceFailedAleady_ZH = "实例已经被移除";

  public static String ERROR_0023_VolumeSizeNotMultipleOfSegmentSize_ZH = "卷的大小不是segment的整数倍";

  public static String ERROR_0033_InvalidLicense_ZH = "license文件错误";

  public static String getError0000Success(String lang) {
    return ERROR_0000_SUCCESS_EN;
  }

  /**
   * get Error 0001 Authentication Failed.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0001AuthenticationFailed(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0001_AuthenticationFailed_ZH;
    } else {
      return ERROR_0001_AuthenticationFailed_EN;
    }
  }

  /**
   * get Error 0002 Account Exists.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0002AccountExists(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0002_AccountExists_ZH;
    } else {
      return ERROR_0002_AccountExists_EN;
    }
  }

  /**
   * get Error 0003 Account Not Exists.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0003AccountNotExists(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0003_AccountNotExists_ZH;
    } else {
      return ERROR_0003_AccountNotExists_EN;
    }
  }

  /**
   * get Error 0004 No Enough Space.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0004NoEnoughSpace(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0004_NoEnoughSpace_ZH;
    } else {
      return ERROR_0004_NoEnoughSpace_EN;
    }
  }

  /**
   * get Error 0005 Volume Been Deleted.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0005VolumeBeenDeleted(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0005_VolumeBeenDeleted_ZH;
    } else {
      return ERROR_0005_VolumeBeenDeleted_EN;
    }
  }

  /**
   * get Error 0006 Volume Being Launching.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0006VolumeBeingLaunching(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0006_VolumeBeingLaunching_ZH;
    } else {
      return ERROR_0006_VolumeBeingLaunching_EN;
    }
  }

  /**
   * get Error 0007 No Volume Now.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0007NoVolumeNow(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0007_NoVolumeNow_ZH;
    } else {
      return ERROR_0007_NoVolumeNow_EN;
    }
  }

  /**
   * get Error 0008 No Instance Now.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0008NoInstanceNow(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0008_NoInstanceNow_ZH;
    } else {
      return ERROR_0008_NoInstanceNow_EN;
    }
  }

  /**
   * get Error 0009 Old Password Not Correct.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0009OldPasswordNotCorrect(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0009_OldPasswordNotCorrect_ZH;
    } else {
      return ERROR_0009_OldPasswordNotCorrect_EN;
    }
  }

  /**
   * get Error 0010 Internal Error.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0010InternalError(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0010_InternalError_ZH;
    } else {
      return ERROR_0010_InternalError_EN;
    }
  }

  /**
   * get Error 0011 Volume Not Found.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0011VolumeNotFound(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0011_VolumeNotFound_ZH;
    } else {
      return ERROR_0011_VolumeNotFound_EN;
    }
  }

  /**
   * get Error 0012 Volume Not Available.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0012VolumeNotAvailable(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0012_VolumeNotAvailable_ZH;
    } else {
      return ERROR_0012_VolumeNotAvailable_EN;
    }
  }

  /**
   * get Error 0013 Driver Has Mounted.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0013DriverHasMounted(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0013_DriverHasMounted_ZH;
    } else {
      return ERROR_0013_DriverHasMounted_EN;
    }
  }

  /**
   * get Error 0014 Operation Failed.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0014OperationFailed(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0014_OperationFailed_ZH;
    } else {
      return ERROR_0014_OperationFailed_EN;
    }
  }

  /**
   * get Error 0015 No Driver Available.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0015NoDriverAvailable(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0015_NoDriverAvailable_ZH;
    } else {
      return ERROR_0015_NoDriverAvailable_EN;
    }
  }

  /**
   * get Error 0016 No Permission.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0016NoPermission(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0016_NoPermission_ZH;
    } else {
      return ERROR_0016_NoPermission_EN;
    }
  }

  /**
   * get Error 0017 Instance Not Exist.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0017InstanceNotExist(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0017_InstanceNotExist_ZH;
    } else {
      return ERROR_0017_InstanceNotExist_EN;
    }
  }

  /**
   * get Error 0018 Instance Failed Aleady.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0018InstanceFailedAleady(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0018_InstanceFailedAleady_ZH;
    } else {
      return ERROR_0018_InstanceFailedAleady_EN;
    }
  }

  /**
   * get Error 0023 Volume Size Not Multiple Of Segment Size.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0023VolumeSizeNotMultipleOfSegmentSize(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0023_VolumeSizeNotMultipleOfSegmentSize_ZH;
    } else {
      return ERROR_0023_VolumeSizeNotMultipleOfSegmentSize_EN;
    }
  }

  /**
   * get Error 0024 Invalid License.
   *
   * @param lang language code
   * @return description
   */
  public static String getError0024InvalidLicense(String lang) {
    if (lang == null || lang.equals("zh")) {
      return ERROR_0033_InvalidLicense_ZH;
    } else {
      return ERROR_0033_InvalidLicense_EN;
    }
  }
}
