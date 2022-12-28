package com.caoguzelmas.secondhandshop.product.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Advert extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long advertId;
    private String title;
    private String description;
    private BigDecimal price;
    @Transient
    private Set<String> hashtags = new HashSet<>();
    private Long userId;
    private Long addressId;
}
