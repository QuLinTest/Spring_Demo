package order.contorller;



import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.utils.FallbackMethod;
import order.command.OrderCommand;
import order.entity.Product;

import order.feign.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.support.FallbackCommand;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
@DefaultProperties(defaultFallback = "orderGetFallBack")
public class OrderContorller {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;



    @HystrixCommand
   @RequestMapping(value = "/buy/{id}",method = RequestMethod.GET)
    public Product findById(@PathVariable Long id){
        Product product =null;

        product=restTemplate.getForObject("http://service-product/product/"+id,Product.class);
        return product;
    }

    /**
     * 公共的降级方法
     * @param
     * @return
     */
    public  Product orderGetFallBack(){
        Product product = new Product();
        product.setProductDesc("查询出问题了，可能没有这个东西");
        return  product;
    }
    /**
     * feign方式
     * @param id
     * @return
     */
   /* @RequestMapping(value = "/buy/{id}",method = RequestMethod.GET)
    public Product findById(@PathVariable Long id){
        Product product =null;

        product=productFeignClient.findById(id);
        return product;
    }*/

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public String findOrderId(@PathVariable Long id){
        return "根据ID查询";

    }

    /**
     * restTemplete+ribbon方式请求访问
     */
  /*  @RequestMapping(value = "/buy/{id}",method = RequestMethod.GET)
    public Product findById(@PathVariable Long id){
        Product product =null;

        product=restTemplate.getForObject("http://service-product/product/1",Product.class);
        return product;
    }*/

/**
 * restTemplete方式请求访问
 */
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
