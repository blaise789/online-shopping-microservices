package com.programmingBlaise.product.service.controller;

import com.programmingBlaise.product.service.dto.ProductRequest;
import com.programmingBlaise.product.service.dto.ProductResponse;
import com.programmingBlaise.product.service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  void createProduct(@RequestBody ProductRequest productRequest)
    {
        productService.createProduct(productRequest);

    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private List<ProductResponse> getAllProducts(){
       return productService.getAllProducts();
    }
}
