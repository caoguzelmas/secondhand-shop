package com.caoguzelmas.secondhandshop.product.model;

import com.caoguzelmas.secondhandshop.user.model.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private Double price;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "advertId")
    private Advert advert;
    private String productName;
    private String productDescription;

}
