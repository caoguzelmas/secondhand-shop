package com.caoguzelmas.secondhandshop.product.dto;

import java.util.HashSet;
import java.util.Set;

public class CreateAdvertRequest {
    private String title;
    private String description;
    private Double price;
    private Set<String> hashtags;
    private Long userId;
    private Long addressId;
}
