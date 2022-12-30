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

package py.console.service.access.rule.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.common.Constants;
import py.common.PyService;
import py.common.RequestIdBuilder;
import py.console.bean.SimpleDriverClientInfo;
import py.console.bean.SimpleDriverMetadata;
import py.console.bean.SimpleInstance;
import py.console.bean.SimpleIscsiAccessRule;
import py.console.bean.SimpleVolumeAccessRule;
import py.console.bean.SimpleVolumeMetadata;
import py.console.service.access.rule.VolumeAccessRuleOperationResult;
import py.console.service.access.rule.VolumeAccessRuleService;
import py.console.service.driver.DriverService;
import py.console.service.instance.InstanceService;
import py.console.service.volume.VolumeService;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.infocenter.client.InformationCenterClientFactory;
import py.infocenter.client.InformationCenterClientWrapper;
import py.instance.InstanceStatus;
import py.thrift.infocenter.service.FailedToTellDriverAboutAccessRulesExceptionThrift;
import py.thrift.infocenter.service.InformationCenter;
import py.thrift.share.AccessDeniedExceptionThrift;
import py.thrift.share.AccessPermissionTypeThrift;
import py.thrift.share.AccessRuleNotAppliedThrift;
import py.thrift.share.AccessRuleNotFoundThrift;
import py.thrift.share.AccessRuleUnderOperationThrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.ApplyFailedDueToConflictExceptionThrift;
import py.thrift.share.ApplyFailedDueToVolumeIsReadOnlyExceptionThrift;
import py.thrift.share.ApplyIscsiAccessRuleOnIscsisRequest;
import py.thrift.share.ApplyIscsiAccessRulesRequest;
import py.thrift.share.ApplyVolumeAccessRuleOnVolumesRequest;
import py.thrift.share.ApplyVolumeAccessRuleOnVolumesResponse;
import py.thrift.share.ApplyVolumeAccessRulesRequest;
import py.thrift.share.ApplyVolumeAccessRulesResponse;
import py.thrift.share.CancelIscsiAccessRuleAllAppliedRequest;
import py.thrift.share.CancelIscsiAccessRulesRequest;
import py.thrift.share.CancelIscsiAccessRulesResponse;
import py.thrift.share.CancelVolAccessRuleAllAppliedRequest;
import py.thrift.share.CancelVolumeAccessRulesRequest;
import py.thrift.share.CancelVolumeAccessRulesResponse;
import py.thrift.share.ChapSameUserPasswdErrorThrift;
import py.thrift.share.CreateIscsiAccessRulesRequest;
import py.thrift.share.CreateVolumeAccessRulesRequest;
import py.thrift.share.DeleteIscsiAccessRulesRequest;
import py.thrift.share.DeleteIscsiAccessRulesResponse;
import py.thrift.share.DeleteVolumeAccessRulesRequest;
import py.thrift.share.DeleteVolumeAccessRulesResponse;
import py.thrift.share.DriverKeyThrift;
import py.thrift.share.DriverMetadataThrift;
import py.thrift.share.DriverTypeThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.GetAppliedIscsisRequest;
import py.thrift.share.GetAppliedIscsisResponse;
import py.thrift.share.GetAppliedVolumesRequest;
import py.thrift.share.GetAppliedVolumesResponse;
import py.thrift.share.GetIscsiAccessRulesRequest;
import py.thrift.share.GetIscsiAccessRulesResponse;
import py.thrift.share.GetVolumeAccessRulesRequest;
import py.thrift.share.GetVolumeAccessRulesResponse;
import py.thrift.share.InvalidInputExceptionThrift;
import py.thrift.share.IscsiAccessRuleDuplicateThrift;
import py.thrift.share.IscsiAccessRuleFormatErrorThrift;
import py.thrift.share.IscsiAccessRuleNotFoundThrift;
import py.thrift.share.IscsiAccessRuleThrift;
import py.thrift.share.IscsiAccessRuleUnderOperationThrift;
import py.thrift.share.IscsiBeingDeletedExceptionThrift;
import py.thrift.share.IscsiNotFoundExceptionThrift;
import py.thrift.share.ListIscsiAccessRulesRequest;
import py.thrift.share.ListIscsiAccessRulesResponse;
import py.thrift.share.ListVolumeAccessRulesRequest;
import py.thrift.share.ListVolumeAccessRulesResponse;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.ResourceNotExistsExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;
import py.thrift.share.VolumeAccessRuleDuplicateThrift;
import py.thrift.share.VolumeAccessRuleThrift;
import py.thrift.share.VolumeBeingDeletedExceptionThrift;
import py.thrift.share.VolumeMetadataThrift;
import py.thrift.share.VolumeNotFoundExceptionThrift;

/**
 * VolumeAccessRuleServiceImpl.
 */
public class VolumeAccessRuleServiceImpl implements VolumeAccessRuleService {

  private static final Logger logger = LoggerFactory.getLogger(VolumeAccessRuleServiceImpl.class);

  private InformationCenterClientFactory infoCenterClientFactory;

  private VolumeService volumeService;

  private DriverService driverService;

  private InstanceService instanceService;

  public InstanceService getInstanceService() {
    return instanceService;
  }

  public void setInstanceService(InstanceService instanceService) {
    this.instanceService = instanceService;
  }

  public DriverService getDriverService() {
    return driverService;
  }

