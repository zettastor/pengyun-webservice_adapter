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
public class SimpleSnapshot {

  @ApiModelProperty(value = "快照id")
  private int key;
  @ApiModelProperty(value = "快照名")
  private String name;
  @ApiModelProperty(value = "是否是根节点")
  private boolean isRoot;
  @ApiModelProperty(value = "快照描述信息")
  private String description;
  @ApiModelProperty(value = "父节点id")
  private int parent;
  @ApiModelProperty(value = "快照创建时间")
  private String createTime;
  @ApiModelProperty(value = "颜色")
  private String color;
  @ApiModelProperty(value = "快照容量，单位byte")
  private String size;

  public int getKey() {
    return key;
  }

  public void setKey(int key) {
    this.key = key;
  }

  public int getParent() {
    return parent;
  }

  public void setParent(int parent) {
    this.parent = parent;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isRoot() {
    return isRoot;
  }

  public void setRoot(boolean root) {
    isRoot = root;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  @Override
  public String toString() {
    return "SimpleSnapshot{" + "key=" + key + ", name='" + name + '\'' + ", isRoot=" + isRoot
        + ", description='"
        + description + '\'' + ", parent=" + parent + ", createTime='" + createTime + '\''
        + ", color='" + color
        + '\'' + ", size='" + size + '\'' + '}';
  }
}
