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

package py.console.service.account.impl;

import org.apache.struts2.ServletActionContext;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.common.RequestIdBuilder;
import py.console.bean.Account;
import py.console.service.account.AccountSessionService;
import py.console.utils.Constants;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.infocenter.client.InformationCenterClientFactory;
import py.thrift.infocenter.service.InformationCenter;
import py.thrift.infocenter.service.LogoutRequest;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;

/**
 * AccountSessionServiceImpl.
 */
public class AccountSessionServiceImpl implements AccountSessionService {

  private static final Logger logger = LoggerFactory.getLogger(AccountSessionServiceImpl.class);
  private InformationCenterClientFactory infoCenterClientFactory;

  public InformationCenterClientFactory getInfoCenterClientFactory() {
    return infoCenterClientFactory;
  }

  public void setInfoCenterClientFactory(InformationCenterClientFactory infoCenterClientFactory) {
    this.infoCenterClientFactory = infoCenterClientFactory;
  }

  @Override
  public void setAccount(Account account) {
    ServletActionContext.getRequest().getSession()
        .setAttribute(Constants.ACCOUNT_SESSION_ID, account);
  }

  @Override
  public Account getAccount() {
    return (Account) ServletActionContext.getRequest().getSession()
        .getAttribute(Constants.ACCOUNT_SESSION_ID);
  }

  @Override
  public void logout(long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    LogoutRequest request = new LogoutRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);

    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.logout(request);
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }

  }

}
