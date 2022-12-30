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

package py.console.service.system;

import java.util.List;
import py.console.bean.SimpleServiceMetadata;
import py.console.bean.SimpleServicesMetadata;

/**
 * This service provides a way to manage all service, including startup and shutdown operation.
 *
 * <p>There are several types services. Each service has a lot of instance and which was deployed to
 * different host.
 *
 */
public interface ServiceService {

  /**
   * startup a service.
   *
   * <p>In fact, this api do the job that startup all instance of the given service.
   */
  public void startup(String serviceName);

  /**
   * startup a service by the given name at the host assigned.
   *
   * @param appName app name
   * @param hostname hostname
   */
  public void startup(String appName, String hostname);

  /**
   * shutdown a service by the given name at the host assigned.
   *
   * @param appName app name
   * @param hostname hostname
   */
  public void shutdown(String appName, String hostname);

  /**
   * Shutdown a service.
   *
   * <p>In fact, this api do the job that shutdown all instances of the given service.
   */
  public void shutdown(String serviceName);

  /**
   * list all instances of a service by the given service name.
   *
   * @param serviceName service name
   */
  public List<SimpleServiceMetadata> listService(String serviceName);

  /**
   * List all kinds of services
   *
   * <p>Service such as DIH is a kind and so is ControCenter, InfoCenter and so on.
   */
  public List<SimpleServicesMetadata> listServices();

}
