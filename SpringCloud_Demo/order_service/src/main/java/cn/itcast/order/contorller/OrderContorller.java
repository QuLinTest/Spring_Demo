package cn.itcast.order.contorller;


import cn.itcast.order.entity.Product;
import cn.itcast.order.feign.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderContorller {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;


    @Autowired
    private ProductFeignClient productFeignClient;


    @RequestMapping(value = "/buy/{id}",method = RequestMethod.GET)
    public Product findById(@PathVariable Long id){
        Product product =null;

        product=productFeignClient.findById(id);
        return product;
    }

  /*  @RequestMapping(value = "/buy/{id}",method = RequestMethod.GET)
    public Product findById(@PathVariable Long id){
        Product product =null;

        product=restTemplate.getForObject("http://service-product/product/1",Product.class);
        return product;
    }*/


  /*  @RequestMapping(value = "/buy/{id}",method = RequestMethod.GET)
    public Product findById(@PathVariable Long id){
        Product product =null;
        //调用自己需要的服务名的原数据
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        ServiceInstance serviceInstance = instances.get(0);
        String strHost = serviceInstance.getHost();
        int intPort = serviceInstance.getPort();
        System.out.println("strHost:"+strHost+";intPort"+intPort);

        product=restTemplate.getForObject("http://"+strHost+":"+intPort+"/product/1",Product.class);
        return product;
    }*/
}
