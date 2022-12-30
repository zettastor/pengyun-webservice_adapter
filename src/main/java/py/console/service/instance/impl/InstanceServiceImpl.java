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

package py.console.service.instance.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.common.PyService;
import py.common.struct.EndPoint;
import py.console.bean.SimpleInstance;
import py.console.service.instance.InstanceService;
import py.dd.DeploymentDaemonClientHandler;
import py.dd.client.exception.FailedToStartServiceException;
import py.dih.client.DihClientFactory;
import py.dih.client.DihServiceBlockingClientWrapper;
import py.exception.GenericThriftClientFactoryException;
import py.exception.InternalErrorException;
import py.infocenter.client.InformationCenterClientFactory;
import py.instance.Instance;
import py.instance.InstanceId;
import py.instance.InstanceStatus;
import py.instance.InstanceStore;
import py.thrift.deploymentdaemon.FailedToStartServiceExceptionThrift;
import py.thrift.deploymentdaemon.ServiceIsBusyExceptionThrift;
import py.thrift.share.InstanceHasFailedAleadyExceptionThrift;
import py.thrift.share.InstanceNotExistsExceptionThrift;

/**
 * InstanceServiceImpl.
 */
public class InstanceServiceImpl implements InstanceService {

  private static final Logger logger = LoggerFactory.getLogger(InstanceServiceImpl.class);

  private EndPoint localDihEndPoint;

  private DihClientFactory dihClientFactory;

  private DeploymentDaemonClientHandler deploymentDaemonClientHandler;

  private InstanceStore instanceStore;

  private int ddPort;

  private InformationCenterClientFactory infoCenterClientFactory;

  public InformationCenterClientFactory getInfoCenterClientFactory() {
    return infoCenterClientFactory;
  }

  public void setInfoCenterClientFactory(InformationCenterClientFactory infoCenterClientFactory) {
    this.infoCenterClientFactory = infoCenterClientFactory;
  }

  public DeploymentDaemonClientHandler getDeploymentDaemonClientHandler() {
    return deploymentDaemonClientHandler;
  }

  public void setDeploymentDaemonClientHandler(
      DeploymentDaemonClientHandler deploymentDaemonClientHandler) {
    this.deploymentDaemonClientHandler = deploymentDaemonClientHandler;
  }

  public int getDdPort() {
    return ddPort;
  }

  public void setDdPort(int ddPort) {
    this.ddPort = ddPort;
  }

  public InstanceStore getInstanceStore() {
    return instanceStore;
  }

  public void setInstanceStore(InstanceStore instanceStore) {
    this.instanceStore = instanceStore;
  }

  public DihClientFactory getDihClientFactory() {
    return dihClientFactory;
  }

  public void setDihClientFactory(DihClientFactory dihClientFactory) {
    this.dihClientFactory = dihClientFactory;
  }

  public void init() {
  }

  public void setLocalDihEndPoint(EndPoint localDihEndPoint) {
    this.localDihEndPoint = localDihEndPoint;
  }

  @Override
  public List<SimpleInstance> getAll(long accountId) throws Exception {
    List<SimpleInstance> instanceList = new ArrayList<SimpleInstance>();
    List<String> dihHosts = new ArrayList<String>();

    try {

      for (Instance instance : instanceStore.getAll()) {
        SimpleInstance simpleInstance = new SimpleInstance(instance);
        if ((simpleInstance.getInstanceName().equals(PyService.DIH.getServiceName()))
            && simpleInstance
            .getStatus().endsWith(InstanceStatus.HEALTHY.name())) {
          dihHosts.add(simpleInstance.getHost());
        }
        if (!simpleInstance.getInstanceName().equals(PyService.COORDINATOR.getServiceName())) {
          instanceList.add(simpleInstance);
        }
      }

      // remove
      // List<String> errDih = new ArrayList<String>();
      // for (SimpleInstance simpleInstance : instanceList) {
      //    if (!dihHosts.contains(simpleInstance.getHost()) && !simpleInstance.getInstanceName()
      //    .equals("DIH")) {
      //        EndPoint sdEndPoint = new EndPoint();
      //        sdEndPoint.setHostName(simpleInstance.getHost());
      //        sdEndPoint.setPort(13333);
      //        if(errDih.contains(simpleInstance.getHost())){
      //            simpleInstance.setStatus("INC");
      //            continue;
      //        }
      //        try{
      //            logger.warn("get sd host is {}",simpleInstance.getHost());
      //            SystemDaemon.Iface clientSD = systemDaemonClientFactory.build(sdEndPoint)
      //            .getClient();
      //            if (clientSD != null) {
      //                clientSD.ping();
      //                simpleInstance.setStatus("UNKNOWN");
      //            }else {
      //                logger.warn("clientSD is null");
      //                errDih.add(simpleInstance.getHost());
      //                simpleInstance.setStatus("INC");
      //            }
      //        }catch  (Exception e){
      //            logger.error("catch an Exception", e);
      //            logger.warn("clientSD.ping is error");
      //            errDih.add(simpleInstance.getHost());
      //            simpleInstance.setStatus("INC");
      //        }
      //    }
      //
      //    logger.debug("get all：simpleInstance is {}", simpleInstance);
      //
      // }

    } catch (Exception e) {
      logger.error("getAll Instance catch an Exception:", e);
      throw e;
    }

    return instanceList;
  }


