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

import java.util.Map;
import java.util.TreeMap;

/**
 * this class contains the read/write IOPS and throughput of a volume.
 *
 */

public class Performances {

  public Performances() {

  }

  /**
   * performances.
   *
   * @param requestId request id
   * @param volumeId volume id
   * @param writeThroughput write throughput
   * @param readThroughput read throughput
   * @param writeIoPs write IO ps
   * @param readIoPs read IO ps
   * @param writeLatency write latency
   * @param readLatency read latency
   */
  public Performances(String requestId, String volumeId, Map<Long, Long> writeThroughput,
      Map<Long, Long> readThroughput, Map<Long, Long> writeIoPs, Map<Long, Long> readIoPs,
      long writeLatency,
      long readLatency) {
    super();
    this.requestId = requestId;
    this.volumeId = volumeId;
    this.writeThroughput = writeThroughput;
    this.readThroughput = readThroughput;
    this.writeIoPs = writeIoPs;
    this.readIoPs = readIoPs;
    this.writeLatency = writeLatency;
    this.readLatency = readLatency;
  }

  private String requestId;
  private String volumeId;
  private Map<Long, Long> writeThroughput;
  private Map<Long, Long> readThroughput;
  private Map<Long, Long> writeIoPs;
  private Map<Long, Long> readIoPs;
  private long writeLatency;
  private long readLatency;

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

  public Map<Long, Long> getWriteThroughput() {
    return writeThroughput;
  }

  public void setWriteThroughput(Map<Long, Long> writeThroughput) {
    this.writeThroughput = new TreeMap<>();
    this.writeThroughput.putAll(writeThroughput);
  }

  public Map<Long, Long> getReadThroughput() {
    return readThroughput;
  }

  public void setReadThroughput(Map<Long, Long> readThroughput) {
    this.readThroughput = new TreeMap<>();
    this.readThroughput.putAll(readThroughput);
  }

  public Map<Long, Long> getWriteIoPs() {
    return writeIoPs;
  }

  public void setWriteIoPs(Map<Long, Long> writeIoPs) {
    this.writeIoPs = new TreeMap<>();
    this.writeIoPs.putAll(writeIoPs);
  }

  public Map<Long, Long> getReadIoPs() {
    return readIoPs;
  }

  public void setReadIoPs(Map<Long, Long> readIoPs) {
    this.readIoPs = new TreeMap<>();
    this.readIoPs.putAll(readIoPs);
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
    return "Performances [requestId=" + requestId + ", volumeId=" + volumeId + ", writeThroughput="
        + writeThroughput + ", readThroughput=" + readThroughput + ", writeIOPS=" + writeIoPs
        + ", readIOPS=" + readIoPs + ", writeLatency=" + writeLatency
        + ", readLatency=" + readLatency + "]";
  }

}
