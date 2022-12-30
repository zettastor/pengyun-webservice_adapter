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


public class Performance {

  public Performance() {

  }

  /**
   * performance.
   *
   * @param volumeName volume name
   * @param requestId request id
   * @param volumeId volume id
   * @param writeThroughput write throughput
   * @param readThroughput read throughput
   * @param writeIoPs write IO ps
   * @param readIoPs read IO ps
   * @param writeLatency write latency
   * @param readLatency read latency
   */
  public Performance(String volumeName, String requestId, String volumeId,
      long writeThroughput, long readThroughput, long writeIoPs,
      long readIoPs, long writeLatency, long readLatency) {
    super();
    this.volumeName = volumeName;
    this.requestId = requestId;
    this.volumeId = volumeId;
    this.writeThroughput = writeThroughput;
    this.readThroughput = readThroughput;
    this.writeIoPs = writeIoPs;
    this.readIoPs = readIoPs;
    this.writeLatency = writeLatency;
    this.readLatency = readLatency;
  }

  private String volumeName;
  private String requestId;
  private String volumeId;
  private long writeThroughput;
  private long readThroughput;
  private long writeIoPs;
  private long readIoPs;
  private long writeLatency;
  private long readLatency;

  public String getVolumeName() {
    return volumeName;
  }

  public void setVolumeName(String volumeName) {
    this.volumeName = volumeName;
  }

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

  public long getWriteThroughput() {
    return writeThroughput;
  }

  public void setWriteThroughput(long writeThroughput) {
    this.writeThroughput = writeThroughput;
  }

  public long getReadThroughput() {
    return readThroughput;
  }

  public void setReadThroughput(long readThroughput) {
    this.readThroughput = readThroughput;
  }

  public long getReadIoPs() {
    return readIoPs;
  }

  public void setReadIoPs(long readIoPs) {
    this.readIoPs = readIoPs;
  }

  public long getWriteIoPs() {
    return writeIoPs;
  }

  public void setWriteIoPs(long writeIoPs) {
    this.writeIoPs = writeIoPs;
  }

  public long getWriteLatency() {
    return writeLatency;
  }

  public void setWriteLatency(long writeLatency) {
    this.writeLatency = writeLatency;
  }

  public long getReadLatency() {
    return readLatency;
  }

  public void setReadLatency(long readLatency) {
    this.readLatency = readLatency;
  }

  @Override
  public String toString() {
    return "Performance [volumeName=" + volumeName + ", requestId="
        + requestId + ", volumeId=" + volumeId + ", writeThroughput="
        + writeThroughput + ", readThroughput=" + readThroughput
        + ", writeIOPS=" + writeIoPs + ", readIOPS=" + readIoPs
        + ", writeLatency=" + writeLatency + ", readLatency="
        + readLatency + "]";
  }
}
