package com.caoguzelmas.secondhandshop.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateUserRequest {
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;

}
