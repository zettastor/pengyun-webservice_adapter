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

@ApiModel
public class Volume2Snapshot {

  @ApiModelProperty(value = "卷信息")
  SimpleVolumeMetadata volume;
  @ApiModelProperty(value = "快照信息")
  List<SimpleSnapshot> snapshotList;

  public SimpleVolumeMetadata getVolume() {
    return volume;
  }

  public void setVolume(SimpleVolumeMetadata volume) {
    this.volume = volume;
  }

  public List<SimpleSnapshot> getSnapshotList() {
    return snapshotList;
  }

  public void setSnapshotList(List<SimpleSnapshot> snapshotList) {
    this.snapshotList = snapshotList;
  }

  @Override
  public String toString() {
    return "Volume2Snapshot{" + "volume=" + volume + ", snapshotList=" + snapshotList + '}';
  }
}
