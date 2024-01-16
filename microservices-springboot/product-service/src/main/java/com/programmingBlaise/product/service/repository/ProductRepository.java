package com.programmingBlaise.product.service.repository;

import com.programmingBlaise.product.service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,Long> {
}
