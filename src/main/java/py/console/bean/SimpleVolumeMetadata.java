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

/**
 * SimpleVolumeMetadata.
 */
@ApiModel
public class SimpleVolumeMetadata {

  @ApiModelProperty(value = "信息")
  private String message;
  @ApiModelProperty(value = "卷id")
  private String volumeId;
  @ApiModelProperty(value = "卷名称")
  private String volumeName;
  @ApiModelProperty(value = "卷状态")
  private String volumeStatus;
  @ApiModelProperty(value = "卷大小")
  private String volumeSize;
  @ApiModelProperty(value = "扩展的大小，默认为1024整数倍")
  private String extendSize;
  @ApiModelProperty(value = "版本号")
  private int version;
  @ApiModelProperty(value = "用户id")
  private long accountId;
  @ApiModelProperty(value = "卷类型")
  private String volumeType;
  @ApiModelProperty(value = "卷所属域id")
  private String volumeDomain;
  @ApiModelProperty(value = "卷所属存储池id")
  private String volumeStoragePoolId;
  @ApiModelProperty(value = "创建进度")
  private String creatingProgress;
  @ApiModelProperty(value = "还原进度")
  private String rollbackingProgress;
  @ApiModelProperty(value = "Snapshot回滚")
  private String inSnapshotRollback;
  @ApiModelProperty(value = "Snapshot　正在回滚")
  private String snapshotIdRollbacking;
  @ApiModelProperty(value = "segment状态")
  private String segmentStatus;
  @ApiModelProperty(value = "精简配置")
  private String simpleConfiguration;
  @ApiModelProperty(value = "卷所属存储池名称")
  private String storagePoolName;
  @ApiModelProperty(value = "卷所属存储池id")
  private String poolId;
  @ApiModelProperty(value = "卷使用率")
  private double freeSpaceRatio;
  @ApiModelProperty(value = "缓存类型")
  private String cacheType;
  @ApiModelProperty(value = "卷创建时间")
  private Long createTime;
  @ApiModelProperty(value = "最后一个扩展时间")
  private String lastExtendedTime;
  @ApiModelProperty(value = "已弃用", hidden = true)
  private String volumeBuildType;
  @ApiModelProperty(value = "扩展标记")
  private String extendFlag;
  @ApiModelProperty(value = "设置卷可读写")
  private String readWrite;
  @ApiModelProperty(value = "重构速度")
  private String migrationSpeed;
  @ApiModelProperty(value = "重构进度")
  private String migrationRatio;

  @ApiModelProperty(value = "已弃用", hidden = true)
  private String snapshotTotalSize;
  @ApiModelProperty(value = "rebalance进度")
  private String rebalanceRatio;
  @ApiModelProperty(value = "rebalance版本")
  private String rebalanceVersion;
  @ApiModelProperty(value = "掉电保护")
  private String wtsType;
  @ApiModelProperty(value = "磁盘使用率")
  private String totalPhysicalSpace;
  @ApiModelProperty(value = "被克隆时候的源卷名")
  private String srcVolumeNameWithClone;
  @ApiModelProperty(value = "被克隆时候的快照名")
  private String srcSnapshotNameWithClone;
  @ApiModelProperty(value = "CSI挂载数量")
  private String csiLaunchCount;
  @ApiModelProperty(value = "停止延迟")
  private String stopDelay;
  @ApiModelProperty(value = "时间延迟")
  private String timeForDelay;
  @ApiModelProperty(value = "时间回收")
  private String timeForRecycle;
  @ApiModelProperty(value = "描述")
  private String description;
  @ApiModelProperty(value = "卷最后连接时间")
  private String clientLastConnectTime;
  @ApiModelProperty(value = "读写权限")
  private String readOnlyForCsi;
  @ApiModelProperty(value = "文件系统已用容量")
  private String usedSpaceForCsi;
  @ApiModelProperty(value = "文件系统总容量")
  private String totalSpaceFroCsi;

