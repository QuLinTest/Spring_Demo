package order.feign;

import order.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductFeignClientCallcopy implements ProductFeignClient{


    /**
     * 熔断降级方法
     * @param id
     * @return
     */
    public Product findById(Long id) {
        Product product = new Product();
        product.setProductDesc("error");
        return product;
    }
}
