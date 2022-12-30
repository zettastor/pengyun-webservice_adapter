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

package py.console.service.storagepool;

import java.util.List;
import java.util.Set;
import org.apache.thrift.TException;
import py.console.bean.SimpleStoragePool;
import py.thrift.share.AccessDeniedExceptionThrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.ArchiveIsUsingExceptionThrift;
import py.thrift.share.ArchiveNotFoundExceptionThrift;
import py.thrift.share.ArchiveNotFreeToUseExceptionThrift;
import py.thrift.share.DomainIsDeletingExceptionThrift;
import py.thrift.share.DomainNotExistedExceptionThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.FailToRemoveArchiveFromStoragePoolExceptionThrift;
import py.thrift.share.InvalidInputExceptionThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.ResourceNotExistsExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.StillHaveVolumeExceptionThrift;
import py.thrift.share.StoragePoolExistedExceptionThrift;
import py.thrift.share.StoragePoolIsDeletingExceptionThrift;
import py.thrift.share.StoragePoolNameExistedExceptionThrift;
import py.thrift.share.StoragePoolNotExistedExceptionThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;

/**
 * StoragePoolService.
 */
public interface StoragePoolService {

  public SimpleStoragePool createStoragePool(SimpleStoragePool storagepool, long accountId)
      throws InvalidInputExceptionThrift, ServiceHavingBeenShutdownThrift,
      StoragePoolExistedExceptionThrift,
      StoragePoolNameExistedExceptionThrift, ServiceIsNotAvailableThrift,
      ArchiveNotFreeToUseExceptionThrift,
      ArchiveNotFoundExceptionThrift, DomainNotExistedExceptionThrift,
      ArchiveIsUsingExceptionThrift,
      DomainIsDeletingExceptionThrift, PermissionNotGrantExceptionThrift,
      AccessDeniedExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public Set<SimpleStoragePool> listStoragePools(String domainId, List<Long> storagePoolIds,
      long accountId)
      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      ServiceIsNotAvailableThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, ResourceNotExistsExceptionThrift, TException;

  public void updateStoragePool(SimpleStoragePool storagPool, long accountId)
      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      ServiceIsNotAvailableThrift,
      ArchiveNotFreeToUseExceptionThrift, ArchiveNotFoundExceptionThrift,
      StoragePoolNotExistedExceptionThrift,
      DomainNotExistedExceptionThrift, ArchiveIsUsingExceptionThrift,
      StoragePoolIsDeletingExceptionThrift,
      PermissionNotGrantExceptionThrift, AccessDeniedExceptionThrift,
      AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public void removeArchiveFormStoragePool(String storagePoolId, String domainId, String datanodeId,
      String archiveId,
      long accountId)
      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      ServiceIsNotAvailableThrift,
      FailToRemoveArchiveFromStoragePoolExceptionThrift, ArchiveNotFoundExceptionThrift,
      StoragePoolNotExistedExceptionThrift, DomainNotExistedExceptionThrift,
      StoragePoolIsDeletingExceptionThrift, PermissionNotGrantExceptionThrift,
      AccessDeniedExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public void deleteStoragePool(String domainId, String storagePoolId, long accountId)
      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      StoragePoolNotExistedExceptionThrift, ServiceIsNotAvailableThrift,
      StillHaveVolumeExceptionThrift,
      DomainNotExistedExceptionThrift, StoragePoolIsDeletingExceptionThrift,
      ResourceNotExistsExceptionThrift,
      PermissionNotGrantExceptionThrift, AccessDeniedExceptionThrift,
      AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public List<SimpleStoragePool> listStoragePoolCapacity(String domainId, List<Long> storagePoolIds,
      long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      InvalidInputExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public boolean isPoolHasPerformanceData(String poolId) throws Exception;
}
