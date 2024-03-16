package com.ypay.taskconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ypay.common.RechargingMoneyTask;
import com.ypay.common.SubTask;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Component
public class TaskConsumer {

    private final TaskResultProducer taskResultProducer;
    private final KafkaConsumer<String, String> consumer;


    public TaskConsumer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                        @Value("${task.topic}")String topic, TaskResultProducer taskResultProducer) {
        this.taskResultProducer = taskResultProducer;

        Properties props = new Properties();

        props.put("bootstrap.servers", bootstrapServers);

        // consumer group
        props.put("group.id", "my-group");

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singletonList(topic));
        Thread consumerThread = new Thread(() -> {
            try {
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                    ObjectMapper mapper = new ObjectMapper();
                    for (ConsumerRecord<String, String> record : records) {
                        // record: RechargingMoneyTask (jsonString)
                        RechargingMoneyTask task;
                        // task 수행
                        try {
                            task = mapper.readValue(record.value(), RechargingMoneyTask.class);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException();
                        }

                        // task result
                        for(SubTask subTask: task.getSubTaskList()) {
                            // what subtask, membership, banking 어떤 서브 테스트 인지 판단
                            // external port, adapter
                            // hexagonal architecture 로 만들어주는 것이 맞다. TODO

                            // all subtask is done, true 성공이라고 가정
                            subTask.setStatus("success");

                        }


                        // produce TaskResult
                        this.taskResultProducer.sendTaskResult(task.getTaskID(), task);



                    }
                }
            } finally {
                consumer.close();
            }
        });
        consumerThread.start();
    }




}
