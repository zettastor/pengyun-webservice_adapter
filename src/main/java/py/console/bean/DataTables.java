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

import com.opensymphony.xwork2.ActionContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.Validate;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DataTables.
 */
public class DataTables {

  private static final Logger logger = LoggerFactory.getLogger(DataTables.class);

  private boolean enabled = true;

  private int draw;

  private int start;

  private int length;

  private String direction;

  private int column;

  private String searchValue;

  /**
   * Data Tables.
   */
  public DataTables() {
    HttpServletRequest httpServletRequest = (HttpServletRequest) ActionContext.getContext()
        .get(ServletActionContext.HTTP_REQUEST);
    if (httpServletRequest == null) {
      logger.warn("No such http servlet request.");
      this.enabled = false;
      return;
    }

    if (httpServletRequest.getParameter("draw") == null) {
      logger.debug("Datatable is not enabled!");
      this.enabled = false;
      return;
    }

    /*
     * pagination
     */
    this.draw = Integer.valueOf(httpServletRequest.getParameter("draw"));
    this.start = Integer.valueOf(httpServletRequest.getParameter("start"));
    this.length = Integer.valueOf(httpServletRequest.getParameter("length"));

    /*
     * order
     */
    this.direction = httpServletRequest.getParameter("order[0][dir]");

    String paramColumn = httpServletRequest.getParameter("order[0][column]");
    if (paramColumn != null) {
      this.column = Integer.valueOf(paramColumn);
    }

    /*
     * search
     */
    this.searchValue = httpServletRequest.getParameter("search[value]");

    logger.debug("Draw a data table {}", this);
  }

  /**
   * Data Tables.
   *
   * @param other other data tables
   */
  public DataTables(DataTables other) {
    Validate.isTrue(other.isEnabled(), "Param data-tables is disabled!");

    this.draw = other.draw;
    this.start = other.start;
    this.length = other.length;
    this.direction = other.direction;
    this.column = other.column;
    this.searchValue = other.searchValue;
  }

  public int getDraw() {
    return draw;
  }

  public void setDraw(int draw) {
    this.draw = draw;
  }

  public int getStart() {
    return start;
  }

  public void setStart(int start) {
    this.start = start;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public boolean isInOrder() {
    return this.direction != null;
  }

  public boolean hasSearchValue() {
    return !(this.searchValue == null || this.searchValue.isEmpty());
  }

  public String getSearchValue() {
    return searchValue;
  }

  public void setSearchValue(String searchValue) {
    this.searchValue = searchValue;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public String toString() {
    return "DataTables [enabled=" + enabled + ", draw=" + draw + ", start=" + start + ", length="
        + length
        + ", direction=" + direction + ", column=" + column + ", searchValue=" + searchValue + "]";
  }
}
