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

package py.console.exception;

/**
 * DomainNameNullException.
 */
public class DomainNameNullException extends Exception {

  private static final long serialVersionUID = -1065415306395856777L;

  public DomainNameNullException() {
    super();
  }

  public DomainNameNullException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public DomainNameNullException(String message, Throwable cause) {
    super(message, cause);
  }

  public DomainNameNullException(String message) {
    super(message);
  }

  public DomainNameNullException(Throwable cause) {
    super(cause);
  }

}
