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

package py.console.service.instance;

import java.util.List;
import org.apache.thrift.TException;
import py.console.bean.SimpleInstance;
import py.dd.client.exception.FailedToStartServiceException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.InternalErrorException;
import py.thrift.deploymentdaemon.FailedToStartServiceExceptionThrift;
import py.thrift.deploymentdaemon.ServiceIsBusyExceptionThrift;
import py.thrift.share.InstanceHasFailedAleadyExceptionThrift;
import py.thrift.share.InstanceNotExistsExceptionThrift;

/**
 * InstanceService.
 */
public interface InstanceService {

  public List<SimpleInstance> getAll(long accountId) throws Exception;

  public List<SimpleInstance> getInstances(String name);

  public SimpleInstance getInstances(long instanceId);

  public List<SimpleInstance> getInstancesByGroupId(int groupId);

  public boolean kill(long instanceId)
      throws InstanceNotExistsExceptionThrift, InstanceHasFailedAleadyExceptionThrift;

  boolean start(long instanceId)
      throws InternalErrorException, FailedToStartServiceException,
      GenericThriftClientFactoryException,
      FailedToStartServiceExceptionThrift, ServiceIsBusyExceptionThrift, TException;

  boolean stop(long instanceId) throws InstanceNotExistsExceptionThrift;

  public SimpleInstance getDataNodeByIp(String ip);


}
