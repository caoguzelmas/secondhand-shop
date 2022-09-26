package com.caoguzelmas.secondhandshop.user.support;

import com.caoguzelmas.secondhandshop.user.dto.AddressDto;
import com.caoguzelmas.secondhandshop.user.dto.InsertAddressRequest;
import com.caoguzelmas.secondhandshop.user.dto.UpdateAddressRequest;
import com.caoguzelmas.secondhandshop.user.model.Address;
import com.caoguzelmas.secondhandshop.user.model.User;
import com.caoguzelmas.secondhandshop.user.model.enums.AddressType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddressUserTestSupport extends UserTestSupport {
    public static Long addressId = 101L;

    public static List<Address> generateAddressList() {
        User user = generateUser(true);
        List<Address> addressList = new ArrayList<>();
        addressList.addAll(IntStream.range(0, 2).mapToObj(i -> Address.builder()
                .addressId((long) i)
                .addressType(AddressType.BUYING)
                .address("address" + i)
                .city("city" + i)
                .region("region" + i)
                .postalCode("postalCode" + i)
                .country("country" + i)
                .user(user)
                .build()).collect(Collectors.toList()));

        addressList.addAll(IntStream.range(3, 5).mapToObj(i -> Address.builder()
                .addressId((long) i)
                .addressType(AddressType.SELLING)
                .address("address" + i)
                .city("city" + i)
                .region("region" + i)
                .postalCode("postalCode" + i)
                .country("country" + i)
                .user(user)
                .build()).collect(Collectors.toList()));

        return addressList;
    }

    public static List<Address> generateAddressList(AddressType addressType, int range) {
        User user = generateUser(true);

        return IntStream.range(0, range).mapToObj(i -> Address.builder()
                .addressId((long) i)
                .addressType(addressType)
                .address("address" + i)
                .city("city" + i)
                .region("region" + i)
                .postalCode("postalCode" + i)
                .country("country" + i)
                .user(user)
                .build()).collect(Collectors.toList());
    }

    public static List<AddressDto> generateAddressDtoList(List<Address> addressList) {
        return addressList.stream()
                .map(from -> AddressDto.builder()
                        .addressId(from.getAddressId())
                        .addressType(from.getAddressType())
                        .city(from.getCity())
                        .region(from.getRegion())
                        .postalCode(from.getPostalCode())
                        .country(from.getCountry())
                        .build()).collect(Collectors.toList());
    }

    public static InsertAddressRequest generateInsertAddressRequestWithAddressType(AddressType addressType) {
        return InsertAddressRequest.builder()
                .addressType(addressType)
                .address("address")
                .city("city")
                .region("region")
                .postalCode("postalCode")
                .country("country")
                .build();
    }

    public static UpdateAddressRequest generateUpdateAddressRequestWithAddressType(AddressType addressType) {
        return UpdateAddressRequest.builder()
                .addressType(addressType)
                .address("address")
                .city("city")
                .region("region")
                .postalCode("postalCode")
                .country("country")
                .build();
    }

    public static Address generateAddress(AddressType addressType, User user) {
        return Address.builder()
                .addressId(1L)
                .addressType(addressType)
                .address("address")
                .city("city")
                .region("region")
                .postalCode("postalCode")
                .country("country")
                .user(user)
                .build();
    }

    public static AddressDto generateAddressDto(AddressType addressType) {
        return AddressDto.builder()
                .addressId(1L)
                .addressType(addressType)
                .address("address")
                .city("city")
                .region("region")
                .postalCode("postalCode")
                .country("country")
                .build();
    }
}
