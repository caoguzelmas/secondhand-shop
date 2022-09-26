package com.caoguzelmas.secondhandshop.user.service;

import com.caoguzelmas.secondhandshop.user.dto.CreateUserRequest;
import com.caoguzelmas.secondhandshop.user.dto.UpdateUserRequest;
import com.caoguzelmas.secondhandshop.user.exception.UserActivityException;
import com.caoguzelmas.secondhandshop.user.support.UserTestSupport;
import com.caoguzelmas.secondhandshop.user.dto.UserDto;
import com.caoguzelmas.secondhandshop.user.exception.UserNotFoundException;
import com.caoguzelmas.secondhandshop.user.mapper.UserMapper;
import com.caoguzelmas.secondhandshop.user.model.User;
import com.caoguzelmas.secondhandshop.user.repository.UserRepository;
import com.caoguzelmas.secondhandshop.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplUserTest extends UserTestSupport {
    private UserMapper mapper;
    private UserRepository userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        mapper = mock(UserMapper.class);
        userRepository = mock(UserRepository.class);

        userService = new UserServiceImpl(userRepository, mapper);
    }

    @Test
    public void testGetAllUsers_shouldReturnUserDtoList() {
        List<User> userList = generateUserList();
        List<UserDto> userDtoList = generateUserDtoList(userList);

        when(userRepository.findAll()).thenReturn(userList);
        when(mapper.convert(userList)).thenReturn(userDtoList);

        List<UserDto> result = userService.getAllUsers();

        assertEquals(userDtoList, result);
        verify(userRepository).findAll();
        verify(mapper).convert(userList);
    }

    @Test
    public void testGetUserByMail_whenUserExist_shouldReturnUserDto() {
        User user = generateUser(true);
        UserDto userDto = generateUserDto();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(mapper.convert(user)).thenReturn(userDto);

        UserDto result = userService.getUserByEmail(email);

        assertEquals(userDto, result);
        verify(userRepository).findByEmail(email);
        verify(mapper).convert(user);
    }

    @Test
    public void testGetUserByMail_whenUserDoesNotExist_shouldThrowUserNotFoundException() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(email));
        verify(userRepository).findByEmail(email);
        verifyNoInteractions(mapper);
    }

    @Test
    public void testCreateUser_shouldReturnCreatedUserDto() {
        CreateUserRequest createUserRequest = generateCreateUserRequest(email,"password", "firstName", "middleName", "lastName");
        User savedUser = generateUser(false);
        UserDto userDto = generateUserDto();

        when(userRepository.save(any())).thenReturn(savedUser);
        when(mapper.convert(savedUser)).thenReturn(userDto);

        UserDto result = userService.createUser(createUserRequest);

        assertEquals(userDto, result);
        verify(userRepository).save(any());
        verify(mapper).convert(savedUser);
    }


    @Test
    public void testUpdateUser_whenUserMailExistAndUserIsActive_shouldReturnUpdatedUserDto() {
        UpdateUserRequest updateUserRequest = generateUpdateUserRequest(email,"password2", "firstName2", "middleName2", "lastName2");
        User user = generateUser(userId, email, "password2","firstName2", "middleName2", "lastName2", true);
        UserDto userDto = generateUserDto(email,"password2","firstName2", "middleName2", "lastName2");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
        when(mapper.convert(user)).thenReturn(userDto);

        UserDto result = userService.updateUser(email, updateUserRequest);

        assertEquals(userDto, result);

        verify(userRepository).findByEmail(email);
        verify(userRepository).save(any());
        verify(mapper).convert(user);
    }

    @Test
    public void testUpdateUser_whenUserMailDoesNotExist_shouldThrowUserNotFoundException() {
        UpdateUserRequest updateUserRequest = generateUpdateUserRequest(email,"password2", "firstName2", "middleName2", "lastName2");

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(email, updateUserRequest));

        verify(userRepository).findByEmail(email);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(mapper);
    }

    @Test
    public void testUpdateUser_whenUserMailExistButUserIsNotActive_shouldThrowUserActivityException() {
        UpdateUserRequest updateUserRequest = generateUpdateUserRequest(email,"password2", "firstName2", "middleName2", "lastName2");
        User user = generateUser(false);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        assertThrows(UserActivityException.class, () -> userService.updateUser(email, updateUserRequest));

        verify(userRepository).findByEmail(email);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(mapper);
    }

    @Test
    public void testDeactivateUser_whenUserIdExist_shouldUpdateUserByActiveFalse() {
        User user = generateUser(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deactivateUser(userId);

        verify(userRepository).findById(userId);
        verify(userRepository).save(any());
    }

    @Test
    public void testDeactivateUser_whenUserIdDoesNotExist_shouldThrowUserNotFoundException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deactivateUser(userId));

        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testActivateUser_whenUserIdExist_shouldUpdateUserByActiveTrue() {
        User user = generateUser(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.activateUser(userId);

        verify(userRepository).findById(userId);
        verify(userRepository).save(any());
    }

    @Test
    public void testActivateUser_whenUserIdDoesNotExist_shouldThrowUserNotFoundException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.activateUser(userId));

        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testActivateUser_whenUserAlreadyActive_shouldThrowUserActivityException() {
        User activeUser = generateUser(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(activeUser));

        assertThrows(UserActivityException.class, () -> userService.activateUser(userId));
    }

    @Test
    public void testDeActivateUser_whenUserAlreadyDeActive_shouldThrowUserActivityException() {
        User activeUser = generateUser(false);
        when(userRepository.findById(userId)).thenReturn(Optional.of(activeUser));

        assertThrows(UserActivityException.class, () -> userService.deactivateUser(userId));
    }

    @Test
    public void testDeleteUser_whenUserIdExist_shouldDeleteUser() {
        User user = generateUser(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteUser(userId);

        verify(userRepository).findById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    public void testDeleteUser_whenUserIdDoesNotExist_shouldThrowUserNotFoundException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));

        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }
}
