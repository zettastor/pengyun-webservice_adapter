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