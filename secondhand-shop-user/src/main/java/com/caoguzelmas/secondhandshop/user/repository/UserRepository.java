package com.caoguzelmas.secondhandshop.user.repository;

import com.caoguzelmas.secondhandshop.user.model.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInformation, Long> {

    Optional<UserInformation> findByEmail(String email);
}
