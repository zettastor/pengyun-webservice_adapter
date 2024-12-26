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
 * SimplePerformanceItemWithCustomizedName.
 */
public class SimplePerformanceItemWithCustomizedName {

  private String beanName2CustomizedNameMapperId;
  private String customizedName;
  private String range;
  private String unitOfMeasurement;

  public String getBeanName2CustomizedNameMapperId() {
    return beanName2CustomizedNameMapperId;
  }

  public void setBeanName2CustomizedNameMapperId(String beanName2CustomizedNameMapperId) {
    this.beanName2CustomizedNameMapperId = beanName2CustomizedNameMapperId;
  }

  public String getCustomizedName() {
    return customizedName;
  }

  public void setCustomizedName(String customizedName) {
    this.customizedName = customizedName;
  }

  public String getRange() {
    return range;
  }

  public void setRange(String range) {
    this.range = range;
  }

  public String getUnitOfMeasurement() {
    return unitOfMeasurement;
  }

  public void setUnitOfMeasurement(String unitOfMeasurement) {
    this.unitOfMeasurement = unitOfMeasurement;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((beanName2CustomizedNameMapperId == null) ? 0
        : beanName2CustomizedNameMapperId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    SimplePerformanceItemWithCustomizedName other = (SimplePerformanceItemWithCustomizedName) obj;
    if (beanName2CustomizedNameMapperId == null) {
      if (other.beanName2CustomizedNameMapperId != null) {
        return false;
      }
    } else if (!beanName2CustomizedNameMapperId.equals(other.beanName2CustomizedNameMapperId)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SimplePerformanceItemWithCustomizedName [beanName2CustomizedNameMapperId="
        + beanName2CustomizedNameMapperId + ", customizedName=" + customizedName + ", range="
        + range
        + ", unitOfMeasurement=" + unitOfMeasurement + "]";
  }

}
