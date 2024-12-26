

package py.console.service.alert.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.console.service.alert.DtoLogService;
import py.infocenter.client.InformationCenterClientFactory;

/**
 * DtoLogServiceImpl.
 */
public class DtoLogServiceImpl implements DtoLogService {

  private static final Logger logger = LoggerFactory.getLogger(DtoLogServiceImpl.class);
  private InformationCenterClientFactory infoCenterClientFactory;


  public InformationCenterClientFactory getInfoCenterClientFactory() {
    return infoCenterClientFactory;
  }

  public void setInfoCenterClientFactory(InformationCenterClientFactory infoCenterClientFactory) {
    this.infoCenterClientFactory = infoCenterClientFactory;
  }

}
