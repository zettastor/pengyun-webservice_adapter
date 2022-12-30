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

package py.console.service.access.rule;

import java.util.List;
import java.util.Map;
import org.apache.thrift.TException;
import py.console.bean.SimpleDriverMetadata;
import py.console.bean.SimpleIscsiAccessRule;
import py.console.bean.SimpleVolumeAccessRule;
import py.console.bean.SimpleVolumeMetadata;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.thrift.infocenter.service.FailedToTellDriverAboutAccessRulesExceptionThrift;
import py.thrift.share.AccessDeniedExceptionThrift;
import py.thrift.share.AccessRuleNotAppliedThrift;
import py.thrift.share.AccessRuleNotFoundThrift;
import py.thrift.share.AccessRuleUnderOperationThrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.ApplyFailedDueToConflictExceptionThrift;
import py.thrift.share.ApplyFailedDueToVolumeIsReadOnlyExceptionThrift;
import py.thrift.share.ChapSameUserPasswdErrorThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.InvalidInputExceptionThrift;
import py.thrift.share.IscsiAccessRuleDuplicateThrift;
import py.thrift.share.IscsiAccessRuleFormatErrorThrift;
import py.thrift.share.IscsiAccessRuleNotFoundThrift;
import py.thrift.share.IscsiAccessRuleUnderOperationThrift;
import py.thrift.share.IscsiBeingDeletedExceptionThrift;
import py.thrift.share.IscsiNotFoundExceptionThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.ResourceNotExistsExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;
import py.thrift.share.VolumeAccessRuleDuplicateThrift;
import py.thrift.share.VolumeBeingDeletedExceptionThrift;
import py.thrift.share.VolumeNotFoundExceptionThrift;

/**
 * VolumeAccessRuleService.
 */
public interface VolumeAccessRuleService {

  public void createVolumeAccessRules(List<SimpleVolumeAccessRule> simpleVolumeAccessRuleList,
      long accountId)
      throws VolumeAccessRuleDuplicateThrift, InvalidInputExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      ServiceIsNotAvailableThrift, PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      AccountNotFoundExceptionThrift,
      TException;

  public VolumeAccessRuleOperationResult deleteVolumeAccessRules(long accountId,
      List<SimpleVolumeAccessRule> ruleListToDelete, boolean isConfirm)
      throws ServiceHavingBeenShutdownThrift, FailedToTellDriverAboutAccessRulesExceptionThrift,
      ServiceIsNotAvailableThrift, PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public List<SimpleVolumeAccessRule> listVolumeAccessRules(long accountId)
      throws ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public List<SimpleVolumeAccessRule> getVolumeAccessRules(long volumeId, long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public VolumeAccessRuleOperationResult applyVolumeAccessRules(long volumeId,
      List<Long> ruleIdListToApply,
      long accountId)
      throws VolumeNotFoundExceptionThrift, VolumeBeingDeletedExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      FailedToTellDriverAboutAccessRulesExceptionThrift, ServiceIsNotAvailableThrift,
      ApplyFailedDueToVolumeIsReadOnlyExceptionThrift, PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      AccessDeniedExceptionThrift, InvalidInputExceptionThrift, TException;

  public VolumeAccessRuleOperationResult cancelVolumeAccessRules(long volumeId,
      List<Long> ruleIdListToCancel,
      long accountId)
      throws ServiceHavingBeenShutdownThrift, FailedToTellDriverAboutAccessRulesExceptionThrift,
      ServiceIsNotAvailableThrift, AccessRuleNotAppliedThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, VolumeNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, AccessDeniedExceptionThrift,
      InvalidInputExceptionThrift, TException;

  public void createIscsiAccessRules(long accountId,
      List<SimpleIscsiAccessRule> simpleIscsiAccessRules)
      throws IscsiAccessRuleDuplicateThrift, IscsiAccessRuleFormatErrorThrift,
      InvalidInputExceptionThrift,
      ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      ChapSameUserPasswdErrorThrift, AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public List<SimpleIscsiAccessRule> listIscsiAccessRules(long accountId)
      throws ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public Map<String, Object> deleteIscsiAccessRules(long accountId,
      List<SimpleIscsiAccessRule> rules,
      boolean isConfirm)
      throws ServiceHavingBeenShutdownThrift, FailedToTellDriverAboutAccessRulesExceptionThrift,
      ServiceIsNotAvailableThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      Exception;

  public void applyIscsiAccessRules(long accountId, List<Long> ruleIds, SimpleDriverMetadata driver)
      throws IscsiNotFoundExceptionThrift, IscsiBeingDeletedExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      FailedToTellDriverAboutAccessRulesExceptionThrift, ServiceIsNotAvailableThrift,
      ApplyFailedDueToConflictExceptionThrift, PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      AccountNotFoundExceptionThrift, AccessDeniedExceptionThrift, TException;

  public Map<String, Object> cancelIscsiAccessRules(long accountId, List<Long> ruleIds,
      SimpleDriverMetadata driver)
      throws ServiceHavingBeenShutdownThrift, FailedToTellDriverAboutAccessRulesExceptionThrift,
      ServiceIsNotAvailableThrift, AccessRuleNotAppliedThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, AccessDeniedExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public List<SimpleDriverMetadata> getDriversBeAppliedOneIscsisRule(long accountId, long ruleId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, Exception;

  public List<SimpleIscsiAccessRule> getIscsiAccessRulesAppliedOnOneDriver(long accountId,
      SimpleDriverMetadata driver)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public List<SimpleVolumeMetadata> getAppliedVolumes(long accountId, long ruleId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public List<SimpleVolumeMetadata> getUnappliedVolumes(long accountId, long ruleId)
      throws AccessDeniedExceptionThrift, ResourceNotExistsExceptionThrift,
      InvalidInputExceptionThrift,
      ServiceHavingBeenShutdownThrift, VolumeNotFoundExceptionThrift, ServiceIsNotAvailableThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public List<SimpleVolumeMetadata> applyVolumeAccessRuleOnVolumes(long accountId, long ruleId,
      List<Long> volumeIds)
      throws VolumeNotFoundExceptionThrift, VolumeBeingDeletedExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      ServiceIsNotAvailableThrift, ApplyFailedDueToVolumeIsReadOnlyExceptionThrift,
      AccessRuleUnderOperationThrift, AccessRuleNotFoundThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public void cancelVolAccessRuleAllApplied(long accountId, long ruleId, List<Long> volumeIds)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      AccessRuleNotAppliedThrift,
      AccessRuleUnderOperationThrift, AccessRuleNotFoundThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public List<SimpleDriverMetadata> getDriversBeUnappliedOneIscsisRule(long accountId, long ruleId)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift, Exception;

  public void applyIscsiAccessRuleOnIscsis(long accountId, long ruleId,
      List<SimpleDriverMetadata> driverList)
      throws IscsiNotFoundExceptionThrift, IscsiBeingDeletedExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      ServiceIsNotAvailableThrift, ApplyFailedDueToConflictExceptionThrift,
      IscsiAccessRuleUnderOperationThrift, IscsiAccessRuleNotFoundThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public void cancelIscsiAccessRuleAllApplied(long accountId, long ruleId,
      List<SimpleDriverMetadata> driverList)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      AccessRuleNotAppliedThrift,
      IscsiAccessRuleUnderOperationThrift, IscsiAccessRuleNotFoundThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

}
