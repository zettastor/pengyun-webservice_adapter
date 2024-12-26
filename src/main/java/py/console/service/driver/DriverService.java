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

package py.console.service.driver;

import java.util.List;
import java.util.Map;
import org.apache.thrift.TException;
import py.console.bean.SimpleDriverMetadata;
import py.console.bean.SimpleInstance;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.ParametersIsErrorExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.ScsiClientIsExistExceptionThrift;
import py.thrift.share.ScsiClientOperationExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;

public interface DriverService {

  List<SimpleDriverMetadata> listAllDrivers(SimpleDriverMetadata driverMetadata)
      throws ServiceIsNotAvailableThrift, ServiceHavingBeenShutdownThrift,
      ParametersIsErrorExceptionThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      Exception;

  public void setIscsiChapControl(SimpleDriverMetadata driver)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public void createScsiClient(long accountId, String ip, long driverContainerId)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      ScsiClientIsExistExceptionThrift,
      TException;

  public Map<String, ScsiClientOperationExceptionThrift> deleteScsiClient(long accountId,
      List<String> ips)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, TException;

  public Map<String, Object> listScsiClient(long accountId, String ip)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, TException;

  public void launchDriverForScsi(long accountId, List<Long> volumeIds, String driverType,
      String scsiIp,
      int driverNum)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, TException;

  public Map<String, ScsiClientOperationExceptionThrift> umountDriverForScsi(long accountId,
      List<Long> volumeIds,
      String scsiIp)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, TException;

  public List<SimpleInstance> availableDriverContainerForClient()
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, TException;


}
