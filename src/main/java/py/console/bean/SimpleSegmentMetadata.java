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

/**
 * SimpleSegmentMetadata.
 */
@ApiModel
public class SimpleSegmentMetadata implements Comparable<SimpleSegmentMetadata> {

  @ApiModelProperty(value = "segment id")
  private int segId;
  @ApiModelProperty(value = "segment　unit　大小")
  private int unitSize;
  @ApiModelProperty(value = "Segment版本")
  private SimpleSegmentVersion simpleSegmentVersion;
  @ApiModelProperty(value = "包含的 unit")
  private List<SimpleSegUnit> unitList;

  public SimpleSegmentMetadata() {

  }

  public int getUnitSize() {
    return unitSize;
  }

  public void setUnitSize(int unitSize) {
    this.unitSize = unitSize;
  }

  public int getSegId() {
    return segId;
  }

  public void setSegId(int segId) {
    this.segId = segId;
  }

  public List<SimpleSegUnit> getUnitList() {
    return unitList;
  }

  public void setUnitList(List<SimpleSegUnit> unitList) {
    this.unitList = unitList;
  }

  public SimpleSegmentVersion getSimpleSegmentVersion() {
    return simpleSegmentVersion;
  }

  public void setSimpleSegmentVersion(SimpleSegmentVersion simpleSegmentVersion) {
    this.simpleSegmentVersion = simpleSegmentVersion;
  }

  @Override
  public int compareTo(SimpleSegmentMetadata simpleSegmentMetadata) {
    return segId - simpleSegmentMetadata.getSegId();
  }

}
