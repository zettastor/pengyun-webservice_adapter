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
