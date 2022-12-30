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

package py.console.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import py.DeploymentDaemonClientFactory;
import py.app.NetworkConfiguration;
import py.client.thrift.GenericThriftClientFactory;
import py.common.struct.EndPoint;
import py.common.struct.EndPointParser;
import py.console.service.access.rule.impl.VolumeAccessRuleServiceImpl;
import py.console.service.account.impl.AccountServiceImpl;
import py.console.service.account.impl.AccountSessionServiceImpl;
import py.console.service.alarm.impl.AlarmServiceImpl;
import py.console.service.alert.imp.AlertServiceImpl;
import py.console.service.alert.imp.DtoLogServiceImpl;
import py.console.service.alert.imp.DtoUserServiceImpl;
import py.console.service.checkservice.impl.CheckStatusServiceImpl;
import py.console.service.configuration.impl.ConfigurationServiceImpl;
import py.console.service.disk.impl.DiskServiceImpl;
import py.console.service.domain.impl.DomainServiceImpl;
import py.console.service.driver.impl.DriverServiceImpl;
import py.console.service.instance.impl.InstanceServiceImpl;
import py.console.service.license.impl.LicenseServiceImpl;
import py.console.service.operation.impl.OperationServiceImpl;
import py.console.service.performance.impl.PerformanceServiceImpl;
import py.console.service.qos.impl.QosServiceImpl;
import py.console.service.storagepool.impl.StoragePoolServiceImpl;
import py.console.service.system.impl.SystemServiceImpl;
import py.console.service.volume.impl.VolumeServiceImpl;
import py.dd.DeploymentDaemonClientHandler;
import py.dih.client.DihClientFactory;
import py.dih.client.DihInstanceStore;
import py.drivercontainer.client.CoordinatorClientFactory;
import py.infocenter.client.InformationCenterClientFactory;
import py.monitorserver.client.MonitorServerClientFactory;
import py.storage.StorageConfiguration;
import py.thrift.datanode.service.DataNodeService;

/**
 * ServiceBeans.
 */
@Configuration
@EnableScheduling
@Import({StorageConfiguration.class, NetworkConfiguration.class})
public class ServiceBeans {

  private static final Logger logger = LoggerFactory.getLogger(ServiceBeans.class);

  @Value("${local.dih.endpoint}")
  private String localDihEndPoint;

  @Value("${request.timeout}")
  private int requestTimeout;

  @Value("${refresh.cpu.chart.time:7200000}")
  private long refreshCpuChartTime;

  @Value("${lower.iops.limitation:11000}")
  private int lowerIoPsLimitation;

  @Value("${lower.iops.limitation1:13000}")
  private int lowerIoPsLimitation1;

  @Value("${lower.throughput.limitation.mb:600}")
  private int lowerThroughputLimitation;

  @Value("${lower.throughput.limitation1.mb:700}")
  private int lowerThroughputLimitation1;

  @Value("${test.button.switch.flag:false}")
  private boolean swithcFlag;
  @Value("${deployment.daemon.port}")
  private int ddPort;

  @Value("${deploy.path}")
  private String deployPath;

  @Value("${ftp.username:user}")
  private String ftpUserame;

  @Value("${ftp.password:user}")
  private String ftpPwd;

  @Value("${volume.detail.show.flag}")
  private String volumeDetailShowFlag;

  @Value("${snapshot.show.flag}")
  private String snapshotShowFlag;

  @Value("${csi.show.flag:false}")
  private String csiShowFlag;

  /* @Value("${app.name}")
  private String appName;

  @Value("${app.main.endpoint}")
  private String mainEndPoint;

  @Value("${health.checker.rate}")
  private int healthCheckerRate;

  @Value("${thrift.client.timeout}")
  private int thriftClientTimeout;

  @Value("${dih.endpoint}")
  private String dihEndPoint; */

  @Autowired
  private StorageConfiguration storageConfiguration;

  @Autowired
  private NetworkConfiguration networkConfiguration;

  public long getSegmentSize() {
    return storageConfiguration.getSegmentSizeByte();
  }

