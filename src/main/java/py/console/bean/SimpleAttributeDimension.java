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
