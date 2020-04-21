package com.xabe.redis.consumer.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

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
  public RedisMessageListenerContainer redisContainer(final MessageListener messageListener) {
    final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(this.jedisConnectionFactory());
    container.addMessageListener(messageListener, this.topic());
    return container;
  }

  @Bean
  public ObjectMapper objectMapper() {
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    objectMapper.configure(JsonParser.Feature.STRICT_DUPLICATE_DETECTION, true);
    objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    objectMapper.setSerializationInclusion(Include.NON_NULL);
    return objectMapper;
  }

}
