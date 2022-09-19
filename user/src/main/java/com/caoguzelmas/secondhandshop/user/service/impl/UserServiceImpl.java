package com.caoguzelmas.secondhandshop.user.service.impl;

import com.caoguzelmas.secondhandshop.user.dto.CreateUserRequest;
import com.caoguzelmas.secondhandshop.user.dto.UpdateUserRequest;
import com.caoguzelmas.secondhandshop.user.dto.UserDto;
import com.caoguzelmas.secondhandshop.user.exception.UserActivityException;
import com.caoguzelmas.secondhandshop.user.exception.UserNotFoundException;
import com.caoguzelmas.secondhandshop.user.mapper.UserDtoMapper;
import com.caoguzelmas.secondhandshop.user.model.UserInformation;
import com.caoguzelmas.secondhandshop.user.repository.UserRepository;
import com.caoguzelmas.secondhandshop.user.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public UserServiceImpl(UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userDtoMapper.convert(userRepository.findAll());
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserInformation user = findUserByMail(email);
        return userDtoMapper.convert(user);
    }

    @Override
    public UserDto createUser(CreateUserRequest userRequest) {
        UserInformation user = new UserInformation(userRequest.getEmail(),
                userRequest.getFirstName(),
                userRequest.getMiddleName(),
                userRequest.getLastName(),
                false);

        return userDtoMapper.convert(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(String email, UpdateUserRequest updateUserRequest) {
        UserInformation user = findUserByMail(email);

        if (!user.getActive())  {
            throw new UserActivityException();
        }
        UserInformation updatedUser = new UserInformation(user.getId(),
                updateUserRequest.getEmail(),
                updateUserRequest.getFirstName(),
                updateUserRequest.getMiddleName(),
                updateUserRequest.getLastName(),
                user.getActive());

        return userDtoMapper.convert(userRepository.save(updatedUser));
    }

    @Override
    public void deactivateUser(Long id) {
        changeActivity(id, false);
    }
    @Override
    public void activateUser(Long id) {
        changeActivity(id, true);
    }

    @Override
    public void deleteUser(Long id) {
        findUserById(id);

        userRepository.deleteById(id);
    }

    private UserInformation findUserByMail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with given Email :" + email));
    }

    private UserInformation findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with given Id :" + id));
    }

    private void changeActivity(Long id, boolean activity) {
        UserInformation user = findUserById(id);

        if (user.getActive().equals(activity)) {
            if (activity) {
                throw new UserActivityException("User is already Active!");
            } else {
                throw new UserActivityException("User is already Inactive !");
            }
        }

        UserInformation updatedUser = new UserInformation(user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                activity);

        userRepository.save(updatedUser);
    }
}
