package kafka.examples;

import kafka.examples.util.KafkaProperties;
import kafka.examples.util.KafkaTopicConfig;
import org.junit.Test;

public class ExampleTest {

  @Test
  public void testProducer() {
    boolean isAsync = false;
    Producer producerThread = new Producer(KafkaProperties.TOPIC, isAsync);
//    producerThread.start();
    producerThread.run();
  }

  @Test
  public void testConsumer() {
    Consumer consumer = new Consumer(KafkaProperties.TOPIC);
    consumer.start();
//    consumer.doWork();
  }

  @Test
  public void testCreateTopic() {
    String bootstrapServersConfig =
        KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT;
    KafkaAdminClient kafkaAdminClient = new KafkaAdminClient.Builder()
        .bootstrapServersConfig(bootstrapServersConfig).build();

    KafkaTopicConfig kafkaTopicConfig = KafkaTopicConfig.builder().name(KafkaProperties.TOPIC)
        .numPartitions(3).replicationFactor((short) 2).build();

    kafkaAdminClient.createTopics(kafkaTopicConfig);
  }

}
