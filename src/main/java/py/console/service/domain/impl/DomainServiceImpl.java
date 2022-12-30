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

package py.console.service.domain.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.Validate;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.client.thrift.GenericThriftClientFactory;
import py.common.PyService;
import py.common.RequestIdBuilder;
import py.common.struct.EndPoint;
import py.console.bean.SimpleDomain;
import py.console.bean.SimpleInstance;
import py.console.service.domain.DomainService;
import py.drivercontainer.client.CoordinatorClientFactory;
import py.exception.EndPointNotFoundException;
import py.exception.GenericThriftClientFactoryException;
import py.exception.TooManyEndPointFoundException;
import py.infocenter.client.InformationCenterClientFactory;
import py.instance.Instance;
import py.instance.InstanceStore;
import py.instance.PortType;
import py.thrift.coordinator.service.AddOrModifyLimitationRequest;
import py.thrift.coordinator.service.Coordinator;
import py.thrift.coordinator.service.DeleteLimitationRequest;
import py.thrift.datanode.service.DataNodeService;
import py.thrift.icshare.ListArchivesRequestThrift;
import py.thrift.icshare.ListArchivesResponseThrift;
import py.thrift.infocenter.service.InformationCenter;
import py.thrift.share.AccessDeniedExceptionThrift;
import py.thrift.share.AccountNotFoundExceptionThrift;
import py.thrift.share.CreateDomainRequest;
import py.thrift.share.CreateDomainResponse;
import py.thrift.share.DatanodeIsUsingExceptionThrift;
import py.thrift.share.DatanodeNotFoundExceptionThrift;
import py.thrift.share.DatanodeNotFreeToUseExceptionThrift;
import py.thrift.share.DeleteDomainRequest;
import py.thrift.share.DomainExistedExceptionThrift;
import py.thrift.share.DomainIsDeletingExceptionThrift;
import py.thrift.share.DomainNameExistedExceptionThrift;
import py.thrift.share.DomainNotExistedExceptionThrift;
import py.thrift.share.DomainThrift;
import py.thrift.share.EndPointNotFoundExceptionThrift;
import py.thrift.share.FailToRemoveDatanodeFromDomainExceptionThrift;
import py.thrift.share.GetConfigurationsRequest;
import py.thrift.share.GetConfigurationsResponse;
import py.thrift.share.InstanceIsSubHealthExceptionThrift;
import py.thrift.share.InstanceMetadataThrift;
import py.thrift.share.InvalidInputExceptionThrift;
import py.thrift.share.IoLimitationThrift;
import py.thrift.share.LimitTypeThrift;
import py.thrift.share.ListDomainRequest;
import py.thrift.share.ListDomainResponse;
import py.thrift.share.NetworkErrorExceptionThrift;
import py.thrift.share.OneDomainDisplayThrift;
import py.thrift.share.PermissionNotGrantExceptionThrift;
import py.thrift.share.RemoveDatanodeFromDomainRequest;
import py.thrift.share.ResourceNotExistsExceptionThrift;
import py.thrift.share.ServiceHavingBeenShutdownThrift;
import py.thrift.share.ServiceIsNotAvailableThrift;
import py.thrift.share.SetConfigurationsRequest;
import py.thrift.share.StillHaveStoragePoolExceptionThrift;
import py.thrift.share.TooManyEndPointFoundExceptionThrift;
import py.thrift.share.UpdateDomainRequest;

/**
 * DomainServiceImpl.
 */
public class DomainServiceImpl implements DomainService {

  private static final Logger logger = LoggerFactory.getLogger(DomainServiceImpl.class);
  private InformationCenterClientFactory informationCenterClientFactory;
  private CoordinatorClientFactory coordinatorClientFactory;
  private GenericThriftClientFactory<DataNodeService.Iface> dataNodeClientFactory;
  private final String configWriteKey = "discardWriteRatio";
  private final String configWriteValue = "75";
  private final String configWriteHigherValue = "85";
  private final String configReadKey = "discardReadRatio";
  private final String configReadValue = "75";
  private final String configReadHigherValue = "85";
  private final String configShadowPage = "dynamicShadowPageSwitch";

