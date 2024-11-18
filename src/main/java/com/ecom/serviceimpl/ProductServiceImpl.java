package com.ecom.serviceimpl;

import com.ecom.dto.ProductDto;
import com.ecom.entity.Category;
import com.ecom.entity.Product;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.exception.ResourseExistException;
import com.ecom.repository.CategoryRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

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
        product.setDiscountPrice(calculateDiscountPrice(product.getDiscountPersentage(),product.getPrice()));
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
        }
        return false;
    }

    @Override
    public ProductDto getProduct(String title) {
        log.info("ProductServiceImpl class :: getProduct method :: with title : "+title);
        Product product =  productRepository.findByTitle(title.toLowerCase()).orElseThrow(() -> new ResourceNotFoundException("Product is not available with this title : "+title));
        if(!ObjectUtils.isEmpty(product)){
            return mapper.map(product, ProductDto.class);
        }
        return null;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String title) {
        log.info("ProductServiceImpl class :: updateProduct method!");
        Product product =  productRepository.findByTitle(title.toLowerCase()).orElseThrow(() -> new ResourceNotFoundException("Product is not available with this title : "+title));
        if(!ObjectUtils.isEmpty(product)){
            Product product1 = mapper.map(productDto, Product.class);
            product.setDescription(product1.getDescription().length()>0? product1.getDescription() : product.getDescription());
            product.setStock(product1.getStock().intValue()>0? product1.getStock() : product.getStock());
            product.setPrice(product1.getPrice().doubleValue()>0? product1.getPrice() : product.getPrice());
            product.setDiscountPrice(calculateDiscountPrice(product1.getDiscountPersentage(),product1.getPrice()));
            product.setImageName(product1.getImageName().length()>0? product1.getImageName() : product.getImageName());
            Product updateProduct = productRepository.saveAndFlush(product);
            return mapper.map(updateProduct, ProductDto.class);
        }
        return null;
    }

    @Override
    public Boolean deleteProduct(String title) {
        log.info("ProductServiceImpl class deleteProduct method with title : "+title);
        Product product =  productRepository.findByTitle(title.toLowerCase()).orElseThrow(() -> new ResourceNotFoundException("Product is not available with this title : "+title));
        if(!ObjectUtils.isEmpty(product)){
            productRepository.deleteById(product.getId());
            return true;
        }
        return false;
    }

    @Override
    public List<ProductDto> getActiveProducts(String name) {
        log.info("ProductServiceImpl class :: getActiveProducts method! ");
        Category category = categoryRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Category is not present with this name :"+name));

        List<Product> productList = productRepository.findAllByCategory(category.getId());
        return productList.stream().map(product -> mapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }

    public Double calculateDiscountPrice(Integer discountPersentage, Double price){
        Double discount = price*(discountPersentage/100);
        Double discountPrice = price- discount;
         return discountPrice;
    }
}
