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
 * FsMetadata.
 */
public class FsMetadata {

  private String fsId;

  private String fsName;

  private String fsSize;

  private String fsStatus;

  private String message;

  public String getFsId() {
    return fsId;
  }

  public void setFsId(String fsId) {
    this.fsId = fsId;
  }

  public String getFsName() {
    return fsName;
  }

  public void setFsName(String fsName) {
    this.fsName = fsName;
  }

  public String getFsSize() {
    return fsSize;
  }

  public void setFsSize(String fsSize) {
    this.fsSize = fsSize;
  }

  public String getFsStatus() {
    return fsStatus;
  }

  public void setFsStatus(String fsStatus) {
    this.fsStatus = fsStatus;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


}
