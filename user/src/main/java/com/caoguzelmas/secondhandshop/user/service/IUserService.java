package com.caoguzelmas.secondhandshop.user.service;

import com.caoguzelmas.secondhandshop.user.dto.CreateUserRequest;
import com.caoguzelmas.secondhandshop.user.dto.UpdateUserRequest;
import com.caoguzelmas.secondhandshop.user.dto.UserDto;

import java.util.List;

public interface IUserService {
    List<UserDto> getAllUsers();
    UserDto getUserByEmail(String email);
    UserDto createUser(CreateUserRequest userRequest);
    UserDto updateUser(String email, UpdateUserRequest updateUserRequest);
    void deactivateUser(Long id);
    void activateUser(Long id);
    void deleteUser(Long id);
}
