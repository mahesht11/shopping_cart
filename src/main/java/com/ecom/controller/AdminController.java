package com.ecom.controller;

import com.ecom.dto.CategoryDto;
import com.ecom.dto.ProductDto;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @PostMapping("/save-category")
    public ResponseEntity<String> saveCategory(@RequestBody CategoryDto categoryDto){
        validCategory(categoryDto);
        log.info("AdminController class saveCategory method !");
        CategoryDto category1 = categoryService.saveCategory(categoryDto);
        if(ObjectUtils.isEmpty(category1)){
            return new ResponseEntity<>("Category not saved successfully!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity("Category saved successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        log.info("AdminController class getAllCategory method !");
        List<CategoryDto> categoryDtoList = categoryService.getAllCategory();
        if(!CollectionUtils.isEmpty(categoryDtoList))
            return new ResponseEntity<>(categoryDtoList, HttpStatus.FOUND);
        return null;
    }

    @GetMapping("/exist-category/{name}")
    public ResponseEntity<String> existCategory(@PathVariable String name){
        log.info("AdminController class existCategory method by name : "+name);
        boolean exist = categoryService.existCategory(name);
        if(ObjectUtils.isEmpty(exist)){
            return new ResponseEntity<>("Category is not exist!", HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>("category is exist!", HttpStatus.FOUND);
        }
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable  String name){
        log.info("AdminController class getCategory method by name : "+ name);
        CategoryDto categoryDto = categoryService.getCategory(name);
        if(!ObjectUtils.isEmpty(categoryDto)){
            return new ResponseEntity<>(categoryDto, HttpStatus.FOUND);
        }else{
            return null;
        }
    }

    @PutMapping("/category/{name}")
    public ResponseEntity<CategoryDto> udpateCategory(@RequestBody CategoryDto categoryDto, @PathVariable String name){
        log.info("AdminController class udpateCategory method : ");
        CategoryDto updateCategoryDto = categoryService.updateCategory(categoryDto, name);
        if(!ObjectUtils.isEmpty(updateCategoryDto)){
            return new ResponseEntity<>(updateCategoryDto, HttpStatus.FOUND);
        }else{
            return null;
        }
    }

    @DeleteMapping("/category/{name}")
    public ResponseEntity<String> deleteCategory(@PathVariable String name){
        log.info("AdminController class getCategory method by name : "+ name);
        Boolean result = categoryService.deleteCategory(name);
        if(result){
            return new ResponseEntity<>("Category deleted successfully!", HttpStatus.ACCEPTED);
        }else{
            return null;
        }
    }

    //Product api's

    @PostMapping("/save-product")
    public ResponseEntity<String> saveProduct(@RequestBody ProductDto productDto){
        validProduct(productDto);
        log.info("AdminController class saveCategory method !");
        ProductDto productDto1 = productService.saveProduct(productDto);
        if(ObjectUtils.isEmpty(productDto1)){
            return new ResponseEntity<>("Product not saved successfully!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity("Product saved successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        log.info("AdminController class :: getAllProducts method !");
        List<ProductDto> productDtoList = productService.getAllProducts();
        if(!CollectionUtils.isEmpty(productDtoList))
            return new ResponseEntity<>(productDtoList, HttpStatus.FOUND);
        return null;
    }

    @GetMapping("/exist-product/{title}")
    public ResponseEntity<String> existProduct(@PathVariable String title){
        log.info("AdminController class existProduct method by title : "+title);
        boolean exist = productService.existProduct(title);
        if(ObjectUtils.isEmpty(exist)){
            return new ResponseEntity<>("Product is not exist!", HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>("Product is exist!", HttpStatus.FOUND);
        }
    }

    @GetMapping("/product/{title}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable  String title){
        log.info("AdminController class getProduct method by title : "+ title);
        ProductDto productDto = productService.getProduct(title);
        if(!ObjectUtils.isEmpty(productDto)){
            return new ResponseEntity<>(productDto, HttpStatus.FOUND);
        }else{
            return null;
        }
    }

    @PutMapping("/product/{title}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable String title){
        log.info("AdminController class :: updateProduct method : ");
        ProductDto updateProductDto = productService.updateProduct(productDto, title);
        if(!ObjectUtils.isEmpty(updateProductDto)){
            return new ResponseEntity<>(updateProductDto, HttpStatus.FOUND);
        }else{
            return null;
        }
    }

    @DeleteMapping("/product/{title}")
    public ResponseEntity<String> deleteProduct(@PathVariable String title){
        log.info("AdminController class deleteProduct method by title : "+ title);
        Boolean result = productService.deleteProduct(title);
        if(result){
            return new ResponseEntity<>("Product deleted successfully!", HttpStatus.ACCEPTED);
        }else{
            return null;
        }
    }

    private void validCategory(CategoryDto categoryDto) {
        if(categoryDto.getName().isEmpty() || categoryDto.getName().isBlank())
            throw new ResourceNotFoundException("Category name should not be empty or blank!");
        if(categoryDto.getImageName().isEmpty() || categoryDto.getImageName().isBlank())
            throw new ResourceNotFoundException("Category image should not be empty or blank!");
    }

    private void validProduct(ProductDto productDto) {
        if(productDto.getTitle().isEmpty() || productDto.getTitle().isBlank())
            throw new ResourceNotFoundException("Product title should not be empty or blank!");
        if(productDto.getDescription().isEmpty() || productDto.getDescription().isBlank())
            throw new ResourceNotFoundException("Product description should not be empty or blank!");
        if(productDto.getImageName().isEmpty() || productDto.getImageName().isBlank())
            throw new ResourceNotFoundException("Product image name should not be empty or blank!");
        if(productDto.getImageName().isBlank())
            throw new ResourceNotFoundException("Product price should not be empty or blank!");
    }

}
