package com.caoguzelmas.secondhandshop.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateUserRequest {

    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
}
