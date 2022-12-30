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

/**
 * ZtreeDataFormat.
 */
public class ZtreeDataFormat {

  private String id;
  private String pid;
  private String name;
  private Object info;
  private boolean nocheck = true;
  private boolean isLeaf = false;
  private boolean open = false;
  private boolean isAdd = false;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Object getInfo() {
    return info;
  }

  public void setInfo(Object info) {
    this.info = info;
  }

  public boolean isNocheck() {
    return nocheck;
  }

  public void setNocheck(boolean nocheck) {
    this.nocheck = nocheck;
  }

  public boolean isLeaf() {
    return isLeaf;
  }

  public void setLeaf(boolean leaf) {
    isLeaf = leaf;
  }

  public boolean isOpen() {
    return open;
  }

  public void setOpen(boolean open) {
    this.open = open;
  }

  public boolean isAdd() {
    return isAdd;
  }

  public void setAdd(boolean add) {
    isAdd = add;
  }

  /**
   * Ztree Data Format.
   *
   * @param pid pid
   * @param id id
   * @param name name
   */
  public ZtreeDataFormat(String pid, String id, String name) {
    this.setId(id);
    this.setPid(pid);
    this.setName(name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ZtreeDataFormat that = (ZtreeDataFormat) o;

    if (nocheck != that.nocheck) {
      return false;
    }
    if (isLeaf != that.isLeaf) {
      return false;
    }
    if (open != that.open) {
      return false;
    }
    if (isAdd != that.isAdd) {
      return false;
    }
    if (!id.equals(that.id)) {
      return false;
    }
    if (!pid.equals(that.pid)) {
      return false;
    }
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + pid.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + (nocheck ? 1 : 0);
    result = 31 * result + (isLeaf ? 1 : 0);
    result = 31 * result + (open ? 1 : 0);
    result = 31 * result + (isAdd ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ZtreeDataFormat{" + "id='" + id + '\'' + ", pId='" + pid + '\'' + ", name='" + name
        + '\'' + ", info="
        + info + ", nocheck=" + nocheck + ", isLeaf=" + isLeaf + '}';
  }
}
