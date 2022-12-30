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
