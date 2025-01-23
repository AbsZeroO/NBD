package org.example.repositories;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.managers.RentManager;
import org.example.mappers.RentMapper;
import org.example.model.Rent;
import org.example.red.RentJsonb;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.example.repositories.AbstractRedisRepository.getJsonb;

public class AbstractRentConsumer {

    private final Consumer<Integer, String> consumer;
    public static final String TOPIC_NAME = "wypozyczenia-rezerwacje";
    public static final String GROUP_ID = "grupa";

    RentManager rentManager = new RentManager();

    public Consumer<Integer, String> getConsumer() {
        return consumer;
    }

    public AbstractRentConsumer() {

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");

        this.consumer = new KafkaConsumer<>(properties);
        subscribe();
        // startListening();
    }

    public void subscribe() {
        consumer.subscribe(Collections.singletonList(TOPIC_NAME));
        consumer.commitSync();
    }


    public void startListening() {
        try {
            while (true) {
                var records = consumer.poll(100);

                for (ConsumerRecord<Integer, String> record : records) {
                    try {
                        processMessage(record.value());
                        consumer.commitSync();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

    protected void processMessage(String message) {
        RentJsonb rentJsonb = getJsonb().fromJson(message, RentJsonb.class);
        Rent rent = RentMapper.rentFromRedis(rentJsonb);
        rentManager.addRent(rent);
    }
}
