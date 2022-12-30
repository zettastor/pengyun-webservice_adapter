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

@ApiModel
public class SmtpItem {

  @ApiModelProperty(value = "发件箱地址")
  private String userName;
  @ApiModelProperty(value = "密码")
  private String password;
  @ApiModelProperty(value = "端口号，默认2")
  private String smtpPort;
  @ApiModelProperty(value = "加密类型(AUTH,SSL,TLS)")
  private String encryptType;
  @ApiModelProperty(value = "主题")
  private String subject;
  @ApiModelProperty(value = "SMTP服务器")
  private String smtpServer;
  @ApiModelProperty(value = "固定值HTML")
  private String contentType;
  @ApiModelProperty(value = "是否启用(true,false)")
  private String enable;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSmtpPort() {
    return smtpPort;
  }

  public void setSmtpPort(String smtpPort) {
    this.smtpPort = smtpPort;
  }

  public String getEncryptType() {
    return encryptType;
  }

  public void setEncryptType(String encryptType) {
    this.encryptType = encryptType;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getSmtpServer() {
    return smtpServer;
  }

  public void setSmtpServer(String smtpServer) {
    this.smtpServer = smtpServer;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getEnable() {
    return enable;
  }

  public void setEnable(String enable) {
    this.enable = enable;
  }

  @Override
  public String toString() {
    return "SmtpItem{" + "userName='" + userName + '\'' + ", password='" + password + '\''
        + ", smtpPort='"
        + smtpPort + '\'' + ", encryptType='" + encryptType + '\'' + ", subject='" + subject + '\''
        + ", smtpServer='" + smtpServer + '\'' + ", contentType='" + contentType + '\''
        + ", enable='" + enable
        + '\'' + '}';
  }
}
