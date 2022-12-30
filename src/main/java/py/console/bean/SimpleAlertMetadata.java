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
 * SimpleAlertMetadata.
 */
public class SimpleAlertMetadata {

  private String id;
  private String alertObject;
  private String ipAddress;
  private String description;
  private long firstOccurTime;
  private long lastOccurTime;
  private int frequency;
  private String alertLevel;
  private String alertKey;
  private String acknowledge;
  private String alertClear;
  private String clearTime;
  private String alertClass;
  private boolean isDiscardTimeFiler;
  private String alertRuleName;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAlertObject() {
    return alertObject;
  }

  public void setAlertObject(String alertObject) {
    this.alertObject = alertObject;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getFirstOccurTime() {
    return firstOccurTime;
  }

  public void setFirstOccurTime(long firstOccurTime) {
    this.firstOccurTime = firstOccurTime;
  }

  public long getLastOccurTime() {
    return lastOccurTime;
  }

  public void setLastOccurTime(long lastOccurTime) {
    this.lastOccurTime = lastOccurTime;
  }

  public int getFrequency() {
    return frequency;
  }

  public void setFrequency(int frequency) {
    this.frequency = frequency;
  }

  public String getAlertLevel() {
    return alertLevel;
  }

  public void setAlertLevel(String alertLevel) {
    this.alertLevel = alertLevel;
  }

  public String getAlertKey() {
    return alertKey;
  }

  public void setAlertKey(String alertKey) {
    this.alertKey = alertKey;
  }

  public String getAcknowledge() {
    return acknowledge;
  }

  public void setAcknowledge(String acknowledge) {
    this.acknowledge = acknowledge;
  }

  public String getAlertClass() {
    return alertClass;
  }

  public void setAlertClass(String alertClass) {
    this.alertClass = alertClass;
  }

  public boolean isDiscardTimeFiler() {
    return isDiscardTimeFiler;
  }

  public void setDiscardTimeFiler(boolean isDiscardTimeFiler) {
    this.isDiscardTimeFiler = isDiscardTimeFiler;
  }

  public String getAlertRuleName() {
    return alertRuleName;
  }

  public void setAlertRuleName(String alertRuleName) {
    this.alertRuleName = alertRuleName;
  }

  public String getAlertClear() {
    return alertClear;
  }

  public void setAlertClear(String alertClear) {
    this.alertClear = alertClear;
  }

  public String getClearTime() {
    return clearTime;
  }

  public void setClearTime(String clearTime) {
    this.clearTime = clearTime;
  }

  @Override
  public String toString() {
    return "SimpleAlertMetadata{"
        + "id='" + id + '\''
        + ", alertObject='" + alertObject + '\''
        + ", ipAddress='" + ipAddress + '\''
        + ", description='" + description + '\''
        + ", firstOccurTime=" + firstOccurTime
        + ", lastOccurTime=" + lastOccurTime
        + ", frequency=" + frequency
        + ", alertLevel='" + alertLevel + '\''
        + ", alertKey='" + alertKey + '\''
        + ", acknowledge='" + acknowledge + '\''
        + ", alertClear='" + alertClear + '\''
        + ", clearTime='" + clearTime + '\''
        + ", alertClass='" + alertClass + '\''
        + ", isDiscardTimeFiler=" + isDiscardTimeFiler
        + ", alertRuleName='" + alertRuleName + '\''
        + '}';
  }
}
