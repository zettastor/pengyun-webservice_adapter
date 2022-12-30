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
import py.archive.ArchiveStatus;

/**
 * SimpleArchiveMetadata.
 */
@ApiModel
public class SimpleArchiveMetadata {

  @ApiModelProperty(value = "磁盘id")
  private String archiveId;
  @ApiModelProperty(value = "节点id")
  private String datanodeId;
  @ApiModelProperty(value = "磁盘序列号")
  private String serialNumber; //iscsi id
  @ApiModelProperty(value = "设备名称")
  private String deviceName;
  @ApiModelProperty(value = "磁盘逻辑容量")
  private long logicalSpace;
  @ApiModelProperty(value = "磁盘剩余容量")
  private long logicalFreeSpace;
  @ApiModelProperty(value = "磁盘状态")
  private ArchiveStatus status;
  @ApiModelProperty(value = "存储池id")
  private String storagePool;
  @ApiModelProperty(value = "存储池名称")
  private String poolName;
  @ApiModelProperty(value = "域id")
  private String domainId;
  @ApiModelProperty(value = "域名称")
  private String domainName;
  @ApiModelProperty(value = "磁盘类")
  private String archiveType;
  @ApiModelProperty(value = "磁盘存储类型")
  private String storageType;
  @ApiModelProperty(value = "磁盘重构速度")
  private String migrationSpeed;
  @ApiModelProperty(value = "磁盘重构进度百分比")
  private String migrationRatio;
  @ApiModelProperty(value = "主机IP:端口")
  private String dataNodeEndPoint;
  @ApiModelProperty(value = "已用容量")
  private String dataSizeMb; //已用容量
  @ApiModelProperty(value = "供应商")
  private String vendor;
  @ApiModelProperty(value = "型号")
  private String model;
  @ApiModelProperty(value = "转速")
  private String rate;
  @ApiModelProperty(value = "wwn号")
  private String wwn;
  @ApiModelProperty(value = "控制id")
  private String controllerId;
  @ApiModelProperty(value = "槽位")
  private String slotNumber;
  @ApiModelProperty(value = "enclosure id")
  private String enclosureId;
  @ApiModelProperty(value = "卡类型")
  private String cardType;
  @ApiModelProperty(value = "开关")
  private String swith;
  @ApiModelProperty(value = "磁盘序列号")
  private String diskSerialNumber; //real sn
  @ApiModelProperty(value = "产品序列号")
  private String sn;
  @ApiModelProperty(value = "smart检测信息")
  private List<SmartInfo> smartInfos;

  public SimpleArchiveMetadata() {

  }

  public String getArchiveId() {
    return archiveId;
  }

  public void setArchiveId(String archiveId) {
    this.archiveId = archiveId;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public ArchiveStatus getStatus() {
    return status;
  }

  public void setStatus(ArchiveStatus status) {
    this.status = status;
  }

  public String getStorageType() {
    return storageType;
  }

  public void setStorageType(String storageType) {
    this.storageType = storageType;
  }

  public long getLogicalSpace() {
    return logicalSpace;
  }

  public void setLogicalSpace(long logicalSpace) {
    this.logicalSpace = logicalSpace;
  }

  public long getLogicalFreeSpace() {
    return logicalFreeSpace;
  }

  public void setLogicalFreeSpace(long logicalFreeSpace) {
    this.logicalFreeSpace = logicalFreeSpace;
  }

  public String getStoragePool() {
    return storagePool;
  }

  public void setStoragePool(String storagePool) {
    this.storagePool = storagePool;
  }

  public String getArchiveType() {
    return archiveType;
  }

  public void setArchiveType(String archiveType) {
    this.archiveType = archiveType;
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

  public String getVendor() {
    return vendor;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getRate() {
    return rate;
  }

  public void setRate(String rate) {
    this.rate = rate;
  }

  public String getDataNodeEndPoint() {
    return dataNodeEndPoint;
  }

  public void setDataNodeEndPoint(String dataNodeEndPoint) {
    this.dataNodeEndPoint = dataNodeEndPoint;
  }

  public String getWwn() {
    return wwn;
  }

  public void setWwn(String wwn) {
    this.wwn = wwn;
  }

  public String getControllerId() {
    return controllerId;
  }

  public void setControllerId(String controllerId) {
    this.controllerId = controllerId;
  }

  public String getSlotNumber() {
    return slotNumber;
  }

  public void setSlotNumber(String slotNumber) {
    this.slotNumber = slotNumber;
  }

  public String getEnclosureId() {
    return enclosureId;
  }

  public void setEnclosureId(String enclosureId) {
    this.enclosureId = enclosureId;
  }

  public String getCardType() {
    return cardType;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }

  public String getSwith() {
    return swith;
  }

  public void setSwith(String swith) {
    this.swith = swith;
  }

  public String getDatanodeId() {
    return datanodeId;
  }

  public void setDatanodeId(String datanodeId) {
    this.datanodeId = datanodeId;
  }

  public String getDiskSerialNumber() {
    return diskSerialNumber;
  }

  public void setDiskSerialNumber(String diskSerialNumber) {
    this.diskSerialNumber = diskSerialNumber;
  }

  public String getPoolName() {
    return poolName;
  }

  public void setPoolName(String poolName) {
    this.poolName = poolName;
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

  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public List<SmartInfo> getSmartInfos() {
    return smartInfos;
  }

  public void setSmartInfos(List<SmartInfo> smartInfos) {
    this.smartInfos = smartInfos;
  }

  public String getDataSizeMb() {
    return dataSizeMb;
  }

  public void setDataSizeMb(String dataSizeMb) {
    this.dataSizeMb = dataSizeMb;
  }

  @Override
  public String toString() {
    return "SimpleArchiveMetadata{" + "archiveId='" + archiveId + '\'' + ", datanodeId='"
        + datanodeId + '\''
        + ", serialNumber='" + serialNumber + '\'' + ", deviceName='" + deviceName + '\''
        + ", logicalSpace="
        + logicalSpace + ", logicalFreeSpace=" + logicalFreeSpace + ", status=" + status
        + ", storagePool='"
        + storagePool + '\'' + ", poolName='" + poolName + '\'' + ", domainId='" + domainId + '\''
        + ", domainName='" + domainName + '\'' + ", archiveType='" + archiveType + '\''
        + ", storageType='"
        + storageType + '\'' + ", migrationSpeed='" + migrationSpeed + '\'' + ", migrationRatio='"
        + migrationRatio + '\'' + ", dataNodeEndPoint='" + dataNodeEndPoint + '\''
        + ", dataSizeMb='"
        + dataSizeMb + '\'' + ", vendor='" + vendor + '\'' + ", model='" + model + '\'' + ", rate='"
        + rate
        + '\'' + ", wwn='" + wwn + '\'' + ", controllerId='" + controllerId + '\''
        + ", slotNumber='"
        + slotNumber + '\'' + ", enclosureId='" + enclosureId + '\'' + ", cardType='" + cardType
        + '\''
        + ", swith='" + swith + '\'' + ", diskSerialNumber='" + diskSerialNumber + '\'' + ", sn='"
        + sn + '\''
        + ", smartInfos=" + smartInfos + '}';
  }
}
