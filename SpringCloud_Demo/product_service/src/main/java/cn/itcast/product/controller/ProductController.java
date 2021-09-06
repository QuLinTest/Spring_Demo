package cn.itcast.product.controller;

import cn.itcast.product.entity.Product;
import cn.itcast.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Product findById(@PathVariable Long id){
        Product product=  productService.findById(id);
        return product;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.POST)
    public String save(@RequestBody Product product){
         productService.save(product);
        return "OK";
    }

}
