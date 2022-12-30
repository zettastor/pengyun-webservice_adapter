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

package py.console.service.operation.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.common.RequestIdBuilder;
import py.console.bean.SimpleDriverLinkedLog;
import py.console.bean.SimpleOperation;
import py.console.service.operation.OperationService;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.infocenter.client.InformationCenterClientFactory;
import py.thrift.icshare.ListDriverClientInfoRequest;
import py.thrift.icshare.ListDriverClientInfoResponse;
import py.thrift.infocenter.service.InformationCenter;
import py.thrift.infocenter.service.SaveOperationLogsToCsvRequest;
import py.thrift.infocenter.service.SaveOperationLogsToCsvResponse;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.DriverClientInfoThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.ListOperationsRequest;
import py.thrift.share.ListOperationsResponse;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.OperationNotFoundExceptionThrift;
import py.thrift.share.OperationThrift;
import py.thrift.share.ParametersIsErrorExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;
import py.thrift.share.UnsupportedEncodingExceptionThrift;

/**
 * OperationServiceImpl.
 */
public class OperationServiceImpl implements OperationService {

  private static final Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);
  private InformationCenterClientFactory infoCenterClientFactory;

  private String deployPath;
  private String ftpUsername;
  private String ftpPwd;

  public String getDeployPath() {
    return deployPath;
  }

  public void setDeployPath(String deployPath) {
    this.deployPath = deployPath;
  }

  public String getFtpUsername() {
    return ftpUsername;
  }

  public void setFtpUsername(String ftpUsername) {
    this.ftpUsername = ftpUsername;
  }

  public String getFtpPwd() {
    return ftpPwd;
  }

  public void setFtpPwd(String ftpPwd) {
    this.ftpPwd = ftpPwd;
  }

  public InformationCenterClientFactory getInfoCenterClientFactory() {
    return infoCenterClientFactory;
  }

  public void setInfoCenterClientFactory(InformationCenterClientFactory infoCenterClientFactory) {
    this.infoCenterClientFactory = infoCenterClientFactory;
  }

  @Override
  public List<SimpleOperation> listOperations(List<Long> ids, long accountId,
      SimpleOperation operationCondition)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      OperationNotFoundExceptionThrift,
      PermissionNotGrantExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    ListOperationsRequest request = new ListOperationsRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);

    if (ids != null && ids.size() != 0) {
      request.setOperationIds(ids);
    }
    if (operationCondition != null) {
      if (!StringUtils.isEmpty(operationCondition.getAccountName())) {
        request.setAccountName(operationCondition.getAccountName());
      }
      if (!StringUtils.isEmpty(operationCondition.getType())) {
        request.setOperationType(operationCondition.getType());
      }
      if (!StringUtils.isEmpty(operationCondition.getTargetType())) {
        request.setTargetType(operationCondition.getTargetType());
      }
      if (!StringUtils.isEmpty(operationCondition.getTargetName())) {
        request.setTargetName(operationCondition.getTargetName());
      }
      if (!StringUtils.isEmpty(operationCondition.getStatus())) {
        request.setStatus(operationCondition.getStatus());
      }
      if (!StringUtils.isEmpty(operationCondition.getStartTime())) {
        request.setStartTime(Long.valueOf(operationCondition.getStartTime()));
      }
      if (!StringUtils.isEmpty(operationCondition.getEndTime())) {
        request.setEndTime(Long.valueOf(operationCondition.getEndTime()));
      }
    }
    List<SimpleOperation> operationsList = new ArrayList<>();
    logger.debug("listOperations request is {}", request);
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      ListOperationsResponse response = client.listOperations(request);
      logger.debug("listOperations response is {}", response);
      for (OperationThrift operationThrift : response.getOperationList()) {
        SimpleOperation operation = new SimpleOperation();
        operation.setOperationId(String.valueOf(operationThrift.getOperationId()));
        operation.setTargetId(String.valueOf(operationThrift.getTargetId()));
        operation.setTargetName(operationThrift.getTargetName());
        /* operation.setTargetObject(String.valueOf(operationThrift.getOperationTargetThrift()));*/
        operation.setOperationObject(operationThrift.getOperationObject());
        operation.setTargetType(operationThrift.getOperationTarget());
        operation.setStartTime(String.valueOf(operationThrift.getStartTime()));
        operation.setEndTime(String.valueOf(operationThrift.getEndTime()));
        operation.setDescription(operationThrift.getDescription());
        operation.setStatus(operationThrift.getStatus());
        operation.setProgress(String.valueOf(operationThrift.getProgress()));
        operation.setErrorMessage(operationThrift.getErrorMessage());
        operation.setAccountId(String.valueOf(operationThrift.getAccountId()));
        operation.setType(String.valueOf(operationThrift.getOperationType()));
        operation.setAccountName(String.valueOf(operationThrift.getAccountName()));
        operationsList.add(operation);
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
    logger.debug("operationsList is [{}]", operationsList);
    return operationsList;
  }

  @Override
  public Map<String, String> getFtpInfo() {
    Map<String, String> ftpInfo = new HashMap<>();
    ftpInfo.put("ftpUsername", ftpUsername);
    ftpInfo.put("ftpPwd", ftpPwd);
    ftpInfo.put("deployPath", deployPath);
    return ftpInfo;
  }

  @Override
  public byte[] saveOperationLogsToCsv(long accountId, SimpleOperation operationCondition)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, UnsupportedEncodingExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    SaveOperationLogsToCsvRequest request = new SaveOperationLogsToCsvRequest();
    SaveOperationLogsToCsvResponse response = new SaveOperationLogsToCsvResponse();
    byte[] csvFile = null;
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    if (!StringUtils.isEmpty(operationCondition.getAccountName())) {
      request.setAccountName(operationCondition.getAccountName());
    }
    if (!StringUtils.isEmpty(operationCondition.getType())) {
      request.setOperationType(operationCondition.getType());
    }
    if (!StringUtils.isEmpty(operationCondition.getTargetType())) {
      request.setTargetType(operationCondition.getTargetType());
    }
    if (!StringUtils.isEmpty(operationCondition.getTargetName())) {
      request.setTargetName(operationCondition.getTargetName());
    }
    if (!StringUtils.isEmpty(operationCondition.getStatus())) {
      request.setStatus(operationCondition.getStatus());
    }
    if (!StringUtils.isEmpty(operationCondition.getStartTime())) {
      request.setStartTime(Long.valueOf(operationCondition.getStartTime()));
    }
    if (!StringUtils.isEmpty(operationCondition.getEndTime())) {
      request.setEndTime(Long.valueOf(operationCondition.getEndTime()));
    }
    logger.debug("saveOperationLogsToCSV request is [{}]", request);
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      response = client.saveOperationLogsToCsv(request);
      logger.debug("saveOperationLogsToCSV response is [{}]", response);
      csvFile = response.getCsvFile();
      logger.debug("csvFile is [{}]", csvFile);
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
    return csvFile;
  }

  @Override
  public List<SimpleDriverLinkedLog> listDriverLinkedLog(long accountId, String driverName,
      String volumeName, String volumeDesc)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, ParametersIsErrorExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    ListDriverClientInfoRequest request = new ListDriverClientInfoRequest();
    request.setRequestId(RequestIdBuilder.get());
    if (!StringUtils.isEmpty(driverName)) {
      request.setDriverName(driverName);
    }
    if (!StringUtils.isEmpty(volumeName)) {
      request.setVolumeName(volumeName);
    }
    if (!StringUtils.isEmpty(volumeDesc)) {
      request.setVolumeDescription(volumeDesc);
    }
    InformationCenter.Iface client = null;
    List<SimpleDriverLinkedLog> driverLinkedLogList = new ArrayList<>();
    logger.debug("ListDriverClientInfoRequest is [{}]", request);
    try {
      client = infoCenterClientFactory.build().getClient();
      ListDriverClientInfoResponse response = client.listDriverClientInfo(request);
      logger.debug("ListDriverClientInfoResponse is [{}]", response);
      if (response != null && response.getDriverClientInfothrift() != null) {
        for (DriverClientInfoThrift driverClientInfothrift : response
            .getDriverClientInfothrift()) {
          SimpleDriverLinkedLog driverLinkedLog = new SimpleDriverLinkedLog();
          driverLinkedLog
              .setDriverContainerId(String.valueOf(driverClientInfothrift.getDriverContainerId()));
          driverLinkedLog.setVolumeId(String.valueOf(driverClientInfothrift.getVolumeId()));
          driverLinkedLog.setSnapshotId(String.valueOf(driverClientInfothrift.getSnapshotId()));
          driverLinkedLog.setDriverType(driverClientInfothrift.getDriverType().name());
          driverLinkedLog.setClientInfo(driverClientInfothrift.getClientInfo());
          driverLinkedLog.setTime(String.valueOf(driverClientInfothrift.getTime()));
          driverLinkedLog.setDriverName(driverClientInfothrift.getDriverName());
          driverLinkedLog.setHostName(driverClientInfothrift.getHostName());
          driverLinkedLog.setStatus(String.valueOf(driverClientInfothrift.isStatus()));
          driverLinkedLog.setVolumeName(driverClientInfothrift.getVolumeName());
          driverLinkedLog.setVolumeDesc(driverClientInfothrift.getVolumeDescription());
          driverLinkedLogList.add(driverLinkedLog);
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

    return driverLinkedLogList;
  }

}
