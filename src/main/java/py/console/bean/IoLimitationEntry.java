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
public class IoLimitationEntry {

  @ApiModelProperty(value = "iops上限")
  private String upperLimitedIoPs;
  @ApiModelProperty(value = "iops下限")
  private String lowerLimitedIoPs;
  @ApiModelProperty(value = "吞吐量上限")
  private String upperLimitedThroughput;
  @ApiModelProperty(value = "吞吐量下限")
  private String lowerLimitedThroughput;
  @ApiModelProperty(value = "开始时间")
  private String startTime;
  @ApiModelProperty(value = "结束时间")
  private String endTime;

  public String getUpperLimitedIoPs() {
    return upperLimitedIoPs;
  }

  public void setUpperLimitedIoPs(String upperLimitedIoPs) {
    this.upperLimitedIoPs = upperLimitedIoPs;
  }

  public String getLowerLimitedIoPs() {
    return lowerLimitedIoPs;
  }

  public void setLowerLimitedIoPs(String lowerLimitedIoPs) {
    this.lowerLimitedIoPs = lowerLimitedIoPs;
  }

  public String getUpperLimitedThroughput() {
    return upperLimitedThroughput;
  }

  public void setUpperLimitedThroughput(String upperLimitedThroughput) {
    this.upperLimitedThroughput = upperLimitedThroughput;
  }

  public String getLowerLimitedThroughput() {
    return lowerLimitedThroughput;
  }

  public void setLowerLimitedThroughput(String lowerLimitedThroughput) {
    this.lowerLimitedThroughput = lowerLimitedThroughput;
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

  @Override
  public String toString() {
    return "IOLimitationEntry{" + "upperLimitedIOPS='" + upperLimitedIoPs + '\''
        + ", lowerLimitedIOPS='"
        + lowerLimitedIoPs + '\'' + ", upperLimitedThroughput='" + upperLimitedThroughput + '\''
        + ", lowerLimitedThroughput='" + lowerLimitedThroughput + '\'' + ", startTime='" + startTime
        + '\''
        + ", endTime='" + endTime + '\'' + '}';
  }
}
