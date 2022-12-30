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
 * this bean contains the total IOPS and throughput.
 *
 */
public class TotalIoPsAndThroughput {

  private String message;

  private long readIoPs;

  private long writeIoPs;

  private long readThroughput;

  private long writeThroughput;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
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

  public long getReadThroughput() {
    return readThroughput;
  }

  public void setReadThroughput(long readThroughput) {
    this.readThroughput = readThroughput;
  }

  public long getWriteThroughput() {
    return writeThroughput;
  }

  public void setWriteThroughput(long writeThroughput) {
    this.writeThroughput = writeThroughput;
  }

  public String toString() {
    return "message:" + message + " readIOPS=" + readIoPs + " writeIOPS=" + writeIoPs
        + " readThroughput=" + readThroughput + " writeThroughput=" + writeThroughput;
  }

}