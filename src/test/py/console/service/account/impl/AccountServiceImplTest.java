package py.console.service.account.impl;

import io.swagger.models.auth.In;
import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.common.struct.EndPoint;
import py.infocenter.client.InformationCenterClientFactory;
import py.instance.*;
import py.test.TestBase;

import java.util.Map;

import static org.mockito.Mockito.when;

public class AccountServiceImplTest extends TestBase {

  private static final Logger logger = LoggerFactory.getLogger(AccountServiceImplTest.class);

  @Mock
  private InstanceStore instanceStore;

  private long infocenterId = 4565816997833504914L;
  private InstanceId infocenterInstanceId = new InstanceId(infocenterId);

  public InformationCenterClientFactory infoCenterClientFactory() {
    InformationCenterClientFactory infoCenterClientFactory = new InformationCenterClientFactory(1);
    infoCenterClientFactory.setInstanceStore(instanceStore);
    infoCenterClientFactory.setInstanceId(infocenterInstanceId);
    return infoCenterClientFactory;
  }

  @Before
  public void init() {
    Instance infocenter = new Instance(infocenterInstanceId, "InfoCenter", InstanceStatus.HEALTHY,
        new EndPoint("10.0.2.105", 8020));
    when(instanceStore.get(infocenterInstanceId)).thenReturn(infocenter);

  }

  @Test
  public void authenticateAccountTest() throws TException {
    AccountServiceImpl accountService = new AccountServiceImpl();
    accountService.setInfoCenterClientFactory(infoCenterClientFactory());
    Map<String, Object> ret = accountService.authenticateAccount("admin", "admin");
    logger.warn("got ret:{}", ret);

  }
}