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
 * SimplePerformanceItem.
 */
public class SimplePerformanceItem {

  private String endPoint;
  private Long itemId;
  private String itemName;
  private String resourceType;
  private String range;
  private String unitOfMeasurement;

  /**
   * Simple Performance Item.
   *
   * @param endPoint end point
   * @param itemId item id
   */
  public SimplePerformanceItem(String endPoint, Long itemId) {
    super();
    this.endPoint = endPoint;
    this.itemId = itemId;
  }

  public SimplePerformanceItem() {
    super();
  }

  public String getEndPoint() {
    return endPoint;
  }

  public void setEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }

  public Long getItemId() {
    return this.itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getResourceType() {
    return resourceType;
  }

  public void setResourceType(String resourceType) {
    this.resourceType = resourceType;
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
  public String toString() {
    return "SimplePerformanceItem [endPoint=" + endPoint + ", itemId=" + itemId + ", itemName="
        + itemName
        + ", resourceType=" + resourceType + ", range=" + range + ", unitOfMeasurement="
        + unitOfMeasurement
        + "]";
  }

}