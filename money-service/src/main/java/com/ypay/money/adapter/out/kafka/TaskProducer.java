package com.ypay.money.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ypay.common.RechargingMoneyTask;
import com.ypay.money.application.port.out.SendRechargingMoneyTaskPort;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class TaskProducer implements SendRechargingMoneyTaskPort {

    private final KafkaProducer<String, String> producer;

    private final String topic;

    public TaskProducer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapservers,
                        @Value("${task.topic}") String topic) {
        Properties props = new Properties();
        props.put("bootstrap.server", bootstrapservers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    @Override
    public void sendRechargingMoneyTaskPort(RechargingMoneyTask task) {



    }

    public void sendMessage(String key, RechargingMoneyTask task) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStringToProduce;
        try {
            jsonStringToProduce = mapper.writeValueAsString(task);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, jsonStringToProduce);

        producer.send(record, (metadata, exception) -> {
            if(exception == null) {

            } else {
                exception.printStackTrace();
            }
        });
    }

}
