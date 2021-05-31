package org.ddu.java.concurrent.pc.impl;

import org.ddu.java.concurrent.pc.Producer;
import org.ddu.java.concurrent.pc.ResourcePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunnableProducer extends AbstractProducer implements Producer, Runnable {

  private static final Logger logger = LoggerFactory.getLogger(RunnableProducer.class);

  public RunnableProducer(ResourcePool<String> resPool, int producerNo) {
    super(resPool, producerNo);
  }

  @Override
  public void run() {
    String res = String.format("%s:%s", Thread.currentThread().getName(), this.getProducerNo());
    this.produce(res);
  }

}
