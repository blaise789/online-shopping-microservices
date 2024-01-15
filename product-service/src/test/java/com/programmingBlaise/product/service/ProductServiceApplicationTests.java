package com.programmingBlaise.product.service;

import com.programmingBlaise.product.service.dto.ProductRequest;
import com.programmingBlaise.product.service.dto.ProductResponse;
import com.programmingBlaise.product.service.model.Product;
import com.programmingBlaise.product.service.repository.ProductRepository;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer=new MongoDBContainer("mongo:4.4.2");
	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
   static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
   }
   @Autowired
   private MockMvc mockMvc;
  private ObjectMapper objectMapper=new ObjectMapper();
  static {
	  mongoDBContainer.start();
  }

	@Test
void ShouldCreateProduct() throws Exception{

		ProductRequest productRequest=getProductRequest();

        String productRequestString=objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product").contentType(MediaType.APPLICATION_JSON).content(
productRequestString
		)).andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.findAll().size());

	}




	private ProductRequest getProductRequest() {
	   return ProductRequest.builder()
			   .name("computers")
			   .description("chinese comps")
			   .price(BigDecimal.valueOf(1200))
			   .build();
	}
}
