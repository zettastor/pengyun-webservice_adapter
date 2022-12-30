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
