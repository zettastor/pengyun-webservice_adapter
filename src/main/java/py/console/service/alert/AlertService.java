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
