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
public class PerformanceItem {

  @ApiModelProperty(value = "键")
  private String key;
  @ApiModelProperty(value = "英文描述")
  private String enName;
  @ApiModelProperty(value = "中文描述")
  private String zhName;
  @ApiModelProperty(value = "性能项类型(Threshold:域值，Status:状态)")
  private String type;
  @ApiModelProperty(value = "性能类")
  private String monitorObject;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getEnName() {
    return enName;
  }

  public void setEnName(String enName) {
    this.enName = enName;
  }

  public String getZhName() {
    return zhName;
  }

  public void setZhName(String zhName) {
    this.zhName = zhName;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMonitorObject() {
    return monitorObject;
  }

  public void setMonitorObject(String monitorObject) {
    this.monitorObject = monitorObject;
  }

  @Override
  public String toString() {
    return "PerformanceItem{" + "key='" + key + '\'' + ", en_name='" + enName + '\''
        + ", zh_name='" + zhName
        + '\'' + ", type='" + type + '\'' + ", monitorObject='" + monitorObject + '\'' + '}';
  }
}
