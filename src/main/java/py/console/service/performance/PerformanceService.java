

package py.console.service.performance;

import java.util.List;
import py.console.bean.Performance;
import py.console.bean.PerformanceRealTime;
import py.console.bean.Performances;
import py.console.bean.SimpleVolumeMetadata;

/**
 * PerformanceService.
 */
public interface PerformanceService {

  public List<Performance> getAll(long accountId) throws Exception;

  public Performance getByVolumeId(long volumeId, long accountId);

  public Performances pullPerformances(long volumeId, long accountId) throws Exception;

  public PerformanceRealTime pullPerformanceRealTime(long accountId, long volumeId)
      throws Exception;

  public int checkHealthStatus(SimpleVolumeMetadata simpleVolumeMetadata);
}
