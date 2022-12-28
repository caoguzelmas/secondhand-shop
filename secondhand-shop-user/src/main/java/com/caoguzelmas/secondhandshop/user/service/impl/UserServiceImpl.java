package com.caoguzelmas.secondhandshop.user.service.impl;

import com.caoguzelmas.secondhandshop.user.dto.CreateUserRequest;
import com.caoguzelmas.secondhandshop.user.dto.UpdateUserRequest;
import com.caoguzelmas.secondhandshop.user.dto.UserDto;
import com.caoguzelmas.secondhandshop.user.exception.UserActivityException;
import com.caoguzelmas.secondhandshop.user.exception.UserNotFoundException;
import com.caoguzelmas.secondhandshop.user.mapper.UserMapper;
import com.caoguzelmas.secondhandshop.user.model.User;
import com.caoguzelmas.secondhandshop.user.repository.UserRepository;
import com.caoguzelmas.secondhandshop.user.service.IUserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.convert(userRepository.findAll());
    }

    @Override
    public UserDto getUserByEmail(final String email) {
        User user = findUserByMail(email);
        return userMapper.convert(user);
    }

    @Override
    public UserDto createUser(final CreateUserRequest userRequest) {
        User user = User.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .firstName(userRequest.getFirstName())
                .middleName(userRequest.getMiddleName())
                .lastName(userRequest.getLastName())
                .isActive(false)
                .build();
        user.setCreatedDate(LocalDateTime.now());
        return userMapper.convert(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(final String email, final UpdateUserRequest updateUserRequest) {
        User user = findUserByMail(email);

        if (!user.getIsActive())  {
            throw new UserActivityException();
        }
        User updatedUser = User.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .firstName(updateUserRequest.getFirstName())
                .middleName(updateUserRequest.getMiddleName())
                .lastName(updateUserRequest.getLastName())
                .isActive(user.getIsActive())
                .build();
        user.setUpdatedDate(LocalDateTime.now());
        return userMapper.convert(userRepository.save(updatedUser));
    }

    @Override
    public void deactivateUser(final Long id) {
        changeActivity(id, false);
    }
    @Override
    public void activateUser(final Long id) {
        changeActivity(id, true);
    }

    @Override
    public void deleteUser(final Long id) {
        findUserById(id);

        userRepository.deleteById(id);
    }

    private User findUserByMail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with given Email :" + email));
    }

    public User findUserById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with given Id :" + id));
    }

    private void changeActivity(Long id, boolean activity) {
        User user = findUserById(id);

        if (user.getIsActive().equals(activity)) {
            if (activity) {
                throw new UserActivityException("User is already Active!");
            } else {
                throw new UserActivityException("User is already Inactive !");
            }
        }

        User updatedUser = User.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .middleName(user.getFirstName())
                .lastName(user.getLastName())
                .isActive(activity)
                .build();
        userRepository.save(updatedUser);
    }
}
