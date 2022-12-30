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

package py.console.bean;

/**
 * for a type services metadata.
 *
 */
public class SimpleServicesMetadata {

  private String serviceType;

  private String servicesStatus;

  private String canDoOperation;

  public String getServiceType() {
    return serviceType;
  }

  public void setServiceType(String serviceType) {
    this.serviceType = serviceType;
  }

  public String getServicesStatus() {
    return servicesStatus;
  }

  public void setServicesStatus(String servicesStatus) {
    this.servicesStatus = servicesStatus;
  }

  public String getCanDoOperation() {
    return canDoOperation;
  }

  public void setCanDoOperation(String canDoOperation) {
    this.canDoOperation = canDoOperation;
  }


}
