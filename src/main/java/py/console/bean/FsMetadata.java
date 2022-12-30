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
