package cn.itcast.product.controller;

import cn.itcast.product.entity.Product;
import cn.itcast.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Value("${spring.cloud.client.ip-address}")
    private  String ip;
    @Value("${server.port}")
    private  String port;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Product findById(@PathVariable Long id){
        Product product=  productService.findById(id);
        product.setProductName("本次访问IP"+ip+":"+port);
        System.out.println();

        return product;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.POST)
    public String save(@RequestBody Product product){
         productService.save(product);
        return "OK";
    }

}
