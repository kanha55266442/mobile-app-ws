package com.mobAppWS.ui.service;

import com.mobAppWS.ui.io.entity.UserEntity;
import com.mobAppWS.ui.model.request.UserLoginRequestModel;
import com.mobAppWS.ui.repository.UserRepository;
import com.mobAppWS.ui.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDto createUser(UserDto userDto) {
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null)
            throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
