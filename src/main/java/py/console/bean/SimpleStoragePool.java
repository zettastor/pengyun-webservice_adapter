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
import java.util.Map;
import java.util.Set;

/**
 * SimpleStoragePool.
 */
@ApiModel
public class SimpleStoragePool extends BaseStoragePool {

  @ApiModelProperty(value = "磁盘信息集合")
  private Map<SimpleInstance, Set<SimpleArchiveMetadata>> archivesInDatanode;
  @ApiModelProperty(value = "卷id集合")
  private Set<String> volumeIds;

  public Map<SimpleInstance, Set<SimpleArchiveMetadata>> getArchivesInDatanode() {
    return archivesInDatanode;
  }

  public void setArchivesInDatanode(
      Map<SimpleInstance, Set<SimpleArchiveMetadata>> archivesInDatanode) {
    this.archivesInDatanode = archivesInDatanode;
  }

  public Set<String> getVolumeIds() {
    return volumeIds;
  }

  public void setVolumeIds(Set<String> volumeIds) {
    this.volumeIds = volumeIds;
  }


  @Override
  public String toString() {
    return "SimpleStoragePool{"
        + "archivesInDatanode=" + archivesInDatanode
        + ", volumeIds=" + volumeIds + "super" + super.toString()
        + '}';
  }
}
