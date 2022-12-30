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

package py.console.service.alert.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.common.RequestIdBuilder;
import py.console.MessageForwardItem;
import py.console.bean.PerformanceItem;
import py.console.bean.PerformanceSearchTemplate;
import py.console.service.alert.AlertService;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.infocenter.client.InformationCenterClientFactory;
import py.monitor.common.CounterName;
import py.monitor.common.PerformanceTask;
import py.monitorserver.client.MonitorServerClientFactory;
import py.thrift.infocenter.service.InformationCenter;
import py.thrift.infocenter.service.VerifyReportStatisticsPermissionRequest;
import py.thrift.monitorserver.service.DeleteMessageForwardItemRequestThrift;
import py.thrift.monitorserver.service.DeletePerformanceSearchTemplateRequest;
import py.thrift.monitorserver.service.GetPerformanceDataTimeSpanRequestThrift;
import py.thrift.monitorserver.service.GetPerformanceDataTimeSpanResponseThrift;
import py.thrift.monitorserver.service.GetStatisticsRequestThrift;
import py.thrift.monitorserver.service.GetStatisticsResponseThrift;
import py.thrift.monitorserver.service.IllegalParameterExceptionThrift;
import py.thrift.monitorserver.service.ListMessageForwardItemRequestThrift;
import py.thrift.monitorserver.service.ListMessageForwardItemResponseThrift;
import py.thrift.monitorserver.service.ListPerformanceSearchTemplateRequest;
import py.thrift.monitorserver.service.ListPerformanceSearchTemplateResponse;
import py.thrift.monitorserver.service.MessageForwardItemThrift;
import py.thrift.monitorserver.service.MonitorServer;
import py.thrift.monitorserver.service.PerformanceDataTimeCrossBorderExceptionThrift;
import py.thrift.monitorserver.service.PerformanceDataTimeSpanIsBigExceptionThrift;
import py.thrift.monitorserver.service.PerformanceDataTimeUnit;
import py.thrift.monitorserver.service.PerformanceSearchTemplateThrift;
import py.thrift.monitorserver.service.SaveMessageForwardItemRequestThrift;
import py.thrift.monitorserver.service.SaveOrUpdatePerformanceSearchTemplateRequest;
import py.thrift.monitorserver.service.SourceObjectThrift;
import py.thrift.monitorserver.service.StatementStatisticsTimeUnit;
import py.thrift.monitorserver.service.UpdateMessageForwardItemRequestThrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;

/**
 * AlertServiceImpl.
 */
public class AlertServiceImpl implements AlertService {

  private static final Logger logger = LoggerFactory.getLogger(AlertServiceImpl.class);

  private MonitorServerClientFactory monitorServerClientFactory;

  private InformationCenterClientFactory infoCenterClientFactory;

  public MonitorServerClientFactory getMonitorServerClientFactory() {
    return monitorServerClientFactory;
  }

  public void setMonitorServerClientFactory(MonitorServerClientFactory monitorServerClientFactory) {
    this.monitorServerClientFactory = monitorServerClientFactory;
  }

  public InformationCenterClientFactory getInfoCenterClientFactory() {
    return infoCenterClientFactory;
  }

  public void setInfoCenterClientFactory(InformationCenterClientFactory infoCenterClientFactory) {
    this.infoCenterClientFactory = infoCenterClientFactory;
  }

