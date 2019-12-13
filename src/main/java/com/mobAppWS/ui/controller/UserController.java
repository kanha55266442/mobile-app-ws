package com.mobAppWS.ui.controller;

import com.mobAppWS.ui.model.request.UserDetailsRequestModel;
import com.mobAppWS.ui.model.request.UserLoginRequestModel;
import com.mobAppWS.ui.model.response.UserRest;
import com.mobAppWS.ui.service.UserService;
import com.mobAppWS.ui.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
  private UserService userService;

    @GetMapping
    public String getUser() {
        return "get user was called";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel request) {
        UserRest returnValue=new UserRest();
        UserDto userDto=new UserDto();
        //BeanUtils is a spring framework class that have a static copyProperties(Object source, Object target) to copy source to target
        BeanUtils.copyProperties(request,userDto);
        UserDto createdUser=userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser,returnValue);
        return returnValue;
    }

    @PutMapping
    public String updateUser() {
        return "update user was called";
    }

    @DeleteMapping
    public String deleteUser() {
        return "delete user was called";
    }

}
