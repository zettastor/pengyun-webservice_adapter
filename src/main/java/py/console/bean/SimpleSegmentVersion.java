
package py.console.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * SimpleSegmentVersion.
 */
@ApiModel
public class SimpleSegmentVersion {

  @ApiModelProperty(value = "Membership大版本")
  private int epoch;
  @ApiModelProperty(value = "Membership小版本")
  private int generation;

  public int getEpoch() {
    return epoch;
  }

  public void setEpoch(int epoch) {
    this.epoch = epoch;
  }

  public int getGeneration() {
    return generation;
  }

  public void setGeneration(int generation) {
    this.generation = generation;
  }

}
