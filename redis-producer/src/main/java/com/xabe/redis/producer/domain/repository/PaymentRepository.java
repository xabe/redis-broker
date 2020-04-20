package com.xabe.redis.producer.domain.repository;

import com.xabe.redis.producer.domain.entity.PaymentDO;

public interface PaymentRepository {

  void createPayment(PaymentDO paymentDO);
}
