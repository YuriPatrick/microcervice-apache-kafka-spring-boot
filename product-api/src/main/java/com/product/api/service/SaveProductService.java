package com.product.api.service;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.api.gateway.json.ProductJson;
/*
 * CLASSE DE SERVIÇO RESPONSÁVEL PELO ENVIO/RECEBIMENTO DA MENSSAGEM
 */
@Service
public class SaveProductService {

    @Autowired
    ReplyingKafkaTemplate<String, String, String> kafkaTemplate;

    @Value("${kafka.topic.request-topic}")
    String requestTopic;

    @Value("${kafka.topic.requestreply-topic}")
    String requestReplyTopic;

    public String execute(ProductJson productJson) throws ExecutionException, InterruptedException, JsonProcessingException {

		// CONVERTENDO O OBJETO PARA STRING
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(productJson);

		// MONTANDO O PRODUCER QUE IRA SER ENVIADO PARA O KAFKA
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(requestTopic, jsonString);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));

		// ENVIADO
        RequestReplyFuture<String, String, String> sendAndReceive = kafkaTemplate.sendAndReceive(record);

		// RECEBENDO OS RETORNO
        SendResult<String, String> sendResult = sendAndReceive.getSendFuture().get();
        sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));
        ConsumerRecord<String, String> consumerRecord = sendAndReceive.get();

        ProductJson productJsonReturn = mapper.readValue(consumerRecord.value(), ProductJson.class);

        return productJsonReturn.toString();
    }
      
    /*
    public ProductJson executeRead(ProductJson productJson) throws ExecutionException, InterruptedException, IOException {

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
	
	*/
	
}
