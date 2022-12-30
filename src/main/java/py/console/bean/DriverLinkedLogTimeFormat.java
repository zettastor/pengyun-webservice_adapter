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
 * DriverLinkedLogTimeFormat.
 */
public class DriverLinkedLogTimeFormat {

  private String dayTime;
  private List<SimpleDriverLinkedLog> driverLinkedLogs;

  public String getDayTime() {
    return dayTime;
  }

  public void setDayTime(String dayTime) {
    this.dayTime = dayTime;
  }

  public List<SimpleDriverLinkedLog> getDriverLinkedLogs() {
    return driverLinkedLogs;
  }

  public void setDriverLinkedLogs(List<SimpleDriverLinkedLog> driverLinkedLogs) {
    this.driverLinkedLogs = driverLinkedLogs;
  }

  @Override
  public String toString() {
    return "DriverLinkedLogTimeFormat{"
        + "dayTime='" + dayTime + '\''
        + ", driverLinkedLogs=" + driverLinkedLogs
        + '}';
  }
}
