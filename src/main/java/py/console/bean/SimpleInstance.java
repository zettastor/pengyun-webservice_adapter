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
import py.instance.Instance;
import py.instance.PortType;

/**
 * SimpleInstance.
 */
@ApiModel
public class SimpleInstance implements Comparable<SimpleInstance> {

  @ApiModelProperty(value = "节点id")
  private String instanceId;
  @ApiModelProperty(value = "节点名称")
  private String instanceName;
  @ApiModelProperty(value = "组id")
  private String groupId;
  @ApiModelProperty(value = "ip地址")
  private String host;
  @ApiModelProperty(value = "端口号")
  private int port;
  @ApiModelProperty(value = "状态")
  private String status;
  @ApiModelProperty(value = "信息")
  private String message;
  @ApiModelProperty(value = "域id")
  private String domainId;
  @ApiModelProperty(value = "状态")
  private String statusInDd;
  @ApiModelProperty(value = "主节点")
  private String maintenance;
  @ApiModelProperty(value = "监视器IP地址")
  private String monitorFlowHost;
  @ApiModelProperty(value = "域id")
  private boolean netSubHealth;

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

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getStatusInDd() {
    return statusInDd;
  }

  public void setStatusInDd(String statusInDd) {
    this.statusInDd = statusInDd;
  }

  public String getMonitorFlowHost() {
    return monitorFlowHost;
  }

  public void setMonitorFlowHost(String monitorFlowHost) {
    this.monitorFlowHost = monitorFlowHost;
  }

  public boolean isNetSubHealth() {
    return netSubHealth;
  }

  public void setNetSubHealth(boolean netSubHealth) {
    this.netSubHealth = netSubHealth;
  }

  /**
   * Simple Instance.
   *
   * @param instance instance
   */
  public SimpleInstance(Instance instance) {
    this.instanceId = String.valueOf(instance.getId().getId());
    this.instanceName = instance.getName();
    this.groupId =
        (instance.getGroup() == null) ? "" : String.valueOf(instance.getGroup().getGroupId());
    this.host = instance.getEndPoint().getHostName();
    this.port = instance.getEndPoint().getPort();
    this.status = instance.getStatus().name();
    this.netSubHealth = instance.isNetSubHealth();
    if (instance.getEndPointByServiceName(PortType.MONITOR) != null) {
      this.monitorFlowHost = instance.getEndPointByServiceName(PortType.MONITOR).getHostName();
    }

    this.domainId = null;
  }

  public SimpleInstance() {

  }

  @Override
  public int compareTo(SimpleInstance instance) {
    return instanceName.compareTo(instance.getInstanceName());
  }

  @Override
  public String toString() {
    return "SimpleInstance{" + "instanceId='" + instanceId + '\'' + ", instanceName='"
        + instanceName + '\''
        + ", groupId='" + groupId + '\'' + ", host='" + host + '\'' + ", port=" + port
        + ", status='" + status
        + '\'' + ", message='" + message + '\'' + ", domainId='" + domainId + '\''
        + ", statusInDD='"
        + statusInDd + '\'' + ", maintenance='" + maintenance + '\'' + ", monitorFlowHost='"
        + monitorFlowHost
        + '\'' + '}';
  }

  public String getDomainId() {
    return domainId;
  }

  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }

  public String getMaintenance() {
    return maintenance;
  }

  public void setMaintenance(String maintenance) {
    this.maintenance = maintenance;
  }
}
