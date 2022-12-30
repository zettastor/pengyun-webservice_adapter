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
