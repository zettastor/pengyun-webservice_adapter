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
 * SimpleFinalAlarm.
 */
public class SimpleFinalAlarm {

  private String id;
  private String templateId;
  private String name;
  private String level;
  private String description;
  private String alarmObject;
  private long alarmTime;


  public String getAlarmObject() {
    return alarmObject;
  }

  public void setAlarmObject(String alarmObject) {
    this.alarmObject = alarmObject;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTemplateId() {
    return templateId;
  }

  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }

  public String getName() {
    return name;
  }

  public long getAlarmTime() {
    return alarmTime;
  }

  public void setAlarmTime(long alarmTime) {
    this.alarmTime = alarmTime;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "SimpleFinalAlarm [id=" + id + ", templateId=" + templateId + ", name=" + name
        + ", level=" + level
        + ", description=" + description + ", alarmObject=" + alarmObject + ", alarmTime="
        + alarmTime + "]";
  }


}
