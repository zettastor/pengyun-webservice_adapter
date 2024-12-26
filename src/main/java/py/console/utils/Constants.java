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
