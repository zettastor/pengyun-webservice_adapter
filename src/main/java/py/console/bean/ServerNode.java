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
import java.util.List;

@ApiModel
public class ServerNode {

  @ApiModelProperty(value = "服务器 id")
  private String serverId;
  @ApiModelProperty(value = "系统信息")
  private String modelInfo;
  @ApiModelProperty(value = "cpu信息")
  private String cpuInfo;
  @ApiModelProperty(value = "内存大小，单位GB")
  private String memoryInfo;
  @ApiModelProperty(value = "磁盘大小，单位GB")
  private String diskInfo;
  @ApiModelProperty(value = "网卡ip")
  private String networkCardInfo;
  @ApiModelProperty(value = "管理ip")
  private String manageIp;
  @ApiModelProperty(value = "网关ip")
  private String gatewayIp;
  @ApiModelProperty(value = "存储ip")
  private String storeIp;
  @ApiModelProperty(value = "机柜名称")
  private String rackNo;
  @ApiModelProperty(value = "槽位号")
  private String slotNo;
  @ApiModelProperty(value = "状态")
  private String status;
  @ApiModelProperty(value = "子框号")
  private String childFramNo;
  @ApiModelProperty(value = "服务器名称")
  private String hostName;
  @ApiModelProperty(value = "所属域")
  private SimpleDomain domain;
  @ApiModelProperty(value = "磁盘信息")
  private List<DiskInfo> diskNotInDataNode;
  @ApiModelProperty(value = "缓存盘信息")
  private List<SimpleArchiveMetadata> diskCache;


  @ApiModelProperty(value = "数据盘信息", hidden = true)
  private List<SimpleArchiveMetadata> diskRaw;
  @ApiModelProperty(value = "无功能盘信息", hidden = true)
  private List<SimpleArchiveMetadata> diskNonfunction;
  @ApiModelProperty(value = "节点信息")
  private List<SimpleInstance> instances;
  @ApiModelProperty(value = "传感器信息")
  private List<SimpleSensorInfo> sensorInfos;
  @ApiModelProperty(value = "维护信息")
  private Maintenance maintenance;


  public String getServerId() {
    return serverId;
  }

  public void setServerId(String serverId) {
    this.serverId = serverId;
  }

  public String getModelInfo() {
    return modelInfo;
  }

  public void setModelInfo(String modelInfo) {
    this.modelInfo = modelInfo;
  }

  public String getCpuInfo() {
    return cpuInfo;
  }

  public void setCpuInfo(String cpuInfo) {
    this.cpuInfo = cpuInfo;
  }

  public String getMemoryInfo() {
    return memoryInfo;
  }

  public void setMemoryInfo(String memoryInfo) {
    this.memoryInfo = memoryInfo;
  }

  public String getDiskInfo() {
    return diskInfo;
  }

  public void setDiskInfo(String diskInfo) {
    this.diskInfo = diskInfo;
  }

  public String getNetworkCardInfo() {
    return networkCardInfo;
  }

  public void setNetworkCardInfo(String networkCardInfo) {
    this.networkCardInfo = networkCardInfo;
  }

  public String getManageIp() {
    return manageIp;
  }

  public void setManageIp(String manageIp) {
    this.manageIp = manageIp;
  }

  public String getGatewayIp() {
    return gatewayIp;
  }

  public void setGatewayIp(String gatewayIp) {
    this.gatewayIp = gatewayIp;
  }

  public String getStoreIp() {
    return storeIp;
  }

  public void setStoreIp(String storeIp) {
    this.storeIp = storeIp;
  }

  public String getRackNo() {
    return rackNo;
  }

  public void setRackNo(String rackNo) {
    this.rackNo = rackNo;
  }

  public String getSlotNo() {
    return slotNo;
  }

  public void setSlotNo(String slotNo) {
    this.slotNo = slotNo;
  }

  public List<DiskInfo> getDiskNotInDataNode() {
    return diskNotInDataNode;
  }

  public void setDiskNotInDataNode(List<DiskInfo> diskNotInDataNode) {
    this.diskNotInDataNode = diskNotInDataNode;
  }

  public List<SimpleArchiveMetadata> getDiskCache() {
    return diskCache;
  }

  public void setDiskCache(List<SimpleArchiveMetadata> diskCache) {
    this.diskCache = diskCache;
  }

  public List<SimpleArchiveMetadata> getDiskRaw() {
    return diskRaw;
  }

  public void setDiskRaw(List<SimpleArchiveMetadata> diskRaw) {
    this.diskRaw = diskRaw;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getChildFramNo() {
    return childFramNo;
  }

  public void setChildFramNo(String childFramNo) {
    this.childFramNo = childFramNo;
  }

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public List<SimpleInstance> getInstances() {
    return instances;
  }

  public void setInstances(List<SimpleInstance> instances) {
    this.instances = instances;
  }

  public SimpleDomain getDomain() {
    return domain;
  }

  public void setDomain(SimpleDomain domain) {
    this.domain = domain;
  }

  public Maintenance getMaintenance() {
    return maintenance;
  }

  public void setMaintenance(Maintenance maintenance) {
    this.maintenance = maintenance;
  }

  public List<SimpleArchiveMetadata> getDiskNonfunction() {
    return diskNonfunction;
  }

  public void setDiskNonfunction(List<SimpleArchiveMetadata> diskNonfunction) {
    this.diskNonfunction = diskNonfunction;
  }

  public List<SimpleSensorInfo> getSensorInfos() {
    return sensorInfos;
  }

  public void setSensorInfos(List<SimpleSensorInfo> sensorInfos) {
    this.sensorInfos = sensorInfos;
  }

  @Override
  public String toString() {
    return "ServerNode{" + "serverId='" + serverId + '\'' + ", modelInfo='" + modelInfo + '\''
        + ", cpuInfo='"
        + cpuInfo + '\'' + ", memoryInfo='" + memoryInfo + '\'' + ", diskInfo='" + diskInfo + '\''
        + ", networkCardInfo='" + networkCardInfo + '\'' + ", manageIp='" + manageIp + '\''
        + ", gatewayIp='"
        + gatewayIp + '\'' + ", storeIp='" + storeIp + '\'' + ", rackNo='" + rackNo + '\''
        + ", slotNo='"
        + slotNo + '\'' + ", status='" + status + '\'' + ", childFramNo='" + childFramNo + '\''
        + ", hostName='"
        + hostName + '\'' + ", domain=" + domain + ", diskNotInDataNode=" + diskNotInDataNode
        + ", diskCache="
        + diskCache + ", diskRaw=" + diskRaw + ", diskNonfunction=" + diskNonfunction
        + ", instances="
        + instances + ", sensorInfos=" + sensorInfos + ", maintenance=" + maintenance + '}';
  }
}
