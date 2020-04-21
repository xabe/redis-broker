package com.xabe.redis.consumer.infrastructure.integration;

import com.xabe.redis.consumer.domain.entity.PaymentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class Config {

  @Autowired
  private JedisConnectionFactory jedisConnectionFactory;

  @Bean
  public RedisTemplate<String, PaymentDO> redisTemplate() {
    final RedisTemplate<String, PaymentDO> template = new RedisTemplate<>();
    template.setConnectionFactory(this.jedisConnectionFactory);
    template.setValueSerializer(new Jackson2JsonRedisSerializer<>(PaymentDO.class));
    return template;
  }

}
