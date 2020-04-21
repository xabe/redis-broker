package com.xabe.redis.consumer.infrastructure.persistence;

import com.xabe.redis.consumer.domain.entity.PaymentDO;
import com.xabe.redis.consumer.domain.repository.PaymentRepository;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryPaymentRepository implements PaymentRepository {

  private final List<PaymentDO> paymentDOS = new LinkedList<>();

  @Override
  public void save(final PaymentDO paymentDO) {
    this.paymentDOS.add(paymentDO);
  }

  @Override
  public List<PaymentDO> getPayments() {
    return Collections.unmodifiableList(this.paymentDOS);
  }
}
