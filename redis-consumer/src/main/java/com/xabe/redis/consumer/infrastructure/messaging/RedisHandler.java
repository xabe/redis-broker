package com.xabe.redis.consumer.infrastructure.messaging;

import org.springframework.data.redis.connection.Message;

public interface RedisHandler {

  String getTopic();

  void handler(Message message);

}
