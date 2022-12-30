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
 * this bean contains the counts of nodes in different status.
 *
 */
public class NodeCounts {

  private String message;

  private int okCounts;

  private int incCounts;

  private int failedCounts;

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

  public int getIncCounts() {
    return incCounts;
  }

  public void setIncCounts(int incCounts) {
    this.incCounts = incCounts;
  }

  public int getFailedCounts() {
    return failedCounts;
  }

  public void setFailedCounts(int failedCounts) {
    this.failedCounts = failedCounts;
  }

  public String toString() {
    return "message:" + message + "OK: " + okCounts + "INC: " + incCounts + "Failed: "
        + failedCounts;
  }
}
