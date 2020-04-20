package com.xabe.redis.producer.infrastructure.repository;

import com.xabe.redis.producer.domain.entity.PaymentDO;
import com.xabe.redis.producer.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisPaymentRepository implements PaymentRepository {

  private final Logger logger;

  private final RedisTemplate<String, PaymentDO> redisTemplate;

  private final ChannelTopic topic;

  @Override
  public void createPayment(final PaymentDO paymentDO) {
    this.redisTemplate.convertAndSend(this.topic.getTopic(), paymentDO);
    this.logger.info("Send payment in redis {}", paymentDO);
  }
}
