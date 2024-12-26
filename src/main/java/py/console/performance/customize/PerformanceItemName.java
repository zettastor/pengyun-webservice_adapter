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
