package com.ecom.serviceimpl;

import com.ecom.dto.CategoryDto;
import com.ecom.entity.Category;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.exception.ResourseExistException;
import com.ecom.repository.CategoryRepository;
import com.ecom.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        log.info("CategoryServiceImpl class saveCategory method!");
        boolean exist = existCategory(categoryDto.getName().toLowerCase());
        if(exist)
            throw new ResourseExistException("Category already existed in DB!");
        Category category = modelMapper.map(categoryDto, Category.class);
        category.setName(categoryDto.getName().toLowerCase());
        Category category1 = categoryRepository.saveAndFlush(category);
        return modelMapper.map(category1, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        log.info("CategoryServiceImpl class getAllCategory method!");
        List<Category> categoryList = categoryRepository.findAll();
        if(CollectionUtils.isEmpty(categoryList))
            throw new ResourceNotFoundException("category list not found!");
         return categoryList.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public boolean existCategory(String name) {
        log.info("CategoryServiceImpl class existCategory method with the name : "+name);
       Optional<Category> category =  categoryRepository.findByName(name.toLowerCase());
       if(category.isPresent()){
           return true;
       }else {
           return false;
       }
    }

    @Override
    public CategoryDto getCategory(String name) {
        log.info("CategoryServiceImpl class getCategory method with the name : "+name);
        Category category =  categoryRepository.findByName(name.toLowerCase()).orElseThrow(() -> new ResourceNotFoundException("Category is not available with this name : "+name));
        if(!ObjectUtils.isEmpty(category)){
            return modelMapper.map(category, CategoryDto.class);
        }
        return null;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String name) {
        log.info("CategoryServiceImpl class :: updateCategory method!");
        Category category =  categoryRepository.findByName(name.toLowerCase()).orElseThrow(() -> new ResourceNotFoundException("Category is not available with this name : "+categoryDto.getName()));
        if(!ObjectUtils.isEmpty(category)){
        Category category1 = modelMapper.map(categoryDto, Category.class);
        category.setName(category1.getName().toLowerCase());
        category.setImageName(category1.getImageName());
        category.setIsActive(category1.getIsActive());
        Category updateCategory = categoryRepository.saveAndFlush(category);
        return modelMapper.map(updateCategory, CategoryDto.class);}
        return null;
    }

    @Override
    public Boolean deleteCategory(String name) {
        log.info("CategoryServiceImpl class deleteCategory method with name : "+name);
        Category category =  categoryRepository.findByName(name.toLowerCase()).orElseThrow(() -> new ResourceNotFoundException("Category is not available with this name : "+name));
        if(!ObjectUtils.isEmpty(category)){
            categoryRepository.deleteById(category.getId());
            return true;
        }
        return false;
    }


}
