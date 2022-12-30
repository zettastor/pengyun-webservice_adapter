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

package py.console.service.configuration.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.client.thrift.GenericThriftClientFactory;
import py.common.PyService;
import py.common.RequestIdBuilder;
import py.common.struct.EndPoint;
import py.console.bean.SimpleConfiguration;
import py.console.bean.SimpleConfigurationCondition;
import py.console.bean.SimpleConfigurationResult;
import py.console.bean.SimpleInstance;
import py.console.service.configuration.ConfigurationService;
import py.console.service.instance.InstanceService;
import py.thrift.datanode.service.DataNodeService;
import py.thrift.share.GetConfigurationsRequest;
import py.thrift.share.GetConfigurationsResponse;
import py.thrift.share.InvalidInputExceptionThrift;
import py.thrift.share.SetConfigurationsRequest;
import py.thrift.share.SetConfigurationsResponse;

/**
 * ConfigurationServiceImpl.
 */
public class ConfigurationServiceImpl implements ConfigurationService {

  private static final Logger logger = LoggerFactory.getLogger(ConfigurationServiceImpl.class);
  private InstanceService instanceService;

  @Override
  public List<SimpleConfigurationResult> setConfiguration(String unFormattedConfigurations)
      throws InvalidInputExceptionThrift, TException {
    // format input string into struct
    List<SimpleConfiguration> configurations = null;
    try {
      configurations = getFormattedConfigurations(unFormattedConfigurations);
      logger.error("All formatted configurations are : {}", configurations);
    } catch (InvalidInputExceptionThrift e) {
      logger.error("Caught an exception when formatting configurations", e);
      throw e;
    }

    List<SimpleConfigurationResult> resultOfSet = new ArrayList<SimpleConfigurationResult>();

    for (SimpleConfiguration configuration : configurations) {
      Map<String, String> configs = new HashMap<String, String>();

      logger.error("Current configuration is {}", configuration);
      logger.error("Datanode service name is {}", PyService.DATANODE.getServiceName());
      // get connection of service
      if (configuration.getServiceName().equals(PyService.DATANODE.getServiceName())) {
        // empty
      } else if (configuration.getServiceName().contains(PyService.DATANODE.getServiceName())) {
        logger.error("Into datanode process");
        if (configuration.getHost().compareToIgnoreCase("All") == 0) {
          List<SimpleInstance> allDatanodes = instanceService.getInstances(PyService.DATANODE
              .getServiceName());
          for (SimpleInstance instance : allDatanodes) {
            EndPoint endPoint = new EndPoint(instance.getHost(), 10011);
            try {
              resultOfSet.addAll(setConfig(endPoint, configuration));
            } catch (Exception e) {
              logger.error("Caught an Exception", e);
              continue;
            }
          }
        } else {
          EndPoint endPoint = new EndPoint(configuration.getHost(), 10011);
          try {
            resultOfSet.addAll(setConfig(endPoint, configuration));
          } catch (Exception e) {
            logger.error("Caught an Exception", e);
          }
        }
      }
    }

    return resultOfSet;
  }

  private List<SimpleConfigurationResult> setConfig(EndPoint endPoint,
      SimpleConfiguration configuration)
      throws TException {
    Map<String, String> configs = new HashMap<String, String>();
    List<SimpleConfigurationResult> resultOfSet = new ArrayList<SimpleConfigurationResult>();
    GenericThriftClientFactory<DataNodeService.Iface> dataNodeClientFactory =
        GenericThriftClientFactory.create(DataNodeService.Iface.class);
    try {
      SetConfigurationsRequest request = new SetConfigurationsRequest();
      request.setRequestId(RequestIdBuilder.get());
      configs.put(configuration.getKey(), configuration.getValue());
      request.setConfigurations(configs);

      DataNodeService.Iface dataNodeClient = dataNodeClientFactory.generateSyncClient(endPoint,
          5000, 10000);
      SetConfigurationsResponse response = dataNodeClient.setConfigurations(request);
      logger.error("set configuration response : {}", response);

      Map<String, String> tmp = response.getResults();
      for (Entry<String, String> entry : tmp.entrySet()) {
        SimpleConfigurationResult result = new SimpleConfigurationResult();
        result.setKey(entry.getKey());
        result.setValue(entry.getValue());
        resultOfSet.add(result);
      }
    } catch (Exception e) {
      logger.error("Caught an Exception", e);
      throw new TException();
    }

    return resultOfSet;
  }

