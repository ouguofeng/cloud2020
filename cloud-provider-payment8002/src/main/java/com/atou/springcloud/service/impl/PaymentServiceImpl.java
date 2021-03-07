package com.atou.springcloud.service.impl;

import com.atou.springcloud.dao.PaymentDao;
import com.atou.springcloud.entity.Payment;
import com.atou.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    PaymentDao paymentDao;

    public int create(Payment payment){
        return paymentDao.create(payment);
    };
    public Payment getPaymentById(Integer id){
        return paymentDao.getPaymentById(id);
    }

    @Override
    public List<Payment> finnAll() {
        return paymentDao.finnAll();
    }

    ;
}
