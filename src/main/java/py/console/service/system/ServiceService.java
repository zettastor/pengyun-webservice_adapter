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
