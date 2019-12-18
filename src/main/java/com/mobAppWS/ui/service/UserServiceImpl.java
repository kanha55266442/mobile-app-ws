package com.mobAppWS.ui.service;

import com.mobAppWS.ui.exceptions.UserServiceException;
import com.mobAppWS.ui.io.entity.UserEntity;
import com.mobAppWS.ui.model.request.UserLoginRequestModel;
import com.mobAppWS.ui.model.response.ErrorMessage;
import com.mobAppWS.ui.model.response.ErrorMessages;
import com.mobAppWS.ui.repository.UserRepository;
import com.mobAppWS.ui.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDto createUser(UserDto userDto)  {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        userEntity.setEncryptedPassword(encoder.encode(userDto.getPassword()));
        UserEntity savedUserEntity = userRepository.save(userEntity);
        UserDto returnValue = new UserDto();
        returnValue.setUserID(userEntity.getUserId());
        BeanUtils.copyProperties(savedUserEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUser(String userName) {
        UserEntity userEntity=userRepository.findByEmail(userName);
        UserDto returnValue=new UserDto();
        returnValue.setUserID(userEntity.getUserId());
        BeanUtils.copyProperties(userEntity,returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String id) {
        Optional<UserEntity> entityOptional = userRepository.findById(id);
        UserEntity userById=entityOptional.get();
        UserDto returnValue=new UserDto();
        returnValue.setUserID(userById.getUserId());
        BeanUtils.copyProperties(userById,returnValue);
        return returnValue;
    }

    @Override
    public UserDto updateUser(String userId,UserDto userDto) {
        UserDto returnValue=new UserDto();
        Optional<UserEntity> entityOptional = userRepository.findById(userId);
        UserEntity userById=entityOptional.get();
        if (userById==null)
            throw new UserServiceException(ErrorMessages.RECORD_NOT_FOUND.getErrorMessage());
        userById.setFirstName(userDto.getFirstName());
        userById.setLastName(userDto.getLastName());
        UserEntity updatedUser = userRepository.save(userById);
        BeanUtils.copyProperties(updatedUser,returnValue);
        return returnValue;
    }

    @Override
    public void deleteUser(String id) {
        UserDto returnValue=new UserDto();
        Optional<UserEntity> entityOptional = userRepository.findById(id);
        UserEntity userById=entityOptional.get();
        if (userById==null)
            throw new UserServiceException(ErrorMessages.RECORD_NOT_FOUND.getErrorMessage());
        userRepository.delete(userById);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue=new ArrayList<>();
        Pageable userEntityPage= PageRequest.of(page, limit);
        Page<UserEntity> users = userRepository.findAll(userEntityPage);
        for (UserEntity userEntity:users){
            UserDto user=new UserDto();
            BeanUtils.copyProperties(userEntity,users);
            returnValue.add(user);
        }
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null)
            throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
