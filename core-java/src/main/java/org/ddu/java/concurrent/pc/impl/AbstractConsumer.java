package org.ddu.java.concurrent.pc.impl;

import org.ddu.java.concurrent.pc.Consumer;
import org.ddu.java.concurrent.pc.ResourcePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractConsumer implements Consumer {

  private ResourcePool<String> resPool;

  private static final Logger logger = LoggerFactory.getLogger(AbstractConsumer.class);

  private int consumerNo;

  public AbstractConsumer(ResourcePool<String> resPool, int consumerNo) {
    this.resPool = resPool;
    this.consumerNo = consumerNo;
  }

  @Override
  public String consume() {
    String threadName  = Thread.currentThread().getName();
    logger.info("C-[ {} ] is consuming ...", threadName);
    String res = this.resPool.get();
    logger.info("C-[ {} ] consumed res:[ {} ]", threadName, res);
    return res;
  }

  public int getConsumerNo() {
    return consumerNo;
  }

}
