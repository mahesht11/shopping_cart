package com.ecom.util;

import com.ecom.dto.UserDto;
import com.ecom.exception.UserValidationException;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserValidation {

    public boolean validateUser(UserDto userDto){

        if(userDto.getName().isEmpty() || userDto.getName().length()<3)
            throw new UserValidationException("User should not be empty or user length should not be lessthan 3!");
        if(userDto.getMobileNum().length()!=10)
            throw new UserValidationException("mobile number should be 10 digit!");
        if(userDto.getPincode().length()!=5)
            throw new UserValidationException("pincode should be 6 digit!");
        if(userDto.getCity().isEmpty() || userDto.getState().isEmpty())
            throw new UserValidationException("City and State should not be empty!");
        if(userDto.getPassword().length()>=5)
            throw new UserValidationException("password should be more than 5 digit!");
        return true;
    }
}