  @Override
  public List<SimpleConfiguration> getConfiguration(String unFormattedConditions)
      throws TException {
    // format input string into struct
    List<SimpleConfigurationCondition> conditions = null;
    try {
      conditions = getFormattedConditions(unFormattedConditions);
      logger.error("All formatted conditions are : {}", conditions);
    } catch (InvalidInputExceptionThrift e) {
      logger.error("Caught an exception when formatting configurations", e);
      throw e;
    }

    List<SimpleConfiguration> resultOfGet = new ArrayList<SimpleConfiguration>();

    for (SimpleConfigurationCondition condition : conditions) {
      Map<String, String> configs = new HashMap<String, String>();
      // get connection of service
      if (condition.getServiceName().equals(PyService.DATANODE.getServiceName())) {
        // empty
      } else if (condition.getServiceName().contains(PyService.DATANODE.getServiceName())) {
        if (condition.getHost().compareToIgnoreCase("All") == 0) {
          // empty
        } else {
          logger.warn("Get configurations from {}", PyService.DATANODE.getServiceName());
          EndPoint endPoint = new EndPoint(condition.getHost(), 10011);
          GenericThriftClientFactory<DataNodeService.Iface> dataNodeClientFactory =
              GenericThriftClientFactory
              .create(DataNodeService.Iface.class);
          try {
            GetConfigurationsRequest request = new GetConfigurationsRequest();
            request.setRequestId(RequestIdBuilder.get());
            Set<String> keys = new HashSet<String>();
            keys.add(condition.getKey());
            request.setKeys(keys);

            DataNodeService.Iface dataNodeClient = dataNodeClientFactory.generateSyncClient(
                endPoint, 5000, 10000);
            GetConfigurationsResponse response = dataNodeClient.getConfigurations(request);

            Map<String, String> tmp = response.getResults();
            for (Entry<String, String> entry : tmp.entrySet()) {
              SimpleConfiguration result = new SimpleConfiguration();
              result.setHost(condition.getHost());
              result.setServiceName(condition.getServiceName());
              result.setKey(entry.getKey());
              result.setValue(entry.getValue());
              resultOfGet.add(result);
            }
          } catch (Exception e) {
            logger.error("Caught an Exception", e);
            throw new TException();
          }
        }

      }
    }
    return resultOfGet;
  }

  /**
   * total regular expression is:
   * \s*[Gg][Ee][Tt]\s*\n(\s*(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.
   * (\d{1,2}|1\d\d|2[0-4]
   * \d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\s*[:]\s*
   * ([Cc]ontrol[Cc]enter|[Ii]nfo[Cc]enter|[Dd][Ii][Hh]|[Dd]ata[
   * Nn]ode)\s+\w+\d*\s*[=]\s*[A-Za-z0-9]+)+
   *
   * <p>for example: 10.0.1.20:InfoCenter log.level=DEBUG
   *
   * @param unFormattedConfigurations un formatted configurations
   * @return formatted configurations
   * @throws InvalidInputExceptionThrift invalid input exception thrift
   */
  private List<SimpleConfiguration> getFormattedConfigurations(String unFormattedConfigurations)
      throws InvalidInputExceptionThrift {
    String remainderConfigStr = unFormattedConfigurations;
    List<SimpleConfiguration> formattedConfigurations = new ArrayList<SimpleConfiguration>();

    SimpleConfiguration formattedConfiguration = new SimpleConfiguration();

    // operation (GET or get or Get or gEt or geT ...)
    String subString = matchString(remainderConfigStr, "\\s*[Ss][Ee][Tt][\\s\\n]+");
    if (subString == "") {
      throw new InvalidInputExceptionThrift();
    } else {
      remainderConfigStr = remainderConfigStr.substring(subString.length(),
          remainderConfigStr.length());
    }

    while (remainderConfigStr.length() > 0) {
      // host
      subString = matchString(
          remainderConfigStr,
          "\\s*((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|("
              + "(1\\d{2})|([1-9]?\\d))))|([Aa]ll)\\s*");
      if (subString == "") {
        throw new InvalidInputExceptionThrift();
      } else {
        formattedConfiguration.setHost(subString);
        remainderConfigStr = remainderConfigStr.substring(subString.length(),
            remainderConfigStr.length());
      }

      // separator (:)
      subString = matchString(remainderConfigStr, "\\s*[:]\\s*");
      if (subString == "") {
        throw new InvalidInputExceptionThrift();
      } else {
        remainderConfigStr = remainderConfigStr.substring(subString.length(),
            remainderConfigStr.length());
      }

      // service name
      subString = matchString(remainderConfigStr,
          "\\s*([Cc]ontrol[Cc]enter|[Ii]nfo[Cc]enter|[Dd][Ii][Hh]|[Dd]ata[Nn]ode)\\s+");
      if (subString == "") {
        throw new InvalidInputExceptionThrift();
      } else {
        formattedConfiguration.setServiceName(subString);
        remainderConfigStr = remainderConfigStr.substring(subString.length(),
            remainderConfigStr.length());
      }

      // key
      subString = matchString(remainderConfigStr, "\\s*[\\w+\\d*\\.*]+\\s*");
      if (subString == "") {
        throw new InvalidInputExceptionThrift();
      } else {
        formattedConfiguration.setKey(subString);
        remainderConfigStr = remainderConfigStr.substring(subString.length(),
            remainderConfigStr.length());
      }

      // separator (=)
      subString = matchString(remainderConfigStr, "\\s*[=]\\s*");
      if (subString == "") {
        throw new InvalidInputExceptionThrift();
      } else {
        remainderConfigStr = remainderConfigStr.substring(subString.length(),
            remainderConfigStr.length());
      }

      // value
      subString = matchString(remainderConfigStr, "\\s*[A-Za-z0-9]+\\s*\\n*");
      if (subString == "") {
        throw new InvalidInputExceptionThrift();
      } else {
        formattedConfiguration.setValue(subString);
        remainderConfigStr = remainderConfigStr.substring(subString.length(),
            remainderConfigStr.length());
      }

      formattedConfigurations.add(formattedConfiguration);
    }

    logger.debug("formatted configurations are : {}", formattedConfigurations);
    return formattedConfigurations;
  }

