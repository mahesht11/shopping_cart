package com.ecom.serviceimpl;

import com.ecom.dto.ProductDto;
import com.ecom.entity.Product;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.exception.ResourseExistException;
import com.ecom.repository.ProductRepository;
import com.ecom.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        log.info("ProductServiceImpl class :: saveProduct method!");
        boolean exist = existProduct(productDto.getTitle().toLowerCase());
        if(exist) {
            throw new ResourseExistException("Product already existed in DB!");
        }
        Product product = mapper.map(productDto, Product.class);
        product.setTitle(productDto.getTitle().toLowerCase());
        Product product1 = productRepository.saveAndFlush(product);
        return mapper.map(product1, ProductDto.class);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        log.info("ProductServiceImpl class :: getAllProducts method!");
        List<Product> productList = productRepository.findAll();
        if(CollectionUtils.isEmpty(productList))
            throw new ResourceNotFoundException("Products list is not found!");
        return productList.stream().map(product -> mapper.map(product, ProductDto.class)).collect(Collectors.toList());

    }

    @Override
    public Boolean existProduct(String title) {
        Optional<Product> product =  productRepository.findByTitle(title.toLowerCase());
        if(product.isPresent()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public ProductDto getProduct(String title) {
        log.info("ProductServiceImpl class :: getProduct method :: with title : "+title);
        Optional<Product> product =  productRepository.findByTitle(title.toLowerCase());
        if(product.isPresent()){
            return mapper.map(product.get(), ProductDto.class);
        }else {
            throw new ResourceNotFoundException("Product is not available with this title : "+title);
        }
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String title) {
        log.info("ProductServiceImpl class :: updateProduct method!");
        Optional<Product> product =  productRepository.findByTitle(title.toLowerCase());
        if(product.isPresent()){
            Product product1 = mapper.map(productDto, Product.class);
            Product updateProduct = productRepository.saveAndFlush(product.get());
            return mapper.map(updateProduct, ProductDto.class);}
        else{
            throw new ResourceNotFoundException("Product is not available with this title : "+productDto.getTitle());
        }
    }

    @Override
    public Boolean deleteProduct(String title) {
        log.info("ProductServiceImpl class deleteProduct method with title : "+title);
        Optional<Product> product =  productRepository.findByTitle(title.toLowerCase());
        if(product.isPresent()){
            productRepository.deleteById(product.get().getId());
            return true;
        }else {
            throw new ResourceNotFoundException("Product is not available with this title : "+title);
        }
    }
}
