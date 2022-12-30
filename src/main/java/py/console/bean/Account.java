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
import java.util.List;
import java.util.Map;

/**
 * Account.
 */
@ApiModel
public class Account {

  @ApiModelProperty(value = "用户id")
  private String accountId;
  @ApiModelProperty(value = "用户名")
  private String accountName;
  @ApiModelProperty(value = "用户密码")
  private String password;
  @ApiModelProperty(value = "用户类型(SuperAdmin,Admin,Regular)")
  private String accountType;
  @ApiModelProperty(value = "已弃用", hidden = true)
  private String department;
  @ApiModelProperty(value = "已弃用", hidden = true)
  private String remark;
  @ApiModelProperty(value = "用户角色信息")
  private List<Role> roles;
  @ApiModelProperty(value = "用户可用资源信息")
  private Map<String, List<Resource>> resources;

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public Map<String, List<Resource>> getResources() {
    return resources;
  }

  public void setResources(Map<String, List<Resource>> resources) {
    this.resources = resources;
  }


  @Override
  public String toString() {
    return "Account{" + "accountId='" + accountId + '\'' + ", accountName='" + accountName + '\''
        + ", password='"
        + password + '\'' + ", accountType='" + accountType + '\'' + ", department='" + department
        + '\''
        + ", remark='" + remark + '\'' + ", roles=" + roles + ", resources=" + resources + '}';
  }
}
