package com.caoguzelmas.secondhandshop.user.service.impl;

import com.caoguzelmas.secondhandshop.user.dto.AddressDto;
import com.caoguzelmas.secondhandshop.user.dto.InsertAddressRequest;
import com.caoguzelmas.secondhandshop.user.dto.UpdateAddressRequest;
import com.caoguzelmas.secondhandshop.user.exception.AddressNotFoundException;
import com.caoguzelmas.secondhandshop.user.exception.UserActivityException;
import com.caoguzelmas.secondhandshop.user.mapper.AddressMapper;
import com.caoguzelmas.secondhandshop.user.model.Address;
import com.caoguzelmas.secondhandshop.user.model.User;
import com.caoguzelmas.secondhandshop.user.model.enums.AddressType;
import com.caoguzelmas.secondhandshop.user.repository.AddressRepository;
import com.caoguzelmas.secondhandshop.user.service.IAddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {
    private final AddressRepository addressRepository;
    private final UserServiceImpl userService;
    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository addressRepository, UserServiceImpl userService, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.addressMapper = addressMapper;
    }

    @Override
    public AddressDto insertAddress(final Long userId, final InsertAddressRequest request) {
        User user = userService.findUserById(userId);

        if (!user.getIsActive()) {
            throw new UserActivityException("User should be activated, before adding any address : " + user.getUserId());
        }
        Address address = Address.builder()
                .addressType(request.getAddressType())
                .address(request.getAddress())
                .city(request.getCity())
                .region(request.getRegion())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .user(user)
                .build();

        return addressMapper.convert(addressRepository.save(address));
    }

    @Override
    public List<AddressDto> getAllAddressesByUserId(final Long id) {
        User user = userService.findUserById(id);

        List<Address> addressList = addressRepository.findAllByUser(user)
                .orElseThrow(() -> new AddressNotFoundException("No Address found with given userId: " + id));
        return addressMapper.convert(addressList);
    }

    @Override
    public List<AddressDto> getAllAddressesByUserIdAndAddressType(final Long id, final AddressType addressType) {
        User user = userService.findUserById(id);

        List<Address> addressList = addressRepository.findAllByUserAndAddressType(user, addressType)
                .orElseThrow(() -> new AddressNotFoundException("No Address found with given userId and type: "
                        + id + ", " + addressType.name()));
        return addressMapper.convert(addressList);
    }

    @Override
    public AddressDto updateAddress(final Long addressId, final UpdateAddressRequest request) {
        Address address = findByAddressId(addressId);
        User user = userService.findUserById(address.getUser().getUserId());

        if (!user.getIsActive()) {
            throw new UserActivityException("User related with given address is not active : " + user.getUserId());
        }
        Address updatedAddress = Address.builder()
                .addressId(address.getAddressId())
                .addressType(request.getAddressType())
                .address(request.getAddress())
                .city(request.getCity())
                .region(request.getRegion())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .user(user)
                .build();
        return addressMapper.convert(addressRepository.save(updatedAddress));
    }

    @Override
    public void deleteAddress(final Long addressId) {
        findByAddressId(addressId);

        addressRepository.deleteById(addressId);
    }

    private Address findByAddressId(final Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found with given addressId: " + addressId));
    }
}
