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
