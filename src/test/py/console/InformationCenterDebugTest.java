package py.console;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import py.common.PyService;
import py.common.struct.EndPoint;
import py.common.struct.EndPointParser;
import py.console.config.ServiceBeans;
import py.console.service.account.impl.AccountServiceImpl;
import py.dih.client.DihClientFactory;
import py.dih.client.DihInstanceStore;
import py.infocenter.client.InformationCenterClientFactory;
import py.instance.InstanceStore;
import py.test.TestBase;
import py.thrift.infocenter.service.InformationCenter;
import py.thrift.share.AuthenticateAccountRequest;
import py.thrift.share.AuthenticateAccountResponse;

import static py.common.Constants.SUPERADMIN_ACCOUNT_ID;

/**
 * in this unit,there is some option must change hostName: one of dih host name volumeId : which you
 * create in console
 *
 */

/**
 * just for debug the informationCenter interface
 ****/
@Ignore
public class InformationCenterDebugTest extends TestBase {

  private int localDIHPort = 10000;
  private long accountId = SUPERADMIN_ACCOUNT_ID;
  private InformationCenterClientFactory infoCenterClientFactory;
  private InformationCenter.Iface informationCenter;
  private InstanceStore instanceStore;

  /*** modify fields, only set the hostName(one of the DIH host)
   **/
//    private String hostName = "10.0.2.170";
//    private String hostName = "192.168.2.80";
  private String hostName = "10.0.2.175";
  private long volumeId = 2152091268712079375L;

  public InformationCenterDebugTest() {
  }

  public EndPoint localDIHEP() {
    return EndPointParser.parseLocalEndPoint(localDIHPort, hostName);
  }

  public DihClientFactory dihClientFactory() {
    DihClientFactory dihClientFactory = new DihClientFactory(1);
    return dihClientFactory;
  }

  public InstanceStore instanceStore() throws Exception {
    Object instanceStore = DihInstanceStore.getSingleton();
    ((DihInstanceStore) instanceStore).setDihClientFactory(dihClientFactory());
    ((DihInstanceStore) instanceStore).setDihEndPoint(localDIHEP());
    ((DihInstanceStore) instanceStore).init();
    Thread.sleep(3000);
    System.out.println("====" + ((DihInstanceStore) instanceStore).getAll());
    return (InstanceStore) instanceStore;
  }

  public InformationCenterClientFactory informationCenterClientFactory() throws Exception {
    InformationCenterClientFactory factory = new InformationCenterClientFactory(1);
    factory.setInstanceName(PyService.INFOCENTER.getServiceName());
    factory.setInstanceStore(instanceStore());
    return factory;
  }

  @Override
  @Before
  public void init() throws Exception {
    /** set log **/
    Logger rootLogger = Logger.getRootLogger();
    rootLogger.setLevel(Level.WARN);

    super.init();
    instanceStore = instanceStore();
    infoCenterClientFactory = informationCenterClientFactory();
  }

  @Test
  public void testLogin() throws TException {
    AuthenticateAccountRequest request = new AuthenticateAccountRequest();
    request.setAccountName("admin");
    request.setPassword("admin");

    try {
      informationCenter = infoCenterClientFactory.build().getClient();
      AuthenticateAccountResponse response = informationCenter.authenticateAccount(request);
      logger.warn("get the value is :{}", response);
    } catch (Exception e) {
      logger
          .error("catch an exception when list volumes {}, accountId: {}", volumeId, accountId, e);
    }
  }

}
