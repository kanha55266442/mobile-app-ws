package com.mobAppWS.ui.controller;

import com.mobAppWS.ui.exceptions.UserServiceException;
import com.mobAppWS.ui.model.request.RequestOperationName;
import com.mobAppWS.ui.model.request.UserDetailsRequestModel;
import com.mobAppWS.ui.model.request.UserLoginRequestModel;
import com.mobAppWS.ui.model.response.ErrorMessages;
import com.mobAppWS.ui.model.response.OperationRequestStatus;
import com.mobAppWS.ui.model.response.OperationStatusModel;
import com.mobAppWS.ui.model.response.UserRest;
import com.mobAppWS.ui.service.UserService;
import com.mobAppWS.ui.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DuplicatedCode")
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
    public UserRest createUser(@RequestBody UserDetailsRequestModel request)throws Exception {
        UserRest returnValue = new UserRest();
        if (request.getEmail().isEmpty()){
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        UserDto userDto = new UserDto();
        //BeanUtils is a spring framework class that have a static copyProperties(Object source, Object target) to copy source to target
        BeanUtils.copyProperties(request, userDto);
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);
        return returnValue;
    }

    @SuppressWarnings("DuplicatedCode")
    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserRest updateUser(@PathVariable String id,@RequestBody UserDetailsRequestModel request) {
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        //BeanUtils is a spring framework class that have a static copyProperties(Object source, Object target) to copy source to target
        BeanUtils.copyProperties(request, userDto);
        UserDto createdUser = userService.updateUser(id,userDto);
        BeanUtils.copyProperties(createdUser, returnValue);
        return returnValue;
    }

    @DeleteMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
            )
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel returnModel=new OperationStatusModel();
        returnModel.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(id);
        returnModel.setOperationStatus(OperationRequestStatus.SUCCESS.name());
        return returnModel;
    }
    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<UserRest> getAllUser(@RequestParam(value = "page" ,defaultValue = "1") int page,
                                     @RequestParam(value = "limit" ,defaultValue = "25") int limit
                                     ){
        List<UserRest> returnValue=new ArrayList<UserRest>();
        List<UserDto> users=userService.getUsers(page,limit);
        for (UserDto userDto:users){
            UserRest user=new UserRest();
            BeanUtils.copyProperties(userDto,user);
            returnValue.add(user);
        }
        return  returnValue;
    }


}
