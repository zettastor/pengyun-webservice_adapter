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

@ApiModel
public class DashboardInfo {

  //Capacity
  @ApiModelProperty(value = "系统容量：总容量")
  private String totalCapacity;
  @ApiModelProperty(value = "系统容量：可用容量")
  private String availableCapacity;
  @ApiModelProperty(value = "系统容量：已用容")
  private String usedCapacity;
  @ApiModelProperty(value = "系统容量:未分配容量")
  private String freeSpace;
  @ApiModelProperty(value = "系统容量：可用容量百分比")
  private String availableCapacityPer;
  @ApiModelProperty(value = "系统容量：已用容量百分比")
  private String usedCapacityPer;
  @ApiModelProperty(value = "系统容量：已使用容量百分比")
  public String theUsedUniPerStr;
  @ApiModelProperty(value = "系统容量：未使用容量百分比")
  public String theunUsedUniPerStr;
  @ApiModelProperty(value = "系统容量：已使用容量")
  public String theUsedUnitSpace;
  @ApiModelProperty(value = "系统容量：未使用容量")
  public String theunUsedUnitSpace;
  //Volume
  @ApiModelProperty(value = "卷：健康")
  private String okCounts;
  @ApiModelProperty(value = "用户名")
  private String degreeCounts;
  @ApiModelProperty(value = "卷：错误")
  private String unavailableCounts;

  //client
  @ApiModelProperty(value = "客户机：数量")
  private String clientTotal;
  @ApiModelProperty(value = "客户机：连接数")
  private String connectedClients;
  //Instance
  @ApiModelProperty(value = "服务：健康")
  private String serviceHealthy;
  @ApiModelProperty(value = "服务：错误")
  private String serviceSick;
  @ApiModelProperty(value = "服务：失败")
  private String serviceFailed;
  @ApiModelProperty(value = "服务：总数")
  private String serviceTotal;
  //Pool
  @ApiModelProperty(value = "存储池：健康")
  private String poolHigh;
  @ApiModelProperty(value = "存储池：亚健康")
  private String poolMiddle;
  @ApiModelProperty(value = "存储池：亚健康")
  private String poolLow;
  @ApiModelProperty(value = "存储池：总数")
  private String poolTotal;
  //Disk
  @ApiModelProperty(value = "磁盘：健康")
  private String goodDiskCount;
  @ApiModelProperty(value = "磁盘：错误")
  private String badDiskCount;
  @ApiModelProperty(value = "磁盘：总数")
  private String allDiskCount;
  //Alarm
  @ApiModelProperty(value = "告警:严重告警")
  private String criticalAlertCount;
  @ApiModelProperty(value = "告警：重要告警")
  private String majorAlertCount;
  @ApiModelProperty(value = "告警：次要告警")
  private String minorAlertCount;
  @ApiModelProperty(value = "告警:告警")
  private String warningAlertCount;
  @ApiModelProperty(value = "告警：已清除")
  private String clearedAlertCount;
  //Server
  @ApiModelProperty(value = "服务器：健康")
  private String okServerNodeCounts;
  @ApiModelProperty(value = "服务器：错误")
  private String unknownServerNodeCount;
  @ApiModelProperty(value = "服务器：总数")
  private String totalServerNodeCount;

  //error message
  @ApiModelProperty(value = "已弃用", hidden = true)
  private List<String> errorMessage;

  public String getTotalCapacity() {
    return totalCapacity;
  }

  public void setTotalCapacity(String totalCapacity) {
    this.totalCapacity = totalCapacity;
  }

  public String getAvailableCapacity() {
    return availableCapacity;
  }

  public void setAvailableCapacity(String availableCapacity) {
    this.availableCapacity = availableCapacity;
  }

  public String getUsedCapacity() {
    return usedCapacity;
  }

  public void setUsedCapacity(String usedCapacity) {
    this.usedCapacity = usedCapacity;
  }

  public String getFreeSpace() {
    return freeSpace;
  }

  public void setFreeSpace(String freeSpace) {
    this.freeSpace = freeSpace;
  }

