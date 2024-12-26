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
 * SimpleAlarm.
 */
public class SimpleAlarm {

  public String alarmObject;
  public String alarmName;
  public String timeStamp;
  public String alarmLevel;
  public String description;

  public String getAlarmObject() {
    return alarmObject;
  }

  public void setAlarmObject(String alarmObject) {
    this.alarmObject = alarmObject;
  }

  public String getAlarmName() {
    return alarmName;
  }

  public void setAlarmName(String alarmName) {
    this.alarmName = alarmName;
  }

  public String getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getAlarmLevel() {
    return alarmLevel;
  }

  public void setAlarmLevel(String alarmLevel) {
    this.alarmLevel = alarmLevel;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "SimpleAlarm [alarmObject=" + alarmObject + ", alarmName=" + alarmName + ", timeStamp="
        + timeStamp
        + ", alarmLevel=" + alarmLevel + ", description=" + description + "]";
  }

}
