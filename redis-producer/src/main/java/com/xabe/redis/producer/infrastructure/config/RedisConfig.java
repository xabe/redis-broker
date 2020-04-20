package com.xabe.redis.producer.infrastructure.config;

import com.xabe.redis.producer.domain.entity.PaymentDO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {

  @Value("${redis.config.hostname}")
  private String hostname;

  @Value("${redis.config.port}")
  private Integer port;

  @Value("${redis.config.topic}")
  private String topic;

  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(this.hostname);
    redisStandaloneConfiguration.setPort(this.port);
    return new JedisConnectionFactory(redisStandaloneConfiguration);
  }

  @Bean
  public ChannelTopic topic() {
    return new ChannelTopic(this.topic);
  }

  @Bean
  public RedisTemplate<String, PaymentDO> redisTemplate() {
    final RedisTemplate<String, PaymentDO> template = new RedisTemplate<>();
    template.setConnectionFactory(this.jedisConnectionFactory());
    template.setValueSerializer(new Jackson2JsonRedisSerializer<>(PaymentDO.class));
    return template;
  }

}
