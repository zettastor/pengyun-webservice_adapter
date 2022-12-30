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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.common.RequestIdBuilder;
import py.console.bean.Account;
import py.console.bean.Resource;
import py.console.bean.Role;
import py.console.service.account.AccountService;
import py.console.service.volume.VolumeService;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.infocenter.client.InformationCenterClientFactory;
import py.thrift.infocenter.service.AssignRolesRequest;
import py.thrift.infocenter.service.AssignRolesResponse;
import py.thrift.infocenter.service.CreateRoleNameExistedExceptionThrift;
import py.thrift.infocenter.service.CreateRoleRequest;
import py.thrift.infocenter.service.CreateRoleResponse;
import py.thrift.infocenter.service.DeleteRolesRequest;
import py.thrift.infocenter.service.DeleteRolesResponse;
import py.thrift.infocenter.service.InformationCenter;
import py.thrift.infocenter.service.ListApisRequest;
import py.thrift.infocenter.service.ListApisResponse;
import py.thrift.infocenter.service.ListRolesRequest;
import py.thrift.infocenter.service.ListRolesResponse;
import py.thrift.infocenter.service.LoadVolumeRequest;
import py.thrift.infocenter.service.UpdateRoleRequest;
import py.thrift.infocenter.service.UpdateRoleResponse;
import py.thrift.share.AccessDeniedExceptionThrift;
import py.thrift.share.AccountAlreadyExistsExceptionThrift;
import py.thrift.share.AccountMetadataThrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.AccountTypeThrift;
import py.thrift.share.ApiToAuthorizeThrift;
import py.thrift.share.AssignResourcesRequest;
import py.thrift.share.AssignResourcesResponse;
import py.thrift.share.AuthenticateAccountRequest;
import py.thrift.share.AuthenticateAccountResponse;
import py.thrift.share.AuthenticationFailedExceptionThrift;
import py.thrift.share.CreateAccountRequest;
import py.thrift.share.CreateAccountResponse;
import py.thrift.share.CrudBuiltInRoleExceptionThrift;
import py.thrift.share.CrudSuperAdminAccountExceptionThrift;
import py.thrift.share.DeleteAccountsRequest;
import py.thrift.share.DeleteAccountsResponse;
import py.thrift.share.DeleteLoginAccountExceptionThrift;
import py.thrift.share.DeleteRoleExceptionThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.InsufficientPrivilegeExceptionThrift;
import py.thrift.share.InternalErrorThrift;
import py.thrift.share.InvalidInputExceptionThrift;
import py.thrift.share.ListAccountsRequest;
import py.thrift.share.ListAccountsResponse;
import py.thrift.share.ListResourcesRequest;
import py.thrift.share.ListResourcesResponse;
import py.thrift.share.LoadVolumeExceptionThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.OlderPasswordIncorrectExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.ResetAccountPasswordRequest;
import py.thrift.share.ResetAccountPasswordResponse;
import py.thrift.share.ResourceThrift;
import py.thrift.share.RoleNotExistedExceptionThrift;
import py.thrift.share.RoleThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;
import py.thrift.share.UpdatePasswordRequest;
import py.thrift.share.UpdatePasswordResponse;

/**
 * account information stores in infocenter, so need to communicate with infocenter.
 *
 */
public class AccountServiceImpl implements AccountService {

  private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

  private InformationCenterClientFactory infoCenterClientFactory;

  private VolumeService volumeService;

  private String snapshotShowFlag;

  private String csiFlag;

  public String getSnapshotShowFlag() {
    return snapshotShowFlag;
  }

  public void setSnapshotShowFlag(String snapshotShowFlag) {
    this.snapshotShowFlag = snapshotShowFlag;
  }

  public String getCsiFlag() {
    return csiFlag;
  }

  public void setCsiFlag(String csiFlag) {
    this.csiFlag = csiFlag;
  }

