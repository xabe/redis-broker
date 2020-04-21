package com.xabe.redis.consumer.domain.repository;

import com.xabe.redis.consumer.domain.entity.PaymentDO;
import java.util.List;

public interface PaymentRepository {

  void save(PaymentDO paymentDO);

  List<PaymentDO> getPayments();
}
