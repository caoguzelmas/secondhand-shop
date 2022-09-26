package com.caoguzelmas.secondhandshop.user.mapper;

import com.caoguzelmas.secondhandshop.user.dto.AddressDto;
import com.caoguzelmas.secondhandshop.user.model.Address;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

// TODO
@Component
public class AddressMapper {

    public AddressDto convert(Address from) {
        return AddressDto.builder()
                .addressId(from.getAddressId())
                .addressType(from.getAddressType())
                .address(from.getAddress())
                .city(from.getCity())
                .region(from.getRegion())
                .postalCode(from.getPostalCode())
                .country(from.getCountry())
                .build();
    }

    public List<AddressDto> convert(List<Address> fromList) {
        return fromList.stream()
                .map(this::convert).collect(Collectors.toList());
    }
}
