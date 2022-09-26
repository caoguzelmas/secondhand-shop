package com.caoguzelmas.secondhandshop.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserDto {
    private Long userId;
    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private List<AddressDto> sellingAddresses;
    private List<AddressDto> buyingAddresses;
}
