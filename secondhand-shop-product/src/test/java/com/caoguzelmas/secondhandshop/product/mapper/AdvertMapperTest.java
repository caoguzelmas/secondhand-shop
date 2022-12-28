package com.caoguzelmas.secondhandshop.product.mapper;

import com.caoguzelmas.secondhandshop.product.dto.AdvertDto;
import com.caoguzelmas.secondhandshop.product.model.Advert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AdvertMapperTest {

    @Test
    public void testConverter() {
        Set<String> hashtags = new HashSet<>();
        hashtags.add("tag1");
        hashtags.add("tag2");
        Advert advert = new Advert(1L, "title", "descr",new BigDecimal("1.1"), hashtags, 1L, 1L);
        advert.setCreatedDate(LocalDateTime.now());
        advert.setUpdatedDate(LocalDateTime.now());
        AdvertDto advertDto = AdvertMapper.INSTANCE.advertToAdvertDto(advert);

        assertEquals(advertDto.getTitle(), "title");
        assertEquals(advertDto.getDescription(), "descr");
        assertEquals(advertDto.getPrice(), new BigDecimal("1.1"));
        assertEquals(advertDto.getHashtags(), hashtags);
        assertEquals(advertDto.getUserId(), 1L);
        assertEquals(advertDto.getAddressId(), 1L);

    }
}
