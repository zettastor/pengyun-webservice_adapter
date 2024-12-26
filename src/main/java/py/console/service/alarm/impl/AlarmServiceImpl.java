

package py.console.service.alarm.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.console.service.alarm.AlarmService;
import py.infocenter.client.InformationCenterClientFactory;

/**
 * AlarmServiceImpl.
 */
public class AlarmServiceImpl implements AlarmService {

  private static final Logger logger = LoggerFactory.getLogger(AlarmServiceImpl.class);
  private InformationCenterClientFactory infoCenterClientFactory;

  public InformationCenterClientFactory getInfoCenterClientFactory() {
    return infoCenterClientFactory;
  }

  public void setInfoCenterClientFactory(InformationCenterClientFactory infoCenterClientFactory) {
    this.infoCenterClientFactory = infoCenterClientFactory;
  }

}