  public String getUsedSpaceForCsi() {
    return usedSpaceForCsi;
  }

  public void setUsedSpaceForCsi(String usedSpaceForCsi) {
    this.usedSpaceForCsi = usedSpaceForCsi;
  }

  public String getTotalSpaceFroCsi() {
    return totalSpaceFroCsi;
  }

  public void setTotalSpaceFroCsi(String totalSpaceFroCsi) {
    this.totalSpaceFroCsi = totalSpaceFroCsi;
  }

  public String getTimeForRecycle() {
    return timeForRecycle;
  }

  public void setTimeForRecycle(String timeForRecycle) {
    this.timeForRecycle = timeForRecycle;
  }

  public String getSrcVolumeNameWithClone() {
    return srcVolumeNameWithClone;
  }

  public void setSrcVolumeNameWithClone(String srcVolumeNameWithClone) {
    this.srcVolumeNameWithClone = srcVolumeNameWithClone;
  }

  public String getSrcSnapshotNameWithClone() {
    return srcSnapshotNameWithClone;
  }

  public void setSrcSnapshotNameWithClone(String srcSnapshotNameWithClone) {
    this.srcSnapshotNameWithClone = srcSnapshotNameWithClone;
  }

  private List<SimpleSegmentMetadata> segmentList;

  private List<SimpleDriverMetadata> driverMetadatas;

  private List<SimpleSnapshotMetadata> snapshotMetadatas;

  private String domainId;

  private String enableLaunchMultiDrivers;

  public String getVolumeId() {
    return volumeId;
  }

  public void setVolumeId(String volumeId) {
    this.volumeId = volumeId;
  }

  public String getVolumeName() {
    return volumeName;
  }

  public void setVolumeName(String volumeName) {
    this.volumeName = volumeName;
  }

  public String getVolumeStatus() {
    return volumeStatus;
  }

  public void setVolumeStatus(String volumeStatus) {
    this.volumeStatus = volumeStatus;
  }

  public String getVolumeSize() {
    return volumeSize;
  }

  public void setVolumeSize(String volumeSize) {
    this.volumeSize = volumeSize;
  }

  public List<SimpleSegmentMetadata> getSegmentList() {
    return segmentList;
  }

  public void setSegmentList(List<SimpleSegmentMetadata> segmentList) {
    this.segmentList = segmentList;
  }

  public List<SimpleDriverMetadata> getDriverMetadatas() {
    return driverMetadatas;
  }

  public void setDriverMetadatas(List<SimpleDriverMetadata> driverMetadatas) {
    this.driverMetadatas = driverMetadatas;
  }

  public long getAccountId() {
    return accountId;
  }

  public void setAccountId(long accountId) {
    this.accountId = accountId;
  }

  public String getVolumeType() {
    return volumeType;
  }

