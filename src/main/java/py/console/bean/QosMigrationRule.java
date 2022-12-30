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

@ApiModel
public class QosMigrationRule {

  @ApiModelProperty(value = "策略id")
  private String ruleId;
  @ApiModelProperty(value = "策略名称")
  private String ruleName;
  @ApiModelProperty(value = "单盘重构速度上限(mb/s),策略类型为手动模式时需要设定此值，否则为空字符串")
  private String maxMigrationSpeed;
  @ApiModelProperty(value = "策略类型,Smart(智能)Manual(手动)")
  private String strategy;
  @ApiModelProperty(value = "状态")
  private String status;
  @ApiModelProperty(value = "模式，两个值：AbsoluteTime(绝对时间)/RelativeTime(相对时间)")
  private String mode;
  @ApiModelProperty(value = "开始时间，策略模式为绝对时间时需要设定此值")
  private String startTime;
  @ApiModelProperty(value = "结束时间，策略模式为绝对时间时需要设定此值")
  private String endTime;
  @ApiModelProperty(value = "等待时间，策略模式为相对时间时需要设定此值")
  private String waitTime;
  @ApiModelProperty(value = "内置规则标识")
  private String builtInRule; //内置规则flag
  @ApiModelProperty(value = "及时追平数据")
  private String ignoreMissPagesAndLogs;

  public String getRuleId() {
    return ruleId;
  }

  public void setRuleId(String ruleId) {
    this.ruleId = ruleId;
  }

  public String getRuleName() {
    return ruleName;
  }

  public void setRuleName(String ruleName) {
    this.ruleName = ruleName;
  }

  public String getMaxMigrationSpeed() {
    return maxMigrationSpeed;
  }

  public void setMaxMigrationSpeed(String maxMigrationSpeed) {
    this.maxMigrationSpeed = maxMigrationSpeed;
  }

  public String getStrategy() {
    return strategy;
  }

  public void setStrategy(String strategy) {
    this.strategy = strategy;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getWaitTime() {
    return waitTime;
  }

  public void setWaitTime(String waitTime) {
    this.waitTime = waitTime;
  }

  public String getIgnoreMissPagesAndLogs() {
    return ignoreMissPagesAndLogs;
  }

  public void setIgnoreMissPagesAndLogs(String ignoreMissPagesAndLogs) {
    this.ignoreMissPagesAndLogs = ignoreMissPagesAndLogs;
  }

  public String getBuiltInRule() {
    return builtInRule;
  }

  public void setBuiltInRule(String builtInRule) {
    this.builtInRule = builtInRule;
  }

  @Override
  public String toString() {
    return "QosMigrationRule{" + "ruleId='" + ruleId + '\'' + ", ruleName='" + ruleName + '\''
        + ", maxMigrationSpeed='" + maxMigrationSpeed + '\'' + ", strategy='" + strategy + '\''
        + ", status='"
        + status + '\'' + ", mode='" + mode + '\'' + ", startTime='" + startTime + '\''
        + ", endTime='"
        + endTime + '\'' + ", waitTime='" + waitTime + '\'' + ", builtInRule='" + builtInRule + '\''
        + ", ignoreMissPagesAndLogs='" + ignoreMissPagesAndLogs + '\'' + '}';
  }
}
