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

package py.console.service.performance.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.common.RequestIdBuilder;
import py.console.bean.Performance;
import py.console.bean.PerformanceRealTime;
import py.console.bean.Performances;
import py.console.bean.SimpleSegUnit;
import py.console.bean.SimpleSegUnit.UnitType;
import py.console.bean.SimpleSegmentMetadata;
import py.console.bean.SimpleVolumeMetadata;
import py.console.service.performance.PerformanceService;
import py.console.service.volume.VolumeService;
import py.exception.GenericThriftClientFactoryException;
import py.infocenter.client.InformationCenterClientFactory;
import py.thrift.infocenter.service.InformationCenter;
import py.thrift.share.AccessDeniedExceptionThrift;
import py.thrift.share.GetPerformanceFromPyMetricsResponseThrift;
import py.thrift.share.GetPerformanceParameterRequestThrift;
import py.thrift.share.GetPerformanceResponseThrift;
import py.thrift.share.InternalErrorThrift;
import py.thrift.share.InvalidInputExceptionThrift;
import py.volume.VolumeType;

/**
 * PerformanceServiceImpl.
 */
public class PerformanceServiceImpl implements PerformanceService {

  private static final Logger logger = LoggerFactory.getLogger(PerformanceServiceImpl.class);

  private VolumeService volumeService;

  private InformationCenterClientFactory infoCenterClientFactory;

  public VolumeService getVolumeService() {
    return volumeService;
  }

  public void setVolumeService(VolumeService volumeService) {
    this.volumeService = volumeService;
  }

  public InformationCenterClientFactory getInfoCenterClientFactory() {
    return infoCenterClientFactory;
  }

  public void setInfoCenterClientFactory(InformationCenterClientFactory infoCenterClientFactory) {
    this.infoCenterClientFactory = infoCenterClientFactory;
  }

  /**
   * get the IOPS and Throughput in the past one hour for the selected volume.
   */
  public Performances pullPerformances(long accountId, long volumeId) throws Exception {

    Performances performances = new Performances();

    try {
      GetPerformanceParameterRequestThrift request = new GetPerformanceParameterRequestThrift();
      request.setRequestId(RequestIdBuilder.get());
      request.setVolumeId(volumeId);
      GetPerformanceFromPyMetricsResponseThrift response = null;

      InformationCenter.Iface iface = null;
      try {
        iface = infoCenterClientFactory.build().getClient();
      } catch (Exception e) {
        logger.error("build controlcenter client failed ", e);
        throw e;
      }
      response = iface.pullPerformanceFromPyMetrics(request);
      if (response.getRequestId() == 0) {
        performances.setRequestId(null);
      } else {
        performances.setRequestId(String.valueOf(response.getRequestId()));
      }
      performances.setVolumeId(Long.toString(response.getVolumeId()));
      performances.setReadIoPs(response.getReadIoPs());
      performances.setReadLatency(response.getReadLatency());
      performances.setReadThroughput(response.getReadThroughput());
      performances.setWriteIoPs(response.getWriteIoPs());
      performances.setWriteLatency(response.getWriteLatency());
      performances.setWriteThroughput(response.getWriteThroughput());
      logger.debug("add a performance element to list." + performances.toString());
    } catch (TTransportException e) {
      logger.error("Exception catch", e);
    } catch (AccessDeniedExceptionThrift e) {
      logger.error("Exception catch", e);
    } catch (InternalErrorThrift e) {
      logger.error("Exception catch", e);
    } catch (InvalidInputExceptionThrift e) {
      logger.error("Exception catch", e);
    } catch (TException e) {
      logger.error("Exception catch", e);
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
    }

    return performances;
  }

