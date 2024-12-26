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

package py.console.service.operation;

import java.util.List;
import java.util.Map;
import org.apache.thrift.TException;
import py.console.bean.SimpleDriverLinkedLog;
import py.console.bean.SimpleOperation;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.OperationNotFoundExceptionThrift;
import py.thrift.share.ParametersIsErrorExceptionThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;
import py.thrift.share.UnsupportedEncodingExceptionThrift;

/**
 * OperationService.
 */
public interface OperationService {

  public List<SimpleOperation> listOperations(List<Long> ids, long accountId,
      SimpleOperation operationCondition)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      OperationNotFoundExceptionThrift,
      PermissionNotGrantExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException;

  public Map<String, String> getFtpInfo();

  public byte[] saveOperationLogsToCsv(long accountId, SimpleOperation operationCondition)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, UnsupportedEncodingExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;

  public List<SimpleDriverLinkedLog> listDriverLinkedLog(long accountId, String driverName,
      String volumeName, String volumeDesc)
      throws ServiceHavingBeenShutdownThrift, ServiceIsNotAvailableThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, ParametersIsErrorExceptionThrift,
      EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException;
}
