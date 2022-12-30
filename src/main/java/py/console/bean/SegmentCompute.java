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
 * SegmentCompute.
 */
public class SegmentCompute {

  private int pcount;
  private int scount;
  private int acount;

  public int getPcount() {
    return pcount;
  }

  public void setPcount(int pcount) {
    this.pcount = pcount;
  }

  public int getScount() {
    return scount;
  }

  public void setScount(int scount) {
    this.scount = scount;
  }

  public int getAcount() {
    return acount;
  }

  public void setAcount(int acount) {
    this.acount = acount;
  }

  @Override
  public String toString() {
    return "SegmentCompute [pCount=" + pcount + ", sCount=" + scount + ", aCount=" + acount + "]";
  }

}
