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
public class RebalanceRule {

  @ApiModelProperty(value = "策略id")
  private String ruleId;
  @ApiModelProperty(value = "策略名称")
  private String ruleName;
  @ApiModelProperty(value = "绝对时间")
  private List<RebalanceAbsoluteTime> absoluteTimeList;
  @ApiModelProperty(value = "相对时间")
  private String waitTime;

  public String getRuleId() {
    return ruleId;
  }

  public void setRuleId(String ruleId) {
    this.ruleId = ruleId;
  }

  public String getRuleName() {
    return ruleName;
  }

  public List<RebalanceAbsoluteTime> getAbsoluteTimeList() {
    return absoluteTimeList;
  }

  public void setAbsoluteTimeList(List<RebalanceAbsoluteTime> absoluteTimeList) {
    this.absoluteTimeList = absoluteTimeList;
  }

  public void setRuleName(String ruleName) {
    this.ruleName = ruleName;
  }

  public String getWaitTime() {
    return waitTime;
  }

  public void setWaitTime(String waitTime) {
    this.waitTime = waitTime;
  }

  @Override
  public String toString() {
    return "RebalanceRule{"
      + "ruleId='" + ruleId + '\''
      + ", ruleName='" + ruleName + '\''
      + ", absoluteTimeList=" + absoluteTimeList
      + ", waitTime='" + waitTime + '\''
      + '}';
  }
}
