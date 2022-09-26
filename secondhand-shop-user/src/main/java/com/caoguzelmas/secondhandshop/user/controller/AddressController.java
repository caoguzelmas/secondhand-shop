package com.caoguzelmas.secondhandshop.user.controller;

import com.caoguzelmas.secondhandshop.user.dto.AddressDto;
import com.caoguzelmas.secondhandshop.user.dto.InsertAddressRequest;
import com.caoguzelmas.secondhandshop.user.dto.UpdateAddressRequest;
import com.caoguzelmas.secondhandshop.user.model.enums.AddressType;
import com.caoguzelmas.secondhandshop.user.service.IAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/address")
public class AddressController {
    private final IAddressService addressService;

    public AddressController(IAddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<AddressDto>> getAllAddressesByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getAllAddressesByUserId(id));
    }

    @GetMapping("/{id}/buying")
    public ResponseEntity<List<AddressDto>> getAllBuyingAddressesByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getAllAddressesByUserIdAndAddressType(id, AddressType.BUYING));
    }


    @GetMapping("/{id}/selling")
    public ResponseEntity<List<AddressDto>> getAllSellingAddressesByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getAllAddressesByUserIdAndAddressType(id,AddressType.SELLING));
    }

    @PostMapping("/{id}")
    public ResponseEntity<AddressDto> insertAddress(@PathVariable Long id,
                                                    @RequestBody InsertAddressRequest request) {
        return ResponseEntity.ok(addressService.insertAddress(id ,request));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable Long addressId,
                                                    @RequestBody UpdateAddressRequest request) {
        return ResponseEntity.ok(addressService.updateAddress(addressId, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok().build();
    }
}
