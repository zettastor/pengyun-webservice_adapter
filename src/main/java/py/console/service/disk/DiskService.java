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

package py.console.service.disk;

import java.util.List;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import py.console.bean.ServerNode;
import py.console.bean.SimpleArchiveMetadata;
import py.console.bean.SimpleInstanceMetadata;
import py.console.bean.SmartInfo;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.thrift.infocenter.service.InstanceMaintenanceThrift;
import py.thrift.share.AccessDeniedExceptionThrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.ArchiveManagerNotSupportExceptionThrift;
import py.thrift.share.ArchiveMetadataThrift;
import py.thrift.share.ArchiveTypeThrift;
import py.thrift.share.DiskHasBeenOfflineThrift;
import py.thrift.share.DiskHasBeenOnlineThrift;
import py.thrift.share.DiskIsBusyThrift;
import py.thrift.share.DiskNameIllegalExceptionThrift;
import py.thrift.share.DiskNotBrokenThrift;
import py.thrift.share.DiskNotFoundExceptionThrift;
import py.thrift.share.DiskNotMismatchConfigThrift;
import py.thrift.share.DiskSizeCanNotSupportArchiveTypesThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.InstanceMetadataThrift;
import py.thrift.share.InternalErrorThrift;
import py.thrift.share.InvalidInputExceptionThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.NotEnoughSpaceExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.ServerNodeIsUnknownThrift;
import py.thrift.share.ServerNodeNotExistExceptionThrift;
import py.thrift.share.ServerNodePositionIsRepeatExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;

/**
 * DiskService.
 */
public interface DiskService {

  public void onlineDisk(InstanceMetadataThrift instanceMetadata,
      ArchiveMetadataThrift archiveMetadata,
      long accountId)
      throws DiskNotFoundExceptionThrift, DiskHasBeenOnlineThrift, ServiceHavingBeenShutdownThrift,
      AccessDeniedExceptionThrift, InternalErrorThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift, AccountNotFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift, TException;

  public void settleArchiveType(long accountId, long archiveId, String diskName,
      InstanceMetadataThrift instanceMetadata, List<ArchiveTypeThrift> archiveTypes)
      throws DiskNotFoundExceptionThrift, DiskSizeCanNotSupportArchiveTypesThrift,
      ServiceHavingBeenShutdownThrift, ArchiveManagerNotSupportExceptionThrift,
      DiskHasBeenOfflineThrift,
      ServiceIsNotAvailableThrift, PermissionNotGrantExceptionThrift, NetworkErrorExceptionThrift,
      EndPointNotFoundException, TooManyEndPointFoundExceptionThrift, TException;

  public void offlineDisk(InstanceMetadataThrift instanceMetadata,
      ArchiveMetadataThrift archiveMetadata,
      long accountId)
      throws DiskNotFoundExceptionThrift, DiskHasBeenOfflineThrift, ServiceHavingBeenShutdownThrift,
      AccessDeniedExceptionThrift, DiskIsBusyThrift, NetworkErrorExceptionThrift,
      ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift, AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, TException;

  public void fixBrokenDisk(InstanceMetadataThrift instanceMetadata,
      ArchiveMetadataThrift archiveMetadata,
      long accountId)
      throws DiskNotFoundExceptionThrift, DiskNotBrokenThrift, ServiceHavingBeenShutdownThrift,
      AccessDeniedExceptionThrift, AccountNotFoundExceptionThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift, NetworkErrorExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, TException;

  public void fixConfigMismatchedDisk(InstanceMetadataThrift instanceMetadata,
      ArchiveMetadataThrift archiveMetadata, long accountId)
      throws DiskNotFoundExceptionThrift, DiskNotMismatchConfigThrift,
      ServiceHavingBeenShutdownThrift,
      AccessDeniedExceptionThrift, AccountNotFoundExceptionThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift, NetworkErrorExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      EndPointNotFoundExceptionThrift, TException;

  public List<SimpleInstanceMetadata> listInstanceMetadata()
      throws TTransportException, InternalErrorThrift, NotEnoughSpaceExceptionThrift,
      GenericThriftClientFactoryException, EndPointNotFoundException,
      TooManyEndPointFoundException, TException;

  public List<SimpleArchiveMetadata> listAllDisks(long accountId)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift, TException;

  public List<SimpleArchiveMetadata> getArchives(long instanceId)
      throws TTransportException, InternalErrorThrift, InvalidInputExceptionThrift,
      NotEnoughSpaceExceptionThrift, GenericThriftClientFactoryException, EndPointNotFoundException,
      TooManyEndPointFoundException, TException;

  public List<ServerNode> listServernodes(long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, Exception;

  public ServerNode getServerNode(long accountId, String serverNodeId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, Exception;

  public void updateServernode(ServerNode node, long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, ServerNodePositionIsRepeatExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException;

  public void deleteServerNodes(long accountId, List<String> serverIds)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      ServerNodeIsUnknownThrift,
      PermissionNotGrantExceptionThrift, AccountNotFoundExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public List<SimpleArchiveMetadata> getDiskDetail(List<Long> diskIds, long accountId)
      throws EndPointNotFoundException, TooManyEndPointFoundException,
      GenericThriftClientFactoryException,
      ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift, TException;

  public void markInstanceMaintenance(long accountId, long instanceId, String instanceIp,
      long durationInMinutes)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public List<InstanceMaintenanceThrift> getMaintenanceList(long accountId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public void cancelInstanceMaintenance(long accountId, long instanceId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public List<SmartInfo> obtainDiskSmartInfo(String serverId, String diskName)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, DiskNameIllegalExceptionThrift,
      ServerNodeNotExistExceptionThrift,
      TException;

}
