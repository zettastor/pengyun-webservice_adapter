

package py.console.service.license.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.console.service.license.LicenseService;
import py.console.service.volume.impl.VolumeServiceImpl;
import py.infocenter.client.InformationCenterClientFactory;

/**
 * LicenseServiceImpl.
 */
public class LicenseServiceImpl implements LicenseService {

  private static final Logger logger = LoggerFactory.getLogger(VolumeServiceImpl.class);

  private InformationCenterClientFactory infoCenterClientFactory;

  public InformationCenterClientFactory getInfoCenterClientFactory() {
    return infoCenterClientFactory;
  }

  public void setInfoCenterClientFactory(InformationCenterClientFactory infoCenterClientFactory) {
    this.infoCenterClientFactory = infoCenterClientFactory;
  }
}
