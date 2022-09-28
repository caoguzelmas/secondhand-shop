package com.caoguzelmas.secondhandshop.user.model;

import com.caoguzelmas.secondhandshop.user.model.enums.AddressType;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    @Enumerated(EnumType.ORDINAL)
    private AddressType addressType;
    private String address;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="userId", nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return Objects.equals(addressId, address1.addressId) && addressType == address1.addressType && Objects.equals(address, address1.address) && Objects.equals(city, address1.city) && Objects.equals(region, address1.region) && Objects.equals(postalCode, address1.postalCode) && Objects.equals(country, address1.country) && Objects.equals(user, address1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, addressType, address, city, region, postalCode, country, user);
    }
}