  private final Long ioLimitationId = 12345679012L;
  private int lowerIoPsLimitation;
  // private final Long LOWER_THROUGHPUT_LIMITATION = 600 * 1024l * 1024l;
  private int lowerIoPsLimitation1;
  // private final Long LOWER_THROUGHPUT_LIMITATION_1 = 700 * 1024l * 1024l;
  private int lowerThroughputLimitation;
  private int lowerThroughputLimitation1;

  private InstanceStore instanceStore;

  @Override
  public List<SimpleDomain> listDomains(List<Long> domainIds, long accountId)
      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      ServiceIsNotAvailableThrift,
      EndPointNotFoundExceptionThrift, TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift,
      TException {
    ListDomainRequest request = new ListDomainRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setAccountId(accountId);
    if (domainIds == null || domainIds.size() == 0) {
      request.setDomainIds(null);
    } else {
      request.setDomainIds(domainIds);
    }

    try {
      InformationCenter.Iface iface = informationCenterClientFactory.build().getClient();
      ListDomainResponse response = iface.listDomains(request);
      return buildDomainListFromRsp(response);
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }
  }

  @Override
  public void deleteDomain(String domainId, long accountId)
      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      DomainNotExistedExceptionThrift,
      ServiceIsNotAvailableThrift, StillHaveStoragePoolExceptionThrift,
      DomainIsDeletingExceptionThrift,
      ResourceNotExistsExceptionThrift, PermissionNotGrantExceptionThrift,
      AccessDeniedExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    if (domainId == null) {
      logger.warn("Invalid input params");
      throw new InvalidInputExceptionThrift();
    }
    DeleteDomainRequest deleteDomainRequest = new DeleteDomainRequest();
    deleteDomainRequest.setRequestId(RequestIdBuilder.get());
    deleteDomainRequest.setDomainId(Long.parseLong(domainId));
    deleteDomainRequest.setAccountId(accountId);