  public InformationCenterClientFactory getInfoCenterClientFactory() {
    return infoCenterClientFactory;
  }

  public void setInfoCenterClientFactory(InformationCenterClientFactory infoCenterClientFactory) {
    this.infoCenterClientFactory = infoCenterClientFactory;
  }

  public VolumeService getVolumeService() {
    return volumeService;
  }

  public void setVolumeService(VolumeService volumeService) {
    this.volumeService = volumeService;
  }

  @Override
  public Map<String, Object> authenticateAccount(String accountName, String password)
      throws AuthenticationFailedExceptionThrift, InvalidInputExceptionThrift,
      ServiceHavingBeenShutdownThrift,
      ServiceIsNotAvailableThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    logger.debug("Start to authenticate user...");
    Map<String, Object> result = new HashMap<>();
    InformationCenter.Iface client = null;
    Account account = null;
    try {
      client = infoCenterClientFactory.build().getClient();
      AuthenticateAccountRequest request = new AuthenticateAccountRequest();
      request.setAccountName(accountName);
      request.setPassword(password);
      AuthenticateAccountResponse response = client.authenticateAccount(request);
      logger.info(" AuthenticateAccountResponse is {}", response);
      if (response != null) {
        account = new Account();
        account.setAccountId(String.valueOf(response.getAccountId()));
        account.setAccountName(accountName);
        account.setAccountType(response.getAccountType().name());
        List<Role> roleList = new ArrayList<>();
        for (RoleThrift roleThrift : response.getRoles()) {
          Role role = new Role();
          role.setRoleId(String.valueOf(roleThrift.getId()));
          role.setName(roleThrift.getName());
          role.setDescription(roleThrift.getDescription());
          roleList.add(role);
        }
        account.setRoles(roleList);
        List<ApiToAuthorizeThrift> apisList = response.getApis();
        Map<String, Map<String, Boolean>> apisMap = new HashMap<>();
        for (ApiToAuthorizeThrift api : apisList) {
          if (apisMap.containsKey(api.getCategory())) {
            apisMap.get(api.getCategory()).put(api.getApiName(), true);
          } else {
            Map<String, Boolean> mapTmp = new HashMap<>();
            mapTmp.put(api.getApiName(), true);
            apisMap.put(api.getCategory(), mapTmp);
          }
        }
        logger.debug("apisMap is {}", request);
        logger.debug("account is {}", request);
        result.put("apisMap", apisMap);
        result.put("account", account);

        logger.debug("Done authenticating user.");
        return result;
      }
    } catch (AuthenticationFailedExceptionThrift e) {
      logger.error("Login failed exception", e);
    } catch (InternalErrorThrift e) {
      logger.error("Login failed exception", e);
    } catch (TException e) {
      logger.error("Login failed exception", e);
    } catch (EndPointNotFoundException e) {
      logger.error("Login failed exception", e);
    } catch (TooManyEndPointFoundException e) {
      logger.error("Login failed exception", e);
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Login failed exception", e);
    }
    return result;
  }

  @Override
  public boolean createAccount(Account newAccount, long accountId, Set<Long> roleIds)
      throws AccessDeniedExceptionThrift, AccountNotFoundExceptionThrift,
      InvalidInputExceptionThrift,
      AccountAlreadyExistsExceptionThrift, ServiceHavingBeenShutdownThrift,
      ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    InformationCenter.Iface client = null;
    CreateAccountRequest request = new CreateAccountRequest();
    CreateAccountResponse response = new CreateAccountResponse();
    request.setAccountId(accountId);
    request.setAccountName(newAccount.getAccountName());
    request.setPassword(newAccount.getPassword());
    request.setRoleIds(roleIds);
    request.setAccountType(AccountTypeThrift.Regular);
    logger.debug("CreateAccountRequest is {}", request);
    try {
      client = infoCenterClientFactory.build().getClient();
      response = client.createAccount(request);
      if (response != null) {
        return true;
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

    return false;
  }

  @Override
  public List<Account> getAll(long accountId, Set<Long> listAccountIds)
      throws AccessDeniedExceptionThrift, AccountNotFoundExceptionThrift,
      InvalidInputExceptionThrift,
      ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    logger.debug("Start to get all accounts...");
    List<Account> accountList = new ArrayList<Account>();
    InformationCenter.Iface client = null;
    ListAccountsRequest request = new ListAccountsRequest();
    ListAccountsResponse response = new ListAccountsResponse();
    request.setAccountId(accountId);
    if (listAccountIds != null) {
      request.setListAccountIds(listAccountIds);
    }
    try {
      logger.debug("ListAccounts request is {}", request);
      client = infoCenterClientFactory.build().getClient();
      response = client.listAccounts(request);
      if (response != null) {
        for (AccountMetadataThrift accountThrift : response.getAccounts()) {
          Account account = new Account();
          account.setAccountId(String.valueOf(accountThrift.getAccountId()));
          account.setAccountName(accountThrift.getAccountName());
          account.setAccountType(accountThrift.getAccountType().name());
          List<Role> roleList = new ArrayList<>();
          account.setRoles(roleList);
          if (accountThrift.isSetRoles()) {
            for (RoleThrift roleThrift : accountThrift.getRoles()) {
              Role role = new Role();
              role.setRoleId(String.valueOf(roleThrift.getId()));
              role.setName(roleThrift.getName());
              roleList.add(role);
            }

          }

          Map<String, List<Resource>> resourceMap = new HashMap<>();
          account.setResources(resourceMap);
          if (accountThrift.isSetResources()) {
            for (ResourceThrift resourceThrift : accountThrift.getResources()) {
              Resource resource = new Resource();
              resource.setResourceId(String.valueOf(resourceThrift.getResourceId()));
              resource.setResourceName(resourceThrift.getResourceName());
              String resourceType = resourceThrift.getResourceType();
              resource.setResourceType(resourceType);
              List<Resource> resourcesInType = resourceMap.get(resourceType);
              if (resourcesInType == null) {
                resourcesInType = new ArrayList<>();
                resourceMap.put(resourceType, resourcesInType);
              }
              resourcesInType.add(resource);
            }
          }
          accountList.add(account);
        }
      }
      logger.debug("ListAccounts Response is {}", response);
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

    logger.debug("Done getting all accounts...");
    return accountList;
  }

  @Override
  public Set<Long> delete(Set<Long> deletingAccountIds, long accountId)
      throws AccessDeniedExceptionThrift, InvalidInputExceptionThrift,
      AccountNotFoundExceptionThrift,
      ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      DeleteLoginAccountExceptionThrift,
      PermissionNotGrantExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    Set<Long> deletedAccountsSet = new HashSet<>();
    try {
      InformationCenter.Iface client = null;

      client = infoCenterClientFactory.build().getClient();
      DeleteAccountsRequest request = new DeleteAccountsRequest();
      DeleteAccountsResponse response = new DeleteAccountsResponse();
      request.setAccountId(accountId);
      request.setDeletingAccountIds(deletingAccountIds);
      response = client.deleteAccounts(request);
      deletedAccountsSet = response.getDeletedAccountIds();

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
    return deletedAccountsSet;
  }

  @Override
  public boolean updateUser(Account account, String newPassword, String oldPassword)
      throws OlderPasswordIncorrectExceptionThrift, InsufficientPrivilegeExceptionThrift,
      InvalidInputExceptionThrift, AccountNotFoundExceptionThrift, ServiceHavingBeenShutdownThrift,
      ServiceIsNotAvailableThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    InformationCenter.Iface client = null;

    try {
      client = infoCenterClientFactory.build().getClient();
      UpdatePasswordRequest request = new UpdatePasswordRequest();
      request.setAccountId(Long.parseLong(account.getAccountId()));
      request.setAccountName(account.getAccountName());
      request.setNewPassword(newPassword);
      request.setOldPassword(oldPassword);
      UpdatePasswordResponse response = client.updatePassword(request);
      if (response != null) {
        return true;
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
    return false;
  }

  @Override
  public String resetPassword(long operatorAccountId, long accountId)
      throws InvalidInputExceptionThrift, AccessDeniedExceptionThrift,
      AccountNotFoundExceptionThrift,
      ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    InformationCenter.Iface client = null;
    try {
      client = infoCenterClientFactory.build().getClient();

      ResetAccountPasswordRequest request = new ResetAccountPasswordRequest();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(operatorAccountId);
      request.setTargetAccountId(accountId);
      ResetAccountPasswordResponse response = client.resetAccountPassword(request);
      if (response != null) {
        return response.getPassword();
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
    return null;
  }

  @Override
  public List<ApiToAuthorizeThrift> listApi(long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    InformationCenter.Iface client = null;
    ListApisRequest request = new ListApisRequest();
    ListApisResponse response = new ListApisResponse();
    List<ApiToAuthorizeThrift> apisList = new ArrayList<>();
    Set<ApiToAuthorizeThrift> apisSet = new HashSet<>();

    try {
      client = infoCenterClientFactory.build().getClient();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      logger.debug("listApis request is {}", request);
      response = client.listApis(request);
      logger.debug("listApis response is {}", response);
      apisSet = response.getApis();
      apisList.addAll(apisSet);
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
    return apisList;
  }

  @Override
  public void createRole(long accountId, String roleName, String description, Set<String> apiNames)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      CreateRoleNameExistedExceptionThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    InformationCenter.Iface client = null;
    CreateRoleRequest request = new CreateRoleRequest();
    CreateRoleResponse response = new CreateRoleResponse();

    try {
      client = infoCenterClientFactory.build().getClient();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      request.setRoleName(roleName);
      request.setDescription(description);
      request.setApiNames(apiNames);

      logger.debug("createRole request is {}", request);
      response = client.createRole(request);
      logger.debug("createRole response is {}", response);
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
  public void updateRole(long accountId, long roleId, String roleName, String description,
      Set<String> apiNames)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      RoleNotExistedExceptionThrift,
      CrudBuiltInRoleExceptionThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    InformationCenter.Iface client = null;
    UpdateRoleRequest request = new UpdateRoleRequest();
    UpdateRoleResponse response = new UpdateRoleResponse();

    try {
      client = infoCenterClientFactory.build().getClient();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      request.setRoleId(roleId);
      request.setRoleName(roleName);
      request.setDescription(description);
      request.setApiNames(apiNames);

      logger.debug("updateRole request is {}", request);
      response = client.updateRole(request);
      logger.debug("updateRole response is {}", response);
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

  public List<RoleThrift> listRoles(long accountId, Set<Long> ids)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    InformationCenter.Iface client = null;
    ListRolesRequest request = new ListRolesRequest();
    ListRolesResponse response = new ListRolesResponse();
    List<RoleThrift> roleList = new ArrayList<>();
    try {
      client = infoCenterClientFactory.build().getClient();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      if (ids != null) {
        request.setListRoleIds(ids);
      }

      logger.debug("listRoles request is {}", request);
      response = client.listRoles(request);
      roleList.addAll(response.getRoles());
      logger.debug("listRoles response is {}", response);
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

    return roleList;
  }

  /**
   * list resource.
   *
   * @param accountId account id
   * @return resource list
   * @throws ServiceHavingBeenShutdownThrift ServiceHavingBeenShutdownThrift
   * @throws ServiceIsNotAvailableThrift ServiceIsNotAvailableThrift
   * @throws PermissionNotGrantExceptionThrift PermissionNotGrantExceptionThrift
   * @throws AccountNotFoundExceptionThrift AccountNotFoundExceptionThrift
   * @throws EndPointNotFoundExceptionThrift EndPointNotFoundExceptionThrift
   * @throws TooManyEndPointFoundExceptionThrift TooManyEndPointFoundExceptionThrift
   * @throws NetworkErrorExceptionThrift NetworkErrorExceptionThrift
   * @throws TException TException
   */
  public List<ResourceThrift> listResource(long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    InformationCenter.Iface client = null;
    ListResourcesRequest request = new ListResourcesRequest();
    ListResourcesResponse response = new ListResourcesResponse();
    List<ResourceThrift> resourceList = new ArrayList<>();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);

    try {
      client = infoCenterClientFactory.build().getClient();
      logger.debug("listResource request is {}", request);
      response = client.listResources(request);
      resourceList.addAll(response.getResources());
      logger.debug("listResource response is {}", response);
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
    return resourceList;
  }

  @Override
  public Set<Long> deleteRoles(long accountId, Set<Long> roleIds)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      DeleteRoleExceptionThrift,
      PermissionNotGrantExceptionThrift, AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    InformationCenter.Iface client = null;
    DeleteRolesRequest request = new DeleteRolesRequest();
    DeleteRolesResponse response = new DeleteRolesResponse();
    Set<Long> deletedRoleIds = new HashSet<>();

    try {
      client = infoCenterClientFactory.build().getClient();
      request.setRequestId(RequestIdBuilder.get());
      request.setAccountId(accountId);
      if (roleIds != null) {
        request.setRoleIds(roleIds);
      }
      logger.debug("deleteRoles request is {}", request);
      response = client.deleteRoles(request);
      deletedRoleIds = response.getDeletedRoleIds();
      logger.debug("deleteRoles response is {}", response);
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
    return deletedRoleIds;

  }

  @Override
  public void assignRoles(long accountId, long assignedAccountId, Set<Long> roleIds)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      CrudSuperAdminAccountExceptionThrift, PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    InformationCenter.Iface client = null;
    AssignRolesRequest request = new AssignRolesRequest();
    AssignRolesResponse response = new AssignRolesResponse();
    request.setAccountId(accountId);
    request.setAssignedAccountId(assignedAccountId);
    request.setRoleIds(roleIds);
    logger.debug("assignRoles request is {}", request);

    try {
      client = infoCenterClientFactory.build().getClient();
      client.assignRoles(request);
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
  public void assignResource(long accountId, long targetAccountId, Set<Long> resourceIds)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    InformationCenter.Iface client = null;
    AssignResourcesRequest request = new AssignResourcesRequest();
    AssignResourcesResponse response = new AssignResourcesResponse();
    request.setAccountId(accountId);
    request.setTargetAccountId(targetAccountId);
    request.setResourceIds(resourceIds);
    logger.debug("assignResource request is {}", request);

    try {
      client = infoCenterClientFactory.build().getClient();
      client.assignResources(request);
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
  public String obtainSnapshotShowFlag() {
    return snapshotShowFlag;
  }

  @Override
  public String obtainCsiFlag() {
    return csiFlag;
  }


  @Override
  public void loadVolume(long accountId)
      throws LoadVolumeExceptionThrift, EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException, TException {
    InformationCenter.Iface client = null;
    try {
      client = infoCenterClientFactory.build().getClient();
      LoadVolumeRequest request = new LoadVolumeRequest();
      request.setAccountId(accountId);
      client.loadVolume(request);
    } catch (EndPointNotFoundException e) {
      logger.error("exception catch", e);
      throw e;
    } catch (TooManyEndPointFoundException e) {
      logger.error("exception catch", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("exception catch", e);
      throw e;
    } catch (LoadVolumeExceptionThrift e) {
      logger.error("exception catch", e);
      throw e;
    } catch (TException e) {
      logger.error("exception catch", e);
      throw e;
    }

  }

}
