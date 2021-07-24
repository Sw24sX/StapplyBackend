package com.stapply.backend.stapply.dto;

import com.stapply.backend.stapply.domain.UserInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class UserInfoSet {
    private String email;

    public UserInfo toUserInfo() {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(this, userInfo, "id", "user");
        return userInfo;
    }
}