    try {
      InformationCenter.Iface iface = informationCenterClientFactory.build().getClient();
      iface.deleteDomain(deleteDomainRequest);
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }

  }

  public InformationCenterClientFactory getInformationCenterClientFactory() {
    return informationCenterClientFactory;
  }

  public void setInformationCenterClientFactory(
      InformationCenterClientFactory informationCenterClientFactory) {
    this.informationCenterClientFactory = informationCenterClientFactory;
  }

  /**
   * build domain list from rsp.
   *
   * @param response response
   * @return simple domain list
   * @throws ServiceHavingBeenShutdownThrift ServiceHavingBeenShutdownThrift
   */
  public List<SimpleDomain> buildDomainListFromRsp(ListDomainResponse response)
      throws ServiceHavingBeenShutdownThrift {
    List<SimpleDomain> simpleDomainList = new ArrayList<SimpleDomain>();
    if (response == null) {
      logger.warn("ListDomainResponse is null");
      throw new ServiceHavingBeenShutdownThrift();
    }
    List<OneDomainDisplayThrift> domainThriftList = response.getDomainDisplays();
    if (domainThriftList == null || domainThriftList.size() == 0) {
      return simpleDomainList;
    }
    for (OneDomainDisplayThrift domainDisplay : domainThriftList) {
      SimpleDomain simpleDomain = new SimpleDomain();
      DomainThrift domainThrift = domainDisplay.getDomainThrift();
      List<InstanceMetadataThrift> instanceThriftList = domainDisplay.getDatanodes();
      simpleDomain.setDomainName(domainThrift.getDomainName());
      simpleDomain.setDomainId(String.valueOf(domainThrift.getDomainId()));
      simpleDomain.setDomainDescription(domainThrift.getDomainDescription());
      simpleDomain.setDataNodes(buildSimpleInstanceFromThrift(instanceThriftList));
      simpleDomain.setStatus(String.valueOf(domainThrift.getStatus()));
      simpleDomain.setLogicalSpace(String.valueOf(domainThrift.getLogicalSpace()));
      simpleDomain.setFreeSpace(String.valueOf(domainThrift.getFreeSpace()));
      simpleDomain.setUseSpace(
          String.valueOf((domainThrift.getLogicalSpace() - domainThrift.getFreeSpace())));
      simpleDomainList.add(simpleDomain);
    }
    return simpleDomainList;
  }

  /**
   * build simple instance from thrift.
   *
   * @param instanceThriftList thrift list instance
   * @return instance list
   */
  public List<SimpleInstance> buildSimpleInstanceFromThrift(
      List<InstanceMetadataThrift> instanceThriftList) {
    List<SimpleInstance> instanceList = new ArrayList<SimpleInstance>();
    if (instanceThriftList == null || instanceThriftList.size() == 0) {
      return instanceList;
    }
    for (InstanceMetadataThrift instanceThrift : instanceThriftList) {
      SimpleInstance simpleInstance = new SimpleInstance();
      if (instanceThrift.getInstanceDomain() != null) {
        simpleInstance.setDomainId(
            String.valueOf(instanceThrift.getInstanceDomain().getDomianId()));
      }
      simpleInstance.setInstanceId(String.valueOf(instanceThrift.getInstanceId()));
      simpleInstance.setHost(instanceThrift.getEndpoint());
      simpleInstance.setInstanceName(PyService.DATANODE.getServiceName());
      simpleInstance.setGroupId(String.valueOf(instanceThrift.getGroup().getGroupId()));
      simpleInstance.setStatus(instanceThrift.getDatanodeStatus().name());
      String endpoint = instanceThrift.getEndpoint();
      simpleInstance.setPort(Integer.valueOf(endpoint.split(":")[1]));
      simpleInstance.setHost(endpoint.split(":")[0]);
      instanceList.add(simpleInstance);
    }
    return instanceList;
  }

  @Override
  public List<SimpleInstance> listInstances() {
    List<SimpleInstance> instanceList = new ArrayList<SimpleInstance>();
    try {
      InformationCenter.Iface iface = informationCenterClientFactory.build().getClient();
      ListArchivesRequestThrift listArchiveRequest = new ListArchivesRequestThrift();
      listArchiveRequest.setRequestId(RequestIdBuilder.get());

      ListArchivesResponseThrift listArchiveRsp = iface.listArchives(listArchiveRequest);
      logger.debug("get archives");
      logger.debug("listArchiveRspis {}", listArchiveRsp);
      instanceList = buildSimpleInstanceFromThrift(listArchiveRsp.getInstanceMetadata());

    } catch (Exception e) {
      logger.error("catch an Exception", e);
    }
    return instanceList;
  }

  public InstanceStore getInstanceStore() {
    return instanceStore;
  }

  public void setInstanceStore(InstanceStore instanceStore) {
    this.instanceStore = instanceStore;
  }

  public int getLowerIoPsLimitation() {
    return lowerIoPsLimitation;
  }

  public void setLowerIoPsLimitation(int lowerIoPsLimitation) {
    this.lowerIoPsLimitation = lowerIoPsLimitation;
  }

  public int getLowerIoPsLimitation1() {
    return lowerIoPsLimitation1;
  }

  public void setLowerIoPsLimitation1(int lowerIoPsLimitation1) {
    this.lowerIoPsLimitation1 = lowerIoPsLimitation1;
  }

  public int getLowerThroughputLimitation() {
    return lowerThroughputLimitation;
  }

  public void setLowerThroughputLimitation(int lowerThroughputLimitation) {
    this.lowerThroughputLimitation = lowerThroughputLimitation;
  }

  public int getLowerThroughputLimitation1() {
    return lowerThroughputLimitation1;
  }

  public void setLowerThroughputLimitation1(int lowerThroughputLimitation1) {
    this.lowerThroughputLimitation1 = lowerThroughputLimitation1;
  }

  @Override
  public SimpleDomain createDomain(SimpleDomain domain, long accountId)
      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      DomainExistedExceptionThrift,
      DomainNameExistedExceptionThrift, ServiceIsNotAvailableThrift,
      DatanodeNotFreeToUseExceptionThrift,
      DatanodeNotFoundExceptionThrift, DatanodeIsUsingExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, TException {
    if (domain == null) {
      logger.warn("Invalid input params");
      throw new InvalidInputExceptionThrift();
    }
    if (domain.getDomainName() == null) {
      logger.error("Domain name can not be null");
      throw new InvalidInputExceptionThrift();
    }
    CreateDomainRequest createDomainRequest = new CreateDomainRequest();
    createDomainRequest.setRequestId(RequestIdBuilder.get());
    createDomainRequest.setAccountId(accountId);
    DomainThrift domainThrift = new DomainThrift();

    // new domain to be created
    domainThrift.setDomainId(RequestIdBuilder.get());

    domainThrift.setDomainName(domain.getDomainName());
    if (domain.getDomainDescription() != null) {
      domainThrift.setDomainDescription(domain.getDomainDescription());
    }
    Set<Long> instanceIdsList = new HashSet<Long>();
    List<SimpleInstance> instances = domain.getDataNodes();
    logger.debug("Datanodes from action: {}", instances);
    if (instances != null) {
      for (SimpleInstance instanceId : instances) {
        instanceIdsList.add(Long.parseLong(instanceId.getInstanceId()));
      }
    }

    domainThrift.setDatanodes(instanceIdsList);
    createDomainRequest.setDomain(domainThrift);
    CreateDomainResponse response = new CreateDomainResponse();
    SimpleDomain simpleDomain = new SimpleDomain();
    try {
      InformationCenter.Iface iface = informationCenterClientFactory.build().getClient();
      response = iface.createDomain(createDomainRequest);
      DomainThrift domainthrift = response.getDomainThrift();
      if (domainthrift != null) {
        simpleDomain.setDomainId(String.valueOf(domainthrift.getDomainId()));
        simpleDomain.setDomainName(domainthrift.getDomainName());
        simpleDomain.setDomainDescription((domainthrift.getDomainDescription()));
        simpleDomain.setStatus(String.valueOf(domainthrift.getStatus()));
        simpleDomain.setLogicalSpace(String.valueOf(domainthrift.getLogicalSpace()));
        simpleDomain.setFreeSpace(String.valueOf(domainthrift.getFreeSpace()));
        simpleDomain
            .setUseSpace(
                String.valueOf((domainthrift.getLogicalSpace() - domainthrift.getFreeSpace())));
      }

    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }
    return simpleDomain;

  }

  @Override
  public void updateDomain(SimpleDomain domain, long accountId)
      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      ServiceIsNotAvailableThrift,
      DatanodeNotFreeToUseExceptionThrift, DatanodeNotFoundExceptionThrift,
      DomainNotExistedExceptionThrift,
      DatanodeIsUsingExceptionThrift, DomainIsDeletingExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift,
      NetworkErrorExceptionThrift, AccessDeniedExceptionThrift, InstanceIsSubHealthExceptionThrift,
      TException {
    if (domain == null || domain.getDomainId() == null) {
      logger.warn("Invalid input params");
      throw new InvalidInputExceptionThrift();
    }
    if (domain.getDomainName() == null) {
      logger.error("Domain name can not be null");
      throw new InvalidInputExceptionThrift();
    }
    UpdateDomainRequest updateDomainRequest = new UpdateDomainRequest();
    updateDomainRequest.setRequestId(RequestIdBuilder.get());
    updateDomainRequest.setAccountId(accountId);
    DomainThrift domainThrift = new DomainThrift();

    domainThrift.setDomainId(Long.parseLong(domain.getDomainId()));

    domainThrift.setDomainName(domain.getDomainName());
    if (domain.getDomainDescription() != null) {
      domainThrift.setDomainDescription(domain.getDomainDescription());
    }
    Set<Long> instanceIdsList = new HashSet<Long>();
    List<SimpleInstance> instances = domain.getDataNodes();
    logger.debug("Datanodes from action: {}", instances);
    if (instances != null) {
      for (SimpleInstance instanceId : instances) {
        instanceIdsList.add(Long.parseLong(instanceId.getInstanceId()));
      }
    }

    domainThrift.setDatanodes(instanceIdsList);
    updateDomainRequest.setDomain(domainThrift);
    try {
      InformationCenter.Iface iface = informationCenterClientFactory.build().getClient();
      iface.updateDomain(updateDomainRequest);
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }

  }

  @Override
  public List<SimpleInstance> listInstancesByIds(List<Long> instanceIds) {
    Validate.notNull(instanceIds);
    List<SimpleInstance> allSimpleInstanceList = listInstances();
    List<SimpleInstance> listSimpleInstance = new ArrayList<SimpleInstance>();
    for (SimpleInstance simpleInstance : allSimpleInstanceList) {
      Long datanodeId = Long.valueOf(simpleInstance.getInstanceId());
      if (instanceIds.contains(datanodeId)) {
        listSimpleInstance.add(simpleInstance);
      }
    }
    return listSimpleInstance;
  }

  @Override
  public void removeDatanodeFromDomain(String domainId, String datanodeId, long accountId)
      throws ServiceHavingBeenShutdownThrift, InvalidInputExceptionThrift,
      ServiceIsNotAvailableThrift,
      FailToRemoveDatanodeFromDomainExceptionThrift, DatanodeNotFoundExceptionThrift,
      DomainNotExistedExceptionThrift, DomainIsDeletingExceptionThrift,
      PermissionNotGrantExceptionThrift,
      AccessDeniedExceptionThrift, AccountNotFoundExceptionThrift, EndPointNotFoundExceptionThrift,
      TooManyEndPointFoundExceptionThrift, NetworkErrorExceptionThrift, TException {
    RemoveDatanodeFromDomainRequest request = new RemoveDatanodeFromDomainRequest();
    request.setRequestId(RequestIdBuilder.get());
    request.setDomainId(Long.valueOf(domainId));
    request.setDatanodeInstanceId(Long.valueOf(datanodeId));
    request.setAccountId(accountId);

    try {
      InformationCenter.Iface client = informationCenterClientFactory.build().getClient();
      client.removeDatanodeFromDomain(request);
    } catch (EndPointNotFoundException e) {
      logger.error("Exception catch", e);
      throw new EndPointNotFoundExceptionThrift();
    } catch (TooManyEndPointFoundException e) {
      logger.error("Exception catch", e);
      throw new TooManyEndPointFoundExceptionThrift();
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
      throw new NetworkErrorExceptionThrift();
    } catch (TException e) {
      logger.error("Exception catch", e);
      throw e;
    }
  }

  public CoordinatorClientFactory getCoordinatorClientFactory() {
    return coordinatorClientFactory;
  }

  public void setCoordinatorClientFactory(CoordinatorClientFactory coordinatorClientFactory) {
    this.coordinatorClientFactory = coordinatorClientFactory;
  }

  private IoLimitationThrift buildLimitationThrift(int upperLimitedIoPs, int lowerLimitedIoPs,
      long upperLimitedThroughput, long lowerLimitedThroughput) {
    IoLimitationThrift limitationThrfit = new IoLimitationThrift();
    limitationThrfit.setLimitationId(ioLimitationId);
    // TODO ioLimitationEntry
    //        limitationThrfit.setUpperLimitedIOPS(upperLimitedIoPs);
    //        limitationThrfit.setLowerLimitedIOPS(lowerLimitedIoPs);
    //        limitationThrfit.setUpperLimitedThroughput(upperLimitedThroughput);
    //        limitationThrfit.setLowerLimitedThroughput(lowerLimitedThroughput);
    limitationThrfit.setLimitType(LimitTypeThrift.Static);
    return limitationThrfit;
  }

  @Override
  public void dynamicConfigCoordinator(boolean write, String value) throws TException {
    String configKey = write ? configWriteKey : configReadKey;
    boolean caughtException = false;
    for (Instance instance : instanceStore.getAll(PyService.COORDINATOR.getServiceName())) {
      EndPoint coordinatorEndPoint = instance.getEndPointByServiceName(PortType.CONTROL);
      try {
        logger.warn("going to set coordinator:{}, key:{}, value:{}", coordinatorEndPoint, configKey,
            value);
        SetConfigurationsRequest setConfigRequest = new SetConfigurationsRequest();
        setConfigRequest.setRequestId(RequestIdBuilder.get());
        Map<String, String> configMap = new HashMap<>();
        configMap.put(configKey, value);
        setConfigRequest.setConfigurations(configMap);
        Coordinator.Iface coordinatorClient = coordinatorClientFactory.build(coordinatorEndPoint)
            .getClient();
        coordinatorClient.setConfigurations(setConfigRequest);
        if (write) {
          if (value.equals("0")) {
            DeleteLimitationRequest deleteRequest = new DeleteLimitationRequest();
            deleteRequest.setRequestId(RequestIdBuilder.get());
            deleteRequest.setIoLimitationId(ioLimitationId);
            coordinatorClient.deleteLimitation(deleteRequest);
            logger.warn("going to delete io limitation:{} @ {}", deleteRequest,
                coordinatorEndPoint);
          } else {
            AddOrModifyLimitationRequest addRequest = new AddOrModifyLimitationRequest();
            addRequest.setRequestId(RequestIdBuilder.get());
            IoLimitationThrift ioLimitationThrift = buildLimitationThrift(0, lowerIoPsLimitation, 0,
                lowerThroughputLimitation * 1024 * 1024);
            addRequest.setIoLimitation(ioLimitationThrift);
            coordinatorClient.addOrModifyLimitation(addRequest);
            logger.warn("going to add io limitation request:{} @ {}", addRequest,
                coordinatorEndPoint);
          }
        }
      } catch (Exception e) {
        logger.error("set coordinator:{}, key:{}, value:{} failed", coordinatorEndPoint, configKey,
            value);
        caughtException = true;
      }
    }
    if (caughtException) {
      throw new TException();
    }
  }

  @Override
  public void dynamicConfigDatanodeShadowPage(boolean enableAvoid) throws TException {
    boolean caughtException = false;
    for (Instance instance : instanceStore.getAll(PyService.DATANODE.getServiceName())) {
      EndPoint datanodeEndPoint = instance.getEndPointByServiceName(PortType.CONTROL);
      try {
        logger.warn("going to set datanode:{}, key:{}, value:{}", datanodeEndPoint,
            configShadowPage,
            enableAvoid);
        SetConfigurationsRequest setConfigRequest = new SetConfigurationsRequest();
        setConfigRequest.setRequestId(RequestIdBuilder.get());
        Map<String, String> configMap = new HashMap<>();
        configMap.put(configShadowPage, String.valueOf(enableAvoid));
        setConfigRequest.setConfigurations(configMap);
        DataNodeService.Iface datanodeClient = dataNodeClientFactory.generateSyncClient(
            datanodeEndPoint);
        datanodeClient.setConfigurations(setConfigRequest);
      } catch (Exception e) {
        logger.error("set datanode:{}, key:{}, value:{} failed", datanodeEndPoint,
            configShadowPage,
            enableAvoid);
        caughtException = true;
      }
    }
    if (caughtException) {
      throw new TException();
    }
  }

  public GenericThriftClientFactory<DataNodeService.Iface> getDataNodeClientFactory() {
    return dataNodeClientFactory;
  }

  public void setDataNodeClientFactory(
      GenericThriftClientFactory<DataNodeService.Iface> dataNodeClientFactory) {
    this.dataNodeClientFactory = dataNodeClientFactory;
  }

  @Override
  public void dynamicConfigCoordinatorsBindingOneVolume(List<EndPoint> endPoints, boolean discard,
      boolean higher)
      throws TException {
    boolean caughtException = false;
    Map<String, String> configMap = new HashMap<>();
    for (EndPoint coordinatorEndPoint : endPoints) {
      try {
        Coordinator.Iface coordinatorClient = coordinatorClientFactory.build(coordinatorEndPoint)
            .getClient();
        SetConfigurationsRequest setConfigRequest = new SetConfigurationsRequest();
        setConfigRequest.setRequestId(RequestIdBuilder.get());

        AddOrModifyLimitationRequest addRequest = new AddOrModifyLimitationRequest();
        addRequest.setRequestId(RequestIdBuilder.get());
        IoLimitationThrift ioLimitationThrift = null;
        if (discard) {
          if (higher) {
            configMap.put(configWriteKey, configWriteHigherValue);
            configMap.put(configReadKey, configReadHigherValue);
            ioLimitationThrift = buildLimitationThrift(0, lowerIoPsLimitation1, 0,
                lowerThroughputLimitation1 * 1024 * 1024);
          } else {
            configMap.put(configWriteKey, configWriteValue);
            configMap.put(configReadKey, configReadValue);
            ioLimitationThrift = buildLimitationThrift(0, lowerIoPsLimitation, 0,
                lowerThroughputLimitation * 1024 * 1024);
          }
          addRequest.setIoLimitation(ioLimitationThrift);
          coordinatorClient.addOrModifyLimitation(addRequest);
          logger.warn("going to add io limitation request:{} @ {}", addRequest,
              coordinatorEndPoint);
        } else {
          DeleteLimitationRequest deleteRequest = new DeleteLimitationRequest();
          deleteRequest.setRequestId(RequestIdBuilder.get());
          deleteRequest.setIoLimitationId(ioLimitationId);
          coordinatorClient.deleteLimitation(deleteRequest);
          logger.warn("going to delete io limitation:{} @ {}", deleteRequest, coordinatorEndPoint);
          // no discard
          configMap.put(configWriteKey, "0");
          configMap.put(configReadKey, "0");
        }
        logger.warn("discard:{}, going to set coordinator:{} config request:{}", discard,
            coordinatorEndPoint,
            configMap);
        setConfigRequest.setConfigurations(configMap);
        coordinatorClient.setConfigurations(setConfigRequest);

      } catch (Exception e) {
        logger.error("set coordinator:{}, config request:{} failed", coordinatorEndPoint, configMap,
            e);
        caughtException = true;
      }
    }
    if (caughtException) {
      throw new TException();
    }
  }

  @Override
  public boolean getDiscardConfigStatus(List<EndPoint> endPoints) throws TException {
    boolean caughtException = false;
    boolean noDiscard = true;
    for (EndPoint coordinatorEndPoint : endPoints) {
      try {
        Coordinator.Iface coordinatorClient = coordinatorClientFactory.build(coordinatorEndPoint)
            .getClient();
        GetConfigurationsRequest getConfigRequest = new GetConfigurationsRequest();
        getConfigRequest.setRequestId(RequestIdBuilder.get());

        logger.warn("going to get coordinator config request:{}", coordinatorEndPoint);
        GetConfigurationsResponse response = coordinatorClient.getConfigurations(getConfigRequest);
        Map<String, String> configMap = response.getResults();
        if (!configMap.containsKey(configWriteKey)
            || Integer.valueOf(configMap.get(configWriteKey)) != 0) {
          logger.warn("coordinator:{} config:{}, value:{}", coordinatorEndPoint, configWriteKey,
              configMap.get(configWriteKey));
          noDiscard = false;
        }

        if (!configMap.containsKey(configReadKey)
            || Integer.valueOf(configMap.get(configReadKey)) != 0) {
          logger.warn("coordinator:{} config:{}, value:{}", coordinatorEndPoint, configReadKey,
              configMap.get(configReadKey));
          noDiscard = false;
        }
      } catch (Exception e) {
        logger.error("get coordinator{} config: failed", coordinatorEndPoint, e);
        caughtException = true;
      }
    }
    if (caughtException) {
      throw new TException();
    }
    logger.warn("get endPoint:{} status:{}", endPoints, noDiscard);
    return noDiscard;
  }

  /**
   * return value: true: need NOT shadow page( high performance but snapshot doesn't work normally),
   * false: need shadow page.
   */
  @Override
  public boolean getShadowPageConfigStatus() throws TException {
    boolean caughtException = false;
    boolean avoidShadowPage = true;
    for (Instance instance : instanceStore.getAll(PyService.DATANODE.getServiceName())) {
      EndPoint datanodeEndPoint = instance.getEndPointByServiceName(PortType.CONTROL);
      try {
        DataNodeService.Iface datanodeClient = dataNodeClientFactory.generateSyncClient(
            datanodeEndPoint);
        GetConfigurationsRequest getConfigRequest = new GetConfigurationsRequest();
        getConfigRequest.setRequestId(RequestIdBuilder.get());

        logger.warn("going to get datanode config request:{}", datanodeEndPoint);
        GetConfigurationsResponse response = datanodeClient.getConfigurations(getConfigRequest);
        Map<String, String> configMap = response.getResults();
        if (!configMap.containsKey(configShadowPage) || !Boolean.valueOf(
            configMap.get(configShadowPage))) {
          logger.warn("datanode:{} config:{}, value:{}", datanodeEndPoint, configShadowPage,
              configMap.get(configShadowPage));
          avoidShadowPage = false;
        }
      } catch (Exception e) {
        logger.error("get datanode:{} config: failed", datanodeEndPoint, e);
        caughtException = true;
      }
    }
    if (caughtException) {
      throw new TException();
    }
    return avoidShadowPage;
  }

  @Override
  public void disableAllDiscardConfigCoordinator() throws TException {
    boolean caughtException = false;
    for (Instance instance : instanceStore.getAll(PyService.COORDINATOR.getServiceName())) {
      EndPoint coordinatorEndPoint = instance.getEndPointByServiceName(PortType.CONTROL);
      Map<String, String> configMap = null;
      try {
        logger.warn("going to disable coordinator:{}, discard", coordinatorEndPoint);
        Coordinator.Iface coordinatorClient = coordinatorClientFactory.build(coordinatorEndPoint)
            .getClient();

        // delete io limitation
        DeleteLimitationRequest deleteRequest = new DeleteLimitationRequest();
        deleteRequest.setRequestId(RequestIdBuilder.get());
        deleteRequest.setIoLimitationId(ioLimitationId);
        coordinatorClient.deleteLimitation(deleteRequest);
        logger.warn("going to delete io limitation:{} @ {}", deleteRequest, coordinatorEndPoint);

        SetConfigurationsRequest setConfigRequest = new SetConfigurationsRequest();
        setConfigRequest.setRequestId(RequestIdBuilder.get());
        configMap = new HashMap<>();
        // no discard
        configMap.put(configWriteKey, "0");
        configMap.put(configReadKey, "0");
        setConfigRequest.setConfigurations(configMap);
        coordinatorClient.setConfigurations(setConfigRequest);

      } catch (Exception e) {
        logger.error("diable coordinator:{}, config:{} failed", coordinatorEndPoint, configMap, e);
        caughtException = true;
      }
    }
    if (caughtException) {
      throw new TException();
    }
  }

  @Override
  public SimpleDomain getDomainByIdFromDomainList(long domainId, List<SimpleDomain> domainList) {
    SimpleDomain targetDomain = null;
    logger.debug("getDomainNameById: domainList is {}", domainList);
    for (SimpleDomain domain : domainList) {
      logger.debug("domain.getDomainId() is {}, filter domainId(): {}", domain.getDomainId(),
          domainId);
      if (domain.getDomainId().equals(String.valueOf(domainId))) {
        logger.debug("domainName is {}", domain.getDomainName());
        targetDomain = domain;
        break;
      }
    }
    return targetDomain;
  }

}
