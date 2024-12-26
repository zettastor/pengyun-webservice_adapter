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

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.management.ObjectName;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.monitor.exception.AlreadyExistedException;
import py.monitor.exception.EmptyStoreException;
import py.monitor.exception.NotExistedException;

/**
 * PerformanceItemNameStore.
 *
 */
@XmlRootElement(name = "py.console.performance.customize.PerformanceItemNameStore")
@XmlType(propOrder = {"name", "itemNames"})
@XmlAccessorType(XmlAccessType.NONE)
public class PerformanceItemNameStore implements IfacePerformanceItemNameStore {

  private static final Logger logger = LoggerFactory.getLogger(PerformanceItemNameStore.class);
  private static final String DEFAULT_ITEM_NAME_XML = System.getProperty("user.dir")
      + "/tomcat/webapps/ROOT/WEB-INF/classes/config/PerformanceItemCustormNames.xml";
  private String filePath;

  @XmlElement(name = "store-name")
  private String name;

  @XmlElement(name = "items-customized-names")
  private List<PerformanceItemName> itemNames;

  public PerformanceItemNameStore() {
    this.filePath = DEFAULT_ITEM_NAME_XML;
    this.itemNames = new ArrayList<PerformanceItemName>();
  }

  public PerformanceItemNameStore(String filePath) {
    this.filePath = filePath;
    this.itemNames = new ArrayList<PerformanceItemName>();
  }

  @Override
  public void load() throws EmptyStoreException, Exception {
    logger.debug("Attribute repository xml file path : {}", filePath);

    File xmlFile = new File(filePath);
    if (!xmlFile.exists()) {
      xmlFile.createNewFile();
      throw new EmptyStoreException();
    } else {
      if (xmlFile.getTotalSpace() == 0L) {
        throw new EmptyStoreException();
      } else {
        FileReader fileReader = new FileReader(xmlFile);
        JAXBContext context = JAXBContext.newInstance(PerformanceItemNameStore.class);
        Unmarshaller um = context.createUnmarshaller();
        PerformanceItemNameStore tempStore = (PerformanceItemNameStore) um.unmarshal(fileReader);
        logger.debug("Repository store is {}", tempStore);
        this.setName(tempStore.getName());
        this.itemNames = tempStore.getItemNames();
      }
    }
  }

  @Override
  public void commit() throws Exception {
    logger.debug("Going to write data to disk, file path is : {}", filePath);

    // create JAXB context and instantiate marshaller
    JAXBContext context = JAXBContext.newInstance(PerformanceItemNameStore.class);
    Marshaller mmarshaller = context.createMarshaller();
    mmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

    // Write to File
    mmarshaller.marshal(this, new File(filePath));
  }

  @Override
  public void add(PerformanceItemName itemName) throws AlreadyExistedException, Exception {
    logger.debug("Current item name is {}", itemName);
    if (!this.itemNames.contains(itemName)) {
      this.itemNames.add(itemName);
    } else {
      logger.warn("There is already a item : {} existed in the store now", itemName);
      throw new AlreadyExistedException();
    }
  }

  @Override
  public PerformanceItemName getItemByCustomizedName(String customizedName)
      throws NotExistedException, Exception {
    for (PerformanceItemName itemName : itemNames) {
      if (itemName.getCustomName().equals(customizedName)) {
        return itemName;
      }
    }

    logger.error("Can't get any performance item by customized name: {}", customizedName);
    throw new NotExistedException();
  }

  /**
   * get Item by beanName.
   *
   * @param beanName beanName
   * @return itemName
   * @throws NotExistedException NotExistedException
   * @throws Exception Exception
   */
  public PerformanceItemName getItemByBeanName(String beanName)
      throws NotExistedException, Exception {
    ObjectName objectName = new ObjectName(beanName);
    for (PerformanceItemName itemName : itemNames) {
      if (itemName.getBeanName().contains(objectName.getKeyProperty("name"))) {
        return itemName;
      }
    }

    logger.error("Can't get any performance item by bean name: {}", beanName);
    throw new NotExistedException();
  }

  @Override
  public String getCustomizedNameByBeanName(String beanName) throws NotExistedException, Exception {
    ObjectName objectName = new ObjectName(beanName);
    for (PerformanceItemName itemName : itemNames) {
      if (itemName.getBeanName().contains(objectName.getKeyProperty("name"))) {
        return itemName.getCustomName();
      }
    }

    logger.error("Can't get any customized performance item name by bean name: {}", beanName);
    throw new NotExistedException();
  }

  @Override
  public String getBeannameByCustomizedName(String customizedName)
      throws NotExistedException, Exception {
    for (PerformanceItemName itemName : itemNames) {
      try {
        if (itemName.getCustomName().equals(customizedName)) {
          return itemName.getBeanName();
        }
      } catch (Exception e) {
        logger.error("Caught an exception, current item name is {}, param customizedName is {}",
            itemName,
            customizedName, e);
        throw e;
      }
    }

    logger.error("Can't get any performance item bean name by customized name: {}", customizedName);
    throw new NotExistedException();
  }

  @Override
  public String getBeannameById(UUID id) throws NotExistedException, Exception {
    for (PerformanceItemName itemName : itemNames) {
      if (itemName.getId().equals(id)) {
        return itemName.getBeanName();
      }
    }

    logger.error("Can't get any performance item bean name by id: {}", id);
    throw new NotExistedException();
  }

  @Override
  public UUID getIdByBeanName(String beanName) throws NotExistedException, Exception {
    ObjectName objectName = new ObjectName(beanName);
    for (PerformanceItemName itemName : itemNames) {
      if (itemName.getBeanName().contains(objectName.getKeyProperty("name"))) {
        return itemName.getId();
      }
    }

    logger.error("Can't get any performance item bean name by bean name: {}", beanName);
    throw new NotExistedException();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<PerformanceItemName> getItemNames() {
    return itemNames;
  }

  @Override
  public String toString() {
    return "PerformanceItemNameSet [name=" + name + ", itemNames=" + itemNames + "]";
  }

}
