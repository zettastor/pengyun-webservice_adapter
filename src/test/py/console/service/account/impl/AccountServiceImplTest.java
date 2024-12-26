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