package com.globalcoder.orderservice;

import com.globalcoder.orderservice.repository.OrderRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
@SpringBootApplication
public class OrderServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
   @Bean
//   @LoadBalanced
	public WebClient.Builder webClientBuilder(){
		return  WebClient.builder();
   }
	@Bean
	public RestTemplate restTemplate(){
		return  new RestTemplate();
   }
}
