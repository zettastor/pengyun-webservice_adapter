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

import java.util.UUID;

/**
 * SimpleAttributeDimension.
 */
public class SimpleAttributeDimension {

  public UUID beanName2CustomizedNameMapperId;
  public String expression;
  public long times;

  public String getExpression() {
    return expression;
  }

  public void setExpression(String expression) {
    this.expression = expression;
  }

  public long getTimes() {
    return times;
  }

  public void setTimes(long times) {
    this.times = times;
  }

  public UUID getBeanName2CustomizedNameMapperId() {
    return beanName2CustomizedNameMapperId;
  }

  public void setBeanName2CustomizedNameMapperId(UUID beanName2CustomizedNameMapperId) {
    this.beanName2CustomizedNameMapperId = beanName2CustomizedNameMapperId;
  }

  @Override
  public String toString() {
    return "SimpleAttributeDimension [beanName2CustomizedNameMapperId="
        + beanName2CustomizedNameMapperId
        + ", expression=" + expression + ", times=" + times + "]";
  }

}
