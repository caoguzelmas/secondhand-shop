package com.caoguzelmas.secondhandshop.user.model;

import java.time.LocalDate;

public class BaseEntity {
    private LocalDate createdDate;
    private LocalDate updatedDate;

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }
}
