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
