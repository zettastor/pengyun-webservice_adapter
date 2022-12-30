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
