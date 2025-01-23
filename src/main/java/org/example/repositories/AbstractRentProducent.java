package org.example.repositories;

import ch.qos.logback.core.util.TimeUtil;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class AbstractRentProducent {
    public AbstractRentProducent() {
        initProducent();
    }

    private KafkaProducer<Integer, String> producer;
    private Jsonb jsonb;

    private AdminClient adminClient;
    public static final String TOPIC_NAME = "wypozyczenia-rezerwacje";
    private static final int PARTITIONS = 3;
    private static final int REPLICATION = 2;

    private void initProducent() {

        Properties producentConfig = new Properties();

        producentConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        producentConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producentConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "local");
        producentConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        producentConfig.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        producentConfig.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "rent-producer-transaction-id");


        producer = new KafkaProducer<>(producentConfig);
        producer.initTransactions();
        jsonb = JsonbBuilder.create();

        Properties adminConfig = new Properties();
        adminConfig.put("bootstrap.servers", "kafka1:9192,kafka2:9292,kafka3:9392");
        adminClient = AdminClient.create(adminConfig);

        createTopicIfNotExists();
    }

    private void createTopicIfNotExists() {
        try {
            ListTopicsResult topicsResult = adminClient.listTopics();
            var topicNames = topicsResult.names().get();

            if (!topicNames.contains(TOPIC_NAME)) {
                NewTopic newTopic = new NewTopic(TOPIC_NAME, PARTITIONS, (short) REPLICATION);
                adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
                System.out.println("Utworzono nowy temat: " + TOPIC_NAME);
            } else {
                System.out.println("Temat już istnieje: " + TOPIC_NAME);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public KafkaProducer<Integer, String> getProducer() {
        return producer;
    }

    public Jsonb getJsonb() {
        return jsonb;
    }
}
