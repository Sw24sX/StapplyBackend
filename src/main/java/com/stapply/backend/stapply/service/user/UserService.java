package com.stapply.backend.stapply.service.user;

import com.stapply.backend.stapply.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findUserById(Long id);
    List<User> allUsers();
    boolean saveUser(User user);
    boolean deleteUser(Long id);
    List<User> userGtList(Long idMin);
}
