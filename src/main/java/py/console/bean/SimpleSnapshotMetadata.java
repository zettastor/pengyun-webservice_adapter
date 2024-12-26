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
