package com.caoguzelmas.secondhandshop.user.mapper;

import com.caoguzelmas.secondhandshop.user.dto.UserDto;
import com.caoguzelmas.secondhandshop.user.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
//TODO
@Component
public class UserMapper {
    private final AddressMapper addressMapper;

    public UserMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public UserDto convert(User from) {
        return UserDto.builder()
                .userId(from.getUserId())
                .email(from.getEmail())
                .firstName(from.getFirstName())
                .middleName(from.getMiddleName())
                .lastName(from.getLastName())
                .addressDtos(from.getAddresses() == null ? null : addressMapper.convert(from.getAddresses()))
                .build();
    }

    public List<UserDto> convert(List<User> fromList) {
        return fromList.stream()
                .map(this::convert).collect(Collectors.toList());
    }
}
