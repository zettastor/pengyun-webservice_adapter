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
