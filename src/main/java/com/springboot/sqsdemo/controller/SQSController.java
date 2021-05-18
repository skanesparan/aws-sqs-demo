package com.springboot.sqsdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sqs")
public class SQSController {
	
	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;
	
	private static final Logger LOG = LoggerFactory.getLogger(SQSController.class);
	
	@Value("${cloud.aws.end-point.url}")
	private String sqsEndPoint;
    
	@GetMapping()
	public void sendMessage() {
		queueMessagingTemplate.send(sqsEndPoint, MessageBuilder.withPayload("My First message to aws Ganesh").build());
	}
	
	@SqsListener("spring-boot-sqs")
	public void getMessage(String message) {
		LOG.warn("Queue message is " + message);
	}
}
