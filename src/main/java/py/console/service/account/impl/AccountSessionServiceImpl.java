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
