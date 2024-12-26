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
