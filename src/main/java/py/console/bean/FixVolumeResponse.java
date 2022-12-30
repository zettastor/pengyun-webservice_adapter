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

import java.util.List;

/**
 * FixVolumeResponse.
 */
public class FixVolumeResponse {

  public boolean needFixVolume;
  public boolean fixVolumeCompletely;
  public List<SimpleInstance> lostDatanodes;

  public boolean isNeedFixVolume() {
    return needFixVolume;
  }

  public void setNeedFixVolume(boolean needFixVolume) {
    this.needFixVolume = needFixVolume;
  }

  public boolean isFixVolumeCompletely() {
    return fixVolumeCompletely;
  }

  public void setFixVolumeCompletely(boolean fixVolumeCompletely) {
    this.fixVolumeCompletely = fixVolumeCompletely;
  }

  public List<SimpleInstance> getLostDatanodes() {
    return lostDatanodes;
  }

  public void setLostDatanodes(List<SimpleInstance> lostDatanodes) {
    this.lostDatanodes = lostDatanodes;
  }

  @Override
  public String toString() {
    return "FixVolumeResponse [needFixVolume=" + needFixVolume + ", fixVolumeCompletely="
        + fixVolumeCompletely
        + ", lostDatanodes=" + lostDatanodes + "]";
  }


}