  public void setDriverService(DriverService driverService) {
    this.driverService = driverService;
  }

  public VolumeService getVolumeService() {
    return volumeService;
  }

  public void setVolumeService(VolumeService volumeService) {
    this.volumeService = volumeService;
  }

  public InformationCenterClientFactory getInfoCenterClientFactory() {
    return infoCenterClientFactory;
  }

  public void setInfoCenterClientFactory(InformationCenterClientFactory infoCenterClientFactory) {
    this.infoCenterClientFactory = infoCenterClientFactory;
  }

  @Override
  public List<SimpleVolumeAccessRule> listVolumeAccessRules(long accountId)
      throws ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    logger.debug("Going to list all volume access rules");

    List<SimpleVolumeAccessRule> simpleVolumeAccessRuleList =
        new ArrayList<SimpleVolumeAccessRule>();
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();

      ListVolumeAccessRulesRequest listVolumeAccessRulesRequest = new ListVolumeAccessRulesRequest(
          RequestIdBuilder.get(), accountId);
      ListVolumeAccessRulesResponse listVolumeAccessRulesResponse = client
          .listVolumeAccessRules(listVolumeAccessRulesRequest);
      if (listVolumeAccessRulesResponse != null
          && listVolumeAccessRulesResponse.getAccessRules() != null
          && listVolumeAccessRulesResponse.getAccessRulesSize() > 0) {
        for (VolumeAccessRuleThrift volumeAccessRuleThrift : listVolumeAccessRulesResponse
            .getAccessRules()) {
          simpleVolumeAccessRuleList
              .add(
                  SimpleVolumeAccessRule.buildSimpleVolumeAccessRuleFrom(volumeAccessRuleThrift));
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

    logger.debug("All volume access rules: {}", simpleVolumeAccessRuleList);
    return simpleVolumeAccessRuleList;
  }

  @Override
  public void createVolumeAccessRules(List<SimpleVolumeAccessRule> simpleVolumeAccessRuleList,
      long accountId)
      throws VolumeAccessRuleDuplicateThrift, InvalidInputExceptionThrift,
      ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      AccountNotFoundExceptionThrift,
      TException {
    logger.debug("Going to create volume access rules {}", simpleVolumeAccessRuleList);

    try {
      CreateVolumeAccessRulesRequest createVolumeAccessRulesRequest =
          new CreateVolumeAccessRulesRequest();
      createVolumeAccessRulesRequest.setRequestId(RequestIdBuilder.get());
      createVolumeAccessRulesRequest.setAccountId(accountId);
      if (simpleVolumeAccessRuleList != null && simpleVolumeAccessRuleList.size() > 0) {
        for (SimpleVolumeAccessRule simpleVolumeAccessRule : simpleVolumeAccessRuleList) {
          createVolumeAccessRulesRequest
              .addToAccessRules(simpleVolumeAccessRule.toVolumeAccessRuleThrift());
        }
      }
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.createVolumeAccessRules(createVolumeAccessRulesRequest);
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
  public VolumeAccessRuleOperationResult deleteVolumeAccessRules(long accountId,
      List<SimpleVolumeAccessRule> ruleListToDelete, boolean isConfirm)
      throws ServiceHavingBeenShutdownThrift, FailedToTellDriverAboutAccessRulesExceptionThrift,
      ServiceIsNotAvailableThrift, PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    logger.debug("Going to delete volume access rules {} with confirm {}", ruleListToDelete,
        isConfirm);

    long requestId = RequestIdBuilder.get();
    VolumeAccessRuleOperationResult result = new VolumeAccessRuleOperationResult();
    try {
      InformationCenterClientWrapper ccClientWrapper = infoCenterClientFactory.build();
      if (!isConfirm) {
        for (SimpleVolumeAccessRule rule : ruleListToDelete) {
          List<Long> appliedVolumeIdList = ccClientWrapper
              .getAppliedVolumeIds(requestId, accountId, Long.parseLong(rule.getRuleId()));

          if (appliedVolumeIdList == null) {
            logger.error("Something wrong to check if the rule {} is applied to some volume",
                rule.getRuleId());
            throw new TException();
          }
          if (!appliedVolumeIdList.isEmpty()) {
            List<SimpleVolumeMetadata> volumeList = new ArrayList<>();
            Set<Long> volumeIdSet = new HashSet<>();
            volumeIdSet.addAll(appliedVolumeIdList);
            List<String> volumeListString = new ArrayList<>();

            volumeList = volumeService.getMultipleVolumes(accountId, volumeIdSet);
            for (SimpleVolumeMetadata volume : volumeList) {
              volumeListString.add(volume.getVolumeName());
            }

            String ruleString = rule.getRemoteHostName() + ":" + rule.getPermission() + " ";
            result.putToAppliedRule2VolumeTable(ruleString, volumeListString);
          }
        }
      }
      if (!result.getAppliedRule2VolumeTable().isEmpty()) {
        return result;
      }

      DeleteVolumeAccessRulesRequest deleteVolumeAccessRulesRequest =
          new DeleteVolumeAccessRulesRequest();
      deleteVolumeAccessRulesRequest.setAccountId(accountId);
      deleteVolumeAccessRulesRequest.setRequestId(RequestIdBuilder.get());
      for (SimpleVolumeAccessRule rule : ruleListToDelete) {
        deleteVolumeAccessRulesRequest.addToRuleIds(Long.parseLong(rule.getRuleId()));
      }
      DeleteVolumeAccessRulesResponse response = ccClientWrapper.getClient()
          .deleteVolumeAccessRules(deleteVolumeAccessRulesRequest);
      if (response.getAirAccessRuleList() != null && response.getAirAccessRuleListSize() > 0) {
        for (VolumeAccessRuleThrift accessRuleFromRemote : response.getAirAccessRuleList()) {
          result.addToExistingActionOnRuleList(accessRuleFromRemote.getRuleId());
        }
        return result;
      }

      return result;
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
  public List<SimpleVolumeAccessRule> getVolumeAccessRules(long volumeId, long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    logger.debug("Going to get volume access rules applied to volume {}", volumeId);

    List<SimpleVolumeAccessRule> simpleVolumeAccessRuleList =
        new ArrayList<SimpleVolumeAccessRule>();
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      GetVolumeAccessRulesRequest getVolumeAccessRulesRequest = new GetVolumeAccessRulesRequest();
      getVolumeAccessRulesRequest.setRequestId(RequestIdBuilder.get());
      getVolumeAccessRulesRequest.setVolumeId(volumeId);
      getVolumeAccessRulesRequest.setAccountId(accountId);
      GetVolumeAccessRulesResponse getVolumeAccessRulesResponse = client
          .getVolumeAccessRules(getVolumeAccessRulesRequest);
      if (getVolumeAccessRulesResponse != null
          && getVolumeAccessRulesResponse.getAccessRules() != null
          && getVolumeAccessRulesResponse.getAccessRulesSize() > 0) {
        for (VolumeAccessRuleThrift volumeAccessRuleThrift : getVolumeAccessRulesResponse
            .getAccessRules()) {
          simpleVolumeAccessRuleList
              .add(
                  SimpleVolumeAccessRule.buildSimpleVolumeAccessRuleFrom(volumeAccessRuleThrift));
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
    logger.debug("Get volume access rules: {}", simpleVolumeAccessRuleList);
    return simpleVolumeAccessRuleList;
  }

  @Override
  public VolumeAccessRuleOperationResult applyVolumeAccessRules(long volumeId,
      List<Long> ruleIdListToApply,
      long accountId)
      throws VolumeNotFoundExceptionThrift, VolumeBeingDeletedExceptionThrift,
      ServiceHavingBeenShutdownThrift, NetworkErrorExceptionThrift,
      FailedToTellDriverAboutAccessRulesExceptionThrift, ServiceIsNotAvailableThrift,
      ApplyFailedDueToVolumeIsReadOnlyExceptionThrift, PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      AccessDeniedExceptionThrift, InvalidInputExceptionThrift, TException {
    logger.debug("Going to apply volume access rules {} to volume {}", ruleIdListToApply, volumeId);

    VolumeAccessRuleOperationResult result;
    try {
      result = new VolumeAccessRuleOperationResult();
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      ApplyVolumeAccessRulesRequest applyVolumeAccessRulesRequest =
          new ApplyVolumeAccessRulesRequest();
      applyVolumeAccessRulesRequest.setRequestId(RequestIdBuilder.get());
      applyVolumeAccessRulesRequest.setVolumeId(volumeId);
      applyVolumeAccessRulesRequest.setRuleIds(ruleIdListToApply);
      applyVolumeAccessRulesRequest.setAccountId(accountId);
      ApplyVolumeAccessRulesResponse response = client
          .applyVolumeAccessRules(applyVolumeAccessRulesRequest);
      if (response.getAirAccessRuleList() != null && !response.getAirAccessRuleList().isEmpty()) {
        for (VolumeAccessRuleThrift ruleFromRemote : response.getAirAccessRuleList()) {
          result.addToExistingActionOnRuleList(ruleFromRemote.getRuleId());
        }
      }
      CreateIscsiAccessRulesRequest request = new CreateIscsiAccessRulesRequest();
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

    return result;
  }

  @Override
  public VolumeAccessRuleOperationResult cancelVolumeAccessRules(long volumeId,
      List<Long> ruleIdListToCancel,
      long accountId)
      throws ServiceHavingBeenShutdownThrift, FailedToTellDriverAboutAccessRulesExceptionThrift,
      ServiceIsNotAvailableThrift, AccessRuleNotAppliedThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, VolumeNotFoundExceptionThrift, NetworkErrorExceptionThrift,
      TooManyEndPointFoundExceptionThrift, AccessDeniedExceptionThrift,
      EndPointNotFoundExceptionThrift, InvalidInputExceptionThrift, TException {
    logger.debug("Going to cancle volume access rules {} from volume {}", ruleIdListToCancel,
        volumeId);

    VolumeAccessRuleOperationResult result = new VolumeAccessRuleOperationResult();
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      CancelVolumeAccessRulesRequest cancelVolumeAccessRulesRequest =
          new CancelVolumeAccessRulesRequest();
      cancelVolumeAccessRulesRequest.setRequestId(RequestIdBuilder.get());
      cancelVolumeAccessRulesRequest.setVolumeId(volumeId);
      cancelVolumeAccessRulesRequest.setRuleIds(ruleIdListToCancel);
      cancelVolumeAccessRulesRequest.setAccountId(accountId);
      CancelVolumeAccessRulesResponse response = client
          .cancelVolumeAccessRules(cancelVolumeAccessRulesRequest);
      if (response.getAirAccessRuleList() != null && !response.getAirAccessRuleList().isEmpty()) {
        for (VolumeAccessRuleThrift ruleFromRemote : response.getAirAccessRuleList()) {
          result.addToExistingActionOnRuleList(ruleFromRemote.getRuleId());
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

    return result;
  }

  @Override
  public void createIscsiAccessRules(long accountId,
      List<SimpleIscsiAccessRule> simpleIscsiAccessRules)
      throws IscsiAccessRuleDuplicateThrift, IscsiAccessRuleFormatErrorThrift,
      InvalidInputExceptionThrift, ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift, ChapSameUserPasswdErrorThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    InformationCenter.Iface client = null;
    CreateIscsiAccessRulesRequest request = new CreateIscsiAccessRulesRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    List<IscsiAccessRuleThrift> iscsiAccessRuleThrifts = new ArrayList<>();

    if (simpleIscsiAccessRules != null && simpleIscsiAccessRules.size() != 0) {
      for (SimpleIscsiAccessRule rule : simpleIscsiAccessRules) {
        IscsiAccessRuleThrift iscsiAccessRuleThrift = new IscsiAccessRuleThrift();
        iscsiAccessRuleThrift.setRuleId(Long.valueOf(rule.getRuleId()));
        iscsiAccessRuleThrift.setRuleNotes(rule.getRuleNotes());
        iscsiAccessRuleThrift.setInitiatorName(rule.getInitiatorName());
        iscsiAccessRuleThrift.setUser(rule.getUser());
        iscsiAccessRuleThrift.setPassed(rule.getPasswd());
        iscsiAccessRuleThrift
            .setPermission(AccessPermissionTypeThrift.valueOf(rule.getPermission()));
        iscsiAccessRuleThrift.setOutUser(rule.getOutUser());
        iscsiAccessRuleThrift.setOutPassed(rule.getOutPasswd());
        iscsiAccessRuleThrifts.add(iscsiAccessRuleThrift);
      }
    }

    request.setAccessRules(iscsiAccessRuleThrifts);
    logger.debug("CreateIscsiAccessRulesRequest is {}", request);

    try {
      client = infoCenterClientFactory.build().getClient();
      client.createIscsiAccessRules(request);

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
  public List<SimpleIscsiAccessRule> listIscsiAccessRules(long accountId)
      throws ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    List<SimpleIscsiAccessRule> simpleIscsiAccessRulesList = new ArrayList<>();
    ListIscsiAccessRulesRequest request = new ListIscsiAccessRulesRequest();
    ListIscsiAccessRulesResponse response = new ListIscsiAccessRulesResponse();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      logger.debug("ListIscsiAccessRulesRequest is {}", request);
      response = client.listIscsiAccessRules(request);
      if (response != null && response.isSetAccessRules()) {
        for (IscsiAccessRuleThrift ruleThrift : response.getAccessRules()) {
          SimpleIscsiAccessRule accessRule = new SimpleIscsiAccessRule();
          accessRule.setRuleId(String.valueOf(ruleThrift.getRuleId()));
          accessRule.setRuleNotes(ruleThrift.getRuleNotes());
          accessRule.setInitiatorName(ruleThrift.getInitiatorName());
          accessRule.setUser(ruleThrift.getUser());
          accessRule.setPasswd(ruleThrift.getPassed());
          accessRule.setOutUser(ruleThrift.getOutUser());
          accessRule.setOutPasswd(ruleThrift.getOutPassed());
          accessRule.setPermission(ruleThrift.getPermission().name());
          accessRule.setStatus(ruleThrift.getStatus().name());
          simpleIscsiAccessRulesList.add(accessRule);
        }
      }
      logger.debug("ListIscsiAccessRulesResponse is {}", response);

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

    return simpleIscsiAccessRulesList;
  }

  @Override
  public void applyIscsiAccessRules(long accountId, List<Long> ruleIds, SimpleDriverMetadata driver)
      throws IscsiNotFoundExceptionThrift, IscsiBeingDeletedExceptionThrift,
      ServiceHavingBeenShutdownThrift, NetworkErrorExceptionThrift,
      FailedToTellDriverAboutAccessRulesExceptionThrift, ServiceIsNotAvailableThrift,
      ApplyFailedDueToConflictExceptionThrift, PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      AccountNotFoundExceptionThrift, AccessDeniedExceptionThrift, TException {
    ApplyIscsiAccessRulesRequest request = new ApplyIscsiAccessRulesRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    DriverKeyThrift driverKeyThrift = new DriverKeyThrift();
    driverKeyThrift.setDriverContainerId(Long.valueOf(driver.getDriverContainerId()));
    driverKeyThrift.setSnapshotId(Integer.valueOf(driver.getSnapshotId()));
    driverKeyThrift.setVolumeId(Long.valueOf(driver.getVolumeId()));
    driverKeyThrift.setDriverType(DriverTypeThrift.valueOf(driver.getDriverType()));
    request.setDriverKey(driverKeyThrift);
    request.setRuleIds(ruleIds);
    request.setCommit(true);
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      logger.debug("ApplyIscsiAccessRulesRequest is {}", request);
      client.applyIscsiAccessRules(request);
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
  public Map<String, Object> cancelIscsiAccessRules(long accountId, List<Long> ruleIds,
      SimpleDriverMetadata driver)
      throws ServiceHavingBeenShutdownThrift, FailedToTellDriverAboutAccessRulesExceptionThrift,
      ServiceIsNotAvailableThrift, AccessRuleNotAppliedThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, AccessDeniedExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    CancelIscsiAccessRulesRequest request = new CancelIscsiAccessRulesRequest();
    CancelIscsiAccessRulesResponse response = new CancelIscsiAccessRulesResponse();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    DriverKeyThrift driverKeyThrift = new DriverKeyThrift();
    driverKeyThrift.setDriverContainerId(Long.valueOf(driver.getDriverContainerId()));
    driverKeyThrift.setSnapshotId(Integer.valueOf(driver.getSnapshotId()));
    driverKeyThrift.setVolumeId(Long.valueOf(driver.getVolumeId()));
    driverKeyThrift.setDriverType(DriverTypeThrift.valueOf(driver.getDriverType()));
    request.setDriverKey(driverKeyThrift);
    request.setRuleIds(ruleIds);
    request.setCommit(true);
    logger.debug("CancelIscsiAccessRulesRequest is {}", request);
    Map<String, Object> result = new HashMap<>();
    List<SimpleIscsiAccessRule> iscsiAccessRulesHasAction = new ArrayList<>();

    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      response = client.cancelIscsiAccessRules(request);
      if (response.getAirAccessRuleList() != null && response.getAirAccessRuleListSize() > 0) {
        for (IscsiAccessRuleThrift ruleThrift : response.getAirAccessRuleList()) {
          SimpleIscsiAccessRule accessRule = new SimpleIscsiAccessRule();
          accessRule.setRuleId(String.valueOf(ruleThrift.getRuleId()));
          accessRule.setRuleNotes(ruleThrift.getRuleNotes());
          accessRule.setInitiatorName(ruleThrift.getInitiatorName());
          accessRule.setUser(ruleThrift.getUser());
          accessRule.setPasswd(ruleThrift.getPassed());
          accessRule.setOutUser(ruleThrift.getOutUser());
          accessRule.setOutPasswd(ruleThrift.getOutPassed());
          accessRule.setPermission(ruleThrift.getPermission().name());
          accessRule.setStatus(ruleThrift.getStatus().name());
          iscsiAccessRulesHasAction.add(accessRule);
        }

      }
      result.put("iscsiAccessRulesHasAction", iscsiAccessRulesHasAction);
      return result;
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
  public List<SimpleIscsiAccessRule> getIscsiAccessRulesAppliedOnOneDriver(long accountId,
      SimpleDriverMetadata driver)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift, NetworkErrorExceptionThrift,
      TooManyEndPointFoundExceptionThrift, TException {
    GetIscsiAccessRulesRequest request = new GetIscsiAccessRulesRequest();
    GetIscsiAccessRulesResponse response = new GetIscsiAccessRulesResponse();
    List<SimpleIscsiAccessRule> iscsiAccessRules = new ArrayList<>();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    DriverKeyThrift driverKeyThrift = new DriverKeyThrift();
    driverKeyThrift.setDriverContainerId(Long.valueOf(driver.getDriverContainerId()));
    driverKeyThrift.setSnapshotId(Integer.valueOf(driver.getSnapshotId()));
    driverKeyThrift.setVolumeId(Long.valueOf(driver.getVolumeId()));
    driverKeyThrift.setDriverType(DriverTypeThrift.valueOf(driver.getDriverType()));
    request.setDriverKey(driverKeyThrift);

    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      logger.debug("GetIscsiAccessRulesRequest is {}", request);
      response = client.getIscsiAccessRules(request);
      logger.debug("GetIscsiAccessRulesResponse is {}", response);
      if (response != null && response.isSetAccessRules()) {
        for (IscsiAccessRuleThrift ruleThrift : response.getAccessRules()) {
          SimpleIscsiAccessRule accessRule = new SimpleIscsiAccessRule();
          accessRule.setRuleId(String.valueOf(ruleThrift.getRuleId()));
          accessRule.setRuleNotes(ruleThrift.getRuleNotes());
          accessRule.setInitiatorName(ruleThrift.getInitiatorName());
          accessRule.setUser(ruleThrift.getUser());
          accessRule.setPasswd(ruleThrift.getPassed());
          accessRule.setOutUser(ruleThrift.getOutUser());
          accessRule.setOutPasswd(ruleThrift.getOutPassed());
          accessRule.setPermission(ruleThrift.getPermission().name());
          accessRule.setStatus(ruleThrift.getStatus().name());
          iscsiAccessRules.add(accessRule);
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

    return iscsiAccessRules;
  }

  @Override
  public List<SimpleVolumeMetadata> getAppliedVolumes(long accountId, long ruleId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift, NetworkErrorExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      TException {
    List<SimpleVolumeMetadata> appliedVolumes = new ArrayList<>();
    GetAppliedVolumesRequest request = new GetAppliedVolumesRequest();
    GetAppliedVolumesResponse response = new GetAppliedVolumesResponse();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setRuleId(ruleId);
    logger.debug("getAppliedVolumes request is {}", request);
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      response = client.getAppliedVolumes(request);
      logger.debug("getAppliedVolumes response is {}", response);
      if (response != null && response.getVolumeIdList().size() != 0) {
        Set<Long> ids = new HashSet<>();
        ids.addAll(response.getVolumeIdList());
        appliedVolumes = volumeService.getMultipleVolumes(Long.valueOf(accountId), ids);
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

    return appliedVolumes;
  }

  @Override
  public List<SimpleVolumeMetadata> getUnappliedVolumes(long accountId, long ruleId)
      throws AccessDeniedExceptionThrift, ResourceNotExistsExceptionThrift,
      InvalidInputExceptionThrift, ServiceIsNotAvailableThrift,
      ServiceHavingBeenShutdownThrift, VolumeNotFoundExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    List<SimpleVolumeMetadata> allVolumes = new ArrayList<>();
    List<SimpleVolumeMetadata> appliedVolumes = new ArrayList<>();
    List<SimpleVolumeMetadata> unappliedVolumes = new ArrayList<>();
    try {
      allVolumes = volumeService.getMultipleVolumes(Long.valueOf(accountId), null);
      appliedVolumes = getAppliedVolumes(accountId, ruleId);
      for (SimpleVolumeMetadata volume : allVolumes) {
        boolean appliedFlag = false;
        for (SimpleVolumeMetadata appliedVolume : appliedVolumes) {

          if (appliedVolume.getVolumeId().equals(volume.getVolumeId())) {
            appliedFlag = true;
            break;
          }
        }
        if (!appliedFlag) {
          unappliedVolumes.add(volume);
        }
      }
      logger.debug("unappliedVolumes is {}", unappliedVolumes);
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }
    return unappliedVolumes;
  }

  @Override
  public List<SimpleVolumeMetadata> applyVolumeAccessRuleOnVolumes(long accountId, long ruleId,
      List<Long> volumeIds)
      throws VolumeNotFoundExceptionThrift, VolumeBeingDeletedExceptionThrift,
      ServiceHavingBeenShutdownThrift, TooManyEndPointFoundExceptionThrift,
      ServiceIsNotAvailableThrift, ApplyFailedDueToVolumeIsReadOnlyExceptionThrift,
      AccessRuleUnderOperationThrift, AccessRuleNotFoundThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    ApplyVolumeAccessRuleOnVolumesRequest request = new ApplyVolumeAccessRuleOnVolumesRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setRuleId(ruleId);
    request.setVolumeIds(volumeIds);
    request.setCommit(true);
    List<SimpleVolumeMetadata> errorVolumeList = new ArrayList<>();
    ApplyVolumeAccessRuleOnVolumesResponse response = new ApplyVolumeAccessRuleOnVolumesResponse();
    logger.debug("applyVolumeAccessRuleOnVolumes request is {}", request);

    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      response = client.applyVolumeAccessRuleOnVolumes(request);
      if (response != null && response.getAirVolumeList() != null) {
        for (VolumeMetadataThrift volumeMetadataThrift : response.getAirVolumeList()) {
          SimpleVolumeMetadata volumeMetadata = new SimpleVolumeMetadata();
          volumeMetadata.setVolumeId(String.valueOf(volumeMetadataThrift.getVolumeId()));
          volumeMetadata.setVolumeName(String.valueOf(volumeMetadataThrift.getName()));
          errorVolumeList.add(volumeMetadata);
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
    return errorVolumeList;

  }

  @Override
  public void cancelVolAccessRuleAllApplied(long accountId, long ruleId, List<Long> volumeIds)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      AccessRuleNotAppliedThrift, TooManyEndPointFoundExceptionThrift,
      AccessRuleUnderOperationThrift, AccessRuleNotFoundThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    CancelVolAccessRuleAllAppliedRequest request = new CancelVolAccessRuleAllAppliedRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setRuleId(ruleId);
    request.setVolumeIds(volumeIds);
    request.setCommit(true);

    logger.debug("cancelVolAccessRuleAllApplied request is {}", request);
    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.cancelVolAccessRuleAllApplied(request);
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
  public List<SimpleDriverMetadata> getDriversBeUnappliedOneIscsisRule(long accountId, long ruleId)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException, ServiceIsNotAvailableThrift,
      ServiceHavingBeenShutdownThrift, Exception {
    List<SimpleDriverMetadata> allIscsiDriver = new ArrayList<>();
    List<SimpleDriverMetadata> appliedIscsiDriver = new ArrayList<>();
    List<SimpleDriverMetadata> unappliedIscsiDriver = new ArrayList<>();
    SimpleDriverMetadata driverCondition = new SimpleDriverMetadata();
    driverCondition.setDriverType(DriverTypeThrift.ISCSI.name());
    try {
      allIscsiDriver = driverService.listAllDrivers(driverCondition);
      appliedIscsiDriver = getDriversBeAppliedOneIscsisRule(accountId, ruleId);
      for (SimpleDriverMetadata driver : allIscsiDriver) {
        boolean appliedFlag = false;
        for (SimpleDriverMetadata appliedDriver : appliedIscsiDriver) {
          if (appliedDriver.getDriverContainerId().equals(driver.getDriverContainerId())
              && appliedDriver
              .getVolumeId().equals(driver.getVolumeId()) && appliedDriver.getSnapshotId()
              .equals(driver.getSnapshotId())) {
            appliedFlag = true;
          }
        }
        if (!appliedFlag) {
          unappliedIscsiDriver.add(driver);
        }
      }
    } catch (ServiceIsNotAvailableThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (ServiceHavingBeenShutdownThrift e) {
      logger.error("Exception catch", e);
      throw e;
    } catch (Exception e) {
      logger.error("Exception catch", e);
      throw e;
    }

    return unappliedIscsiDriver;
  }

  @Override
  public void applyIscsiAccessRuleOnIscsis(long accountId, long ruleId,
      List<SimpleDriverMetadata> driverList)
      throws IscsiNotFoundExceptionThrift, IscsiBeingDeletedExceptionThrift,
      ServiceHavingBeenShutdownThrift, EndPointNotFoundExceptionThrift,
      ServiceIsNotAvailableThrift, ApplyFailedDueToConflictExceptionThrift,
      IscsiAccessRuleUnderOperationThrift, IscsiAccessRuleNotFoundThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    ApplyIscsiAccessRuleOnIscsisRequest request = new ApplyIscsiAccessRuleOnIscsisRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setRuleId(ruleId);
    request.setCommit(true);
    List<DriverKeyThrift> driverKeyThrifts = new ArrayList<>();
    logger.debug("driverList is {}", driverList);
    for (SimpleDriverMetadata driver : driverList) {
      DriverKeyThrift driverKeyThrift = new DriverKeyThrift();
      driverKeyThrift.setDriverContainerId(Long.valueOf(driver.getDriverContainerId()));
      driverKeyThrift.setVolumeId(Long.valueOf(driver.getVolumeId()));
      driverKeyThrift.setSnapshotId(Integer.valueOf(driver.getSnapshotId()));
      logger.debug("driver.getDriverType() is {}", driver.getDriverType());
      driverKeyThrift.setDriverType(DriverTypeThrift.valueOf(driver.getDriverType()));
      driverKeyThrifts.add(driverKeyThrift);
    }
    request.setDriverKeys(driverKeyThrifts);
    logger.debug("applyIscsiAccessRuleOnIscsis request is {}", request);

    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.applyIscsiAccessRuleOnIscsis(request);
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
  public void cancelIscsiAccessRuleAllApplied(long accountId, long ruleId,
      List<SimpleDriverMetadata> driverList)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      AccessRuleNotAppliedThrift, EndPointNotFoundExceptionThrift,
      IscsiAccessRuleUnderOperationThrift, IscsiAccessRuleNotFoundThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    CancelIscsiAccessRuleAllAppliedRequest request = new CancelIscsiAccessRuleAllAppliedRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setRuleId(ruleId);
    request.setCommit(true);
    List<DriverKeyThrift> driverKeyThrifts = new ArrayList<>();
    logger.debug("driverList is {}", driverList);
    for (SimpleDriverMetadata driver : driverList) {
      DriverKeyThrift driverKeyThrift = new DriverKeyThrift();
      driverKeyThrift.setDriverContainerId(Long.valueOf(driver.getDriverContainerId()));
      driverKeyThrift.setVolumeId(Long.valueOf(driver.getVolumeId()));
      driverKeyThrift.setSnapshotId(Integer.valueOf(driver.getSnapshotId()));
      logger.debug("driver.getDriverType() is {}", driver.getDriverType());
      driverKeyThrift.setDriverType(DriverTypeThrift.valueOf(driver.getDriverType()));
      driverKeyThrifts.add(driverKeyThrift);
    }
    request.setDriverKeys(driverKeyThrifts);
    logger.debug("cancelIscsiAccessRuleAllApplied request is {}", request);

    try {
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      client.cancelIscsiAccessRuleAllApplied(request);
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
  public List<SimpleDriverMetadata> getDriversBeAppliedOneIscsisRule(long accountId, long ruleId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, Exception {
    GetAppliedIscsisRequest request = new GetAppliedIscsisRequest();
    GetAppliedIscsisResponse response = new GetAppliedIscsisResponse();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    request.setRuleId(ruleId);
    List<SimpleDriverMetadata> simpleDriverMetadataList = new ArrayList<>();
    try {
      List<SimpleInstance> allInstance = new ArrayList<>();
      List<SimpleInstance> driverContainersList = new ArrayList<>();

      allInstance = instanceService.getAll(Constants.SUPERADMIN_ACCOUNT_ID);
      for (SimpleInstance instance : allInstance) {
        if (instance.getInstanceName().equals(PyService.DRIVERCONTAINER.getServiceName())
                && instance.getStatus().equals(InstanceStatus.HEALTHY.name())) {
          driverContainersList.add(instance);
        }
      }

      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      logger.debug("GetAppliedIscsisRequest is {}", request);
      response = client.getAppliedIscsis(request);
      logger.debug("GetAppliedIscsisResponse is {}", response);
      if (response != null && response.isSetDriverList()) {
        for (DriverMetadataThrift driverThrift : response.getDriverList()) {
          SimpleDriverMetadata driverMetadata = new SimpleDriverMetadata();
          driverMetadata.setDriverName(driverThrift.getDriverName());
          driverMetadata.setVolumeId(String.valueOf(driverThrift.getVolumeId()));
          driverMetadata.setVolumeName(driverThrift.getVolumeName());
          driverMetadata.setSnapshotId(String.valueOf(driverThrift.getSnapshotId()));
          driverMetadata.setDriverType(driverThrift.getDriverType().name());
          driverMetadata.setHost(driverThrift.getHostName());
          driverMetadata.setPort(String.valueOf(driverThrift.getPort()));
          driverMetadata.setCoordinatorPort(String.valueOf(driverThrift.getCoordinatorPort()));
          driverMetadata.setDriverContainerId(String.valueOf(driverThrift.getDriverContainerId()));
          for (SimpleInstance instance : driverContainersList) {
            if (instance.getInstanceId().equals(driverMetadata.getDriverContainerId())) {
              driverMetadata.setDriverContainerIp(instance.getHost());
              break;
            }
          }
          List<SimpleDriverClientInfo> driverClientInfoList = new ArrayList<>();
          for (Map.Entry<String, AccessPermissionTypeThrift> entry : driverThrift
              .getClientHostAccessRule()
              .entrySet()) {
            SimpleDriverClientInfo clientInfo = new SimpleDriverClientInfo();
            clientInfo.setHost(entry.getKey());
            clientInfo.setAuthority(entry.getValue().name());
            driverClientInfoList.add(clientInfo);
          }
          logger.debug("driverClientInfoList is {}", driverClientInfoList);
          logger.debug("driverMetadataThrift.getClientHostAccessRule() is {}",
              driverThrift.getClientHostAccessRule());
          driverMetadata.setDriverClientInfoList(driverClientInfoList);
          driverMetadata.setStatus(driverThrift.getDriverStatus().name());
          String clientAmount = "";
          if (driverThrift.getClientHostAccessRule() == null) {
            clientAmount = String.format("%d", 0);
          } else {
            clientAmount = String.format("%d", driverThrift.getClientHostAccessRule().size());
          }

          if (driverThrift.isSetMakeUnmountForCsi()) {
            driverMetadata.setMarkUnmountForCsi(driverThrift.isMakeUnmountForCsi());
          }

          simpleDriverMetadataList.add(driverMetadata);

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
    } catch (Exception e) {
      logger.error("Exception catch", e);
      throw e;
    }
    return simpleDriverMetadataList;
  }

  @Override
  public Map<String, Object> deleteIscsiAccessRules(long accountId,
      List<SimpleIscsiAccessRule> rules,
      boolean isConfirm)
      throws ServiceHavingBeenShutdownThrift, FailedToTellDriverAboutAccessRulesExceptionThrift,
      ServiceIsNotAvailableThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, NetworkErrorExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      Exception {
    DeleteIscsiAccessRulesRequest request = new DeleteIscsiAccessRulesRequest();
    DeleteIscsiAccessRulesResponse response = new DeleteIscsiAccessRulesResponse();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    //        request.setRuleIds(ruleIds);
    request.setCommit(isConfirm);
    Map<SimpleIscsiAccessRule, Object> beAppliedRule2Drivers = new HashMap<>();
    Map<String, Object> result = new HashMap<>();

    try {

      if (!isConfirm) {
        for (SimpleIscsiAccessRule rule : rules) {
          List<SimpleDriverMetadata> driverListOnOneRule = getDriversBeAppliedOneIscsisRule(
              accountId,
              Long.valueOf(rule.getRuleId()));
          if (driverListOnOneRule == null) {
            logger.error("Something wrong to check if the rule {} is applied to some driver",
                rule.getRuleId());
            throw new TException();
          }
          if (!driverListOnOneRule.isEmpty()) {
            beAppliedRule2Drivers.put(rule, driverListOnOneRule);
          }

        }
        if (!beAppliedRule2Drivers.isEmpty()) {
          result.put("beAppliedRule2Drivers", beAppliedRule2Drivers);
          return result;
        }
      }
      List<Long> ruleIdsList = new ArrayList<>();
      List<SimpleIscsiAccessRule> iscsiAccessRulesHasAction = new ArrayList<>();

      for (SimpleIscsiAccessRule rule : rules) {
        ruleIdsList.add(Long.valueOf(rule.getRuleId()));
      }
      request.setRuleIds(ruleIdsList);
      request.setCommit(true);
      logger.debug("DeleteIscsiAccessRulesRequest is {}", request);
      InformationCenter.Iface client = infoCenterClientFactory.build().getClient();
      response = client.deleteIscsiAccessRules(request);
      if (response.getAirAccessRuleList() != null && response.getAirAccessRuleListSize() > 0) {
        for (IscsiAccessRuleThrift ruleThrift : response.getAirAccessRuleList()) {
          SimpleIscsiAccessRule accessRule = new SimpleIscsiAccessRule();
          accessRule.setRuleId(String.valueOf(ruleThrift.getRuleId()));
          accessRule.setRuleNotes(ruleThrift.getRuleNotes());
          accessRule.setInitiatorName(ruleThrift.getInitiatorName());
          accessRule.setUser(ruleThrift.getUser());
          accessRule.setPasswd(ruleThrift.getPassed());
          accessRule.setOutUser(ruleThrift.getOutUser());
          accessRule.setOutPasswd(ruleThrift.getOutPassed());
          accessRule.setPermission(ruleThrift.getPermission().name());
          accessRule.setStatus(ruleThrift.getStatus().name());

          iscsiAccessRulesHasAction.add(accessRule);
        }

      }
      logger.debug("DeleteIscsiAccessRulesResponse is {}", response);
      result.put("iscsiAccessRulesHasAction", iscsiAccessRulesHasAction);
      return result;

    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (Exception e) {
      logger.error("Exception catch", e);
      throw e;
    }

  }

}
