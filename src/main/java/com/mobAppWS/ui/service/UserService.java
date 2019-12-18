package com.mobAppWS.ui.service;

import com.mobAppWS.ui.model.request.UserLoginRequestModel;
import com.mobAppWS.ui.shared.dto.UserDto;

import java.util.List;

public interface UserService {
    public UserDto createUser(UserDto userDto);
    public UserDto getUser(String userName);
    public UserDto getUserByUserId(String id);
    public UserDto updateUser(String userId,UserDto userDto);
    public void deleteUser(String id);
    public List<UserDto> getUsers(int page, int limit);
}
