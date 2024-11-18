package com.ecom.service;

import com.ecom.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    ProductDto saveProduct(ProductDto ProductDto);

    List<ProductDto> getAllProducts();

    Boolean existProduct(String name);

    ProductDto getProduct(String name);

    ProductDto updateProduct(ProductDto ProductDto, String name);

    Boolean deleteProduct(String name);

    List<ProductDto> getActiveProducts(String name);
}
