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

package py.console.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import py.console.utils.Constants;
import py.thrift.share.AccessPermissionTypeThrift;
import py.thrift.share.VolumeAccessRuleThrift;


/**
 * SimpleVolumeAccessRule.
 */
@ApiModel
public class SimpleVolumeAccessRule {

  @ApiModelProperty(value = "信息")
  private String message;
  @ApiModelProperty(value = "pyd驱动id")
  private String ruleId;
  @ApiModelProperty(value = "客户机IP")
  private String remoteHostName;
  @ApiModelProperty(value = "读写权限(Read-Only:只读，Read/Write:读写)")
  private String permission;
  @ApiModelProperty(value = "状态")
  private String status;

  @Override
  public String toString() {
    return "SimpleVolumeAccessRule [message=" + message + ", ruleId=" + ruleId + ", remoteHostName="
        + remoteHostName + ", permission=" + permission + ", status=" + status + "]";
  }

  public String getRuleId() {
    return ruleId;
  }

  public void setRuleId(String ruleId) {
    this.ruleId = ruleId;
  }

  public String getRemoteHostName() {
    return remoteHostName;
  }

  public void setRemoteHostName(String remoteHostName) {
    this.remoteHostName = remoteHostName;
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * build simple volume access rule from.
   *
   * @param volumeAccessRuleThrift volume access rule thrift
   * @return simple volume access rule
   */
  public static SimpleVolumeAccessRule buildSimpleVolumeAccessRuleFrom(
      VolumeAccessRuleThrift volumeAccessRuleThrift) {
    SimpleVolumeAccessRule simpleVolumeAccessRule = new SimpleVolumeAccessRule();
    simpleVolumeAccessRule.setRuleId("" + volumeAccessRuleThrift.getRuleId());
    simpleVolumeAccessRule.setRemoteHostName(volumeAccessRuleThrift.getIncomingHostName());
    simpleVolumeAccessRule.setPermission(
        stringOfPermission(volumeAccessRuleThrift.getPermission()));
    simpleVolumeAccessRule.setStatus(volumeAccessRuleThrift.getStatus().name());
    return simpleVolumeAccessRule;
  }

  /**
   * to volume access rule thrift.
   *
   * @return volume access rule thrift
   */
  public VolumeAccessRuleThrift toVolumeAccessRuleThrift() {
    VolumeAccessRuleThrift volumeAccessRuleThrift = new VolumeAccessRuleThrift();
    volumeAccessRuleThrift.setRuleId(Long.parseLong(ruleId));
    volumeAccessRuleThrift.setIncomingHostName(remoteHostName);
    volumeAccessRuleThrift.setPermission(valueOfPermission(permission));
    return volumeAccessRuleThrift;
  }

  /**
   * string of permission.
   *
   * @param accessPermissionTypeThrift access permission type thrift
   * @return permission string
   */
  public static String stringOfPermission(AccessPermissionTypeThrift accessPermissionTypeThrift) {
    switch (accessPermissionTypeThrift) {
      case READ:
        return Constants.READ_ONLY;
      case READWRITE:
        return Constants.READ_WRITE;
      default:
        return "";
    }
  }

  /**
   * value of permission.
   *
   * @param permission permission
   * @return permission value
   */
  public static AccessPermissionTypeThrift valueOfPermission(String permission) {
    if (permission.equals(Constants.READ_ONLY)) {
      return AccessPermissionTypeThrift.READ;
    } else if (permission.equals(Constants.READ_WRITE)) {
      return AccessPermissionTypeThrift.READWRITE;
    }

    return null;
  }
}
