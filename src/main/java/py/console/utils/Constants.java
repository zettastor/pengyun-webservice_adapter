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

import static py.common.Constants.SUPERADMIN_ACCOUNT_ID;

/**
 * Constants for the console module.
 *
 */
public class Constants {

  // id of user in session
  public static final String ACCOUNT_SESSION_ID = "account_session_id";

  public static final long MB_SIZE = 1024 * 1024;

  public static final String DRIVER_CONTAINER_INSTANCE_NAME = "DriverContainer";

  public static final String READ_ONLY = "Read-Only";

  public static final String READ_WRITE = "Read/Write";

  public static final String ACTION_RETURN_STRING = "dataMap";

  public static final long SUPER_ADMIN_ACCOUNT_ID = SUPERADMIN_ACCOUNT_ID;

}
