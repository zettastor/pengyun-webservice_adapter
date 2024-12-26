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

package py.console.service.access.rule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * VolumeAccessRuleOperationResult.
 */
@ApiModel
public class VolumeAccessRuleOperationResult {

  @ApiModelProperty(value = "信息")
  private String message;
  @ApiModelProperty(value = "已应用操作")
  private List<Long> existingActionOnRuleList = new ArrayList<Long>();
  @ApiModelProperty(value = "已应用卷信息")
  private Map<String, List<String>> appliedRule2VolumeTable = new HashMap<String, List<String>>();

  public List<Long> getExistingActionOnRuleList() {
    return existingActionOnRuleList;
  }

  public void setExistingActionOnRuleList(List<Long> existingActionOnRuleList) {
    this.existingActionOnRuleList = existingActionOnRuleList;
  }

  public Map<String, List<String>> getAppliedRule2VolumeTable() {
    return appliedRule2VolumeTable;
  }

  public void setAppliedRule2VolumeTable(Map<String, List<String>> appliedRule2VolumeTable) {
    this.appliedRule2VolumeTable = appliedRule2VolumeTable;
  }

  /**
   * add to existing action on rule list.
   *
   * @param ruleIdToAdd rule id to add
   */
  public void addToExistingActionOnRuleList(long ruleIdToAdd) {
    if (existingActionOnRuleList == null) {
      existingActionOnRuleList = new ArrayList<Long>();
    }

    existingActionOnRuleList.add(ruleIdToAdd);
  }

  /**
   * put to applied rule to volume table.
   *
   * @param ruleIdToPut rule id to put
   * @param volumeListToPut volume list to put
   */
  public void putToAppliedRule2VolumeTable(String ruleIdToPut, List<String> volumeListToPut) {
    if (appliedRule2VolumeTable == null) {
      appliedRule2VolumeTable = new HashMap<String, List<String>>();
    }

    appliedRule2VolumeTable.put(ruleIdToPut, volumeListToPut);
  }

  /**
   * add to applied rule to volume table.
   *
   * @param ruleIdToAdd rule id to add
   * @param volumeIdToAdd volume id to add
   */
  public void addToAppliedRule2VolumeTable(String ruleIdToAdd, String volumeIdToAdd) {
    if (appliedRule2VolumeTable == null) {
      appliedRule2VolumeTable = new HashMap<String, List<String>>();
    }

    List<String> volumeList = appliedRule2VolumeTable.get(volumeIdToAdd);
    if (volumeList == null) {
      volumeList = new ArrayList<String>();
      appliedRule2VolumeTable.put(ruleIdToAdd, volumeList);
    }

    volumeList.add(volumeIdToAdd);
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "VolumeAccessRuleOperationResult [message=" + message + ", existingActionOnRuleList="
        + existingActionOnRuleList + ", appliedRule2VolumeTable=" + appliedRule2VolumeTable + "]";
  }

}
