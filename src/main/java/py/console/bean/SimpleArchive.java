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
 * SimpleArchive.
 */
public class SimpleArchive {

  private String datanodeId;
  private String datanodeHost;
  private String diskName;
  private int primaryCount;
  private int secondCount;
  private int abiterCount;

  public String getDatanodeId() {
    return datanodeId;
  }

  public void setDatanodeId(String datanodeId) {
    this.datanodeId = datanodeId;
  }

  public String getDatanodeHost() {
    return datanodeHost;
  }

  public void setDatanodeHost(String datanodeHost) {
    this.datanodeHost = datanodeHost;
  }

  public String getDiskName() {
    return diskName;
  }

  public void setDiskName(String diskName) {
    this.diskName = diskName;
  }

  public int getPrimaryCount() {
    return primaryCount;
  }

  public void setPrimaryCount(int primaryCount) {
    this.primaryCount = primaryCount;
  }

  public int getSecondCount() {
    return secondCount;
  }

  public void setSecondCount(int secondCount) {
    this.secondCount = secondCount;
  }

  public int getAbiterCount() {
    return abiterCount;
  }

  public void setAbiterCount(int abiterCount) {
    this.abiterCount = abiterCount;
  }

  @Override
  public String toString() {
    return "SimpleArchive [datanodeId=" + datanodeId + ", datanodeHost=" + datanodeHost
        + ", diskName=" + diskName
        + ", primaryCount=" + primaryCount + ", secondCount=" + secondCount + ", abiterCount="
        + abiterCount
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((datanodeHost == null) ? 0 : datanodeHost.hashCode());
    result = prime * result + ((datanodeId == null) ? 0 : datanodeId.hashCode());
    result = prime * result + ((diskName == null) ? 0 : diskName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    SimpleArchive other = (SimpleArchive) obj;
    if (datanodeHost == null) {
      if (other.datanodeHost != null) {
        return false;
      }
    } else if (!datanodeHost.equals(other.datanodeHost)) {
      return false;
    }
    if (datanodeId == null) {
      if (other.datanodeId != null) {
        return false;
      }
    } else if (!datanodeId.equals(other.datanodeId)) {
      return false;
    }
    if (diskName == null) {
      if (other.diskName != null) {
        return false;
      }
    } else if (!diskName.equals(other.diskName)) {
      return false;
    }
    return true;
  }


}
