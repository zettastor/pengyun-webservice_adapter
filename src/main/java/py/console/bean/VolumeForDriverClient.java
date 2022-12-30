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
