package kafka.examples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import kafka.examples.util.KafkaTopicConfig;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

public class KafkaAdminClient {

  private AdminClient adminClient;

  private KafkaAdminClient(AdminClient adminClient) {
    this.adminClient = adminClient;
  }

  public void createTopics(KafkaTopicConfig kafkaTopicConfig) {
    NewTopic newTopic = new NewTopic(kafkaTopicConfig.getName(), kafkaTopicConfig.getNumPartitions(),
        kafkaTopicConfig.getReplicationFactor());
    Collection<NewTopic> newTopicList = new ArrayList<>();
    newTopicList.add(newTopic);
    CreateTopicsResult createTopicsResult = adminClient.createTopics(newTopicList);

    try {
      createTopicsResult.all().get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  public static final class Builder {

    private Properties props;

    public Builder() {
      props = new Properties();
      props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    }

    public Builder bootstrapServersConfig(String bootstrapServersConfig) {
      props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersConfig);
      return this;
    }

    public Builder properties(Properties props) {
      props = props;
      return this;
    }

    public KafkaAdminClient build() {
      return new KafkaAdminClient(AdminClient.create(props));
    }
  }
}
