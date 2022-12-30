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
 * SimpleSnapshotMetadata.
 */
public class SimpleSnapshotMetadata {

  private int snapshotId;

  private String name;

  private String description;

  private long createdTime;

  /**
   * Simple Snapshot Metadata.
   *
   * @param snapshotId snapshot id
   * @param name name
   * @param description description
   * @param createdTime created time
   */
  public SimpleSnapshotMetadata(int snapshotId, String name, String description, long createdTime) {
    this.snapshotId = snapshotId;
    this.name = name;
    this.description = description;
    this.createdTime = createdTime;
  }

  public int getSnapshotId() {
    return snapshotId;
  }

  public void setSnapshotId(int snapshotId) {
    this.snapshotId = snapshotId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(long createdTime) {
    this.createdTime = createdTime;
  }
}
