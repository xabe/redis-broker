package com.xabe.redis.consumer.infrastructure.messaging;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RedisProcessor implements MessageListener {

  private final Logger logger;

  private final Map<String, RedisHandler> redisHandlers;

  private final RedisHandler unknown;

  @Autowired
  public RedisProcessor(final Logger logger, final List<RedisHandler> redisHandlers) {
    this.logger = logger;
    this.redisHandlers = redisHandlers.stream().collect(Collectors.toMap(RedisHandler::getTopic, Function.identity()));
    this.unknown = new RedisHandler() {
      @Override
      public String getTopic() {
        return null;
      }

      @Override
      public void handler(final Message message) {
        logger.warn("Not handler for message {}", message);
      }
    };
  }

  @Override
  public void onMessage(final Message message, final byte[] bytes) {
    final String topic = new String(message.getChannel(), StandardCharsets.UTF_8);
    this.redisHandlers.getOrDefault(topic, this.unknown).handler(message);
  }
}
