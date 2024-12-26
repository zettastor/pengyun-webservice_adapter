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
