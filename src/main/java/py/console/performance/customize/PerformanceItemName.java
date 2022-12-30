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

package py.console.performance.customize;

import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * PerformanceItemName.
 */
@XmlRootElement(name = "PerformanceItemNameSet")
@XmlType(propOrder = {"id", "beanName", "customName"})
@XmlAccessorType(XmlAccessType.NONE)
public class PerformanceItemName {

  @XmlAttribute(name = "id")
  private UUID id;

  @XmlAttribute(name = "server-bean-name")
  private String beanName;

  @XmlAttribute(name = "client-custom-name")
  private String customName;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getBeanName() {
    return beanName;
  }

  public void setBeanName(String beanName) {
    this.beanName = beanName;
  }

  public String getCustomName() {
    return customName;
  }

  public void setCustomName(String customName) {
    this.customName = customName;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((beanName == null) ? 0 : beanName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    PerformanceItemName other = (PerformanceItemName) obj;
    if (beanName == null) {
      if (other.beanName != null) {
        return false;
      }
    } else if (!beanName.equals(other.beanName)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "PerformanceItemName [id=" + id + ", beanName=" + beanName + ", customName=" + customName
        + "]";
  }

}
