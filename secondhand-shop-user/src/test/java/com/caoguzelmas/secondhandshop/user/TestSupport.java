package com.caoguzelmas.secondhandshop.user;

import com.caoguzelmas.secondhandshop.user.dto.CreateUserRequest;
import com.caoguzelmas.secondhandshop.user.dto.UserDto;
import com.caoguzelmas.secondhandshop.user.model.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestSupport {
    public static Long userId = 100L;
    public static String email = "email@caoguzelmas.org";

    public static List<User> generateUserList() {
        return IntStream.range(0,5).mapToObj(i -> User.builder()
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
                .firstName("firstName" + userId)
                .middleName("middleName" + userId)
                .lastName("lastName" + userId)
                .isActive(activity)
                .build();
    }

    public static UserDto generateUserDto() {
        return UserDto.builder()
                .email(email)
                .firstName("firstName" + userId)
                .middleName("middleName" + userId)
                .lastName("lastName" + userId)
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
}
