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
