package com.stapply.backend.stapply.controller.user;

import com.stapply.backend.stapply.components.AuthenticationFacade;
import com.stapply.backend.stapply.controller.user.webmodel.UserWebModel;
import com.stapply.backend.stapply.models.User;
import com.stapply.backend.stapply.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public UserController(UserService userService, AuthenticationFacade authenticationFacade) {
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name="id") Long id) {
        var userFromDB = userService.findById(id);
        if(userFromDB == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var user = UserWebModel.fromUser(userFromDB);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser() {
        var username = authenticationFacade.getAuthentication().getName();
        var user = userService.findByUserName(username);
        return new ResponseEntity<>(UserWebModel.fromUser(user), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        var users = userService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
