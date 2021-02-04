package com.customer.save.service.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ContainerAwareErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.listener.MessageListenerContainer;

/**
 * CLASS RESPONSIBLE FOR THE ERROR TREATMENT OF THE CONSUMER APACHE KAFKA
 */
final class KafkaErrorHandler implements ContainerAwareErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaErrorHandler.class);

    /**
	 * INTERFACE METHOD ContainerAwareErrorHandler
	 */
    @Override
    public void handle(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer, MessageListenerContainer container) {
        doSeeks(records, consumer);
        if (!records.isEmpty()) {
            ConsumerRecord<?, ?> record = records.get(0);
            String topic = record.topic();
            long offset = record.offset();
            int partition = record.partition();
            if (thrownException.getClass().equals(ListenerExecutionFailedException.class)) {
            	ListenerExecutionFailedException exception = (ListenerExecutionFailedException) thrownException;
                LOG.info("message with topic {} and offset {} " +
                        "- malformed message: {} , exception: {}", topic, offset, exception.getLocalizedMessage(), exception.getMessage());
            } else {
                LOG.info("message with topic {} - offset {} - partition {} - exception {}", topic, offset, partition, thrownException);
            }
        } else {
            LOG.info("Consumer exception - cause: {}", thrownException.getMessage());
        }
    }
    
    /**
	 * AUXILIARY METHOD OF SEEKS RECORDES, CONSUMER
	 */
    private void doSeeks(List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer) {
        Map<TopicPartition, Long> partitions = new LinkedHashMap<>();
        AtomicBoolean first = new AtomicBoolean(true);
        records.forEach(record ->  {
            if (first.get()) {
                partitions.put(new TopicPartition(record.topic(), record.partition()), record.offset() + 1);
            } else {
                partitions.computeIfAbsent(new TopicPartition(record.topic(), record.partition()),
                        offset -> record.offset());
            }
            first.set(false);
        });
        partitions.forEach(consumer::seek);
    }
}
