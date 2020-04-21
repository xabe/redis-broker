package com.xabe.redis.consumer.infrastructure.messaging;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.data.redis.connection.DefaultMessage;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

class RedisProcessorTest {

  private Logger logger;

  private RedisHandler redisHandler;

  private MessageListener messageListener;

  @BeforeEach
  public void setUp() throws Exception {
    this.logger = mock(Logger.class);
    this.redisHandler = mock(RedisHandler.class);
    when(this.redisHandler.getTopic()).thenReturn("test");
    this.messageListener = new RedisProcessor(this.logger, Arrays.asList(this.redisHandler));
  }

  @Test
  public void givenAMessageWhenInvokeOnMessageThenSend() throws Exception {
    final Message message = new DefaultMessage("test".getBytes(), "body".getBytes());

    this.messageListener.onMessage(message, new byte[]{});

    verify(this.redisHandler).handler(eq(message));
  }

  @Test
  public void givenAMessageErrorWhenInvokeOnMessageThenNotSend() throws Exception {
    final Message message = new DefaultMessage("error".getBytes(), "body".getBytes());

    this.messageListener.onMessage(message, new byte[]{});

    verify(this.redisHandler, never()).handler(eq(message));
    verify(this.logger).warn(any(), eq(message));
  }
}