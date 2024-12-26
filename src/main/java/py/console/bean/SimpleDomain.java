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
