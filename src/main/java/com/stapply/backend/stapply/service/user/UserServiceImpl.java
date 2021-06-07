package com.stapply.backend.stapply.service.user;

import com.stapply.backend.stapply.models.Role;
import com.stapply.backend.stapply.models.Status;
import com.stapply.backend.stapply.models.User;
import com.stapply.backend.stapply.repository.RoleRepository;
import com.stapply.backend.stapply.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        var roleUser = roleRepository.findByName("ROLE_USER");
        var userRoles = new ArrayList<Role>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());

        var userRegistered = userRepository.save(user);
        return userRegistered;
    }

    @Override
    public List<User> getAll() {
        var result = userRepository.findAll();
        return result;
    }

    @Override
    public User findByUserName(String username) {
        var user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public User findById(Long id) {
        var user = userRepository.findById(id).orElse(null);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
