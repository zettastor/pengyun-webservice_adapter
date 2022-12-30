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

@ApiModel
public class IoLimitation {

  @ApiModelProperty(value = "策略id")
  private String limitationId;
  @ApiModelProperty(value = "策略名称")
  private String limitationName;
  @ApiModelProperty(value = "策略类型,Static(静态)，Dynamic(动态)")
  private String limitType;
  @ApiModelProperty(value = "状态")
  private String status;
  @ApiModelProperty(value = "entries信息")
  private List<IoLimitationEntry> entries;

  public String getLimitationId() {
    return limitationId;
  }

  public void setLimitationId(String limitationId) {
    this.limitationId = limitationId;
  }

  public String getLimitationName() {
    return limitationName;
  }

  public void setLimitationName(String limitationName) {
    this.limitationName = limitationName;
  }

  public String getLimitType() {
    return limitType;
  }

  public void setLimitType(String limitType) {
    this.limitType = limitType;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<IoLimitationEntry> getEntries() {
    return entries;
  }

  public void setEntries(List<IoLimitationEntry> entries) {
    this.entries = entries;
  }

  @Override
  public String toString() {
    return "IOLimitation{" + "limitationId='" + limitationId + '\'' + ", limitationName='"
        + limitationName + '\''
        + ", limitType='" + limitType + '\'' + ", status='" + status + '\'' + ", entries=" + entries
        + '}';
  }
}

