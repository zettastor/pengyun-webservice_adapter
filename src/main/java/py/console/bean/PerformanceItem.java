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
