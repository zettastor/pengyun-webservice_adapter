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

public class OperationLogFormat {

  private String dayTime;
  private List<SimpleOperation> operationList;

  public String getDayTime() {
    return dayTime;
  }

  public void setDayTime(String dayTime) {
    this.dayTime = dayTime;
  }

  public List<SimpleOperation> getOperationList() {
    return operationList;
  }

  public void setOperationList(List<SimpleOperation> operationList) {
    this.operationList = operationList;
  }

  @Override
  public String toString() {
    return "OperationLogFormat{" + "dayTime='" + dayTime + '\'' + ", operationList=" + operationList
        + '}';
  }
}
