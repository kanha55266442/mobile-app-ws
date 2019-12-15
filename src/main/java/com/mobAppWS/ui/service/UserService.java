package com.mobAppWS.ui.service;

import com.mobAppWS.ui.model.request.UserLoginRequestModel;
import com.mobAppWS.ui.shared.dto.UserDto;

public interface UserService {
    public UserDto createUser(UserDto userDto);
    public UserDto getUser(String userName);
    public UserDto getUserByUserId(String id);
}
