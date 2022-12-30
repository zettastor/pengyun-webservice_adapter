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

@ApiModel
public class Role {

  @ApiModelProperty(value = "角色id")
  private String roleId;
  @ApiModelProperty(value = "角色名称")
  private String name;
  @ApiModelProperty(value = "描述")
  private String description;
  @ApiModelProperty(value = "角色权限")
  private Map<String, List<ApiToAuthorize>> permissions;

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Map<String, List<ApiToAuthorize>> getPermissions() {
    return permissions;
  }

  public void setPermissions(Map<String, List<ApiToAuthorize>> permissions) {
    this.permissions = permissions;
  }

  @Override
  public String toString() {
    return "Role{" + "roleId='" + roleId + '\'' + ", name='" + name + '\'' + ", description='"
        + description + '\''
        + ", permissions=" + permissions + '}';
  }
}
