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
