package com.caoguzelmas.secondhandshop.user;

import com.caoguzelmas.secondhandshop.user.dto.UserDto;
import com.caoguzelmas.secondhandshop.user.model.UserInformation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestSupport {
    public static Long userId = 100L;
    public static String email = "email@caoguzelmas.org";

    public static List<UserInformation> generateUserList() {
        return IntStream.range(0,5).mapToObj(i -> new UserInformation((long) i,
                        i + "@caoguzelmas.org",
                        "firstName" + i,
                        "middleName" + i,
                        "lastName" + i,
                        true))
                .collect(Collectors.toList());
    }

    public static List<UserDto> generateUserDtoList(List<UserInformation> userInformationList) {
        return userInformationList.stream()
                .map(from -> new UserDto(from.getEmail(), from.getFirstName(), from.getMiddleName(), from.getLastName()))
                .collect(Collectors.toList());
    }

    public static UserInformation generateUser(String email) {
        return new UserInformation(userId,
                email,
                "firstName" + userId,
                "middleName" + userId,
                "lastName" + userId,
                true);
    }

    public static UserDto generateUserDto(String email) {
        return new UserDto(email,
                "firstName" + userId,
                "middleName" + userId,
                "lastName" + userId);
    }
}
