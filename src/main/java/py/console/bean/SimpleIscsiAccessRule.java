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
public class SimpleIscsiAccessRule {

  @ApiModelProperty(value = "驱动id")
  private String ruleId;
  @ApiModelProperty(value = "规则名称")
  private String ruleNotes;
  @ApiModelProperty(value = "initiator名称")
  private String initiatorName;
  @ApiModelProperty(value = "incoming用户")
  private String user;
  @ApiModelProperty(value = "ncoming用户密码")
  private String passwd;
  @ApiModelProperty(value = "outgoing用户")
  private String outUser;
  @ApiModelProperty(value = "outgoing用户密码")
  private String outPasswd;
  @ApiModelProperty(value = "读写权限(READ:只读,READWRITE:读写)")
  private String permission;
  @ApiModelProperty(value = "客户机状态")
  private String status;
  @ApiModelProperty(value = "是否已被应用")
  private boolean applied;

  public SimpleIscsiAccessRule() {
    this.applied = false;
  }

  public String getRuleId() {
    return ruleId;
  }

  public void setRuleId(String ruleId) {
    this.ruleId = ruleId;
  }

  public String getInitiatorName() {
    return initiatorName;
  }

  public void setInitiatorName(String initiatorName) {
    this.initiatorName = initiatorName;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPasswd() {
    return passwd;
  }

  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public boolean isApplied() {
    return applied;
  }

  public void setApplied(boolean applied) {
    this.applied = applied;
  }

  public String getOutUser() {
    return outUser;
  }

  public void setOutUser(String outUser) {
    this.outUser = outUser;
  }

  public String getOutPasswd() {
    return outPasswd;
  }

  public void setOutPasswd(String outPasswd) {
    this.outPasswd = outPasswd;
  }

  public String getRuleNotes() {
    return ruleNotes;
  }

  public void setRuleNotes(String ruleNotes) {
    this.ruleNotes = ruleNotes;
  }

  @Override
  public String toString() {
    return "SimpleIscsiAccessRule{" + "ruleId='" + ruleId + '\'' + ", ruleNotes='" + ruleNotes
        + '\''
        + ", initiatorName='" + initiatorName + '\'' + ", user='" + user + '\'' + ", passwd='"
        + passwd + '\''
        + ", outUser='" + outUser + '\'' + ", outPasswd='" + outPasswd + '\'' + ", permission='"
        + permission
        + '\'' + ", status='" + status + '\'' + ", applied=" + applied + '}';
  }
}
