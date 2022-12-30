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

@ApiModel
public class BaseStoragePool {

  @ApiModelProperty(value = "存储池id")
  private String poolId;
  @ApiModelProperty(value = "存储池所属域的id")
  private String domainId;
  @ApiModelProperty(value = "存储池所属域名称")
  private String domainName;
  @ApiModelProperty(value = "存储池名称")
  private String poolName;
  @ApiModelProperty(value = "存储池描述信息")
  private String description; // optional
  @ApiModelProperty(value = "存储池类型，有三个值Capacity（容量）,  Mixed（混合）,  Performance（性能）")
  private String strategy;
  @ApiModelProperty(value = "存储池状态(Available//可用,Deleting//删除中)")
  private String status;
  @ApiModelProperty(value = "已弃用", hidden = true)
  private String securityLevel;
  @ApiModelProperty(value = "剩余容量")
  private String freeSpace;
  @ApiModelProperty(value = "总容量")
  private String totalSpace;
  @ApiModelProperty(value = "已使用容量")
  private String usedSpace; //已使用容量，total-Space为已分配容量
  @ApiModelProperty(value = "整个pool的的重构速度,是整个磁盘的速度的相加")
  private String migrationSpeed;
  @ApiModelProperty(value = "重构进度百分比")
  private String migrationRatio;
  @ApiModelProperty(value = "oS策略")
  private String migrationStrategy;
  @ApiModelProperty(value = "要重构的总共page 大小, 单位MB")
  private String totalMigrateDataSizeMb;
  @ApiModelProperty(value = "三副本剩余有效容量")
  private String logicalPssFreeSpace;
  @ApiModelProperty(value = "两副本剩余有效容量")
  private String logicalPsaFreeSpace;
  @ApiModelProperty(value = "存储池等级")
  private String storagePoolLevel;

  public String getPoolId() {
    return poolId;
  }

  public void setPoolId(String poolId) {
    this.poolId = poolId;
  }

  public String getDomainId() {
    return domainId;
  }

  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }

  public String getPoolName() {
    return poolName;
  }

  public void setPoolName(String poolName) {
    this.poolName = poolName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getStrategy() {
    return strategy;
  }

  public void setStrategy(String strategy) {
    this.strategy = strategy;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSecurityLevel() {
    return securityLevel;
  }

  public void setSecurityLevel(String securityLevel) {
    this.securityLevel = securityLevel;
  }

  public String getFreeSpace() {
    return freeSpace;
  }

  public void setFreeSpace(String freeSpace) {
    this.freeSpace = freeSpace;
  }

  public String getTotalSpace() {
    return totalSpace;
  }

  public void setTotalSpace(String totalSpace) {
    this.totalSpace = totalSpace;
  }


  public String getMigrationSpeed() {
    return migrationSpeed;
  }

  public void setMigrationSpeed(String migrationSpeed) {
    this.migrationSpeed = migrationSpeed;
  }

  public String getMigrationRatio() {
    return migrationRatio;
  }

  public void setMigrationRatio(String migrationRatio) {
    this.migrationRatio = migrationRatio;
  }

  public String getMigrationStrategy() {
    return migrationStrategy;
  }

  public void setMigrationStrategy(String migrationStrategy) {
    this.migrationStrategy = migrationStrategy;
  }

  public String getLogicalPssFreeSpace() {
    return logicalPssFreeSpace;
  }

  public void setLogicalPssFreeSpace(String logicalPssFreeSpace) {
    this.logicalPssFreeSpace = logicalPssFreeSpace;
  }

  public String getLogicalPsaFreeSpace() {
    return logicalPsaFreeSpace;
  }

  public void setLogicalPsaFreeSpace(String logicalPsaFreeSpace) {
    this.logicalPsaFreeSpace = logicalPsaFreeSpace;
  }

  public String getStoragePoolLevel() {
    return storagePoolLevel;
  }

  public void setStoragePoolLevel(String storagePoolLevel) {
    this.storagePoolLevel = storagePoolLevel;
  }

  public String getDomainName() {
    return domainName;
  }

  public void setDomainName(String domainName) {
    this.domainName = domainName;
  }

  public String getUsedSpace() {
    return usedSpace;
  }

  public void setUsedSpace(String usedSpace) {
    this.usedSpace = usedSpace;
  }

  public String getTotalMigrateDataSizeMb() {
    return totalMigrateDataSizeMb;
  }

  public void setTotalMigrateDataSizeMb(String totalMigrateDataSizeMb) {
    this.totalMigrateDataSizeMb = totalMigrateDataSizeMb;
  }

  @Override
  public String toString() {
    return "SimpleStoragePool{" + "poolId='" + poolId + '\'' + ", domainId='" + domainId + '\''
        + ", domainName='"
        + domainName + '\'' + ", poolName='" + poolName + '\'' + ", description='" + description
        + '\''
        + ", strategy='" + strategy + '\'' + ", status='" + status + '\'' + ", securityLevel='"
        + securityLevel
        + '\'' + ", freeSpace='" + freeSpace + '\'' + ", totalSpace='" + totalSpace + '\''
        + ", usedSpace='"
        + usedSpace + '\'' + ", migrationSpeed='" + migrationSpeed + '\'' + ", migrationRatio='"
        + migrationRatio + '\'' + ", migrationStrategy='" + migrationStrategy + '\''
        + ", totalMigrateDataSizeMB='" + totalMigrateDataSizeMb + '\'' + ", logicalPSSFreeSpace='"
        + logicalPssFreeSpace
        + '\'' + ", logicalPSAFreeSpace='" + logicalPsaFreeSpace + '\'' + ", storagePoolLevel='"
        + storagePoolLevel + '\'' + '}';
  }
}
