package com.dailycodewithme.inventory_service;

import com.dailycodewithme.inventory_service.model.Inventory;
import com.dailycodewithme.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableAutoConfiguration

public class InventoryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("prod1");
			inventory.setQuantity(1);
            Inventory inventory1=new Inventory();
			inventory1.setSkuCode("prod2");
			inventory1.setQuantity(2);
			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);



		};

	}

}