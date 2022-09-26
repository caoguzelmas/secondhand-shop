package com.caoguzelmas.secondhandshop.user.service;

import com.caoguzelmas.secondhandshop.user.support.AddressUserTestSupport;
import com.caoguzelmas.secondhandshop.user.dto.AddressDto;
import com.caoguzelmas.secondhandshop.user.dto.InsertAddressRequest;
import com.caoguzelmas.secondhandshop.user.dto.UpdateAddressRequest;
import com.caoguzelmas.secondhandshop.user.exception.AddressNotFoundException;
import com.caoguzelmas.secondhandshop.user.exception.UserActivityException;
import com.caoguzelmas.secondhandshop.user.exception.UserNotFoundException;
import com.caoguzelmas.secondhandshop.user.mapper.AddressMapper;
import com.caoguzelmas.secondhandshop.user.mapper.UserMapper;
import com.caoguzelmas.secondhandshop.user.model.Address;
import com.caoguzelmas.secondhandshop.user.model.User;
import com.caoguzelmas.secondhandshop.user.model.enums.AddressType;
import com.caoguzelmas.secondhandshop.user.repository.AddressRepository;
import com.caoguzelmas.secondhandshop.user.repository.UserRepository;
import com.caoguzelmas.secondhandshop.user.service.impl.AddressServiceImpl;
import com.caoguzelmas.secondhandshop.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AddressServiceImplUserTest extends AddressUserTestSupport {
    private AddressMapper addressMapper;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private AddressServiceImpl addressService;

    @BeforeEach
    public void setUp() {
        addressMapper = mock(AddressMapper.class);
        UserMapper userMapper = mock(UserMapper.class);
        addressRepository = mock(AddressRepository.class);
        userRepository = mock(UserRepository.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper);
        addressService = new AddressServiceImpl(addressRepository, userService, addressMapper);
    }

    @Test
    public void testGetAllAddressesByUserId_whenUserExist_shouldReturnAddressDtoList() {
        List<Address> addressList = generateAddressList();
        List<AddressDto> addressDtoList = generateAddressDtoList(addressList);
        User user = generateUser(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findAllByUser(user)).thenReturn(Optional.of(addressList));
        when(addressMapper.convert(addressList)).thenReturn(addressDtoList);

        List<AddressDto> result = addressService.getAllAddressesByUserId(user.getUserId());

        assertEquals(addressDtoList, result);
        verify(addressRepository).findAllByUser(user);
        verify(userRepository).findById(userId);
        verify(addressMapper).convert(addressList);
    }

    @Test
    public void testGetAllAddressesByUserId_whenUserDoesNotExist_shouldThrowUserNotFoundException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> addressService.getAllAddressesByUserId(userId));
        verify(userRepository).findById(userId);
        verifyNoInteractions(addressRepository);
        verifyNoInteractions(addressMapper);
    }

    @Test
    public void testGetAllAddressesByUserIdAndAddressType_shouldReturnAddressDtoListOfSellingAddresses() {
        testGetAllAddressesByUserIdAndAddressType(AddressType.SELLING);
    }

    @Test
    public void testGetAllAddressesByUserIdAndAddressType_shouldReturnAddressDtoListOfBuyingAddresses() {
        testGetAllAddressesByUserIdAndAddressType(AddressType.BUYING);
    }

    @Test
    public void testGetAllAddressesByUserIdAndAddressType_whenUserDoesNotExist_shouldThrowUserNotFoundException() {
        User user = generateUser(true);
        when(addressRepository.findAllByUserAndAddressType(user, AddressType.SELLING)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> addressService.getAllAddressesByUserIdAndAddressType(userId, AddressType.SELLING));
        verify(userRepository).findById(userId);
        verifyNoInteractions(addressRepository);
        verifyNoInteractions(addressMapper);
    }

    @Test
    public void testGetAllAddressesByUserIdAndAddressType_whenAddressDoesNotExist_shouldThrowAddressNotFoundException()  {
        User user = generateUser(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(AddressNotFoundException.class, () -> addressService.getAllAddressesByUserIdAndAddressType(userId, AddressType.SELLING));
        verify(userRepository).findById(userId);
        verify(addressRepository).findAllByUserAndAddressType(user, AddressType.SELLING);
        verifyNoInteractions(addressMapper);
    }

    @Test
    public void testInsertAddress_shouldReturnAddressDto() {
        User user = generateUser(true);
        InsertAddressRequest insertAddressRequest = generateInsertAddressRequestWithAddressType(AddressType.BUYING);
        Address insertedAddress = generateAddress(AddressType.BUYING, user);
        AddressDto addressDto = generateAddressDto(AddressType.BUYING);

        when(addressRepository.save(any())).thenReturn(insertedAddress);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressMapper.convert(insertedAddress)).thenReturn(addressDto);

        AddressDto result = addressService.insertAddress(userId, insertAddressRequest);

        assertEquals(addressDto, result);
        verify(addressRepository).save(any());
        verify(addressMapper).convert(insertedAddress);
    }

    @Test
    public void testInsertAddress_whenUserDoesNotExist_shouldThrowUserNotFoundException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        InsertAddressRequest insertAddressRequest = generateInsertAddressRequestWithAddressType(AddressType.BUYING);

        assertThrows(UserNotFoundException.class, () -> addressService.insertAddress(userId, insertAddressRequest));
        verifyNoInteractions(addressRepository);
        verifyNoInteractions(addressMapper);
    }

    @Test
    public void testInsertAddress_whenUserIsNotActive_shouldThrowUserActivityException() {
        User inActiveUser = generateUser(false);
        InsertAddressRequest insertAddressRequest = generateInsertAddressRequestWithAddressType(AddressType.BUYING);
        when(userRepository.findById(userId)).thenReturn(Optional.of(inActiveUser));

        assertThrows(UserActivityException.class, () -> addressService.insertAddress(userId, insertAddressRequest));
        verifyNoInteractions(addressRepository);
        verifyNoInteractions(addressMapper);
    }

    @Test
    public void testUpdateAddress_shouldReturnAddressDto() {
        User user = generateUser(true);
        UpdateAddressRequest updateAddressRequest = generateUpdateAddressRequestWithAddressType(AddressType.BUYING);
        Address addressBeforeUpdate = generateAddress(AddressType.BUYING, user);
        Address updatedAddress = generateAddress(AddressType.SELLING, user);
        AddressDto addressDto = generateAddressDto(AddressType.SELLING);

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(addressBeforeUpdate));
        when(addressRepository.save(any())).thenReturn(updatedAddress);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressMapper.convert(updatedAddress)).thenReturn(addressDto);

        AddressDto result = addressService.updateAddress(addressId, updateAddressRequest);

        assertEquals(addressDto, result);
        verify(addressRepository).save(any());
        verify(addressMapper).convert(updatedAddress);
    }

    @Test
    public void testUpdateAddress_whenAddressDoesNotExist_shouldThrowAddressNotFoundException() {
        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());
        UpdateAddressRequest updateAddressRequest = generateUpdateAddressRequestWithAddressType(AddressType.BUYING);

        assertThrows(AddressNotFoundException.class, () -> addressService.updateAddress(addressId, updateAddressRequest));
        verifyNoInteractions(userRepository);
        verifyNoInteractions(addressMapper);
    }

    @Test
    public void testUpdateAddress_whenUserIsNotActive_shouldThrowUserActivityException() {
        User user = generateUser(false);
        Address address = generateAddress(AddressType.SELLING, user);
            UpdateAddressRequest updateAddressRequest = generateUpdateAddressRequestWithAddressType(AddressType.SELLING);

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(UserActivityException.class, () -> addressService.updateAddress(addressId, updateAddressRequest));
        verifyNoInteractions(addressMapper);
    }

    @Test
    public void testDeleteAddress_whenAddressIdExist_shouldDeleteAddress() {
        User user = generateUser(true);
        Address address = generateAddress(AddressType.BUYING, user);

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));

        addressService.deleteAddress(addressId);

        verify(addressRepository).findById(addressId);
        verify(addressRepository).deleteById(addressId);
    }

    @Test
    public void testDeleteAddress_whenAddressDoesNotExist_shouldThrowAddressNotFoundException() {
        User user = generateUser(true);
        Address address = generateAddress(AddressType.BUYING, user);

        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> addressService.deleteAddress(addressId));
    }


    public void testGetAllAddressesByUserIdAndAddressType(AddressType addressType) {
        List<Address> addressListForAddressType = generateAddressList(addressType, 5);
        List<AddressDto> addressForAddressTypeDtoList = generateAddressDtoList(addressListForAddressType);
        User user = generateUser(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.findAllByUserAndAddressType(user, addressType)).thenReturn(Optional.of(addressListForAddressType));
        when(addressMapper.convert(addressListForAddressType)).thenReturn(addressForAddressTypeDtoList);

        List<AddressDto> result = addressService.getAllAddressesByUserIdAndAddressType(user.getUserId(), addressType);

        assertEquals(addressForAddressTypeDtoList, result);
        verify(addressRepository).findAllByUserAndAddressType(user, addressType);
        verify(userRepository).findById(userId);
        verify(addressMapper).convert(addressListForAddressType);
    }
}
