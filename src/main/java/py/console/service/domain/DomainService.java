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

package py.console.service.domain;

import java.util.List;
import org.apache.thrift.TException;
import py.common.struct.EndPoint;
import py.console.bean.SimpleDomain;
import py.console.bean.SimpleInstance;
import py.thrift.share.AccessDeniedExceptionThrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.DatanodeIsUsingExceptionThrift;
import py.thrift.share.DatanodeNotFoundExceptionThrift;
import py.thrift.share.DatanodeNotFreeToUseExceptionThrift;
import py.thrift.share.DomainExistedExceptionThrift;
import py.thrift.share.DomainIsDeletingExceptionThrift;
import py.thrift.share.DomainNameExistedExceptionThrift;
import py.thrift.share.DomainNotExistedExceptionThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.FailToRemoveDatanodeFromDomainExceptionThrift;
import py.thrift.share.InvalidInputExceptionThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.ResourceNotExistsExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.StillHaveStoragePoolExceptionThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;

/**
 * DomainService.
 */
public interface DomainService {

  public List<SimpleDomain> listDomains(List<Long> domainIds, long accountId)
      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public SimpleDomain createDomain(SimpleDomain domain, long accountId)
      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      DomainExistedExceptionThrift,
      DomainNameExistedExceptionThrift, ServiceIsNotAvailableThrift,
      DatanodeNotFreeToUseExceptionThrift,
      DatanodeNotFoundExceptionThrift, DatanodeIsUsingExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public void updateDomain(SimpleDomain domain, long accountId)
      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      ServiceIsNotAvailableThrift,
      DatanodeNotFreeToUseExceptionThrift, DatanodeNotFoundExceptionThrift,
      DomainNotExistedExceptionThrift,
      DatanodeIsUsingExceptionThrift, DomainIsDeletingExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, AccessDeniedExceptionThrift, TException;

  public void deleteDomain(String domainId, long accountId)
      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      DomainNotExistedExceptionThrift,
      ServiceIsNotAvailableThrift, StillHaveStoragePoolExceptionThrift,
      DomainIsDeletingExceptionThrift,
      ResourceNotExistsExceptionThrift, PermissionNotGrantExceptionThrift,
      AccessDeniedExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public List<SimpleInstance> listInstances();

  public List<SimpleInstance> listInstancesByIds(List<Long> instanceIds);

  public void removeDatanodeFromDomain(String domainId, String datanodeId, long accountId)
      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      ServiceIsNotAvailableThrift,
      FailToRemoveDatanodeFromDomainExceptionThrift, DatanodeNotFoundExceptionThrift,
      DomainNotExistedExceptionThrift, DomainIsDeletingExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccessDeniedExceptionThrift, AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public void dynamicConfigCoordinator(boolean write, String value) throws TException;

  public void disableAllDiscardConfigCoordinator() throws TException;

  public void dynamicConfigCoordinatorsBindingOneVolume(List<EndPoint> endPoints, boolean discard,
      boolean higher)
      throws TException;

  public boolean getDiscardConfigStatus(List<EndPoint> endPoints) throws TException;

  public boolean getShadowPageConfigStatus() throws TException;

  public void dynamicConfigDatanodeShadowPage(boolean enableAvoid) throws TException;

  public SimpleDomain getDomainByIdFromDomainList(long domainId, List<SimpleDomain> domainList);

}