  /**
   * calculate the health index for the selected volume.
   */
  @Override
  public int checkHealthStatus(SimpleVolumeMetadata simpleVolumeMetadata) {
    logger.warn("getVolumeSize STRING{}", simpleVolumeMetadata.getVolumeSize());
    logger.warn("getVolumeSize{}", Double.valueOf(simpleVolumeMetadata.getVolumeSize()));
    int totalSegmentCount = (int) (
        Double.valueOf(simpleVolumeMetadata.getVolumeSize()) * 1024 * 1024
            / this.volumeService.getStorageConfiguration().getSegmentSizeByte());

    VolumeType volumeType = VolumeType.valueOf(simpleVolumeMetadata.getVolumeType());
    int totalSegmentUnitCount = totalSegmentCount * volumeType.getNumMembers();

    int countOk = 0;
    // calculate the health index according to the OK segment unit number.
    for (SimpleSegmentMetadata simpleSegment : simpleVolumeMetadata.getSegmentList()) {
      int countSecondaryOk = 0;
      int unitNum = 0;
      for (SimpleSegUnit simpleSegUnit : simpleSegment.getUnitList()) {
        unitNum++;
        if (simpleSegUnit.getUnitType().equals(UnitType.Primary.name())) {
          if (!simpleSegUnit.getStatusDisplay().equals("OK")
              && !simpleSegUnit.getStatusDisplay().equals("Arbiter")) {
            return 0;
          }
          countOk++;
        } else if (simpleSegUnit.getUnitType().equals(UnitType.Secondary.name())) {
          if (simpleSegUnit.getStatusDisplay().equals("OK")
              || simpleSegUnit.getStatusDisplay().equals("Arbiter")) {
            countOk++;
            countSecondaryOk++;
          }
        }
      }

      if (unitNum < volumeType.getVotingQuorumSize()) {
        return 0;
      }

      if (unitNum == volumeType.getNumMembers()
          && countSecondaryOk < volumeType.getVotingQuorumSize() - 1) {
        return 0;
      }
    }
    return countOk * 100 / totalSegmentUnitCount;
  }

  /**
   * get the read/write latency and the health status of the selected volume.
   */
  @Override
  public PerformanceRealTime pullPerformanceRealTime(long accountId, long volumeId)
      throws Exception {
    PerformanceRealTime performanceRealTime = new PerformanceRealTime();
    try {
      Performance performance = pullPerformance(accountId, volumeId);
      SimpleVolumeMetadata simpleVolumeMetadata = volumeService.getVolumeById(volumeId, accountId,
          true);

      performanceRealTime.setVolumeId(Long.toString(volumeId));

      performanceRealTime.setReadLatency(performance.getReadLatency());
      performanceRealTime.setWriteLatency(performance.getWriteLatency());

      int healthIndex = checkHealthStatus(simpleVolumeMetadata);
      performanceRealTime.setHealthIndex(healthIndex);
      if (healthIndex == 0) {
        performanceRealTime.setHealthStatus("Unavailable");
      } else if (healthIndex == 100) {
        performanceRealTime.setHealthStatus("Healthy");
      } else {
        performanceRealTime.setHealthStatus("Degree");
      }

    } catch (TTransportException e) {
      logger.error("Exception catch", e);
    } catch (AccessDeniedExceptionThrift e) {
      logger.error("Exception catch", e);
    } catch (InternalErrorThrift e) {
      logger.error("Exception catch", e);
    } catch (InvalidInputExceptionThrift e) {
      logger.error("Exception catch", e);
    } catch (TException e) {
      logger.error("Exception catch", e);
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
    }
    return performanceRealTime;
  }

