package order;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EntityScan("cn.itcast.order.entity")
@EnableCircuitBreaker   //hystrix的相关监控
@EnableFeignClients    //启动feign相关
@EnableHystrixDashboard  //监控平台
public class OrderRestApplication {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
       return  new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderRestApplication.class,args);
    }
}
