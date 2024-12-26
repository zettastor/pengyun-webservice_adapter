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
