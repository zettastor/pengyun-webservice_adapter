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
