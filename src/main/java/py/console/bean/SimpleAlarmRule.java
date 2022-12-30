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

import java.util.Set;

/**
 * SimpleAlarmRule.
 */
public class SimpleAlarmRule {

  public Set<SimpleAttributeDimension> times;
  public long timeWindow;
  public long duration;
  public String expression;

  public Set<SimpleAttributeDimension> getTimes() {
    return times;
  }

  public void setTimes(Set<SimpleAttributeDimension> times) {
    this.times = times;
  }

  public long getTimeWindow() {
    return timeWindow;
  }

  public void setTimeWindow(long timeWindow) {
    this.timeWindow = timeWindow;
  }

  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public String getExpression() {
    return expression;
  }

  public void setExpression(String expression) {
    this.expression = expression;
  }

  @Override
  public String toString() {
    return "SimpleAlarmRule [times=" + times + ", timeWindow=" + timeWindow + ", duration="
        + duration
        + ", expression=" + expression + "]";
  }

}
