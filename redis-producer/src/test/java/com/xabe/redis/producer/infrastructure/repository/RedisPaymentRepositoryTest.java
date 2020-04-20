package com.xabe.redis.producer.infrastructure.repository;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.xabe.redis.producer.domain.entity.PaymentDO;
import com.xabe.redis.producer.domain.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

class RedisPaymentRepositoryTest {

  private Logger logger;

  private RedisTemplate redisTemplate;

  private ChannelTopic channelTopic;

  private PaymentRepository paymentRepository;

  @BeforeEach
  public void setUp() throws Exception {
    this.logger = mock(Logger.class);
    this.redisTemplate = spy(new RedisTemplate());
    this.channelTopic = new ChannelTopic("payments");
    this.paymentRepository = new RedisPaymentRepository(this.logger, this.redisTemplate, this.channelTopic);
  }

  @Test
  public void shouldSendPayments() throws Exception {
    final PaymentDO paymentDO = PaymentDO.builder().build();
    doNothing().when(this.redisTemplate).convertAndSend(eq(this.channelTopic.getTopic()), eq(paymentDO));

    this.paymentRepository.createPayment(paymentDO);

    verify(this.redisTemplate).convertAndSend(eq(this.channelTopic.getTopic()), eq(paymentDO));
  }

}