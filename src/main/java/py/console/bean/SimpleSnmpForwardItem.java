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

@ApiModel
public class SimpleSnmpForwardItem {

  @ApiModelProperty(value = "Snmp id")
  private String id;
  @ApiModelProperty(value = "管理站名称")
  private String name;
  @ApiModelProperty(value = "管理站描述")
  private String description;
  @ApiModelProperty(value = "超时时间，单位毫秒")
  private String timeout;
  @ApiModelProperty(value = "SNMP IP")
  private String trapServerip; // required
  @ApiModelProperty(value = "端口")
  private String trapServerport; // required
  @ApiModelProperty(value = "是否启用")
  private String enable; // required
  @ApiModelProperty(value = "类型：SNMPV2C、SNMPV3")
  private String snmpVersion;
  @ApiModelProperty(value = "团体字")
  private String community;
  @ApiModelProperty(value = "安全级别")
  private String securityLevel;
  @ApiModelProperty(value = "SNMP 用户")
  private String securityName;
  @ApiModelProperty(value = "认 证 算 法")
  private String authProtocol;
  @ApiModelProperty(value = "认 证 密 码")
  private String authKey;
  @ApiModelProperty(value = "加 密 算 法")
  private String privProtocol;
  @ApiModelProperty(value = "加 密 密 码")
  private String privkey;

  public String getTrapServerip() {
    return trapServerip;
  }

  public void setTrapServerip(String trapServerip) {
    this.trapServerip = trapServerip;
  }

  public String getTrapServerport() {
    return trapServerport;
  }

  public void setTrapServerport(String trapServerport) {
    this.trapServerport = trapServerport;
  }

  public String getEnable() {
    return enable;
  }

  public void setEnable(String enable) {
    this.enable = enable;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSnmpVersion() {
    return snmpVersion;
  }

  public void setSnmpVersion(String snmpVersion) {
    this.snmpVersion = snmpVersion;
  }

  public String getCommunity() {
    return community;
  }

  public void setCommunity(String community) {
    this.community = community;
  }

  public String getSecurityLevel() {
    return securityLevel;
  }

  public void setSecurityLevel(String securityLevel) {
    this.securityLevel = securityLevel;
  }

  public String getSecurityName() {
    return securityName;
  }

  public void setSecurityName(String securityName) {
    this.securityName = securityName;
  }

  public String getAuthProtocol() {
    return authProtocol;
  }

  public void setAuthProtocol(String authProtocol) {
    this.authProtocol = authProtocol;
  }

  public String getAuthKey() {
    return authKey;
  }

  public void setAuthKey(String authKey) {
    this.authKey = authKey;
  }

  public String getPrivProtocol() {
    return privProtocol;
  }

  public void setPrivProtocol(String privProtocol) {
    this.privProtocol = privProtocol;
  }

  public String getPrivkey() {
    return privkey;
  }

  public void setPrivkey(String privkey) {
    this.privkey = privkey;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTimeout() {
    return timeout;
  }

  public void setTimeout(String timeout) {
    this.timeout = timeout;
  }

  @Override
  public String toString() {
    return "SimpleSnmpForwardItem{" + "id='" + id + '\'' + ", name='" + name + '\''
        + ", description='"
        + description + '\'' + ", timeout='" + timeout + '\'' + ", trapServerip='" + trapServerip
        + '\''
        + ", trapServerport='" + trapServerport + '\'' + ", enable='" + enable + '\''
        + ", snmpVersion='"
        + snmpVersion + '\'' + ", community='" + community + '\'' + ", securityLevel='"
        + securityLevel + '\''
        + ", securityName='" + securityName + '\'' + ", authProtocol='" + authProtocol + '\''
        + ", authKey='"
        + authKey + '\'' + ", privProtocol='" + privProtocol + '\'' + ", privkey='" + privkey + '\''
        + '}';
  }
}
