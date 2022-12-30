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