  public long getPageSize() {
    return storageConfiguration.getPageSizeByte();
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  /**
   * account session service.
   *
   * @return account session service
   */
  @Bean
  public AccountSessionServiceImpl accountSessionService() {
    AccountSessionServiceImpl accountSessionService = new AccountSessionServiceImpl();
    accountSessionService.setInfoCenterClientFactory(infoCenterClientFactory());
    return accountSessionService;
  }

  /**
   * account service.
   *
   * @return account service
   */
  @Bean
  public AccountServiceImpl accountService() {
    AccountServiceImpl accountService = new AccountServiceImpl();
    accountService.setInfoCenterClientFactory(infoCenterClientFactory());
    accountService.setVolumeService(volumeService());
    accountService.setSnapshotShowFlag(snapshotShowFlag);
    accountService.setCsiFlag(csiShowFlag);
    logger.warn("get the snapshotShowFlag:{}, csiShowFlag :{}", snapshotShowFlag, csiShowFlag);
    return accountService;
  }

  /**
   * volume service.
   *
   * @return volume service
   */
  @Bean
  public VolumeServiceImpl volumeService() {
    VolumeServiceImpl volumeService = new VolumeServiceImpl();
    volumeService.setInfoCenterClientFactory(infoCenterClientFactory());
    volumeService.setStorageConfiguration(storageConfiguration);
    volumeService.setDomainService(domainService());
    volumeService.setStoragePoolService(storagePoolService());
    volumeService.setInstanceService(instanceService());
    volumeService.setVolumeDetailShowFlag(volumeDetailShowFlag);
    return volumeService;
  }

  /**
   * info center client factory.
   *
   * @return info center client factory
   */
  @Bean
  public InformationCenterClientFactory infoCenterClientFactory() {
    InformationCenterClientFactory infoCenterClientFactory = new InformationCenterClientFactory(1);
    infoCenterClientFactory.setInstanceStore(instanceStore());
    return infoCenterClientFactory;
  }

  // @Bean
  // public MonitorCenterClientFactory monitorCenterClientFactory() {
  //    MonitorCenterClientFactory monitorCenterClientFactory = new MonitorCenterClientFactory(1);
  //    monitorCenterClientFactory.setInstanceStore(instanceStore());
  //    return monitorCenterClientFactory;
  // }

  /**
   * monitor server client factory.
   *
   * @return monitor server client factory
   */
  @Bean
  public MonitorServerClientFactory monitorServerClientFactory() {
    MonitorServerClientFactory monitorServerClientFactory = new MonitorServerClientFactory(1);
    monitorServerClientFactory.setInstanceStore(instanceStore());
    return monitorServerClientFactory;
  }

  /**
   * coordinator client factory.
   *
    * @return coordinator client factory
   */
  @Bean
  public CoordinatorClientFactory coordinatorClientFactory() {
    CoordinatorClientFactory coordinatorClientFactory = new CoordinatorClientFactory(1);
    coordinatorClientFactory.setInstanceStore(instanceStore());
    return coordinatorClientFactory;
  }

  @Bean
  public GenericThriftClientFactory<DataNodeService.Iface> dataNodeClientFactory() {
    return GenericThriftClientFactory.create(DataNodeService.Iface.class, 1);
  }

  /**
   * instance service.
   *
   * @return instance service
   */
  @Bean
  public InstanceServiceImpl instanceService() {
    InstanceServiceImpl instanceService = new InstanceServiceImpl();
    instanceService.setDihClientFactory(dihClientFactory());
    instanceService.setLocalDihEndPoint(dihEndPoint());
    instanceService.setDeploymentDaemonClientHandler(deploymentDaemonClientHandler());
    instanceService.setInstanceStore(instanceStore());
    instanceService.setDdPort(ddPort);
    instanceService.init();
    instanceService.setInfoCenterClientFactory(infoCenterClientFactory());
    return instanceService;
  }

  /**
   * performance service.
   *
   * @return performance service
   */
  @Bean
  public PerformanceServiceImpl performanceService() {
    PerformanceServiceImpl performanceService = new PerformanceServiceImpl();
    performanceService.setInfoCenterClientFactory(infoCenterClientFactory());
    performanceService.setVolumeService(volumeService());
    return performanceService;
  }

  /**
   * deployment daemon client handler.
   *
   * @return deployment daemon client handler
   */
  @Bean
  public DeploymentDaemonClientHandler deploymentDaemonClientHandler() {
    DeploymentDaemonClientHandler deploymentDaemonClient = new DeploymentDaemonClientHandler();
    deploymentDaemonClient.setDeploymentDaemonClientFactory(deploymentDaemonClientFactory());
    return deploymentDaemonClient;
  }

  /**
   * deployment daemon client factory.
   *
   * @return deployment daemon client factory
   */
  @Bean
  public DeploymentDaemonClientFactory deploymentDaemonClientFactory() {
    DeploymentDaemonClientFactory deploymentDaemonClientFactory =
        new DeploymentDaemonClientFactory();
    deploymentDaemonClientFactory.setThriftTimeout(2000);
    return deploymentDaemonClientFactory;
  }

  /**
   * instance store.
   *
   * @return instance store
   */
  @Bean
  public DihInstanceStore instanceStore() {
    try {
      DihInstanceStore instanceStore = DihInstanceStore.getSingleton();
      instanceStore.setDihEndPoint(dihEndPoint());
      instanceStore.setRequestTimeout(requestTimeout);
      instanceStore.setDihClientFactory(dihClientFactory());
      instanceStore.init();
      return instanceStore;
    } catch (Exception e) {
      logger.error("Failed to init instanceStore.", e);
      return null;
    }
  }

  @Bean
  public DihClientFactory dihClientFactory() {
    DihClientFactory dihClientFactory = new DihClientFactory(1);
    return dihClientFactory;
  }

  /**
   * system service.
   *
   * @return system service
   */
  @Bean
  public SystemServiceImpl systemService() {
    SystemServiceImpl systemService = new SystemServiceImpl();
    systemService.setInfoCenterClientFactory(infoCenterClientFactory());
    systemService.setInstanceService(instanceService());
    systemService.setPerformanceService(performanceService());
    systemService.setVolumeAccessRuleService(volumeAccessRuleService());
    systemService.setVolumeService(volumeService());
    return systemService;
  }

  // @Bean
  // public Bootstrap bootstrap() {
  // Bootstrap bootstrap = new Bootstrap();
  // bootstrap.setAccountService(accountService());
  // bootstrap.init();
  // return bootstrap;
  // }

  /**
   * volume access rule service.
   *
   * @return volume access rule service
   */
  @Bean
  public VolumeAccessRuleServiceImpl volumeAccessRuleService() {
    VolumeAccessRuleServiceImpl volumeAccessRuleServiceImpl = new VolumeAccessRuleServiceImpl();
    volumeAccessRuleServiceImpl.setInfoCenterClientFactory(infoCenterClientFactory());
    volumeAccessRuleServiceImpl.setVolumeService(volumeService());
    volumeAccessRuleServiceImpl.setDriverService(driverService());
    volumeAccessRuleServiceImpl.setInstanceService(instanceService());
    return volumeAccessRuleServiceImpl;
  }

  /**
   * license service.
   *
   * @return license service
   */
  @Bean
  public LicenseServiceImpl licenseService() {
    LicenseServiceImpl licenseService = new LicenseServiceImpl();
    licenseService.setInfoCenterClientFactory(infoCenterClientFactory());
    return licenseService;
  }

  /**
   * domain service.
   *
   * @return domain service
   */
  @Bean
  public DomainServiceImpl domainService() {
    DomainServiceImpl domainService = new DomainServiceImpl();
    domainService.setInstanceStore(instanceStore());
    domainService.setInformationCenterClientFactory(infoCenterClientFactory());
    domainService.setCoordinatorClientFactory(coordinatorClientFactory());
    domainService.setDataNodeClientFactory(dataNodeClientFactory());
    domainService.setLowerIoPsLimitation(lowerIoPsLimitation);
    domainService.setLowerIoPsLimitation1(lowerIoPsLimitation1);
    domainService.setLowerThroughputLimitation(lowerThroughputLimitation);
    domainService.setLowerThroughputLimitation1(lowerThroughputLimitation1);
    return domainService;
  }

  /**
   * storage pool service.
   *
   * @return storage pool service
   */
  @Bean
  public StoragePoolServiceImpl storagePoolService() {
    StoragePoolServiceImpl storagePoolService = new StoragePoolServiceImpl();
    storagePoolService.setInfoCenterClientFactory(infoCenterClientFactory());
    storagePoolService.setInstanceService(instanceService());
    return storagePoolService;
  }

  /**
   * disk service.
   *
   * @return disk service
   */
  @Bean
  public DiskServiceImpl diskService() {
    DiskServiceImpl diskService = new DiskServiceImpl();
    diskService.setInfoCenterClientFactory(infoCenterClientFactory());
    diskService.setPoolService(storagePoolService());
    diskService.setInstanceService(instanceService());
    diskService.setDomainService(domainService());
    return diskService;
  }

  /**
   * alarm service.
   *
   * @return alarm service
   */
  @Bean
  public AlarmServiceImpl alarmService() {
    AlarmServiceImpl alarmService = new AlarmServiceImpl();
    alarmService.setInfoCenterClientFactory(infoCenterClientFactory());
    return alarmService;
  }

  /**
   * operation service.
   *
   * @return operation service
   */
  @Bean
  public OperationServiceImpl operationService() {
    OperationServiceImpl operationService = new OperationServiceImpl();
    operationService.setInfoCenterClientFactory(infoCenterClientFactory());
    operationService.setDeployPath(deployPath);
    operationService.setFtpPwd(ftpPwd);
    operationService.setFtpUsername(ftpUserame);
    return operationService;
  }

  @Bean
  public ConfigurationServiceImpl configurationService() {
    ConfigurationServiceImpl configurationService = new ConfigurationServiceImpl();
    return configurationService;
  }

  @Bean
  public EndPoint dihEndPoint() {
    return EndPointParser.parseInSubnet(localDihEndPoint,
        networkConfiguration.getControlFlowSubnet());
  }

  /**
   * alert service.
   *
   * @return alert service
   */
  @Bean
  public AlertServiceImpl alertService() {
    AlertServiceImpl alertService = new AlertServiceImpl();
    alertService.setMonitorServerClientFactory(monitorServerClientFactory());
    alertService.setInfoCenterClientFactory(infoCenterClientFactory());
    return alertService;
  }

  /**
   * qos service.
   *
   * @return qos service
   */
  @Bean
  public QosServiceImpl qosService() {
    QosServiceImpl qosService = new QosServiceImpl();
    qosService.setInfoCenterClientFactory(infoCenterClientFactory());
    qosService.setPoolService(storagePoolService());
    qosService.setInstanceService(instanceService());
    qosService.setDriverService(driverService());
    return qosService;
  }

  /**
   * driver service.
   *
   * @return driver service
   */
  @Bean
  public DriverServiceImpl driverService() {
    DriverServiceImpl driverService = new DriverServiceImpl();
    driverService.setInfoCenterClientFactory(infoCenterClientFactory());
    driverService.setInstanceStore(instanceStore());
    driverService.setInstanceService(instanceService());
    driverService.setDomainService(domainService());
    driverService.setVolumeService(volumeService());
    driverService.setPoolService(storagePoolService());
    return driverService;
  }

  /**
   * dto user service.
   *
   * @return dto user service
   */
  @Bean
  public DtoUserServiceImpl dtoUserService() {
    DtoUserServiceImpl dtoUserService = new DtoUserServiceImpl();
    dtoUserService.setInfoCenterClientFactory(infoCenterClientFactory());
    return dtoUserService;
  }

  /**
   * dto log service.
   *
   * @return dto log service
   */
  @Bean
  public DtoLogServiceImpl dtoLogService() {
    DtoLogServiceImpl dtoLogService = new DtoLogServiceImpl();
    dtoLogService.setInfoCenterClientFactory(infoCenterClientFactory());
    return dtoLogService;
  }

  /**
   * check status service.
   *
   * @return check status service
   */
  @Bean
  public CheckStatusServiceImpl checkStatusService() {
    CheckStatusServiceImpl checkStatusService = new CheckStatusServiceImpl();
    checkStatusService.setInfoCenterClientFactory(infoCenterClientFactory());
    logger.warn("get the checkStatusService:{}", checkStatusService);
    return checkStatusService;
  }

  /* @Bean
  public ServiceBeanContext appContext() throws Exception {
      ServiceBeanContext appContext = new ServiceBeanContext(appName);
      // control stream
      EndPoint endpointOfControlStream = EndPointParser
              .parseInSubnet(mainEndPoint, networkConfiguration.getControlFlowSubnet());
      appContext.putEndPoint(PortType.CONTROL, endpointOfControlStream);
      appContext.setInstanceIdStore(
        new InstanceIdFileStore(appName, appName, endpointOfControlStream.getPort())
      );
      return appContext;
  }

  @Bean
  public ConsoleHeartbeatWorker healthChecker() throws Exception {
      ConsoleHeartbeatWorker healthChecker =
        new ConsoleHeartbeatWorker(healthCheckerRate,heartBeatWorkerFactory());
      return healthChecker;
  }

  @Bean
  public HeartBeatWorkerFactory heartBeatWorkerFactory() throws Exception {
      HeartBeatWorkerFactory factory = new HeartBeatWorkerFactory();
      factory.setRequestTimeout(thriftClientTimeout);
      factory.setAppContext(appContext());
      factory.setDihClientFactory(dihClientFactory());
      factory.setLocalDIHEndPoint(
        EndPointParser
          .parseLocalEndPoint(dihEndPoint, appContext().getMainEndPoint().getHostName())
      );
      //set to inform heartbeat work to process if use zookeeper
      //factory.setNeedSyncDihAndZookeeper(zookeeperElectionSwitch);
      return factory;
  }
  @Bean
  public HealthChecker startHealthChecker(){
      try{
          HealthChecker healthChecker= healthChecker();
          healthChecker.startHealthCheck();
          logger.warn("start health check");
          return healthChecker;
      }catch (Exception e){
          logger.error("catch exception",e);
      }
      return null;

  } */
}
