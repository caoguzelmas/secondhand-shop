package com.caoguzelmas.secondhandshop.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseDto {
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
