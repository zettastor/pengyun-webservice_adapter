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

import java.util.List;

/**
 * EmailPropertiesMetaData.
 */
public class EmailPropertiesMetaData {

  private String name;
  private String senderAddress;
  private String senderPwd;
  private List<String> destAddressList;

  public List<String> getDestAddressList() {
    return destAddressList;
  }

  public void setDestAddressList(List<String> destAddressList) {
    this.destAddressList = destAddressList;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSenderPwd() {
    return senderPwd;
  }

  public void setSenderPwd(String senderPwd) {
    this.senderPwd = senderPwd;
  }

  public String getSenderAddress() {
    return senderAddress;
  }

  public void setSenderAddress(String senderAddress) {
    this.senderAddress = senderAddress;
  }

  @Override
  public String toString() {
    return "EmailPropertiesMetaData [name=" + name + ", senderAddress=" + senderAddress
        + ", senderPwd=" + senderPwd
        + ", destAddressList=" + destAddressList + "]";
  }

}
