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

package py.console.utils;

import java.text.DecimalFormat;
import java.util.Locale;
import org.apache.struts2.ServletActionContext;

/**
 * Utils.
 */
public class Utils {

  /**
   * volume size builder.
   *
   * @param size size
   * @return volume size
   */
  public static String volumeSizeBuilder(long size) {
    DecimalFormat df = new DecimalFormat(".##");
    double volumeSize = (double) size / Constants.MB_SIZE;
    return df.format(volumeSize);
  }

  /**
   * segment status convert.
   *
   * @param status status
   * @return friendly status
   */
  public static String segmentStatusConvert(String status) {
    if (status.equals("Primary") || status.equals("Secondary")) {
      return "OK";
    }
    if (status.equals("PrePrimary")) {
      return "PrePrimary";
    }
    if (status.equals("SecondaryEnrolled") || status.equals("ModeratorSelected") || status.equals(
        "PreArbiter")) {
      return "Voting";
    }
    if (status.equals("SecondaryApplicant") || status.equals("PreSecondary")) {
      return "Joining";
    }
    if (status.equals("Arbiter")) {
      return "Arbiter";
    }
    if (status.equals("Deleting")) {
      return "Deleting";
    }
    if (status.equals("Deleted")) {
      return "Deleted";
    }
    if (status.equals("Unknown")) {
      return "Unknown";
    }
    return "Transit";
  }

  /**
   * get language.
   *
   * @return language
   */
  public static String getLang() {
    Locale locale = (Locale) ServletActionContext.getRequest().getSession()
        .getAttribute("WW_TRANS_I18N_LOCALE");
    if (locale != null) {
      return locale.getLanguage();
    }
    return null;
  }
}
