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