package cn.itcast.order;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EntityScan("cn.itcast.order.entity")
@EnableFeignClients
public class OrderApplication {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
       return  new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