  @Override
  public List<SimpleInstance> getInstances(String name) {
    List<SimpleInstance> instanceList = new ArrayList<SimpleInstance>();
    try {
      for (Instance instance : instanceStore.getAll(name)) {
        SimpleInstance simpleInstance = new SimpleInstance(instance);
        instanceList.add(simpleInstance);
      }
    } catch (Exception e) {
      logger.error("getInstances by name :{}, catch an Exception:", name, e);
    }
    return instanceList;
  }

  @Override
  public SimpleInstance getInstances(long instanceId) {
    logger.debug("instanceId is {}", instanceId);
    SimpleInstance simpleInstance = null;
    try {
      Instance instanceTmp = instanceStore.get(new InstanceId(instanceId));
      if (instanceTmp != null) {
        simpleInstance = new SimpleInstance(instanceTmp);
      }

    } catch (Exception e) {
      logger.error("catch an Exception", e);
    }
    return simpleInstance;
  }

  @Override
  public SimpleInstance getDataNodeByIp(String ip) {
    SimpleInstance datanode = new SimpleInstance();
    for (Instance instance : instanceStore.getAll(PyService.DATANODE.getServiceName())) {
      SimpleInstance simpleInstance = new SimpleInstance(instance);
      if (simpleInstance.getHost().equals(ip)) {
        datanode = simpleInstance;
      }
    }

    return datanode;
  }

  @Override
  public boolean stop(long instanceId) throws InstanceNotExistsExceptionThrift {
    try {
      SimpleInstance simpleInstance = getInstances(instanceId);
      return deploymentDaemonClientHandler
          .deactivate(simpleInstance.getHost(), 10002, simpleInstance.getInstanceName(),
              simpleInstance.getPort(), false);
    } catch (InstanceNotExistsExceptionThrift e) {
      throw new InstanceNotExistsExceptionThrift();
    } catch (TException e) {
      logger.debug("Exception catch", e);
    } catch (Exception e) {
      logger.error("catch an Exception", e);
    }
    return false;
  }

  @Override
  public boolean start(long instanceId)
      throws InternalErrorException, FailedToStartServiceException,
      GenericThriftClientFactoryException,
      FailedToStartServiceExceptionThrift, ServiceIsBusyExceptionThrift, TException {
    try {
      SimpleInstance simpleInstance = getInstances(instanceId);
      return deploymentDaemonClientHandler
          .start(simpleInstance.getHost(), 10002, simpleInstance.getInstanceName());
    } catch (FailedToStartServiceExceptionThrift e) {
      logger.error("catch an Exception", e);
      throw e;
    } catch (ServiceIsBusyExceptionThrift e) {
      logger.error("catch an Exception", e);
      throw e;
    } catch (InternalErrorException e) {
      logger.error("catch an Exception", e);
      throw e;
    } catch (FailedToStartServiceException e) {
      logger.error("catch an Exception", e);
      throw e;
    } catch (GenericThriftClientFactoryException e) {
      logger.error("catch an Exception", e);
      throw e;
    } catch (TException e) {
      logger.error("catch an Exception", e);
      throw e;
    }

  }

  @Override
  public boolean kill(long instanceId)
      throws InstanceNotExistsExceptionThrift, InstanceHasFailedAleadyExceptionThrift {
    try {
      DihServiceBlockingClientWrapper dihWrapper = dihClientFactory.build(localDihEndPoint);
      return dihWrapper.turnInstanceToFailed(instanceId);
    } catch (InstanceNotExistsExceptionThrift e) {
      throw new InstanceNotExistsExceptionThrift();
    } catch (InstanceHasFailedAleadyExceptionThrift e) {
      throw new InstanceHasFailedAleadyExceptionThrift();
    } catch (TException e) {
      logger.debug("Exception catch", e);
    } catch (Exception e) {
      logger.error("catch an Exception", e);
    }
    return false;
  }

  @Override
  public List<SimpleInstance> getInstancesByGroupId(int groupId) {
    List<SimpleInstance> instanceList = new ArrayList<SimpleInstance>();
    try {
      for (Instance instance : instanceStore.getAll()) {
        if (instance.getGroup() != null && instance.getGroup().getGroupId() == groupId) {
          SimpleInstance simpleInstance = new SimpleInstance(instance);
          instanceList.add(simpleInstance);
        }
      }
    } catch (Exception e) {
      logger.error("catch an Exception", e);
    }
    return instanceList;
  }

}
