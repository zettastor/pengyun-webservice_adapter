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

package py.console.service.account;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.thrift.TException;
import py.console.bean.Account;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.thrift.infocenter.service.CreateRoleNameExistedExceptionThrift;
import py.thrift.share.AccessDeniedExceptionThrift;
import py.thrift.share.AccountAlreadyExistsExceptionThrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.ApiToAuthorizeThrift;
import py.thrift.share.AuthenticationFailedExceptionThrift;
import py.thrift.share.CrudBuiltInRoleExceptionThrift;
import py.thrift.share.CrudSuperAdminAccountExceptionThrift;
import py.thrift.share.DeleteLoginAccountExceptionThrift;
import py.thrift.share.DeleteRoleExceptionThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.InsufficientPrivilegeExceptionThrift;
import py.thrift.share.InvalidInputExceptionThrift;
import py.thrift.share.LoadVolumeExceptionThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.OlderPasswordIncorrectExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.ResourceThrift;
import py.thrift.share.RoleNotExistedExceptionThrift;
import py.thrift.share.RoleThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;

/**
 * AccountService.
 */
public interface AccountService {

  public boolean createAccount(Account newAccount, long accountId, Set<Long> roleIds)
      throws AccessDeniedExceptionThrift, AccountNotFoundExceptionThrift,
      InvalidInputExceptionThrift,
      AccountAlreadyExistsExceptionThrift, ServiceHavingBeenShutdownThrift,
      ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public List<Account> getAll(long accountId, Set<Long> listAccountIds)
      throws AccessDeniedExceptionThrift, AccountNotFoundExceptionThrift,
      InvalidInputExceptionThrift,
      ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public Set<Long> delete(Set<Long> deletingAccountIds, long accountId)
      throws AccessDeniedExceptionThrift, InvalidInputExceptionThrift,
      AccountNotFoundExceptionThrift,
      ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      DeleteLoginAccountExceptionThrift,
      PermissionNotGrantExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public boolean updateUser(Account account, String newPassword, String oldPassword)
      throws OlderPasswordIncorrectExceptionThrift, InsufficientPrivilegeExceptionThrift,
      InvalidInputExceptionThrift, AccountNotFoundExceptionThrift, ServiceHavingBeenShutdownThrift,
      ServiceIsNotAvailableThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public Map<String, Object> authenticateAccount(String accountName, String password)
      throws AuthenticationFailedExceptionThrift, InvalidInputExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      ServiceIsNotAvailableThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public void loadVolume(long accountId)
      throws LoadVolumeExceptionThrift, EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException, TException;

  public String resetPassword(long operatorAccountId, long accoutId)
      throws InvalidInputExceptionThrift, AccessDeniedExceptionThrift,
      AccountNotFoundExceptionThrift,
      ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public List<ApiToAuthorizeThrift> listApi(long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public void createRole(long accountId, String roleName, String description, Set<String> apiNames)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      CreateRoleNameExistedExceptionThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public void updateRole(long accountId, long roleId, String roleName, String description,
      Set<String> apiNames)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      RoleNotExistedExceptionThrift,
      CrudBuiltInRoleExceptionThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;


  public List<RoleThrift> listRoles(long accountId, Set<Long> ids)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;


  public Set<Long> deleteRoles(long accountId, Set<Long> roleIds)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      DeleteRoleExceptionThrift,
      PermissionNotGrantExceptionThrift, AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public void assignRoles(long accountId, long assignedAccountId, Set<Long> roleIds)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      CrudSuperAdminAccountExceptionThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public List<ResourceThrift> listResource(long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public void assignResource(long accountId, long targetAccountId, Set<Long> resourceIds)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public String obtainSnapshotShowFlag();

  // csi
  public String obtainCsiFlag();
}
