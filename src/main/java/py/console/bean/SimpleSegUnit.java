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

/**
 * SimpleSegmentVersion.
 */
@ApiModel
public class SimpleSegUnit implements Comparable<SimpleSegUnit> {

  /**
   * UnitType.
   */
  public enum UnitType {
    Primary, Secondary
  }

  @ApiModelProperty(value = "节点id")
  private String instanceId;
  @ApiModelProperty(value = "节点ip")
  private String instanceIp;
  @ApiModelProperty(value = "状态")
  private String status;
  @ApiModelProperty(value = "状态")
  private String statusDisplay;
  @ApiModelProperty(value = "类型")
  private String unitType;
  @ApiModelProperty(value = "磁盘名")
  private String diskName;
  @ApiModelProperty(value = "Segmentunit在磁盘的初始位置")
  private String offset;
  @ApiModelProperty(value = "回滚进度")
  private String inRollbackProgress;
  @ApiModelProperty(value = "回滚的快照id")
  private String snapshotIdOfRollback;
  @ApiModelProperty(value = "迁移进度")
  private double ratioMigration;

  public String getDiskName() {
    return diskName;
  }

  public void setDiskName(String diskName) {
    this.diskName = diskName;
  }

  public String getOffset() {
    return offset;
  }

  public void setOffset(String offset) {
    this.offset = offset;
  }

  private SimpleSegmentVersion simpleSegmentVersion;

  public SimpleSegUnit() {

  }

  public String getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(String instanceId) {
    this.instanceId = instanceId;
  }

  public String getInstanceIp() {
    return instanceIp;
  }

  public void setInstanceIp(String instanceIp) {
    this.instanceIp = instanceIp;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getUnitType() {
    return unitType;
  }

  public void setUnitType(String unitType) {
    this.unitType = unitType;
  }

  public String getStatusDisplay() {
    return statusDisplay;
  }

  public void setStatusDisplay(String statusDisplay) {
    this.statusDisplay = statusDisplay;
  }

  public SimpleSegmentVersion getSimpleSegmentVersion() {
    return simpleSegmentVersion;
  }

  public void setSimpleSegmentVersion(SimpleSegmentVersion simpleSegmentVersion) {
    this.simpleSegmentVersion = simpleSegmentVersion;
  }

  public String getInRollbackProgress() {
    return inRollbackProgress;
  }

  public void setInRollbackProgress(String inRollbackProgress) {
    this.inRollbackProgress = inRollbackProgress;
  }

  public String getSnapshotIdOfRollback() {
    return snapshotIdOfRollback;
  }

  public void setSnapshotIdOfRollback(String snapshotIdOfRollback) {
    this.snapshotIdOfRollback = snapshotIdOfRollback;
  }

  @Override
  public int compareTo(SimpleSegUnit simpleSegUnit) {
    if (Long.valueOf(instanceId) - Long.valueOf(simpleSegUnit.getInstanceId()) > 0) {
      return 1;
    } else if (Long.valueOf(instanceId) - Long.valueOf(simpleSegUnit.getInstanceId()) < 0) {
      return -1;
    }
    return 0;
  }

  public double getRatioMigration() {
    return ratioMigration;
  }

  public void setRatioMigration(double ratioMigration) {
    this.ratioMigration = ratioMigration;
  }

  @Override
  public String toString() {
    return "SimpleSegUnit [instanceId=" + instanceId + ", instanceIp=" + instanceIp + ", status="
        + status
        + ", statusDisplay=" + statusDisplay + ", unitType=" + unitType + ", diskName=" + diskName
        + ", offset="
        + offset + ", inRollbackProgress=" + inRollbackProgress + ", snapshotIdOfRollback="
        + snapshotIdOfRollback + ", ratioMigration=" + ratioMigration + ", simpleSegmentVersion="
        + simpleSegmentVersion + "]";
  }
}
