package com.mobAppWS.ui.controller;

import com.mobAppWS.ui.model.request.UserDetailsRequestModel;
import com.mobAppWS.ui.model.request.UserLoginRequestModel;
import com.mobAppWS.ui.model.response.UserRest;
import com.mobAppWS.ui.service.UserService;
import com.mobAppWS.ui.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/{id}",
            /*
            *To provide support for both xml and json format
            */
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserRest getUser(@PathVariable String id) {
        UserRest returnValue = new UserRest();
        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);
        return returnValue;
    }

    @PostMapping(
            /*
             *To provide support for both xml and json format
            */
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
            )
    public UserRest createUser(@RequestBody UserDetailsRequestModel request) {
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        //BeanUtils is a spring framework class that have a static copyProperties(Object source, Object target) to copy source to target
        BeanUtils.copyProperties(request, userDto);
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);
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
