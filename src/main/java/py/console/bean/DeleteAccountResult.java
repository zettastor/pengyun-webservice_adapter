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
 * return message of deleting account to the browser, if the account still have volume then return
 * the list of volumes.
 *
 */
public class DeleteAccountResult {

  private String message;

  private List<SimpleVolumeMetadata> volumeList;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<SimpleVolumeMetadata> getVolumeList() {
    return volumeList;
  }

  public void setVolumeList(List<SimpleVolumeMetadata> volumeList) {
    this.volumeList = volumeList;
  }
}
