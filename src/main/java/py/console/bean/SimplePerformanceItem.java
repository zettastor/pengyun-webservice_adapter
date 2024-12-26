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