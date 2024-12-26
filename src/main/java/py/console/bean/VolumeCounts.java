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
 * this bean contains the counts of volumes in different status.
 *
 */
public class VolumeCounts {

  private String message;

  private int okCounts;

  private int degreeCounts;

  private int unavailableCounts;

  private int totalClients;

  private int connectedClients;

  public String getMessage() {
    return message;
  }


  public void setMessage(String message) {
    this.message = message;
  }


  public int getOkCounts() {
    return okCounts;
  }


  public void setOkCounts(int okCounts) {
    this.okCounts = okCounts;
  }


  public int getDegreeCounts() {
    return degreeCounts;
  }


  public void setDegreeCounts(int degreeCounts) {
    this.degreeCounts = degreeCounts;
  }


  public int getUnavailableCounts() {
    return unavailableCounts;
  }


  public void setUnavailableCounts(int unavailableCounts) {
    this.unavailableCounts = unavailableCounts;
  }


  public int getTotalClients() {
    return totalClients;
  }


  public void setTotalClients(int totalClients) {
    this.totalClients = totalClients;
  }


  public int getConnectedClients() {
    return connectedClients;
  }


  public void setConnectedClients(int connectedClients) {
    this.connectedClients = connectedClients;
  }


  @Override
  public String toString() {
    return "VolumeCounts [message=" + message + ", OKCounts=" + okCounts
        + ", degreeCounts=" + degreeCounts + ", unavailableCounts="
        + unavailableCounts + ", totalClients=" + totalClients
        + ", connectedClients=" + connectedClients + "]";
  }

}
