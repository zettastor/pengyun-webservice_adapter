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
