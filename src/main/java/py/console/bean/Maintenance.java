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
public class Maintenance {

  @ApiModelProperty(value = "开始时间")
  private String startTime;
  @ApiModelProperty(value = "结束时间")
  private String endTime;
  @ApiModelProperty(value = "")
  private String duration;
  @ApiModelProperty(value = "已弃用", hidden = true)
  private String message;
  @ApiModelProperty(value = "当前时间")
  private String currentTime;

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

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCurrentTime() {
    return currentTime;
  }

  public void setCurrentTime(String currentTime) {
    this.currentTime = currentTime;
  }

  @Override
  public String toString() {
    return "Maintenance{" + "startTime='" + startTime + '\'' + ", endTime='" + endTime + '\''
        + ", duration='"
        + duration + '\'' + ", message='" + message + '\'' + ", currentTime='" + currentTime + '\''
        + '}';
  }
}
