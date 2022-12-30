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

package py.console.service.alert;

import java.util.List;
import java.util.Map;
import org.apache.thrift.TException;
import py.console.MessageForwardItem;
import py.console.bean.PerformanceItem;
import py.console.bean.PerformanceSearchTemplate;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.thrift.monitorserver.service.IllegalParameterExceptionThrift;
import py.thrift.monitorserver.service.PerformanceDataTimeCrossBorderExceptionThrift;
import py.thrift.monitorserver.service.PerformanceDataTimeSpanIsBigExceptionThrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;

/**
 * AlertService.
 */
public interface AlertService {

  public void saveMessageForwardItem(String phoneNum, String name, String description,
      boolean enable)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift, IllegalParameterExceptionThrift,
      TException;

  public void updateMessageForwardItem(long id, String phoneNum, String name, String description,
      boolean enable)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift, IllegalParameterExceptionThrift,
      TException;

  public void deleteMessageForwardItem(List<Long> idsList)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift, IllegalParameterExceptionThrift,
      TException;

  public List<MessageForwardItem> listMessageForwardItem()
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift, IllegalParameterExceptionThrift,
      TException;

  public Map<String, Map<String, Integer>> getPerformanceDataTimeSpan()
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift, IllegalParameterExceptionThrift,
      TException;

  public Map<String, List<PerformanceItem>> getPerformanceItem();

  public void saveOrUpdatePerformanceSearchTemplate(
      PerformanceSearchTemplate performanceSearchTemplate)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      IllegalParameterExceptionThrift,
      PermissionNotGrantExceptionThrift, AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      PerformanceDataTimeCrossBorderExceptionThrift,
      PerformanceDataTimeSpanIsBigExceptionThrift, TException;

  public void deletePerformanceSearchTemplate(long accountId, long templateId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      IllegalParameterExceptionThrift,
      PermissionNotGrantExceptionThrift, AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public List<PerformanceSearchTemplate> listPerformanceSearchTemplate(long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      IllegalParameterExceptionThrift,
      PermissionNotGrantExceptionThrift, AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

}
