package order.feign;


import order.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 声明需要调用的微服务名称
 * name : 服务名称
 * fallback: 熔断降级触发，就会跳到那个ProductFeignClientCallcopy的方法去
 */
@FeignClient(name = "service-product",fallback =ProductFeignClientCallcopy.class)
public interface ProductFeignClient {
    /**
     *
     * 配置需要调用的微服务接口
     * @param id
     * @return
     */
    @RequestMapping(value = "/product/{id}",method = RequestMethod.GET)
    public Product findById(@PathVariable("id") Long id);
}
