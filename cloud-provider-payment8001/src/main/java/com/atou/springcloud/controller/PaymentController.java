package com.atou.springcloud.controller;

import com.atou.springcloud.entity.CommonResult;
import com.atou.springcloud.entity.Payment;
import com.atou.springcloud.service.PaymentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    PaymentService paymentService;
    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("********插入结果"+result);

        if(result > 0){
            return new CommonResult(200,"插入数据成功,serverPort:"+serverPort,result);
        }else{
            return new CommonResult(444,"插入数据失败,haha",null);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Integer id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("********查询结果"+payment);

        if(payment != null){
            return new CommonResult(200,"查询成功，serverPort"+serverPort,payment);
        }else{
            return new CommonResult(444,"查询失败,id为："+id,null);
        }
    }

    @GetMapping("/finnAll")
    public List<Payment> getAll(){
        List<Payment>  list = paymentService.finnAll();
        log.info("********"+list);
        return list;
    }

    @GetMapping("/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String elment : services){
            log.info("************elment:"+elment);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances){
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping("payment/lb")
    public String getPaymentLb(){
        return serverPort;
    }

    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeOut(){
        try {
            //暂停线程几秒
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
