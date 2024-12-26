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

