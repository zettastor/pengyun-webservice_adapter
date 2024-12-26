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

/**
 * SimpleIoLimitation.
 */
public class SimpleIoLimitation {

  private String limitationId;

  private long upperLimitedIoPs;

  private long lowerLimitedIoPs;

  private long lowerLimitedThroughput;

  private long upperLimitedThroughput;

  private String startTime;

  private String endTime;

  private String limitType;

  public long getUpperLimitedIoPs() {
    return upperLimitedIoPs;
  }

  public void setUpperLimitedIoPs(long upperLimitedIoPs) {
    this.upperLimitedIoPs = upperLimitedIoPs;
  }

  public long getLowerLimitedIoPs() {
    return lowerLimitedIoPs;
  }

  public void setLowerLimitedIoPs(long lowerLimitedIoPs) {
    this.lowerLimitedIoPs = lowerLimitedIoPs;
  }

  public long getLowerLimitedThroughput() {
    return lowerLimitedThroughput;
  }

  public void setLowerLimitedThroughput(long lowerLimitedThroughput) {
    this.lowerLimitedThroughput = lowerLimitedThroughput;
  }

  public long getUpperLimitedThroughput() {
    return upperLimitedThroughput;
  }

  public void setUpperLimitedThroughput(long upperLimitedThroughput) {
    this.upperLimitedThroughput = upperLimitedThroughput;
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

  public String getLimitType() {
    return limitType;
  }

  public void setLimitType(String limitType) {
    this.limitType = limitType;
  }

  public String getLimitationId() {
    return limitationId;
  }

  public void setLimitationId(String limitationId) {
    this.limitationId = limitationId;
  }

  @Override
  public String toString() {
    return "SimpleIOLimitation{" + "limitationId='" + limitationId + '\'' + ", upperLimitedIOPS="
        + upperLimitedIoPs
        + ", lowerLimitedIOPS=" + lowerLimitedIoPs + ", lowerLimitedThroughput="
        + lowerLimitedThroughput
        + ", upperLimitedThroughput=" + upperLimitedThroughput + ", startTime='" + startTime + '\''
        + ", endTime='" + endTime + '\'' + ", limitType='" + limitType + '\'' + '}';
  }
}
