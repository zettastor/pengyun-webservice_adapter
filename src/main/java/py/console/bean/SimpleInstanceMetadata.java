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
import py.common.struct.EndPoint;

/**
 * SimpleInstanceMetadata.
 */
@ApiModel
public class SimpleInstanceMetadata {

  @ApiModelProperty(value = "节点id")
  private String instanceId;
  @ApiModelProperty(value = "端口")
  private String endPoint;
  @ApiModelProperty(value = "总容量")
  private long capacity;
  @ApiModelProperty(value = "逻辑容量")
  private long logicalCapacity;
  @ApiModelProperty(value = "剩余容量")
  private long freeSpace;
  @ApiModelProperty(value = "节点中磁盘信息")
  List<SimpleArchiveMetadata> archives;
  @ApiModelProperty(value = "所属域id")
  private Long domainId;

  public long getCapacity() {
    return capacity;
  }

  public void setCapacity(long capacity) {
    this.capacity = capacity;
  }

  public long getLogicalCapacity() {
    return logicalCapacity;
  }

  public void setLogicalCapacity(long logicalCapacity) {
    this.logicalCapacity = logicalCapacity;
  }

  public long getFreeSpace() {
    return freeSpace;
  }

  public void setFreeSpace(long freeSpace) {
    this.freeSpace = freeSpace;
  }

  public List<SimpleArchiveMetadata> getArchives() {
    return archives;
  }

  public void setArchives(List<SimpleArchiveMetadata> archives) {
    this.archives = archives;
  }

  public String getEndPoint() {
    return endPoint;
  }

  public void setEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }

  public String getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(String instanceId) {
    this.instanceId = instanceId;
  }

  public Long getDomainId() {
    return domainId;
  }

  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }

  // merge from volumeAlter fetch
  public String getHostName() {
    EndPoint endPoint = new EndPoint(this.endPoint);
    return endPoint.getHostName();
  }

  @Override
  public String toString() {
    return "SimpleInstanceMetadata{" + "instanceId='" + instanceId + '\'' + ", endPoint='"
        + endPoint + '\''
        + ", capacity=" + capacity + ", logicalCapacity=" + logicalCapacity + ", freeSpace="
        + freeSpace
        + ", archives=" + archives + ", domainId=" + domainId + '}';
  }
}
