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
        Optional<Category> category =  categoryRepository.findByName(name.toLowerCase());
        if(category.isPresent()){
            return modelMapper.map(category.get(), CategoryDto.class);
        }else {
            throw new ResourceNotFoundException("Category is not available with this name : "+name);
        }
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        log.info("CategoryServiceImpl class updateCategory method!");
        Optional<Category> category =  categoryRepository.findByName(categoryDto.getName().toLowerCase());
        if(category.isPresent()){

        Category category1 = modelMapper.map(categoryDto, Category.class);
        category1.setName(categoryDto.getName().toLowerCase());
        Category updateCategory = categoryRepository.saveAndFlush(category1);
        return modelMapper.map(updateCategory, CategoryDto.class);}
        else{
            throw new ResourceNotFoundException("Category is not available with this name : "+categoryDto.getName());
        }
    }

    @Override
    public Boolean deleteCategory(String name) {
        log.info("CategoryServiceImpl class deleteCategory method with name : "+name);
        Optional<Category> category =  categoryRepository.findByName(name.toLowerCase());
        if(category.isPresent()){
            categoryRepository.deleteById(category.get().getId());
            return true;
        }else {
            throw new ResourceNotFoundException("Category is not available with this name : "+name);
        }
    }


}
