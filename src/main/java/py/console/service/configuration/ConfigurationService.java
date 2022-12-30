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

package py.console.service.configuration;

import java.util.List;
import org.apache.thrift.TException;
import py.console.bean.SimpleConfiguration;
import py.console.bean.SimpleConfigurationResult;
import py.thrift.share.InvalidInputExceptionThrift;

/**
 * ConfigurationService.
 */
public interface ConfigurationService {

  public List<SimpleConfiguration> getConfiguration(String conditions)
      throws InvalidInputExceptionThrift, TException;

  public List<SimpleConfigurationResult> setConfiguration(String unFormattedConfigurations)
      throws InvalidInputExceptionThrift,
      TException;
}
