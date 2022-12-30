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
