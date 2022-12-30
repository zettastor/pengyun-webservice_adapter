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
 * SimpleDomain.
 */
@ApiModel
public class SimpleDomain {

  @ApiModelProperty(value = "域id")
  private String domainId;
  @ApiModelProperty(value = "域名称")
  private String domainName;
  @ApiModelProperty(value = "描述")
  private String domainDescription;
  @ApiModelProperty(value = "域的状态")
  private String status;
  @ApiModelProperty(value = "域空间")
  private String domainfreeSpace;
  @ApiModelProperty(value = "域的节点")
  private List<SimpleInstance> dataNodes;
  @ApiModelProperty(value = "域总容量")
  private String logicalSpace;
  @ApiModelProperty(value = "域已用容量")
  private String useSpace;
  @ApiModelProperty(value = "域剩余容量")
  private String freeSpace;

  public String getDomainfreeSpace() {
    return domainfreeSpace;
  }

  public void setDomainfreeSpace(String domainfreeSpace) {
    this.domainfreeSpace = domainfreeSpace;
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

  public String getDomainDescription() {
    return domainDescription;
  }

  public void setDomainDescription(String domainDescription) {
    this.domainDescription = domainDescription;
  }

  public List<SimpleInstance> getDataNodes() {
    return dataNodes;
  }

  public void setDataNodes(List<SimpleInstance> dataNodes) {
    this.dataNodes = dataNodes;
  }

  public String getLogicalSpace() {
    return logicalSpace;
  }

  public void setLogicalSpace(String logicalSpace) {
    this.logicalSpace = logicalSpace;
  }

  public String getUseSpace() {
    return useSpace;
  }

  public void setUseSpace(String useSpace) {
    this.useSpace = useSpace;
  }

  public String getFreeSpace() {
    return freeSpace;
  }

  public void setFreeSpace(String freeSpace) {
    this.freeSpace = freeSpace;
  }

  @Override
  public String toString() {
    return "SimpleDomain{"
        + "domainId='" + domainId + '\''
        + ", domainName='" + domainName + '\''
        + ", domainDescription='" + domainDescription + '\''
        + ", status='" + status + '\''
        + ", domainfreeSpace='" + domainfreeSpace + '\''
        + ", dataNodes=" + dataNodes
        + ", logicalSpace='" + logicalSpace + '\''
        + ", useSpace='" + useSpace + '\''
        + ", freeSpace='" + freeSpace + '\''
        + '}';
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
