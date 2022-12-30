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

package py.console.service.qos;

import java.util.List;
import java.util.Map;
import org.apache.thrift.TException;
import py.console.bean.IoLimitation;
import py.console.bean.QosMigrationRule;
import py.console.bean.SimpleDriverMetadata;
import py.console.bean.SimpleStoragePool;
import py.thrift.infocenter.service.FailedToTellDriverAboutAccessRulesExceptionThrift;
import py.thrift.share.AccessRuleNotAppliedThrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.ApplyFailedDueToVolumeIsReadOnlyExceptionThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.InvalidInputExceptionThrift;
import py.thrift.share.IoLimitationTimeInterLeavingThrift;
import py.thrift.share.IoLimitationsDuplicateThrift;
import py.thrift.share.MigrationRuleNotExists;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;
import py.thrift.share.VolumeBeingDeletedExceptionThrift;
import py.thrift.share.VolumeNotFoundExceptionThrift;

public interface QosService {

  public List<QosMigrationRule> listMigrationRules(long accountId)
      throws ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public List<SimpleStoragePool> getAppliedStoragePools(long accountId, long ruleId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift, MigrationRuleNotExists,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public void applyMigrationRules(long accountId, List<Long> poolIds, long ruleId)
      throws VolumeNotFoundExceptionThrift, VolumeBeingDeletedExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      FailedToTellDriverAboutAccessRulesExceptionThrift, ServiceIsNotAvailableThrift,
      ApplyFailedDueToVolumeIsReadOnlyExceptionThrift, AccountNotFoundExceptionThrift,
      PermissionNotGrantExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public void cancelMigrationRules(long accountId, List<Long> poolIds, long ruleId)
      throws ServiceHavingBeenShutdownThrift, FailedToTellDriverAboutAccessRulesExceptionThrift,
      ServiceIsNotAvailableThrift, AccessRuleNotAppliedThrift, AccountNotFoundExceptionThrift,
      PermissionNotGrantExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public List<SimpleStoragePool> getUnAppliedPools(long accountId, long ruleId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift, MigrationRuleNotExists,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  //--------Begin driver qos-----------

  public void createIoLimitations(long accountId, IoLimitation ioLimitation)
      throws IoLimitationsDuplicateThrift, InvalidInputExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      ServiceIsNotAvailableThrift, AccountNotFoundExceptionThrift,
      PermissionNotGrantExceptionThrift,
      IoLimitationTimeInterLeavingThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public void updateIoLimitations(long accountId, IoLimitation ioLimitation)
      throws IoLimitationsDuplicateThrift, InvalidInputExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      ServiceIsNotAvailableThrift, AccountNotFoundExceptionThrift,
      PermissionNotGrantExceptionThrift,
      IoLimitationTimeInterLeavingThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public List<IoLimitation> listIoLimitations(long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public Map<String, Object> deleteIoLimitations(long accountId,
      List<IoLimitation> ioLimitationsList, boolean commit)
      throws ServiceHavingBeenShutdownThrift, FailedToTellDriverAboutAccessRulesExceptionThrift,
      ServiceIsNotAvailableThrift, AccountNotFoundExceptionThrift,
      PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      Exception;

  public List<SimpleDriverMetadata> getIoLimitationAppliedDrivers(long accountId, long limitationId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      Exception;

  public List<SimpleDriverMetadata> getIoLimitationUnappliedDrivers(long accountId,
      long limitationId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      Exception;

  public List<SimpleDriverMetadata> applyIoLimitations(long accountId, long limitationId,
      List<SimpleDriverMetadata> driverList)
      throws VolumeNotFoundExceptionThrift, VolumeBeingDeletedExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      FailedToTellDriverAboutAccessRulesExceptionThrift, ServiceIsNotAvailableThrift,
      ApplyFailedDueToVolumeIsReadOnlyExceptionThrift, AccountNotFoundExceptionThrift,
      PermissionNotGrantExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public void cancelIoLimitations(long accountId, long limitationId,
      List<SimpleDriverMetadata> driverList)
      throws ServiceHavingBeenShutdownThrift, FailedToTellDriverAboutAccessRulesExceptionThrift,
      ServiceIsNotAvailableThrift, AccessRuleNotAppliedThrift, AccountNotFoundExceptionThrift,
      PermissionNotGrantExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  //---------End driver qos------------

}
