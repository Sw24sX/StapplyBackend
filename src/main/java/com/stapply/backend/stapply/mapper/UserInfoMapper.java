package com.stapply.backend.stapply.mapper;

import com.stapply.backend.stapply.domain.UserInfo;
import com.stapply.backend.stapply.dto.UserInfoDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserInfoMapper {
    UserInfoDto userInfoToUserInfoDto(UserInfo userInfo);
    UserInfo userInfoDtoToUserInfo(UserInfoDto userInfoDto);
}
