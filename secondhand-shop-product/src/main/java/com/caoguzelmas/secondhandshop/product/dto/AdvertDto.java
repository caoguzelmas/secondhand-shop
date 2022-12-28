package com.caoguzelmas.secondhandshop.product.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
public class AdvertDto extends BaseDto {
    private String title;
    private String description;
    private BigDecimal price;
    private Set<String> hashtags;
    private Long userId;
    private Long addressId;
}
