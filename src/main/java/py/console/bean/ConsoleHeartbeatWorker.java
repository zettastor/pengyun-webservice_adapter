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
