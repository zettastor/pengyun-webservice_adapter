/**
* Copyright (C) 2013-2024 Nanjing Pengyun Network Technology Co., Ltd.
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
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
