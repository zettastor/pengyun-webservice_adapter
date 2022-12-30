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