  private Performance pullPerformance(long accountId, long volumeId) throws Exception {
    Performance performance = new Performance();
    try {

      GetPerformanceParameterRequestThrift request = new GetPerformanceParameterRequestThrift();

      request.setRequestId(RequestIdBuilder.get());
      request.setVolumeId(volumeId);
      GetPerformanceResponseThrift response = null;

      InformationCenter.Iface iface = null;
      try {
        iface = infoCenterClientFactory.build().getClient();
      } catch (Exception e) {
        logger.error("build controlcenter client failed ", e);
        throw e;
      }
      response = iface.pullPerformanceParameter(request);

      // TODO: filter volumeId = 0 situation
      if (response.getVolumeId() == 0) {
        logger.warn("get a volumeId is 0 , request next performance parameter");
        return null;
      }
      performance.setVolumeId(String.valueOf(response.getVolumeId()));
      if (response.getRequestId() == 0) {
        performance.setRequestId(null);
      } else {
        performance.setRequestId(String.valueOf(response.getRequestId()));
      }
      performance.setReadIoPs(response.getReadIoPs());
      performance.setWriteIoPs(response.getWriteIoPs());
      performance.setReadThroughput(response.getReadThroughput());
      performance.setWriteThroughput(response.getWriteThroughput());
      performance.setReadLatency(response.getReadLatency());
      performance.setWriteLatency(response.getWriteLatency());

      logger.debug("add a performance element to list." + performance.toString());
    } catch (TTransportException e) {
      logger.error("Exception catch", e);
    } catch (AccessDeniedExceptionThrift e) {
      logger.error("Exception catch", e);
    } catch (InternalErrorThrift e) {
      logger.error("Exception catch", e);
    } catch (InvalidInputExceptionThrift e) {
      logger.error("Exception catch", e);
    } catch (TException e) {
      logger.error("Exception catch", e);
    } catch (GenericThriftClientFactoryException e) {
      logger.error("Exception catch", e);
    }
    return performance;
  }

  @Override
  public List<Performance> getAll(long accountId) throws Exception {

    List<SimpleVolumeMetadata> volumeList = null;
    volumeList = volumeService.getMultipleVolumes(accountId, null);

    List<Performance> performanceList = new ArrayList<Performance>();

    InformationCenter.Iface iface = null;
    try {
      iface = infoCenterClientFactory.build().getClient();
    } catch (Exception e) {
      logger.error("build controlcenter client failed ", e);
      throw e;
    }
    for (SimpleVolumeMetadata simpleVolumeMetadata : volumeList) {

      GetPerformanceParameterRequestThrift request = new GetPerformanceParameterRequestThrift();

      request.setRequestId(RequestIdBuilder.get());
      request.setVolumeId(Long.parseLong(simpleVolumeMetadata.getVolumeId()));
      GetPerformanceResponseThrift response = null;
      try {
        response = iface.pullPerformanceParameter(request);
        logger.debug("got a performance element[{}] by volumeId[{}]", response.toString(),
            simpleVolumeMetadata.getVolumeId());
      } catch (Exception e) {
        // catch exception and just log it, continue to get other performance of volume
        logger.info("failed to get volumeId: {} driver info", simpleVolumeMetadata.getVolumeId());
        continue;
      }
      // can't get performance
      if (response.getVolumeId() == 0) {
        logger.warn("can't get performance: {} , request next performance parameter",
            simpleVolumeMetadata.getVolumeId());
        continue;
      }
      Performance performance = new Performance();
      performance.setVolumeId(String.valueOf(response.getVolumeId()));
      performance.setVolumeName(simpleVolumeMetadata.getVolumeName());
      if (response.getRequestId() == 0) {
        performance.setRequestId(null);
      } else {
        performance.setRequestId(String.valueOf(response.getRequestId()));
      }
      performance.setReadIoPs(response.getReadIoPs());
      performance.setWriteIoPs(response.getWriteIoPs());
      performance.setReadThroughput(response.getReadThroughput());
      performance.setWriteThroughput(response.getWriteThroughput());
      performance.setReadLatency(response.getReadLatency());
      performance.setWriteLatency(response.getWriteLatency());

      performanceList.add(performance);
      logger.debug("add a performance element to list. {}", performance.toString());
    }

    return performanceList;
  }

  @Override
  public Performance getByVolumeId(long volumeId, long accountId) {
    // TODO:
    return null;
  }

}
