package com.ecom.service;

import com.ecom.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryDto saveCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategory();

    boolean existCategory(String name);

    CategoryDto getCategory(String name);

    CategoryDto updateCategory(CategoryDto categoryDto);

    Boolean deleteCategory(String name);
}