  public void setVolumeType(String volumeType) {
    this.volumeType = volumeType;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public String getExtendSize() {
    return extendSize;
  }

  public void setExtendSize(String extendSize) {
    this.extendSize = extendSize;
  }

  public String getCreatingProgress() {
    return creatingProgress;
  }

  public void setCreatingProgress(int creatingProgress) {
    this.creatingProgress = Integer.toString(creatingProgress);
  }

  public void setCreatingProgress(String creatingProgress) {
    this.creatingProgress = creatingProgress;
  }

  public void setRollbackingProgress(String rollbackingProgress) {
    this.rollbackingProgress = rollbackingProgress;
  }

  public void setRollbackingProgress(int rollbackingProgress) {
    this.rollbackingProgress = Integer.toString(rollbackingProgress);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getVolumeDomain() {
    return volumeDomain;
  }

  public void setVolumeDomain(String volumeDomain) {
    this.volumeDomain = volumeDomain;
  }

  public String getSegmentStatus() {
    return segmentStatus;
  }

  public void setSegmentStatus(String segmentStatus) {
    this.segmentStatus = segmentStatus;
  }

  public String getSimpleConfiguration() {
    return simpleConfiguration;
  }

  public void setSimpleConfiguration(String simpleConfiguration) {
    this.simpleConfiguration = simpleConfiguration;
  }

  public List<SimpleSnapshotMetadata> getSnapshotMetadatas() {
    return snapshotMetadatas;
  }

  public void setSnapshotMetadatas(List<SimpleSnapshotMetadata> snapshotMetadatas) {
    this.snapshotMetadatas = snapshotMetadatas;
  }

  public String getRollbackingProgress() {
    return rollbackingProgress;
  }

  public String getClientLastConnectTime() {
    return clientLastConnectTime;
  }

  public void setClientLastConnectTime(String clientLastConnectTime) {
    this.clientLastConnectTime = clientLastConnectTime;
  }

  public String getInSnapshotRollback() {
    return inSnapshotRollback;
  }

  public void setInSnapshotRollback(String inSnapshotRollback) {
    this.inSnapshotRollback = inSnapshotRollback;
  }

  public String getSnapshotIdRollbacking() {
    return snapshotIdRollbacking;
  }

  public void setSnapshotIdRollbacking(String snapshotIdRollbacking) {
    this.snapshotIdRollbacking = snapshotIdRollbacking;
  }

  public String getStoragePoolName() {
    return storagePoolName;
  }

  public void setStoragePoolName(String storagePoolName) {
    this.storagePoolName = storagePoolName;
  }

  public String getVolumeStoragePoolId() {
    return volumeStoragePoolId;
  }

  public void setVolumeStoragePoolId(String volumeStoragePoolId) {
    this.volumeStoragePoolId = volumeStoragePoolId;
  }

  public double getFreeSpaceRatio() {
    return freeSpaceRatio;
  }

  public void setFreeSpaceRatio(double freeSpaceRatio) {
    this.freeSpaceRatio = freeSpaceRatio;
  }

  public String getLastExtendedTime() {
    return lastExtendedTime;
  }

  public void setLastExtendedTime(String lastExtendedTime) {
    this.lastExtendedTime = lastExtendedTime;
  }

  public String getVolumeBuildType() {
    return volumeBuildType;
  }

  public void setVolumeBuildType(String volumeBuildType) {
    this.volumeBuildType = volumeBuildType;
  }

  public String getExtendFlag() {
    return extendFlag;
  }

  public void setExtendFlag(String extendFlag) {
    this.extendFlag = extendFlag;
  }

  public String getCacheType() {
    return cacheType;
  }

  public void setCacheType(String cacheType) {
    this.cacheType = cacheType;
  }

  public Long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Long createTime) {
    this.createTime = createTime;
  }

  public String getReadWrite() {
    return readWrite;
  }

  public void setReadWrite(String readWrite) {
    this.readWrite = readWrite;
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

  public String getDomainId() {
    return domainId;
  }

  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }

  public String getSnapshotTotalSize() {
    return snapshotTotalSize;
  }

  public void setSnapshotTotalSize(String snapshotTotalSize) {
    this.snapshotTotalSize = snapshotTotalSize;
  }

  public String getEnableLaunchMultiDrivers() {
    return enableLaunchMultiDrivers;
  }

  public void setEnableLaunchMultiDrivers(String enableLaunchMultiDrivers) {
    this.enableLaunchMultiDrivers = enableLaunchMultiDrivers;
  }

  public String getRebalanceRatio() {
    return rebalanceRatio;
  }

  public void setRebalanceRatio(String rebalanceRatio) {
    this.rebalanceRatio = rebalanceRatio;
  }

  public String getRebalanceVersion() {
    return rebalanceVersion;
  }

  public void setRebalanceVersion(String rebalanceVersion) {
    this.rebalanceVersion = rebalanceVersion;
  }

  public String getStopDelay() {
    return stopDelay;
  }

  public void setStopDelay(String stopDelay) {
    this.stopDelay = stopDelay;
  }

  public String getTimeForDelay() {
    return timeForDelay;
  }

  public void setTimeForDelay(String timeForDelay) {
    this.timeForDelay = timeForDelay;
  }

  public String getPoolId() {
    return poolId;
  }

  public void setPoolId(String poolId) {
    this.poolId = poolId;
  }

  public String getWtsType() {
    return wtsType;
  }

  public void setWtsType(String wtsType) {
    this.wtsType = wtsType;
  }

  public String getTotalPhysicalSpace() {
    return totalPhysicalSpace;
  }

  public void setTotalPhysicalSpace(String totalPhysicalSpace) {
    this.totalPhysicalSpace = totalPhysicalSpace;
  }

  public String getCsiLaunchCount() {
    return csiLaunchCount;
  }

  public void setCsiLaunchCount(String csiLaunchCount) {
    this.csiLaunchCount = csiLaunchCount;
  }

  public String getReadOnlyForCsi() {
    return readOnlyForCsi;
  }

  public void setReadOnlyForCsi(String readOnlyForCsi) {
    this.readOnlyForCsi = readOnlyForCsi;
  }

  @Override
  public String toString() {
    return "SimpleVolumeMetadata{"
        + "message='" + message + '\''
        + ", volumeId='" + volumeId + '\''
        + ", volumeName='" + volumeName + '\''
        + ", volumeStatus='" + volumeStatus + '\''
        + ", volumeSize='" + volumeSize + '\''
        + ", extendSize='" + extendSize + '\''
        + ", version=" + version
        + ", accountId=" + accountId
        + ", volumeType='" + volumeType + '\''
        + ", volumeDomain='" + volumeDomain + '\''
        + ", volumeStoragePoolId='" + volumeStoragePoolId + '\''
        + ", creatingProgress='" + creatingProgress + '\''
        + ", rollbackingProgress='" + rollbackingProgress + '\''
        + ", inSnapshotRollback='" + inSnapshotRollback + '\''
        + ", snapshotIdRollbacking='" + snapshotIdRollbacking + '\''
        + ", segmentStatus='" + segmentStatus + '\''
        + ", simpleConfiguration='" + simpleConfiguration + '\''
        + ", storagePoolName='" + storagePoolName + '\''
        + ", poolId='" + poolId + '\''
        + ", freeSpaceRatio=" + freeSpaceRatio
        + ", cacheType='" + cacheType + '\''
        + ", createTime=" + createTime
        + ", lastExtendedTime='" + lastExtendedTime + '\''
        + ", volumeBuildType='" + volumeBuildType + '\''
        + ", extendFlag='" + extendFlag + '\''
        + ", readWrite='" + readWrite + '\''
        + ", migrationSpeed='" + migrationSpeed + '\''
        + ", migrationRatio='" + migrationRatio + '\''
        + ", snapshotTotalSize='" + snapshotTotalSize + '\''
        + ", rebalanceRatio='" + rebalanceRatio + '\''
        + ", rebalanceVersion='" + rebalanceVersion + '\''
        + ", wtsType='" + wtsType + '\''
        + ", totalPhysicalSpace='" + totalPhysicalSpace + '\''
        + ", srcVolumeNameWithClone='" + srcVolumeNameWithClone + '\''
        + ", srcSnapshotNameWithClone='" + srcSnapshotNameWithClone + '\''
        + ", csiLaunchCount='" + csiLaunchCount + '\''
        + ", stopDelay='" + stopDelay + '\''
        + ", timeForDelay='" + timeForDelay + '\''
        + ", timeForRecycle='" + timeForRecycle + '\''
        + ", description='" + description + '\''
        + ", clientLastConnectTime='" + clientLastConnectTime + '\''
        + ", readOnly='" + readOnlyForCsi + '\''
        + ", segmentList=" + segmentList
        + ", driverMetadatas=" + driverMetadatas
        + ", snapshotMetadatas=" + snapshotMetadatas
        + ", domainId='" + domainId + '\''
        + ", enableLaunchMultiDrivers='" + enableLaunchMultiDrivers + '\''
        + '}';
  }
}
