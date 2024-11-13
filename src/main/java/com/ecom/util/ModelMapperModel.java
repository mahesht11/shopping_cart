package com.ecom.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperModel {

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
