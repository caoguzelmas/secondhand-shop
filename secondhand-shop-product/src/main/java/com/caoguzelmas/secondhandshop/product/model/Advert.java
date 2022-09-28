package com.caoguzelmas.secondhandshop.product.model;

import com.caoguzelmas.secondhandshop.user.model.BaseEntity;
import com.caoguzelmas.secondhandshop.user.model.User;
import lombok.*;

import javax.persistence.*;

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
    private String header;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
    @OneToOne(mappedBy = "advert")
    private Product product;




}
