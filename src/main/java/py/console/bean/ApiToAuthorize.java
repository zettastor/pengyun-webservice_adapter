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
public class ApiToAuthorize {

  @ApiModelProperty(value = "权限名")
  private String apiName;
  @ApiModelProperty(value = "权限所属类别")
  private String category;
  @ApiModelProperty(value = "中文描述")
  private String chineseText;
  @ApiModelProperty(value = "英文描述")
  private String englishText;

  public String getApiName() {
    return apiName;
  }

  public void setApiName(String apiName) {
    this.apiName = apiName;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getChineseText() {
    return chineseText;
  }

  public void setChineseText(String chineseText) {
    this.chineseText = chineseText;
  }

  public String getEnglishText() {
    return englishText;
  }

  public void setEnglishText(String englishText) {
    this.englishText = englishText;
  }

  @Override
  public String toString() {
    return "APIToAuthorize{" + "apiName='" + apiName + '\'' + ", category='" + category + '\''
        + ", chineseText='"
        + chineseText + '\'' + ", englishText='" + englishText + '\'' + '}';
  }
}