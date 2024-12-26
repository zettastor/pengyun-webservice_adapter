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
 * this bean contains system capacity property.
 *
 */
public class Capacity {

  private String message;

  private String totalCapacity;

  private String availableCapacity;

  private String usedCapacity;

  private String freeSpace;

  //percentage of available space in total space
  private String availableCapacityPer;

  private String usedCapacityPer;

  public String getTotalCapacity() {
    return totalCapacity;
  }

  public void setTotalCapacity(String totalCapacity) {
    this.totalCapacity = totalCapacity;
  }

  public String getAvailableCapacity() {
    return availableCapacity;
  }

  public void setAvailableCapacity(String availableCapacity) {
    this.availableCapacity = availableCapacity;
  }

  public String getUsedCapacity() {
    return usedCapacity;
  }

  public void setUsedCapacity(String usedCapacity) {
    this.usedCapacity = usedCapacity;
  }

  public String getAvailableCapacityPer() {
    return availableCapacityPer;
  }

  public void setAvailableCapacityPer(String availableCapacityPer) {
    this.availableCapacityPer = availableCapacityPer;
  }

  public String getUsedCapacityPer() {
    return usedCapacityPer;
  }

  public void setUsedCapacityPer(String usedCapacityPer) {
    this.usedCapacityPer = usedCapacityPer;
  }

  public String getFreeSpace() {
    return freeSpace;
  }

  public void setFreeSpace(String freeSpace) {
    this.freeSpace = freeSpace;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


}
