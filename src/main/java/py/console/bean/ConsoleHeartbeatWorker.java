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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.app.healthcheck.HealthChecker;
import py.dih.client.worker.HeartBeatWorkerFactory;
import py.periodic.WorkerFactory;
import py.periodic.impl.ExecutionOptionsReader;
import py.periodic.impl.PeriodicWorkExecutorImpl;

public class ConsoleHeartbeatWorker implements HealthChecker {

  private static final Logger logger = LoggerFactory.getLogger(ConsoleHeartbeatWorker.class);

  // Setters
  private final int checkingRate;
  private final WorkerFactory heartBeatWorkerFactory;
  // Internal variables
  private PeriodicWorkExecutorImpl executor;

  /**
   * Console Heartbeat Worker.
   *
   * @param checkingRate checking rate
   * @param heartBeatWorkerFactory heartBeat worker factory
   */
  public ConsoleHeartbeatWorker(int checkingRate, WorkerFactory heartBeatWorkerFactory) {
    super();
    this.checkingRate = checkingRate;
    this.heartBeatWorkerFactory = heartBeatWorkerFactory;
  }

  @Override
  public void startHealthCheck() throws Exception {
    if (heartBeatWorkerFactory == null) {
      logger.error("heartBeatWorkerFactory can not be null");
      throw new Exception();
    }

    heartBeatWorkerFactory.createWorker();
    ((HeartBeatWorkerFactory) heartBeatWorkerFactory).setNetSubHealth(false);

    ExecutionOptionsReader optionReader = new ExecutionOptionsReader(1, 1, checkingRate, null);
    executor = new PeriodicWorkExecutorImpl(optionReader, heartBeatWorkerFactory,
        "console-heartbeat-worker");
    logger.warn("going to start console heartbeat worker");
    executor.start();
  }

  @Override
  public void stopHealthCheck() {
    // Stop the executor immediately. No meaning to wait
    executor.stopNow();
  }
}
