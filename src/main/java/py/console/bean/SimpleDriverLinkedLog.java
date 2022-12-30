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

import io.swagger.annotations.ApiModelProperty;

/**
 * SimpleDriverLinkedLog.
 */
public class SimpleDriverLinkedLog {

  @ApiModelProperty(value = "driver container id")
  private String driverContainerId;
  @ApiModelProperty(value = "卷id")
  private String volumeId;
  @ApiModelProperty(value = "快照id")
  private String snapshotId;
  @ApiModelProperty(value = "驱动类型")
  private String driverType;
  @ApiModelProperty(value = "客户信息")
  private String clientInfo;
  @ApiModelProperty(value = "时间")
  private String time;
  @ApiModelProperty(value = "驱动名称")
  private String driverName;
  @ApiModelProperty(value = "IP")
  private String hostName;
  @ApiModelProperty(value = "状态")
  private String status;
  @ApiModelProperty(value = "卷名称")
  private String volumeName;
  @ApiModelProperty(value = "卷描述")
  private String volumeDesc;

  public String getDriverContainerId() {
    return driverContainerId;
  }

  public void setDriverContainerId(String driverContainerId) {
    this.driverContainerId = driverContainerId;
  }

  public String getVolumeId() {
    return volumeId;
  }

  public void setVolumeId(String volumeId) {
    this.volumeId = volumeId;
  }

  public String getSnapshotId() {
    return snapshotId;
  }

  public void setSnapshotId(String snapshotId) {
    this.snapshotId = snapshotId;
  }

  public String getDriverType() {
    return driverType;
  }

  public void setDriverType(String driverType) {
    this.driverType = driverType;
  }

  public String getClientInfo() {
    return clientInfo;
  }

  public void setClientInfo(String clientInfo) {
    this.clientInfo = clientInfo;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getDriverName() {
    return driverName;
  }

  public void setDriverName(String driverName) {
    this.driverName = driverName;
  }

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getVolumeName() {
    return volumeName;
  }

  public void setVolumeName(String volumeName) {
    this.volumeName = volumeName;
  }

  public String getVolumeDesc() {
    return volumeDesc;
  }

  public void setVolumeDesc(String volumeDesc) {
    this.volumeDesc = volumeDesc;
  }

  @Override
  public String toString() {
    return "SimpleDriverLinkedLog{"
        + "driverContainerId='" + driverContainerId + '\''
        + ", volumeId='" + volumeId + '\''
        + ", snapshotId='" + snapshotId + '\''
        + ", driverType='" + driverType + '\''
        + ", clientInfo='" + clientInfo + '\''
        + ", time='" + time + '\''
        + ", driverName='" + driverName + '\''
        + ", hostName='" + hostName + '\''
        + ", status='" + status + '\''
        + ", volumeName='" + volumeName + '\''
        + ", volumeDesc='" + volumeDesc + '\''
        + '}';
  }
}
