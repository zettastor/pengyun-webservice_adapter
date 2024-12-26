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

package py.console.service.checkservice.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.common.RequestIdBuilder;
import py.console.bean.ZookeeperStatus;
import py.console.service.checkservice.CheckStatusService;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.infocenter.client.InformationCenterClientFactory;
import py.thrift.infocenter.service.InformationCenter;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIpStatusThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;
import py.thrift.share.listZookeeperServiceStatusRequest;
import py.thrift.share.listZookeeperServiceStatusResponse;

/**
 * CheckStatusServiceImpl.
 */
public class CheckStatusServiceImpl implements CheckStatusService {


  private static final Logger logger = LoggerFactory.getLogger(CheckStatusServiceImpl.class);
  private InformationCenterClientFactory infoCenterClientFactory;
  private long timeOut = 20000; //20s

  public InformationCenterClientFactory getInfoCenterClientFactory() {
    return infoCenterClientFactory;
  }

  public void setInfoCenterClientFactory(InformationCenterClientFactory infoCenterClientFactory) {
    this.infoCenterClientFactory = infoCenterClientFactory;
  }

  @Override
  public List<ZookeeperStatus> listZookeeperServiceStatus(long accountId)
      throws ServiceHavingBeenShutdownThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift,
      Exception {
    listZookeeperServiceStatusRequest request = new listZookeeperServiceStatusRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    InformationCenter.Iface client = null;
    try {
      client = infoCenterClientFactory.build(timeOut).getClient();
      listZookeeperServiceStatusResponse response = client.listZookeeperServiceStatus(request);
      List<ServiceIpStatusThrift> zookeeperStatusThriftList = response.getZookeeperStatusList();
      List<ZookeeperStatus> zookeeperStatusList = new ArrayList<>();
      for (ServiceIpStatusThrift serviceIpStatusThrift : zookeeperStatusThriftList) {
        ZookeeperStatus statusBean = new ZookeeperStatus();
        statusBean.setHostname(serviceIpStatusThrift.getHostname());
        statusBean.setStatus(serviceIpStatusThrift.getStatus());
        zookeeperStatusList.add(statusBean);
      }
      return zookeeperStatusList;
    } catch (EndPointNotFoundException e) {
      logger.error("listZookeeperServiceStatus Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("listZookeeperServiceStatus Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("listZookeeperServiceStatus Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("listZookeeperServiceStatus Exception catch", e);
      throw e;
    }
  }
}
