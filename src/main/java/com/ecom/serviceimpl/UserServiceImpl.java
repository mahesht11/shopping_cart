package com.ecom.serviceimpl;

import com.ecom.dto.UserDto;
import com.ecom.entity.UserDetails;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.exception.ResourseExistException;
import com.ecom.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
public class UserServiceImpl {

    @Autowired
    UserRepository repository;

    @Autowired
    ModelMapper mapper;

    public UserDto createUser(UserDto userDto) {
        log.info(" ");
        UserDetails userDetails = repository.findByEmail(userDto.getEmail());
        if(!ObjectUtils.isEmpty(userDetails))
            throw new ResourseExistException("User already existed with this email id : "+userDto.getEmail());
        UserDetails userDetails1 = mapper.map(userDto, UserDetails.class);
        byte[] encodedBytes = Base64.encodeBase64(userDetails1.getPassword().getBytes());
        String encodedPwd = new String(encodedBytes);
        userDetails1.setPassword(encodedPwd);
        UserDetails userDetails2 = repository.saveAndFlush(userDetails1);
        return mapper.map(userDetails2, UserDto.class);
    }

    public UserDto getUserByEmail(String email) {
        log.info(" ");
        UserDetails userDetails = repository.findByEmail(email);
        if(!userDetails.isEnable())
            throw new ResourceNotFoundException("User account is blocked. Please unblock it!");
        if(ObjectUtils.isEmpty(userDetails)){
            throw new ResourceNotFoundException("User is not exist in the DB with this email : "+email);
        }
        return mapper.map(userDetails, UserDto.class);
    }

    public String loginUser(String email, String password) {
        log.info(" ");
        byte[] encodedBytes = Base64.encodeBase64(password.getBytes());
        String encodedPwd = new String(encodedBytes);
        UserDetails userDetails = repository.findByEmailAndPassword(email, encodedPwd);
        if(!ObjectUtils.isEmpty(userDetails)){
            return "User login successfully with this email : "+email;
        }
        else{
            throw new ResourceNotFoundException("User email and password are incorrect, Please try again!");
        }
    }

    public boolean unblockUser(String email, String status){
        log.info(" ");
        UserDetails userDetails = repository.findByEmail(email);
        if(ObjectUtils.isEmpty(userDetails)){
            throw new ResourceNotFoundException("User is not exist in the DB with this email : "+email);
        }
        if(!userDetails.isEnable())
            throw new ResourceNotFoundException("User account is blocked. Please unblock it!");
        userDetails.setIsEnable(status);
        UserDetails userDetails2 = repository.saveAndFlush(userDetails);
        return true;

    }
}
