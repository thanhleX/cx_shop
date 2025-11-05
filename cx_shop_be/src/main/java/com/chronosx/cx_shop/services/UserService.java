package com.chronosx.cx_shop.services;

import com.chronosx.cx_shop.dtos.UpdateUserDto;
import com.chronosx.cx_shop.dtos.UserDto;
import com.chronosx.cx_shop.models.User;

public interface UserService {
    User createUser(UserDto userDto) throws Exception;

    User updateUser(Long userId, UpdateUserDto userDto) throws Exception;

    String login(String phoneNumber, String password, Long roleId) throws Exception;

    User getUserDetailsFromToken(String token) throws Exception;
}
