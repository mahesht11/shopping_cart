package com.ecom.serviceimpl;

import com.ecom.dto.CategoryDto;
import com.ecom.entity.Category;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.exception.ResourseExistException;
import com.ecom.repository.CategoryRepository;
import com.ecom.service.CategoryService;
import com.ecom.util.ModelMapperModel;
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
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        log.info("CategoryServiceImpl class saveCategory method!");
        boolean exist = existCategory(categoryDto.getName());
        if(exist)
            throw new ResourseExistException("Category already existed in DB!");
        Category category = modelMapper.map(categoryDto, Category.class);
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
        log.info("CategoryServiceImpl class existCategory method!");
       Optional<Category> category =  categoryRepository.findByName(name);
       if(category.isPresent()){
           return true;
       }
        return false;
    }
}
