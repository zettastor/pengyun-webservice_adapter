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

/**
 * SimpleOperation.
 */
@ApiModel
public class SimpleOperation {

  @ApiModelProperty(value = "操作id")
  private String operationId;
  @ApiModelProperty(value = "目标id")
  private String targetId;
  /*    private String targetObject;*/
  @ApiModelProperty(value = "目标名称")
  private String targetName;
  @ApiModelProperty(value = "开始时间")
  private String startTime;
  @ApiModelProperty(value = "结束时间")
  private String endTime;
  @ApiModelProperty(value = " 描述")
  private String description;
  @ApiModelProperty(value = "状态(SUCCESS:成功，FAILED:失败)")
  private String status;
  @ApiModelProperty(value = "进程")
  private String progress;
  @ApiModelProperty(value = "错误信息")
  private String errorMessage;
  @ApiModelProperty(value = "用户id")
  private String accountId;
  @ApiModelProperty(value = "操作类型")
  private String type;
  @ApiModelProperty(value = "用户名")
  private String accountName;
  @ApiModelProperty(value = "目标类型")
  private String targetType;
  @ApiModelProperty(value = "操作对象")
  private String operationObject;

  public String getOperationId() {
    return operationId;
  }

  public void setOperationId(String operationId) {
    this.operationId = operationId;
  }

  public String getTargetId() {
    return targetId;
  }

  public void setTargetId(String targetId) {
    this.targetId = targetId;
  }


  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getProgress() {
    return progress;
  }

  public void setProgress(String progress) {
    this.progress = progress;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTargetName() {
    return targetName;
  }

  public void setTargetName(String targetName) {
    this.targetName = targetName;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public String getTargetType() {
    return targetType;
  }

  public void setTargetType(String targetType) {
    this.targetType = targetType;
  }

  public String getOperationObject() {
    return operationObject;
  }

  public void setOperationObject(String operationObject) {
    this.operationObject = operationObject;
  }

  @Override
  public String toString() {
    return "SimpleOperation{"
        + "operationId='" + operationId + '\''
        + ", targetId='" + targetId + '\''
        + ", targetName='" + targetName + '\''
        + ", startTime='" + startTime + '\''
        + ", endTime='" + endTime + '\''
        + ", description='" + description + '\''
        + ", status='" + status + '\''
        + ", progress='" + progress + '\''
        + ", errorMessage='" + errorMessage + '\''
        + ", accountId='" + accountId + '\''
        + ", type='" + type + '\''
        + ", accountName='" + accountName + '\''
        + ", targetType='" + targetType + '\''
        + ", operationObject='" + operationObject + '\''
        + '}';
  }
}
