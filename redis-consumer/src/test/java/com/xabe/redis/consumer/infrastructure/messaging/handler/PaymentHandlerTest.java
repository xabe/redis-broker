package com.xabe.redis.consumer.infrastructure.messaging.handler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xabe.redis.consumer.domain.entity.PaymentDO;
import com.xabe.redis.consumer.domain.repository.PaymentRepository;
import com.xabe.redis.consumer.infrastructure.messaging.RedisHandler;
import java.util.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.data.redis.connection.DefaultMessage;
import org.springframework.data.redis.connection.Message;

class PaymentHandlerTest {

  private Logger logger;

  private ObjectMapper objectMapper;

  private PaymentRepository paymentRepository;

  private RedisHandler redisHandler;

  @BeforeEach
  public void setUp() throws Exception {
    this.logger = mock(Logger.class);
    this.objectMapper = new ObjectMapper();
    this.paymentRepository = mock(PaymentRepository.class);
    this.redisHandler = new PaymentHandler(this.logger, this.objectMapper, this.paymentRepository, "test");
  }

  @Test
  public void topic() throws Exception {
    assertThat(this.redisHandler.getTopic(), is("test"));
  }

  @Test
  public void shouldSavePayment() throws Exception {
    final Message message = new DefaultMessage("test".getBytes(), "{\"name\":\"chabir\",\"amount\":1000,\"currency\":\"EUR\"}".getBytes());

    this.redisHandler.handler(message);

    verify(this.paymentRepository).save(eq(PaymentDO.builder().amount(1000L).currency(Currency.getInstance("EUR")).name("chabir").build()));
  }

}