  @Override
  public void saveMessageForwardItem(String phoneNum, String name, String description,
      boolean enable)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift, IllegalParameterExceptionThrift,
      TException {
    SaveMessageForwardItemRequestThrift request = new SaveMessageForwardItemRequestThrift();
    request.setRequestId(RequestIdBuilder.get());
    request.setPhoneNum(phoneNum);
    request.setName(name);
    request.setDesc(description);
    request.setEnable(enable);
    MonitorServer.Iface client = null;
    logger.debug("saveMessageForwardItem request is {}", request);
    try {
      client = monitorServerClientFactory.build().getClient();
      client.saveMessageForwardItem(request);
    } catch (EndPointNotFoundException e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (ServiceIsNotAvailableThrift e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (ServiceHavingBeenShutdownThrift e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (IllegalParameterExceptionThrift e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (TException e) {
      logger.error("caught an exception", e);
      throw e;
    }

  }

  @Override
  public void updateMessageForwardItem(long id, String phoneNum, String name, String description,
      boolean enable)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift, IllegalParameterExceptionThrift,
      TException {
    UpdateMessageForwardItemRequestThrift request = new UpdateMessageForwardItemRequestThrift();
    request.setRequestId(RequestIdBuilder.get());
    request.setId(id);
    request.setPhoneNum(phoneNum);
    request.setName(name);
    request.setDesc(description);
    request.setEnable(enable);
    MonitorServer.Iface client = null;
    logger.debug("updateMessageForwardItem request is {}", request);
    try {
      client = monitorServerClientFactory.build().getClient();
      client.updateMessageForwardItem(request);
    } catch (EndPointNotFoundException e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (ServiceIsNotAvailableThrift e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (ServiceHavingBeenShutdownThrift e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (IllegalParameterExceptionThrift e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (TException e) {
      logger.error("caught an exception", e);
      throw e;
    }

  }

  @Override
  public void deleteMessageForwardItem(List<Long> idsList)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift, IllegalParameterExceptionThrift,
      TException {
    DeleteMessageForwardItemRequestThrift request = new DeleteMessageForwardItemRequestThrift();
    List<MessageForwardItemThrift> itemList = new ArrayList<>();
    for (long id : idsList) {
      MessageForwardItemThrift item = new MessageForwardItemThrift();
      item.setId(id);
      itemList.add(item);
    }
    request.setRequestId(RequestIdBuilder.get());
    request.setMessageForwardItemThriftList(itemList);
    MonitorServer.Iface client = null;
    logger.debug("deleteMessageForwardItem request is {}", request);
    try {
      client = monitorServerClientFactory.build().getClient();
      client.deleteMessageForwardItem(request);
    } catch (EndPointNotFoundException e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (ServiceIsNotAvailableThrift e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (ServiceHavingBeenShutdownThrift e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (IllegalParameterExceptionThrift e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (TException e) {
      logger.error("caught an exception", e);
      throw e;
    }

  }

  @Override
  public List<MessageForwardItem> listMessageForwardItem()
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift, IllegalParameterExceptionThrift,
      TException {
    ListMessageForwardItemRequestThrift request = new ListMessageForwardItemRequestThrift();
    ListMessageForwardItemResponseThrift response = new ListMessageForwardItemResponseThrift();
    request.setRequestId(RequestIdBuilder.get());
    List<MessageForwardItem> itemList = new ArrayList<>();
    MonitorServer.Iface client = null;
    logger.debug("listMessageForwardItem request is {}", request);
    try {
      client = monitorServerClientFactory.build().getClient();
      response = client.listMessageForwardItem(request);
      logger.debug("listMessageForwardItem response is {}", request);
      if (response != null) {
        for (MessageForwardItemThrift itemThrift : response.getMessageForwardItemThriftList()) {
          MessageForwardItem item = new MessageForwardItem();
          item.setId(String.valueOf(itemThrift.getId()));
          item.setPhoneNum(itemThrift.getPhoneNum());
          item.setName(itemThrift.getName());
          item.setDescription(itemThrift.getDesc());
          item.setEnable(String.valueOf(itemThrift.isEnable()));
          itemList.add(item);
        }
      }
      logger.debug("listMessageForwardItem itemList is {}", itemList);
    } catch (EndPointNotFoundException e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (ServiceIsNotAvailableThrift e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (ServiceHavingBeenShutdownThrift e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (IllegalParameterExceptionThrift e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (TException e) {
      logger.error("caught an exception", e);
      throw e;
    }

    return itemList;
  }

  @Override
  public Map<String, Map<String, Integer>> getPerformanceDataTimeSpan()
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift, IllegalParameterExceptionThrift,
      TException {
    Map<String, Map<String, Integer>> performanceDataTimeSpan = new HashMap<>();
    GetPerformanceDataTimeSpanRequestThrift request = new GetPerformanceDataTimeSpanRequestThrift();
    GetPerformanceDataTimeSpanResponseThrift response =
        new GetPerformanceDataTimeSpanResponseThrift();
    request.setRequestId(RequestIdBuilder.get());
    try {
      MonitorServer.Iface client = monitorServerClientFactory.build().getClient();
      response = client.getPerformanceDataTimeSpan(request);
      if (response != null) {
        for (Map.Entry<PerformanceDataTimeUnit, Integer> entry : response.getTimeSpanMap()
            .entrySet()) {
          Map<String, Integer> value = new HashMap<>();
          value.put("timeSpan", entry.getValue());
          performanceDataTimeSpan.put(String.valueOf(entry.getKey()), value);

        }
        for (Map.Entry<PerformanceDataTimeUnit, Integer> entry : response.getRetentionTimeMap()
            .entrySet()) {
          performanceDataTimeSpan.get(String.valueOf(entry.getKey()))
              .put("retentionTime", entry.getValue());
        }
      }
    } catch (EndPointNotFoundException e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (ServiceHavingBeenShutdownThrift e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (ServiceIsNotAvailableThrift e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (IllegalParameterExceptionThrift e) {
      logger.error("caught an exception", e);
      throw e;
    } catch (TException e) {
      logger.error("requestId is {}", request.getRequestId());
      logger.error("caught an exception", e);
      throw e;
    }
    return performanceDataTimeSpan;
  }

  @Override
  public Map<String, List<PerformanceItem>> getPerformanceItem() {
    List<PerformanceItem> simpleItem = new ArrayList<>();
    List<PerformanceItem> baseItem = new ArrayList<>();
    for (CounterName counterName : CounterName.values()) {
      PerformanceItem item = new PerformanceItem();
      item.setKey(counterName.name());
      item.setZhName(counterName.getCounterNameCn());
      item.setMonitorObject(counterName.getMonitorObjectEnum().name());
      item.setType(counterName.getCounterType().name());
      if (item.getType().equals("Status")) {
        baseItem.add(item);
      } else {
        simpleItem.add(item);
      }

    }
    Map<String, List<PerformanceItem>> result = new HashMap<>();
    logger.debug("result is {}", result);
    result.put("simpleItem", simpleItem);
    result.put("baseItem", baseItem);

    return result;
  }


  @Override
  public void saveOrUpdatePerformanceSearchTemplate(
      PerformanceSearchTemplate performanceSearchTemplate)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      IllegalParameterExceptionThrift,
      PermissionNotGrantExceptionThrift, AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      PerformanceDataTimeCrossBorderExceptionThrift,
      PerformanceDataTimeSpanIsBigExceptionThrift, TException {
    SaveOrUpdatePerformanceSearchTemplateRequest request =
        new SaveOrUpdatePerformanceSearchTemplateRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(Long.valueOf(performanceSearchTemplate.getAccountId()));
    if (!StringUtils.isEmpty(performanceSearchTemplate.getName())) {
      request.setName(performanceSearchTemplate.getName());
    }
    request.setStartTime(Long.valueOf(performanceSearchTemplate.getStartTime()));
    request.setEndTime(Long.valueOf(performanceSearchTemplate.getEndTime()));
    request.setTimeUnit(performanceSearchTemplate.getTimeUnit());
    request.setSourcesJson(performanceSearchTemplate.getSourcesJson());
    request.setCounterKeyJson(performanceSearchTemplate.getCounterKeyJson());
    request.setObjectType(performanceSearchTemplate.getObjectType());

    logger.debug("SaveOrUpdatePerformanceSearchTemplateRequest is {}", request);
    try {
      MonitorServer.Iface clientMs = monitorServerClientFactory.build().getClient();
      clientMs.saveOrUpdatePerformanceSearchTemplate(request);
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

  @Override
  public void deletePerformanceSearchTemplate(long accountId, long templateId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      IllegalParameterExceptionThrift,
      PermissionNotGrantExceptionThrift, AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    DeletePerformanceSearchTemplateRequest request = new DeletePerformanceSearchTemplateRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setId(templateId);

    try {
      MonitorServer.Iface clientMs = monitorServerClientFactory.build().getClient();
      clientMs.deletePerformanceSearchTemplate(request);
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

  @Override
  public List<PerformanceSearchTemplate> listPerformanceSearchTemplate(long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      IllegalParameterExceptionThrift,
      PermissionNotGrantExceptionThrift, AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    ListPerformanceSearchTemplateRequest request = new ListPerformanceSearchTemplateRequest();
    ListPerformanceSearchTemplateResponse response = new ListPerformanceSearchTemplateResponse();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    List<PerformanceSearchTemplate> performanceSearchTemplateList = new ArrayList<>();
    try {
      MonitorServer.Iface clientMs = monitorServerClientFactory.build().getClient();
      response = clientMs.listPerformanceSearchTemplate(request);
      if (response.getPerformanceSearchTemplateList() != null
          && response.getPerformanceSearchTemplateList().size() != 0) {
        for (PerformanceSearchTemplateThrift performanceSearchTemplateThrift :
            response.getPerformanceSearchTemplateList()) {
          PerformanceSearchTemplate performanceSearchTemplate = new PerformanceSearchTemplate();
          performanceSearchTemplate.setId(String.valueOf(performanceSearchTemplateThrift.getId()));
          performanceSearchTemplate.setName(performanceSearchTemplateThrift.getName());
          performanceSearchTemplate.setStartTime(
              String.valueOf(performanceSearchTemplateThrift.getStartTime()));
          performanceSearchTemplate.setEndTime(
              String.valueOf(performanceSearchTemplateThrift.getEndTime()));
          performanceSearchTemplate.setTimeUnit(performanceSearchTemplateThrift.getTimeUnit());
          performanceSearchTemplate.setCounterKeyJson(
              performanceSearchTemplateThrift.getCounterKeyJson());
          performanceSearchTemplate.setSourcesJson(
              performanceSearchTemplateThrift.getSourcesJson());
          performanceSearchTemplate.setAccountId(
              String.valueOf(performanceSearchTemplateThrift.getAccountId()));
          performanceSearchTemplate.setObjectType(performanceSearchTemplateThrift.getObjectType());
          performanceSearchTemplateList.add(performanceSearchTemplate);
        }
      }
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

    return performanceSearchTemplateList;
  }

}
