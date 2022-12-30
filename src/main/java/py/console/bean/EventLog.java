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

public class EventLog {

  private String id;
  private String eventLog;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEventLog() {
    return eventLog;
  }

  public void setEventLog(String eventLog) {
    this.eventLog = eventLog;
  }

  @Override
  public String toString() {
    return "EventLog{" + "id='" + id + '\'' + ", eventLog='" + eventLog + '\'' + '}';
  }
}
