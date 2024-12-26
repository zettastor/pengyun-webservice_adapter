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
