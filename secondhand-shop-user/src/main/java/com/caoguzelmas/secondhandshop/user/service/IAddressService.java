package com.caoguzelmas.secondhandshop.user.service;

import com.caoguzelmas.secondhandshop.user.dto.AddressDto;
import com.caoguzelmas.secondhandshop.user.dto.InsertAddressRequest;
import com.caoguzelmas.secondhandshop.user.dto.UpdateAddressRequest;
import com.caoguzelmas.secondhandshop.user.model.enums.AddressType;

import java.util.List;


public interface IAddressService {

    AddressDto insertAddress(Long userId, final InsertAddressRequest request);
    void deleteAddress(Long addressId);
    List<AddressDto> getAllAddressesByUserId(Long id);
    List<AddressDto> getAllAddressesByUserIdAndAddressType(Long id, final AddressType addressType);
    AddressDto updateAddress(Long addressId, final UpdateAddressRequest request);
}
