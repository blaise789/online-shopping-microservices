package com.programmingBlaise.product.service.service;

import com.programmingBlaise.product.service.dto.ProductRequest;
import com.programmingBlaise.product.service.dto.ProductResponse;
import com.programmingBlaise.product.service.model.Product;
import com.programmingBlaise.product.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public void createProduct(ProductRequest productRequest) {
    Product product=Product.builder()
            .name(productRequest.getName())
            .description(productRequest.getDescription())
            .price(productRequest.getPrice() )
            .build();
productRepository.save(product);
log.info("product {} is created successfully",product.getId());
    }

    public List<ProductResponse> getAllProducts() {
       List<Product> products= productRepository.findAll();
       return products.stream().map(product ->mapToProductResponse(product)).toList();

    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
