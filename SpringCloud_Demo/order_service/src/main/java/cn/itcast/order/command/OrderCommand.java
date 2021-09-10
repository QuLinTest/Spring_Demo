package cn.itcast.order.command;

import cn.itcast.order.entity.Product;
import com.netflix.hystrix.*;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.springframework.web.client.RestTemplate;

public class OrderCommand extends HystrixCommand<Product> {

	private RestTemplate restTemplate;
	
	private Long id;

	public OrderCommand(RestTemplate restTemplate, Long id) {
		super(setter());
		this.restTemplate = restTemplate;
		this.id = id;
	}

	private static Setter setter() {

		// 服务分组
		HystrixCommandGroupKey groupKey = HystrixCommandGroupKey.Factory.asKey("order_product");
		// 服务标识
		HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey("product");
		// 线程池名称
		HystrixThreadPoolKey threadPoolKey = HystrixThreadPoolKey.Factory.asKey("order_product_pool");
		/**
		 * 线程池配置
		 *     withCoreSize :  线程池大小为10
		 *     withKeepAliveTimeMinutes:  线程存活时间15秒
		 *     withQueueSizeRejectionThreshold  :队列等待的阈值为100,超过100执行拒绝策略
		 */
		HystrixThreadPoolProperties.Setter threadPoolProperties = HystrixThreadPoolProperties.Setter().withCoreSize(10)
				.withKeepAliveTimeMinutes(15).withQueueSizeRejectionThreshold(100);

		// 命令属性配置Hystrix 开启超时
		HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties.Setter()
				// 采用线程池方式实现服务隔离
				.withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
				// 禁止
				.withExecutionTimeoutEnabled(false);
		return Setter.withGroupKey(groupKey).andCommandKey(commandKey).andThreadPoolKey(threadPoolKey)
				.andThreadPoolPropertiesDefaults(threadPoolProperties).andCommandPropertiesDefaults(commandProperties);

	}

	@Override
	protected Product run() throws Exception {
		System.out.println(Thread.currentThread().getName());
		return restTemplate.getForObject("http://service-product/product/"+id,Product.class);
	}

	@Override
	protected Product getFallback(){
		Product product = new Product();
		//服务降级 未获取到数据，自己模拟一套数据反馈给client
		try {

			product.setProductDesc("error");


		}catch (Throwable e)
		{
			e.getLocalizedMessage();
			System.out.println(e.getMessage());
		}
		return product;
	}
}
