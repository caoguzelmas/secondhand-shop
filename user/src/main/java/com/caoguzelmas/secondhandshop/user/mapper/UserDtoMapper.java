package com.caoguzelmas.secondhandshop.user.mapper;

import com.caoguzelmas.secondhandshop.user.dto.UserDto;
import com.caoguzelmas.secondhandshop.user.model.UserInformation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoMapper {
    public UserDto convert(UserInformation from) {
        return new UserDto(from.getEmail(), from.getFirstName(), from.getMiddleName(), from.getLastName());
    }

    public List<UserDto> convert(List<UserInformation> fromList) {
        return fromList.stream()
                .map(from -> new UserDto(from.getEmail(), from.getFirstName(), from.getMiddleName(), from.getLastName()))
                .collect(Collectors.toList());
    }
}
