package com.stapply.backend.stapply.controller;

import com.stapply.backend.stapply.domain.User;
import com.stapply.backend.stapply.domain.UserInfo;
import com.stapply.backend.stapply.dto.UserInfoSet;
import com.stapply.backend.stapply.dto.UserSetDto;
import com.stapply.backend.stapply.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable(name = "id") Long id) {
        return userService.getById(id);
    }

    @PostMapping
    public User createUser(@RequestBody UserSetDto user) {
        return userService.createUser(user.toUser());
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable(name = "id") Long id, @RequestBody UserSetDto user) {
        return userService.updateUser(id, user.toUser());
    }

    @PutMapping("{user_id}/info")
    public UserInfo updateUserInfo(@PathVariable(name = "user_id") Long userId, @RequestBody UserInfoSet userInfo) {
        return userService.updateInfo(userId, userInfo.toUserInfo());
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
    }
}
