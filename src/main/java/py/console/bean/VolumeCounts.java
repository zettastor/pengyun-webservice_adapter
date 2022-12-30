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
