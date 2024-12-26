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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * SimpleDriverMetadata.
 */
@ApiModel
public class SimpleDriverMetadata {

  @ApiModelProperty(value = "驱动的名称")
  private String driverName;
  @ApiModelProperty(value = "已弃用", hidden = true)
  private String message;
  @ApiModelProperty(value = "所属卷id")
  private String volumeId;
  @ApiModelProperty(value = "快照id")
  private String snapshotId;
  @ApiModelProperty(value = "卷名称")
  private String volumeName;
  @ApiModelProperty(value = "驱动类型(ISCSI:iscsi,NBD:pyd)")
  private String driverType;
  @ApiModelProperty(value = "驱动地址")
  private String host;
  @ApiModelProperty(value = "驱动端口")
  private String port;
  @ApiModelProperty(value = "coordinator端口")
  private String coordinatorPort;
  @ApiModelProperty(value = "驱动用户信息")
  private List<SimpleDriverClientInfo> driverClientInfoList;
  @ApiModelProperty(value = "驱动用户数量")
  private String clientAmount;
  @ApiModelProperty(value = "驱动状态")
  private String status;
  @ApiModelProperty(value = "创建时间")
  private String createTime;
  @ApiModelProperty(value = "驱动容器id")
  private String driverContainerId;
  @ApiModelProperty(value = "驱动容器IP")
  private String driverContainerIp;
  @ApiModelProperty(value = "chap认证")
  private String chapControl;
  @ApiModelProperty(value = "ipv6地址")
  private String ipv6Addr;
  @ApiModelProperty(value = "标记卸载")
  private boolean markUnmountForCsi;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
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

  public String getVolumeName() {
    return volumeName;
  }

  public void setVolumeName(String volumeName) {
    this.volumeName = volumeName;
  }

  public String getDriverType() {
    return driverType;
  }

  public void setDriverType(String driverType) {
    this.driverType = driverType;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getClientAmount() {
    return clientAmount;
  }

  public void setClientAmount(String clientAmount) {
    this.clientAmount = clientAmount;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCoordinatorPort() {
    return coordinatorPort;
  }

  public void setCoordinatorPort(String coordinatorPort) {
    this.coordinatorPort = coordinatorPort;
  }

  public List<SimpleDriverClientInfo> getDriverClientInfoList() {
    return driverClientInfoList;
  }

  public void setDriverClientInfoList(List<SimpleDriverClientInfo> driverClientInfoList) {
    this.driverClientInfoList = driverClientInfoList;
  }

  public String getDriverContainerId() {
    return driverContainerId;
  }

  public void setDriverContainerId(String driverContainerId) {
    this.driverContainerId = driverContainerId;
  }

  public String getDriverContainerIp() {
    return driverContainerIp;
  }

  public void setDriverContainerIp(String driverContainerIp) {
    this.driverContainerIp = driverContainerIp;
  }

  public String getDriverName() {
    return driverName;
  }

  public void setDriverName(String driverName) {
    this.driverName = driverName;
  }

  public String getChapControl() {
    return chapControl;
  }

  public void setChapControl(String chapControl) {
    this.chapControl = chapControl;
  }

  public String getIpv6Addr() {
    return ipv6Addr;
  }

  public void setIpv6Addr(String ipv6Addr) {
    this.ipv6Addr = ipv6Addr;
  }

  public boolean isMarkUnmountForCsi() {
    return markUnmountForCsi;
  }

  public void setMarkUnmountForCsi(boolean markUnmountForCsi) {
    this.markUnmountForCsi = markUnmountForCsi;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  @Override
  public String toString() {
    return "SimpleDriverMetadata{"
        + "driverName='" + driverName + '\''
        + ", message='" + message + '\''
        + ", volumeId='" + volumeId + '\''
        + ", snapshotId='" + snapshotId + '\''
        + ", volumeName='" + volumeName + '\''
        + ", driverType='" + driverType + '\''
        + ", host='" + host + '\''
        + ", port='" + port + '\''
        + ", coordinatorPort='" + coordinatorPort + '\''
        + ", driverClientInfoList=" + driverClientInfoList
        + ", clientAmount='" + clientAmount + '\''
        + ", status='" + status + '\''
        + ", createTime='" + createTime + '\''
        + ", driverContainerId='" + driverContainerId + '\''
        + ", driverContainerIP='" + driverContainerIp + '\''
        + ", chapControl='" + chapControl + '\''
        + ", ipv6Addr='" + ipv6Addr + '\''
        + ", markUnmountForCsi=" + markUnmountForCsi
        + '}';
  }
}