  public String getAvailableCapacityPer() {
    return availableCapacityPer;
  }

  public void setAvailableCapacityPer(String availableCapacityPer) {
    this.availableCapacityPer = availableCapacityPer;
  }

  public String getUsedCapacityPer() {
    return usedCapacityPer;
  }

  public void setUsedCapacityPer(String usedCapacityPer) {
    this.usedCapacityPer = usedCapacityPer;
  }

  public String getOkCounts() {
    return okCounts;
  }

  public void setOkCounts(String okCounts) {
    this.okCounts = okCounts;
  }

  public String getDegreeCounts() {
    return degreeCounts;
  }

  public void setDegreeCounts(String degreeCounts) {
    this.degreeCounts = degreeCounts;
  }

  public String getUnavailableCounts() {
    return unavailableCounts;
  }

  public void setUnavailableCounts(String unavailableCounts) {
    this.unavailableCounts = unavailableCounts;
  }

  public String getClientTotal() {
    return clientTotal;
  }

  public void setClientTotal(String clientTotal) {
    this.clientTotal = clientTotal;
  }

  public String getConnectedClients() {
    return connectedClients;
  }

  public void setConnectedClients(String connectedClients) {
    this.connectedClients = connectedClients;
  }

  public String getServiceHealthy() {
    return serviceHealthy;
  }

  public void setServiceHealthy(String serviceHealthy) {
    this.serviceHealthy = serviceHealthy;
  }

  public String getServiceSick() {
    return serviceSick;
  }

  public void setServiceSick(String serviceSick) {
    this.serviceSick = serviceSick;
  }

  public String getServiceFailed() {
    return serviceFailed;
  }

  public void setServiceFailed(String serviceFailed) {
    this.serviceFailed = serviceFailed;
  }

  public String getServiceTotal() {
    return serviceTotal;
  }

  public void setServiceTotal(String serviceTotal) {
    this.serviceTotal = serviceTotal;
  }

  public String getPoolHigh() {
    return poolHigh;
  }

  public void setPoolHigh(String poolHigh) {
    this.poolHigh = poolHigh;
  }

  public String getPoolMiddle() {
    return poolMiddle;
  }

  public void setPoolMiddle(String poolMiddle) {
    this.poolMiddle = poolMiddle;
  }

  public String getPoolLow() {
    return poolLow;
  }

  public void setPoolLow(String poolLow) {
    this.poolLow = poolLow;
  }

  public String getPoolTotal() {
    return poolTotal;
  }

  public void setPoolTotal(String poolTotal) {
    this.poolTotal = poolTotal;
  }

  public String getGoodDiskCount() {
    return goodDiskCount;
  }

  public void setGoodDiskCount(String goodDiskCount) {
    this.goodDiskCount = goodDiskCount;
  }

  public String getBadDiskCount() {
    return badDiskCount;
  }

  public void setBadDiskCount(String badDiskCount) {
    this.badDiskCount = badDiskCount;
  }

  public String getAllDiskCount() {
    return allDiskCount;
  }

  public void setAllDiskCount(String allDiskCount) {
    this.allDiskCount = allDiskCount;
  }

  public String getCriticalAlertCount() {
    return criticalAlertCount;
  }

  public void setCriticalAlertCount(String criticalAlertCount) {
    this.criticalAlertCount = criticalAlertCount;
  }

  public String getMajorAlertCount() {
    return majorAlertCount;
  }

  public void setMajorAlertCount(String majorAlertCount) {
    this.majorAlertCount = majorAlertCount;
  }

  public String getMinorAlertCount() {
    return minorAlertCount;
  }

  public void setMinorAlertCount(String minorAlertCount) {
    this.minorAlertCount = minorAlertCount;
  }

  public String getWarningAlertCount() {
    return warningAlertCount;
  }

  public void setWarningAlertCount(String warningAlertCount) {
    this.warningAlertCount = warningAlertCount;
  }

  public String getClearedAlertCount() {
    return clearedAlertCount;
  }

  public void setClearedAlertCount(String clearedAlertCount) {
    this.clearedAlertCount = clearedAlertCount;
  }

