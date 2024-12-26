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
