package com.xabe.redis.consumer.infrastructure.messaging.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xabe.redis.consumer.domain.entity.PaymentDO;
import com.xabe.redis.consumer.domain.repository.PaymentRepository;
import com.xabe.redis.consumer.infrastructure.messaging.RedisHandler;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentHandler implements RedisHandler {

  private final Logger logger;

  private final ObjectMapper objectMapper;

  private final PaymentRepository paymentRepository;

  private final @Value("${redis.config.topic}")
  String topic;

  @Override
  public String getTopic() {
    return this.topic;
  }

  @Override
  public void handler(final Message message) {
    try {
      final PaymentDO paymentDO = this.objectMapper.readValue(message.getBody(), PaymentDO.class);
      this.paymentRepository.save(paymentDO);
    } catch (final IOException e) {
      this.logger.error("Error read message {} :", message, e);
    }
  }
}
