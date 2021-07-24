package com.stapply.backend.stapply.service;

import com.stapply.backend.stapply.domain.User;
import com.stapply.backend.stapply.domain.UserInfo;
import com.stapply.backend.stapply.repository.UserInfoRepository;
import com.stapply.backend.stapply.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserInfoRepository userInfoRepository) {
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        user = userRepository.save(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        userInfoRepository.save(userInfo);
        user.setUserInfo(userInfo);
        return user;
    }

    public User updateUser(Long id, User user) {
        User userFromDb = getById(id);
        BeanUtils.copyProperties(user, userFromDb, "id", "userInfo");
        return userRepository.save(userFromDb);
    }

    public UserInfo updateInfo(Long userId, UserInfo info) {
        UserInfo userInfo = getById(userId).getUserInfo();
        BeanUtils.copyProperties(info, userInfo, "id", "user");
        return userInfoRepository.save(userInfo);
    }

    public void deleteUser(Long id) {
        User user = getById(id);
        //TODO: delete this with cascade
        userInfoRepository.delete(user.getUserInfo());
        userRepository.delete(user);
    }
}
