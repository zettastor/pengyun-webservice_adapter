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
