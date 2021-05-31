package org.ddu.java.concurrent.pc.impl;

import org.ddu.java.concurrent.pc.Producer;
import org.ddu.java.concurrent.pc.ResourcePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractProducer implements Producer {

  private ResourcePool<String> resPool;
  private int producerNo;

  private static final Logger logger = LoggerFactory.getLogger(AbstractProducer.class);

  public AbstractProducer(ResourcePool<String> resPool, int producerNo) {
    this.resPool = resPool;
    this.producerNo = producerNo;
  }

  @Override
  public void produce(String res) {
    String threadName  = Thread.currentThread().getName();
    logger.info("P-[ {} ] is producing res: [ {} ] ...", threadName, res);
    this.resPool.put(res);
    logger.info("P-[ {} ] produced res: [ {} ]", threadName, res);
  }

  public int getProducerNo() {
    return producerNo;
  }
}
