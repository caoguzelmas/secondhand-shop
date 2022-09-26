package com.caoguzelmas.secondhandshop.user.support;

import com.caoguzelmas.secondhandshop.user.dto.*;
import com.caoguzelmas.secondhandshop.user.model.Address;
import com.caoguzelmas.secondhandshop.user.model.User;
import com.caoguzelmas.secondhandshop.user.model.enums.AddressType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserTestSupport {
    public static Long userId = 100L;
    public static String email = "email@caoguzelmas.org";

    public static List<User> generateUserList() {
        return IntStream.range(0, 5).mapToObj(i -> User.builder()
                .userId((long) i)
                .email(i + "@caoguzelmas.org")
                .firstName("firstName" + i)
                .middleName("middleName" + i)
                .lastName("lastName" + i)
                .isActive(true)
                .build()).collect(Collectors.toList());
    }

    public static List<UserDto> generateUserDtoList(List<User> userList) {
        return userList.stream()
                .map(from -> UserDto.builder()
                        .email(from.getEmail())
                        .firstName(from.getFirstName())
                        .middleName(from.getMiddleName())
                        .lastName(from.getLastName())
                        .build()).collect(Collectors.toList());
    }

    public static User generateUser(boolean activity) {
        return User.builder()
                .userId(userId)
                .email(email)
                .password("password")
                .firstName("firstName" + userId)
                .middleName("middleName" + userId)
                .lastName("lastName" + userId)
                .isActive(activity)
                .build();
    }

    public static User generateUser(Long userId, String email, String password, String firstName, String middleName, String lastName, boolean activity) {
        return User.builder()
                .userId(userId)
                .email(email)
                .password(password)
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .isActive(activity)
                .build();
    }

    public static UserDto generateUserDto() {
        return UserDto.builder()
                .email(email)
                .password("password")
                .firstName("firstName" + userId)
                .middleName("middleName" + userId)
                .lastName("lastName" + userId)
                .build();
    }

    public static UserDto generateUserDto(String email, String password, String firstName, String middleName, String lastName) {
        return UserDto.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .build();
    }

    public static CreateUserRequest generateCreateUserRequest() {
        return CreateUserRequest.builder()
                .email(email)
                .password("password")
                .firstName("firstName" + userId)
                .middleName("middleName" + userId)
                .lastName("lastName" + userId)
                .build();
    }

    public static UpdateUserRequest generateUpdateUserRequest() {
        return UpdateUserRequest.builder()
                .email(email)
                .firstName("firstName")
                .middleName("middleName")
                .lastName("lastName")
                .build();
    }

    public static UpdateUserRequest generateUpdateUserRequest(String email, String password, String firstName, String middleName, String lastName) {
        return UpdateUserRequest.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .build();
    }

    public static CreateUserRequest generateCreateUserRequest(String email, String password, String firstName, String middleName, String lastName) {
        return CreateUserRequest.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .build();
    }
}
