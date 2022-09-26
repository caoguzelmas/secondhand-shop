package com.caoguzelmas.secondhandshop.user.dto;

import com.caoguzelmas.secondhandshop.user.model.enums.AddressType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InsertAddressRequest {
    private AddressType addressType;
    private String address;
    private String city;
    private String region;
    private String postalCode;
    private String country;
}
