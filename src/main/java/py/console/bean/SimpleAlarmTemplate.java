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

/**
 * SimpleAlarmTemplate.
 */
public class SimpleAlarmTemplate {

  public String id;
  public String name;
  public String level;
  public List<String> subscribers;
  public SimpleAlarmRule rule;
  public String description;
  public String advice;

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

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public List<String> getSubscribers() {
    return subscribers;
  }

  public void setSubscribers(List<String> subscribers) {
    this.subscribers = subscribers;
  }

  public SimpleAlarmRule getRule() {
    return rule;
  }

  public void setRule(SimpleAlarmRule rule) {
    this.rule = rule;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAdvice() {
    return advice;
  }

  public void setAdvice(String advice) {
    this.advice = advice;
  }

  @Override
  public String toString() {
    return "SimpleAlarmTemplate [id=" + id + ", name=" + name + ", level=" + level
        + ", subscribers=" + subscribers
        + ", rule=" + rule + ", description=" + description + ", advice=" + advice + "]";
  }

}
