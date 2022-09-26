package com.caoguzelmas.secondhandshop.user.repository;

import com.caoguzelmas.secondhandshop.user.model.Address;
import com.caoguzelmas.secondhandshop.user.model.User;
import com.caoguzelmas.secondhandshop.user.model.enums.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<List<Address>> findAllByUser(User user);
    // Optional<List<Address>> findAllByUserId(User user);
    Optional<List<Address>> findAllByUserAndAddressType(User user, AddressType addressType);
}
