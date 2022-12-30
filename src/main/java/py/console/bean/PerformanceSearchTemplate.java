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
 * PerformanceSearchTemplate.
 */
public class PerformanceSearchTemplate {

  private String id;
  private String name;
  private String startTime;
  private String endTime;
  private String period;
  private String timeUnit;
  private String counterKeyJson;
  private String sourcesJson;
  private String accountId;
  private String objectType;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }

  public String getTimeUnit() {
    return timeUnit;
  }

  public void setTimeUnit(String timeUnit) {
    this.timeUnit = timeUnit;
  }

  public String getCounterKeyJson() {
    return counterKeyJson;
  }

  public void setCounterKeyJson(String counterKeyJson) {
    this.counterKeyJson = counterKeyJson;
  }

  public String getSourcesJson() {
    return sourcesJson;
  }

  public void setSourcesJson(String sourcesJson) {
    this.sourcesJson = sourcesJson;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getObjectType() {
    return objectType;
  }

  public void setObjectType(String objectType) {
    this.objectType = objectType;
  }

  @Override
  public String toString() {
    return "PerformanceSearchTemplate{"
        + "id='" + id + '\''
        + ", name='" + name + '\''
        + ", startTime='" + startTime + '\''
        + ", endTime='" + endTime + '\''
        + ", period='" + period + '\''
        + ", timeUnit='" + timeUnit + '\''
        + ", counterKeyJson='" + counterKeyJson + '\''
        + ", sourcesJson='" + sourcesJson + '\''
        + ", accountId='" + accountId + '\''
        + ", objectType='" + objectType + '\''
        + '}';
  }
}
