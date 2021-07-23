package com.stapply.backend.stapply.controller.login;

import com.stapply.backend.stapply.controller.login.webmodel.TokenWebModel;
import com.stapply.backend.stapply.controller.main.webmodel.AuthenticationRequest;
import com.stapply.backend.stapply.controller.login.webmodel.CreateUserWebModel;
import com.stapply.backend.stapply.domain.User;
import com.stapply.backend.stapply.security.jwt.JwtTokenProvider;
import com.stapply.backend.stapply.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public LoginController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenWebModel> login(@RequestBody AuthenticationRequest request) {
        var username = request.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));
        var user = userService.findByUserName(username);

        if(user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        var token = jwtTokenProvider.createToken(username, user.getRoles());
        var res = new TokenWebModel(username, token);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserWebModel createUserWebModel) {
        var user = new User();
        user.setUsername(createUserWebModel.getUsername());
        user.setEmail(createUserWebModel.getEmail());
        user.setFirstName(createUserWebModel.getFirstName());
        user.setLastName(createUserWebModel.getLastName());
        user.setPassword(createUserWebModel.getPassword());
        userService.register(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
