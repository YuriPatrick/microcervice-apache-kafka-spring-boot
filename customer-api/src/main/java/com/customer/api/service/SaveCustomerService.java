package com.customer.api.service;

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

import com.customer.api.gateway.json.CustomerJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/*
 * CLASSE DE SERVIÇO RESPONSÁVEL PELO ENVIO/RECEBIMENTO DA MENSSAGEM
 */
@Service
public class SaveCustomerService {

	@Autowired
	ReplyingKafkaTemplate<String, String, String> kafkaTemplate;

	@Value("${kafka.topic.request-topic}")
	String requestTopic;

	@Value("${kafka.topic.requestreply-topic}")
	String requestReplyTopic;

	public String execute(CustomerJson customerJson)
			throws JsonProcessingException, InterruptedException, ExecutionException {

		// CONVERTENDO O OBJETO PARA STRING
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(customerJson);

		// MONTANDO O PRODUCER QUE IRA SER ENVIADO PARA O KAFKA
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(requestTopic, jsonString);
		record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));

		// ENVIADO
		RequestReplyFuture<String, String, String> sendAndReceive = kafkaTemplate.sendAndReceive(record);

		// RECEBENDO O RETORNO
		SendResult<String, String> sendResult = sendAndReceive.getSendFuture().get();
		sendResult.getProducerRecord().headers()
				.forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));

		ConsumerRecord<String, String> consumerRecord = sendAndReceive.get();

		CustomerJson customerJsonReturn = mapper.readValue(consumerRecord.value(), CustomerJson.class);

		return customerJsonReturn.toString();

	}
}
