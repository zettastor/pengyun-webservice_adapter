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
 * this class contains the read/write latency and the health status of a volume.
 *
 */
public class PerformanceRealTime {

  public PerformanceRealTime() {

  }

  /**
   * performance realTime.
   *
   * @param requestId request id
   * @param volumeId volume id
   * @param readLatency read latency
   * @param writeLatency write latency
   * @param healthStatus health status
   * @param healthIndex health index
   */
  public PerformanceRealTime(String requestId, String volumeId, long readLatency, long writeLatency,
      String healthStatus, long healthIndex
  ) {
    super();
    this.requestId = requestId;
    this.volumeId = volumeId;
    this.readLatency = readLatency;
    this.writeLatency = writeLatency;
    this.healthStatus = healthStatus;
    this.healthIndex = healthIndex;
  }

  private String requestId;
  private String volumeId;
  private long readLatency;
  private long writeLatency;
  private String healthStatus;
  private long healthIndex;

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public String getVolumeId() {
    return volumeId;
  }

  public void setVolumeId(String volumeId) {
    this.volumeId = volumeId;
  }

  public long getReadLatency() {
    return readLatency;
  }

  public void setReadLatency(long readLatency) {
    this.readLatency = readLatency;
  }

  public long getWriteLatency() {
    return writeLatency;
  }

  public void setWriteLatency(long writeLatency) {
    this.writeLatency = writeLatency;
  }


  public String getHealthStatus() {
    return healthStatus;
  }

  public void setHealthStatus(String healthStatus) {
    this.healthStatus = healthStatus;
  }

  public long getHealthIndex() {
    return healthIndex;
  }

  public void setHealthIndex(long healthIndex) {
    this.healthIndex = healthIndex;
  }

  @Override
  public String toString() {
    return "Performance [requestId=" + requestId + ", volumeId=" + volumeId + ", readlatency="
        + readLatency + ", writelatency=" + writeLatency + ", healthStatus=" + healthStatus
        + ", healthIndex =" + healthIndex + "]";
  }
}
