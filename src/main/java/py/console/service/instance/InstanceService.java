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
