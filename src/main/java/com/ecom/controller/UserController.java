package com.ecom.controller;

import com.ecom.dto.UserDto;
import com.ecom.serviceimpl.UserServiceImpl;
import com.ecom.util.UserValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserValidation validation;

    @PostMapping("/save-user")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        log.info("");
        boolean validateUser = validation.validateUser(userDto);
        UserDto userDto1 = userService.createUser(userDto);
        if(!ObjectUtils.isEmpty(userDto1)){
            return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
        }
        return null;
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        log.info(" ");
        UserDto userDto = userService.getUserByEmail(email);
        if(!ObjectUtils.isEmpty(userDto)){
            return new ResponseEntity<>(userDto, HttpStatus.FOUND);
        }
        return null;
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password){
        log.info(" ");
        String response = userService.loginUser(email, password);
        if(!response.isEmpty()){
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        }
        return null;
    }

    @GetMapping("/{email}/{status}")
    public ResponseEntity<String> unblockUser(@RequestParam String email, @RequestParam String status){
        log.info(" ");
        boolean response = userService.unblockUser(email, status);
        if(response){
            return new ResponseEntity<>("User unblocked successfully!", HttpStatus.ACCEPTED);
        }
        return null;
    }
}
