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

public class SimpleTopoInfo {

  private String instanceId;

  private String instanceName;

  private String host;

  private int port;

  private String status;

  private String serverId;
  private String modelInfo;
  private String cpuInfo;
  private String memoryInfo;
  private String diskInfo;
  private String networkCardInfo;
  private String manageIp;
  private String gatewayIp;
  private String storeIp;
  private String rackNo;
  private String slotNo;
  private String serverStatus;
  private String childFramNo;
  private String hostName;

  public String getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(String instanceId) {
    this.instanceId = instanceId;
  }

  public String getInstanceName() {
    return instanceName;
  }

  public void setInstanceName(String instanceName) {
    this.instanceName = instanceName;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

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

  public String getServerStatus() {
    return serverStatus;
  }

  public void setServerStatus(String serverStatus) {
    this.serverStatus = serverStatus;
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

  @Override
  public String toString() {
    return "SimpleTopoInfo{"
        + "instanceId='" + instanceId + '\''
        + ", instanceName='" + instanceName + '\''
        + ", host='" + host + '\''
        + ", port=" + port
        + ", status='" + status + '\''
        + ", serverId='" + serverId + '\''
        + ", modelInfo='" + modelInfo + '\''
        + ", cpuInfo='" + cpuInfo + '\''
        + ", memoryInfo='" + memoryInfo + '\''
        + ", diskInfo='" + diskInfo + '\''
        + ", networkCardInfo='" + networkCardInfo + '\''
        + ", manageIp='" + manageIp + '\''
        + ", gatewayIp='" + gatewayIp + '\''
        + ", storeIp='" + storeIp + '\''
        + ", rackNo='" + rackNo + '\''
        + ", slotNo='" + slotNo + '\''
        + ", serverStatus='" + serverStatus + '\''
        + ", childFramNo='" + childFramNo + '\''
        + ", hostName='" + hostName + '\''
        + '}';
  }
}
