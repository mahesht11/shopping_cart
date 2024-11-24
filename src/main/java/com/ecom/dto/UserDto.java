package com.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserDto {

        private String name;
        private String mobileNum;
        private String email;
        private String city;
        private String state;
        private String pincode;
        private String password;
        private String profileImage;
    }

