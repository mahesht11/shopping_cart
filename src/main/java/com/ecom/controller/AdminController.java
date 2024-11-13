package com.ecom.controller;

import com.ecom.dto.CategoryDto;
import com.ecom.entity.Category;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.service.CategoryService;
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

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        log.info("AdminController class getAllCategory method !");
        List<CategoryDto> categoryDtoList = categoryService.getAllCategory();
        if(!CollectionUtils.isEmpty(categoryDtoList))
            return new ResponseEntity<>(categoryDtoList, HttpStatus.FOUND);
        return null;
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<String> existCategory(@PathVariable String name){
        log.info("AdminController class existCategory method !");
        boolean exist = categoryService.existCategory(name);
        if(ObjectUtils.isEmpty(exist)){
            return new ResponseEntity<>("Category is not exist!", HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>("category is exist!", HttpStatus.FOUND);
        }
    }

    private void validCategory(CategoryDto categoryDto) {
        if(categoryDto.getName().isEmpty() || categoryDto.getName().isBlank())
            throw new ResourceNotFoundException("Category name should not be empty or blank!");
        if(categoryDto.getImageName().isEmpty() || categoryDto.getImageName().isBlank())
            throw new ResourceNotFoundException("Category image should not be empty or blank!");
    }
}
