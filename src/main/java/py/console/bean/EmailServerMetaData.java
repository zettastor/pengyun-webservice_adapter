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

/**
 * EmailServerMetaData.
 */
public class EmailServerMetaData {

  private String name;
  private String host;
  private String port;
  private String postfix;
  private boolean validate;
  private boolean ssl;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getPostfix() {
    return postfix;
  }

  public void setPostfix(String postfix) {
    this.postfix = postfix;
  }

  public boolean isValidate() {
    return validate;
  }

  public void setValidate(boolean validate) {
    this.validate = validate;
  }

  public boolean isSsl() {
    return ssl;
  }

  public void setSsl(boolean ssl) {
    this.ssl = ssl;
  }

  @Override
  public String toString() {
    return "EmailServerMetaData [name=" + name + ", host=" + host + ", port=" + port + ", postfix="
        + postfix
        + ", validate=" + validate + ", ssl=" + ssl + "]";
  }

}
