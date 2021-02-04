package com.customer.api.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.KafkaReplyTimeoutException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(KafkaReplyTimeoutException.class)
	public ResponseEntity<StandardError> handle(KafkaReplyTimeoutException e, HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		
		StandardError err = new StandardError(Instant.now(), status.value(), "Kafka Reply Time out Exception",
				e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
}