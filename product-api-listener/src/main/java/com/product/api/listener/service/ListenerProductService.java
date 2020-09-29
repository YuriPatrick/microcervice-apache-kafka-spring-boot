package com.product.api.listener.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.api.listener.gateway.json.ProductJson;

@Service
public class ListenerProductService {

    @Autowired
    ReplyingKafkaTemplate<String, String, String> kafkaTemplate;

    @Value("${kafka.topic.request-topic}")
    String requestTopic;

    @Value("${kafka.topic.requestreply-topic}")
    String requestReplyTopic;


    public ProductJson execute(ProductJson productJson) throws ExecutionException, InterruptedException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(productJson);

		ProducerRecord<String, String> record = new ProducerRecord<String, String>(requestTopic, json);

		record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));

		RequestReplyFuture<String, String, String> sendAndReceive = kafkaTemplate.sendAndReceive(record);

		SendResult<String, String> sendResult = sendAndReceive.getSendFuture().get();

		sendResult.getProducerRecord().headers()
				.forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));

		ConsumerRecord<String, String> consumerRecord = sendAndReceive.get();

		String jsonReturn = consumerRecord.value();
		jsonReturn = jsonReturn.toString();

		ProductJson productJsonReturn = mapper.readValue(jsonReturn, ProductJson.class);

		return productJsonReturn;
	}
}
