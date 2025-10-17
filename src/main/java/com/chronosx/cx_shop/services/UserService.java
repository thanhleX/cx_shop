package com.chronosx.cx_shop.services;

import com.chronosx.cx_shop.dtos.UserDto;
import com.chronosx.cx_shop.exceptions.DataNotFoundException;
import com.chronosx.cx_shop.models.User;

public interface UserService {
    User createUser(UserDto userDto) throws DataNotFoundException;

    String login(String phoneNumber, String password);
}
