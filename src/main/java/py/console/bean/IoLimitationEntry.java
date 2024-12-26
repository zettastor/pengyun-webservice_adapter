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
