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
