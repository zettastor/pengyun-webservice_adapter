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
