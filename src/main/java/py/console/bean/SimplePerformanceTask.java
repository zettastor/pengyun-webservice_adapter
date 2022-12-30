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

import java.util.List;
import java.util.Map;
import java.util.Set;
import py.monitor.task.TimeSpan;
import py.thrift.systemmonitor.service.ResourceIdentifier;

/**
 * SimplePerformanceTask.
 */
public class SimplePerformanceTask {

  private String taskId;
  private String taskName;
  private long period;
  private List<TimeSpan> runTime;
  private Map<ResourceIdentifier, Set<String>> attributes;
  private String taskDescription;
  private String taskStatus;

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public long getPeriod() {
    return period;
  }

  public void setPeriod(long period) {
    this.period = period;
  }

  public List<TimeSpan> getRunTime() {
    return runTime;
  }

  public void setRunTime(List<TimeSpan> runTime) {
    this.runTime = runTime;
  }

  public Map<ResourceIdentifier, Set<String>> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<ResourceIdentifier, Set<String>> attributes) {
    this.attributes = attributes;
  }

  public String getTaskDescription() {
    return taskDescription;
  }

  public void setTaskDescription(String taskDescription) {
    this.taskDescription = taskDescription;
  }


  @Override
  public String toString() {
    return "SimplePerformanceTask [taskId=" + taskId + ", taskName=" + taskName + ", period="
        + period
        + ", runTime=" + runTime + ", attributes=" + attributes + ", taskDescription="
        + taskDescription
        + ", taskStatus=" + taskStatus + "]";
  }

  public String getTaskStatus() {
    return taskStatus;
  }

  public void setTaskStatus(String taskStatus) {
    this.taskStatus = taskStatus;
  }

}