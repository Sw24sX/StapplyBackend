package com.stapply.backend.stapply.service.user;

import com.stapply.backend.stapply.models.Role;
import com.stapply.backend.stapply.models.User;
import com.stapply.backend.stapply.repository.RoleRepository;
import com.stapply.backend.stapply.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException("User not found");

        return user;
    }

    @Override
    public User findUserById(Long id) {
        var result = userRepository.findById(id); //todo may be bug
        return result.orElse(new User()); //todo not right may be
    }

    @Override
    public List<User> allUsers() {
        var result = userRepository.findAll();
        return result;
    }

    @Override
    public boolean saveUser(User user) {
        var userInDb = userRepository.findByUsername(user.getUsername());
        if(userInDb == null)
            return false;

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<User> userGtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
}
