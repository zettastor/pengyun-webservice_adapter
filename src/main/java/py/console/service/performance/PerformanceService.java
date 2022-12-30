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
