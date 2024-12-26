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
