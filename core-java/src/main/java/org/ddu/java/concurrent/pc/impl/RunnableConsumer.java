package org.ddu.java.concurrent.pc.impl;

import org.ddu.java.concurrent.pc.Consumer;
import org.ddu.java.concurrent.pc.ResourcePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunnableConsumer extends AbstractConsumer implements Consumer, Runnable {

  private static final Logger logger = LoggerFactory.getLogger(RunnableConsumer.class);

  public RunnableConsumer(ResourcePool<String> resPool, int consumerNo) {
    super(resPool, consumerNo);
  }

  @Override
  public void run() {
    this.consume();
  }

}
