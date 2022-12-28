package com.caoguzelmas.secondhandshop.product.mapper;

import com.caoguzelmas.secondhandshop.product.dto.AdvertDto;
import com.caoguzelmas.secondhandshop.product.model.Advert;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdvertMapper {

    AdvertMapper INSTANCE = Mappers.getMapper(AdvertMapper.class);

    AdvertDto advertToAdvertDto(Advert advert);
}
