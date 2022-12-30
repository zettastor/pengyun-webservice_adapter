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
