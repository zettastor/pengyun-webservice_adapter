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
 * SimpleVolume2AccessRuleRelationship.
 */
public class SimpleVolume2AccessRuleRelationship {

  private String message;

  private String volumeId;

  private SimpleVolumeAccessRule simpleVolumeAccessRule;

  private boolean isApplied;

  public String getVolumeId() {
    return volumeId;
  }

  public void setVolumeId(String volumeId) {
    this.volumeId = volumeId;
  }

  public SimpleVolumeAccessRule getSimpleVolumeAccessRule() {
    return simpleVolumeAccessRule;
  }

  public void setSimpleVolumeAccessRule(SimpleVolumeAccessRule simpleVolumeAccessRule) {
    this.simpleVolumeAccessRule = simpleVolumeAccessRule;
  }

  public boolean getIsApplied() {
    return isApplied;
  }

  public void setIsApplied(boolean isApplied) {
    this.isApplied = isApplied;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * to string.
   *
   * @return string
   */
  public String toString() {
    return "SimpleVolume2AccessRuleRelationship[volumeId: " + volumeId
        + ", simpleVolumeAccessRule: "
        + simpleVolumeAccessRule + ", isApplied: " + isApplied + "]";
  }
}
