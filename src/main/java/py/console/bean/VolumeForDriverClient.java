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


public class VolumeForDriverClient {

  private String id;
  private String name;
  private String domainId;
  private String domainName;
  private String poolId;
  private String poolName;
  private String createTime;
  private String path;
  private String linkStatus;
  private String volumeStatus;
  private String driverStatus;
  private String size;
  private DriverStatusDescription statusDescription;

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDomainId() {
    return domainId;
  }

  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }

  public String getDomainName() {
    return domainName;
  }

  public void setDomainName(String domainName) {
    this.domainName = domainName;
  }

  public String getPoolId() {
    return poolId;
  }

  public void setPoolId(String poolId) {
    this.poolId = poolId;
  }

  public String getPoolName() {
    return poolName;
  }

  public void setPoolName(String poolName) {
    this.poolName = poolName;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getLinkStatus() {
    return linkStatus;
  }

  public void setLinkStatus(String linkStatus) {
    this.linkStatus = linkStatus;
  }

  public String getVolumeStatus() {
    return volumeStatus;
  }

  public void setVolumeStatus(String volumeStatus) {
    this.volumeStatus = volumeStatus;
  }

  public String getDriverStatus() {
    return driverStatus;
  }

  public void setDriverStatus(String driverStatus) {
    this.driverStatus = driverStatus;
  }

  public DriverStatusDescription getStatusDescription() {
    return statusDescription;
  }

  public void setStatusDescription(DriverStatusDescription statusDescription) {
    this.statusDescription = statusDescription;
  }

  @Override
  public String toString() {
    return "VolumeForDriverClient{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", domainId='"
        + domainId
        + '\'' + ", domainName='" + domainName + '\'' + ", poolId='" + poolId + '\''
        + ", poolName='" + poolName
        + '\'' + ", createTime='" + createTime + '\'' + ", path='" + path + '\'' + ", linkStatus='"
        + linkStatus
        + '\'' + ", volumeStatus='" + volumeStatus + '\'' + ", drvierStatus='" + driverStatus + '\''
        + ", size='" + size + '\'' + ", statusDescription=" + statusDescription + '}';
  }
}
