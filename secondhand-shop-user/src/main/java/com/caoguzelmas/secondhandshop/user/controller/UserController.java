package com.caoguzelmas.secondhandshop.user.controller;

import com.caoguzelmas.secondhandshop.user.dto.CreateUserRequest;
import com.caoguzelmas.secondhandshop.user.dto.UpdateUserRequest;
import com.caoguzelmas.secondhandshop.user.dto.UserDto;
import com.caoguzelmas.secondhandshop.user.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserRequest userRequest)  {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("email") String email,
                                              @RequestBody UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userService.updateUser(email, updateUserRequest));
    }

    @PatchMapping("/{id}/deActivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable("id") Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateUser(@PathVariable("id") Long id) {
        userService.activateUser(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
