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

package py.console.service.account;

import org.apache.thrift.TException;
import py.console.bean.Account;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;

/**
 * AccountSessionService.
 */
public interface AccountSessionService {

  public void setAccount(Account account);

  public Account getAccount();

  public void logout(long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

}
