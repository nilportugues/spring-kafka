package com.codenotfound.kafka;

import com.codenotfound.kafka.consumer.Receiver;
import com.codenotfound.kafka.producer.Sender;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringKafkaApplicationTest {

  private static String HELLOWORLD_TOPIC = "hello_world_topic";

  @Autowired
  private Sender sender;

  @Autowired
  private Receiver receiver;

  @Autowired
  private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

  @ClassRule
  public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, HELLOWORLD_TOPIC);

  @BeforeClass
  public static void setUpBeforeClass() {
    System.setProperty("kafka.bootstrap-servers", embeddedKafka.getBrokersAsString());
  }

  @Before
  public void setUp() throws Exception {
    // wait until the partitions are assigned
    for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
        .getListenerContainers()) {
      ContainerTestUtils.waitForAssignment(messageListenerContainer,
          embeddedKafka.getPartitionsPerTopic());
    }
  }

  @Test
  public void testReceive() throws Exception {
    sender.send(HELLOWORLD_TOPIC, "Hello Spring Kafka!");

    receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    assertThat(receiver.getLatch().getCount()).isEqualTo(0);
  }
}
