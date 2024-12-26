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
 * SimpleHost.
 */
public class SimpleHost {

  private String hostName;
  private String cpuUsage;
  private String memoryUsage;

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public String getCpuUsage() {
    return cpuUsage;
  }

  public void setCpuUsage(String cpuUsage) {
    this.cpuUsage = cpuUsage;
  }

  public String getMemoryUsage() {
    return memoryUsage;
  }

  public void setMemoryUsage(String memmoryUsage) {
    this.memoryUsage = memmoryUsage;
  }

  @Override
  public String toString() {
    return "SimpleHost [hostName=" + hostName + ", cpuUsage=" + cpuUsage + ", memoryUsage="
        + memoryUsage + "]";
  }

}
