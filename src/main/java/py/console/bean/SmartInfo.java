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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class SmartInfo {

  @ApiModelProperty(value = "id")
  private String id;
  @ApiModelProperty(value = "英文名")
  private String attributeNameEn;
  @ApiModelProperty(value = "中文名")
  private String attributeNameCn;
  @ApiModelProperty(value = "标记")
  private String flag;
  @ApiModelProperty(value = "当前值")
  private String value;
  @ApiModelProperty(value = "最差值")
  private String worst;
  @ApiModelProperty(value = "临界值")
  private String thresh;
  @ApiModelProperty(value = "类型")
  private String type;
  @ApiModelProperty(value = "更新频率")
  private String updated;
  @ApiModelProperty(value = "WHEN_FAILED")
  private String whenFailed;
  @ApiModelProperty(value = "原始值")
  private String rawValue;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAttributeNameEn() {
    return attributeNameEn;
  }

  public void setAttributeNameEn(String attributeNameEn) {
    this.attributeNameEn = attributeNameEn;
  }

  public String getAttributeNameCn() {
    return attributeNameCn;
  }

  public void setAttributeNameCn(String attributeNameCn) {
    this.attributeNameCn = attributeNameCn;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getWorst() {
    return worst;
  }

  public void setWorst(String worst) {
    this.worst = worst;
  }

  public String getThresh() {
    return thresh;
  }

  public void setThresh(String thresh) {
    this.thresh = thresh;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUpdated() {
    return updated;
  }

  public void setUpdated(String updated) {
    this.updated = updated;
  }

  public String getWhenFailed() {
    return whenFailed;
  }

  public void setWhenFailed(String whenFailed) {
    this.whenFailed = whenFailed;
  }

  public String getRawValue() {
    return rawValue;
  }

  public void setRawValue(String rawValue) {
    this.rawValue = rawValue;
  }

  @Override
  public String toString() {
    return "SmartInfo{" + "id='" + id + '\'' + ", attributeName_EN='" + attributeNameEn + '\''
        + ", attributeName_CN='" + attributeNameCn + '\'' + ", flag='" + flag + '\'' + ", value='"
        + value
        + '\'' + ", worst='" + worst + '\'' + ", thresh='" + thresh + '\'' + ", type='" + type
        + '\''
        + ", updated='" + updated + '\'' + ", whenFailed='" + whenFailed + '\'' + ", rawValue='"
        + rawValue
        + '\'' + '}';
  }
}
