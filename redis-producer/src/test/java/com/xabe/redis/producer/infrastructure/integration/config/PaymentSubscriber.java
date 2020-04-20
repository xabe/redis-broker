package com.xabe.redis.producer.infrastructure.integration.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xabe.redis.producer.domain.entity.PaymentDO;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

@RequiredArgsConstructor
public class PaymentSubscriber implements MessageListener {

  private final BlockingQueue<PaymentDO> messagesRedis = new ArrayBlockingQueue<>(100);

  private final Logger logger;

  private final ObjectMapper objectMapper;

  public void before() {
    this.messagesRedis.clear();
  }

  public PaymentDO expectMessage(final long defaultTimeoutMs) throws InterruptedException {
    final PaymentDO message = this.messagesRedis.poll(defaultTimeoutMs, TimeUnit.MILLISECONDS);
    if (message == null) {
      throw new RuntimeException("An exception happened while polling the queue for ");
    }
    return message;
  }

  @Override
  public void onMessage(final Message message, final byte[] bytes) {
    try {
      this.logger.info("Payment received {}", message);
      final PaymentDO paymentDO = this.objectMapper.readValue(message.getBody(), PaymentDO.class);
      if (!this.messagesRedis.offer(paymentDO, 1, TimeUnit.SECONDS)) {
        this.logger.warn("Adding {} to messagesPipe timed out", message);
      }
    } catch (final Exception e) {
      this.logger.error("Error to adding {} to messages redis timed out", message, e);
    }
  }
}
