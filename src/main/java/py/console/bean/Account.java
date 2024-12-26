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
