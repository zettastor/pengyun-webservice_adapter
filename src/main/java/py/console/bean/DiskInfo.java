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
public class DiskInfo {

  @ApiModelProperty(value = "磁盘名")
  private String name;
  @ApiModelProperty(value = "Ssd或者hdd")
  private String ssdOrHdd;
  @ApiModelProperty(value = "供应商")
  private String vendor;
  @ApiModelProperty(value = "型号")
  private String model;
  @ApiModelProperty(value = "产品序列号")
  private String sn;
  @ApiModelProperty(value = "转速")
  private String rate;
  @ApiModelProperty(value = "磁盘大小")
  private String size;
  @ApiModelProperty(value = "wwn号")
  private String wwn;
  @ApiModelProperty(value = "控制id")
  private String controllerId;
  @ApiModelProperty(value = "槽位")
  private String slotNumber;
  @ApiModelProperty(value = "enclosure id")
  private String enclosureId;
  @ApiModelProperty(value = "卡类型")
  private String cardType;
  @ApiModelProperty(value = "磁盘灯的状态")
  private String swith;
  @ApiModelProperty(value = "磁盘序列号")
  private String serialNumber;
  @ApiModelProperty(value = "Smart检测信息")
  private List<SmartInfo> smartInfos;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSsdOrHdd() {
    return ssdOrHdd;
  }

  public void setSsdOrHdd(String ssdOrHdd) {
    this.ssdOrHdd = ssdOrHdd;
  }

  public String getVendor() {
    return vendor;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public String getRate() {
    return rate;
  }

  public void setRate(String rate) {
    this.rate = rate;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getWwn() {
    return wwn;
  }

  public void setWwn(String wwn) {
    this.wwn = wwn;
  }

  public String getControllerId() {
    return controllerId;
  }

  public void setControllerId(String controllerId) {
    this.controllerId = controllerId;
  }

  public String getSlotNumber() {
    return slotNumber;
  }

  public void setSlotNumber(String slotNumber) {
    this.slotNumber = slotNumber;
  }

  public String getEnclosureId() {
    return enclosureId;
  }

  public void setEnclosureId(String enclosureId) {
    this.enclosureId = enclosureId;
  }

  public String getCardType() {
    return cardType;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }

  public String getSwith() {
    return swith;
  }

  public void setSwith(String swith) {
    this.swith = swith;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public List<SmartInfo> getSmartInfos() {
    return smartInfos;
  }

  public void setSmartInfos(List<SmartInfo> smartInfos) {
    this.smartInfos = smartInfos;
  }

  @Override
  public String toString() {
    return "DiskInfo{" + "name='" + name + '\'' + ", ssdOrHdd='" + ssdOrHdd + '\'' + ", vendor='"
        + vendor + '\''
        + ", model='" + model + '\'' + ", sn='" + sn + '\'' + ", rate='" + rate + '\'' + ", size='"
        + size
        + '\'' + ", wwn='" + wwn + '\'' + ", controllerId='" + controllerId + '\''
        + ", slotNumber='"
        + slotNumber + '\'' + ", enclosureId='" + enclosureId + '\'' + ", cardType='" + cardType
        + '\''
        + ", swith='" + swith + '\'' + ", serialNumber='" + serialNumber + '\'' + ", smartInfos="
        + smartInfos
        + '}';
  }
}