  public String getOkServerNodeCounts() {
    return okServerNodeCounts;
  }

  public void setOkServerNodeCounts(String okServerNodeCounts) {
    this.okServerNodeCounts = okServerNodeCounts;
  }

  public String getUnknownServerNodeCount() {
    return unknownServerNodeCount;
  }

  public void setUnknownServerNodeCount(String unknownServerNodeCount) {
    this.unknownServerNodeCount = unknownServerNodeCount;
  }

  public String getTotalServerNodeCount() {
    return totalServerNodeCount;
  }

  public void setTotalServerNodeCount(String totalServerNodeCount) {
    this.totalServerNodeCount = totalServerNodeCount;
  }

  public List<String> getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(List<String> errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getTheUsedUniPerStr() {
    return theUsedUniPerStr;
  }

  public void setTheUsedUniPerStr(String theUsedUniPerStr) {
    this.theUsedUniPerStr = theUsedUniPerStr;
  }

  public String getTheunUsedUniPerStr() {
    return theunUsedUniPerStr;
  }

  public void setTheunUsedUniPerStr(String theunUsedUniPerStr) {
    this.theunUsedUniPerStr = theunUsedUniPerStr;
  }

  public String getTheUsedUnitSpace() {
    return theUsedUnitSpace;
  }

  public void setTheUsedUnitSpace(String theUsedUnitSpace) {
    this.theUsedUnitSpace = theUsedUnitSpace;
  }

  public String getTheunUsedUnitSpace() {
    return theunUsedUnitSpace;
  }

  public void setTheunUsedUnitSpace(String theunUsedUnitSpace) {
    this.theunUsedUnitSpace = theunUsedUnitSpace;
  }

  @Override
  public String toString() {
    return "DashboardInfo{" + "totalCapacity='" + totalCapacity + '\'' + ", availableCapacity='"
        + availableCapacity
        + '\'' + ", usedCapacity='" + usedCapacity + '\'' + ", freeSpace='" + freeSpace + '\''
        + ", availableCapacityPer='" + availableCapacityPer + '\'' + ", usedCapacityPer='"
        + usedCapacityPer
        + '\'' + ", theUsedUniPerStr='" + theUsedUniPerStr + '\'' + ", theunUsedUniPerStr='"
        + theunUsedUniPerStr + '\'' + ", theUsedUnitSpace='" + theUsedUnitSpace + '\''
        + ", theunUsedUnitSpace='" + theunUsedUnitSpace + '\'' + ", oKCounts='" + okCounts + '\''
        + ", degreeCounts='" + degreeCounts + '\'' + ", unavailableCounts='" + unavailableCounts
        + '\''
        + ", clientTotal='" + clientTotal + '\'' + ", connectedClients='" + connectedClients + '\''
        + ", serviceHealthy='" + serviceHealthy + '\'' + ", serviceSick='" + serviceSick + '\''
        + ", serviceFailed='"
        + serviceFailed + '\'' + ", serviceTotal='" + serviceTotal + '\'' + ", poolHigh='"
        + poolHigh + '\''
        + ", poolMiddle='" + poolMiddle + '\'' + ", poolLow='" + poolLow + '\'' + ", poolTotal='"
        + poolTotal
        + '\'' + ", goodDiskCount='" + goodDiskCount + '\'' + ", badDiskCount='" + badDiskCount
        + '\''
        + ", allDiskCount='" + allDiskCount + '\'' + ", criticalAlertCount='" + criticalAlertCount
        + '\''
        + ", majorAlertCount='" + majorAlertCount + '\'' + ", minorAlertCount='" + minorAlertCount
        + '\''
        + ", warningAlertCount='" + warningAlertCount + '\'' + ", clearedAlertCount='"
        + clearedAlertCount
        + '\'' + ", okServerNodeCounts='" + okServerNodeCounts + '\'' + ", unknownServerNodeCount='"
        + unknownServerNodeCount + '\'' + ", totalServerNodeCount='" + totalServerNodeCount + '\''
        + ", errorMessage=" + errorMessage + '}';
  }
}
