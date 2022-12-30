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