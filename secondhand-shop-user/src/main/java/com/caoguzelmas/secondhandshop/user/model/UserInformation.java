package com.caoguzelmas.secondhandshop.user.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class UserInformation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private Boolean isActive;

    public UserInformation(Long id, String email, String firstName, String middleName, String lastName, Boolean isActive) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.isActive = isActive;
    }

    public UserInformation(String email, String firstName, String middleName, String lastName, Boolean isActive) {
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.isActive = isActive;
    }

    public UserInformation(String email, String firstName, String middleName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public UserInformation() {
    }

    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public String getLastName() {
        return lastName;
    }
    public Boolean getActive() {
        return isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInformation that = (UserInformation) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(firstName, that.firstName) && Objects.equals(middleName, that.middleName) && Objects.equals(lastName, that.lastName) && Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, middleName, lastName, isActive);
    }
}
