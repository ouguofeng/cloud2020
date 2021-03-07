package com.atou.springcloud.service;

import com.atou.springcloud.entity.Payment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaymentService {
    public int create(Payment payment);
    public Payment getPaymentById(@Param("id") Integer id);
    public List<Payment> finnAll();
}
