package kafka.examples.util;

import lombok.Builder;

@Builder
public class KafkaTopicConfig {

  private String name;
  private int numPartitions;
  private short replicationFactor;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getNumPartitions() {
    return numPartitions;
  }

  public void setNumPartitions(int numPartitions) {
    this.numPartitions = numPartitions;
  }

  public short getReplicationFactor() {
    return replicationFactor;
  }

  public void setReplicationFactor(short replicationFactor) {
    this.replicationFactor = replicationFactor;
  }
}