  private List<SimpleConfigurationCondition> getFormattedConditions(String unFormattedConditions)
      throws InvalidInputExceptionThrift {
    logger.warn("unformatted conditions are : {}", unFormattedConditions);
    String remainderConditionStr = unFormattedConditions;
    List<SimpleConfigurationCondition> formattedConditions =
        new ArrayList<SimpleConfigurationCondition>();

    SimpleConfigurationCondition formattedCondition = new SimpleConfigurationCondition();

    // operation (GET or get)
    String subString = matchString(remainderConditionStr, "\\s*[Gg][Ee][Tt][\\s\\n]+");
    if (subString == "") {
      throw new InvalidInputExceptionThrift();
    } else {
      remainderConditionStr = remainderConditionStr.substring(subString.length(),
          remainderConditionStr.length());
    }

    while (remainderConditionStr.length() > 0) {
      // host
      subString = matchString(
          remainderConditionStr,
          "\\s*((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|("
              + "(1\\d{2})|([1-9]?\\d))))|([Aa]ll)\\s*");
      if (subString == "") {
        throw new InvalidInputExceptionThrift();
      } else {
        formattedCondition.setHost(subString);
        remainderConditionStr = remainderConditionStr.substring(subString.length(),
            remainderConditionStr.length());
      }

      // separator (:)
      subString = matchString(remainderConditionStr, "\\s*[:]\\s*");
      if (subString == "") {
        throw new InvalidInputExceptionThrift();
      } else {
        remainderConditionStr = remainderConditionStr.substring(subString.length(),
            remainderConditionStr.length());
      }

      // service name
      subString = matchString(remainderConditionStr,
          "\\s*([Cc]ontrol[Cc]enter|[Ii]nfo[Cc]enter|[Dd][Ii][Hh]|[Dd]ata[Nn]ode)\\s+");
      if (subString == "") {
        throw new InvalidInputExceptionThrift();
      } else {
        formattedCondition.setServiceName(subString);
        remainderConditionStr = remainderConditionStr.substring(subString.length(),
            remainderConditionStr.length());
      }

      // key
      subString = matchString(remainderConditionStr, "\\s*[\\w+\\d*\\.*]+\\s*");
      if (subString == "") {
        throw new InvalidInputExceptionThrift();
      } else {
        formattedCondition.setKey(subString);
        remainderConditionStr = remainderConditionStr.substring(subString.length(),
            remainderConditionStr.length());
      }

      formattedConditions.add(formattedCondition);
    }

    logger.warn("formatted conditions are : {}", formattedConditions);
    return formattedConditions;
  }

  private String matchString(String str, String regex) {
    String subStr = new String();
    Pattern p = Pattern.compile(regex);
    Matcher mtc = p.matcher(str);
    if (mtc.find()) {
      subStr = mtc.group();
    } else {
      subStr = "";
    }
    return subStr;

  }

  public InstanceService getInstanceService() {
    return instanceService;
  }

  public void setInstanceService(InstanceService instanceService) {
    this.instanceService = instanceService;
  }

}
