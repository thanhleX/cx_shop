package com.chronosx.cx_shop.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chronosx.cx_shop.components.LocalizationUtils;
import com.chronosx.cx_shop.dtos.UserDto;
import com.chronosx.cx_shop.dtos.UserLoginDto;
import com.chronosx.cx_shop.models.User;
import com.chronosx.cx_shop.responses.LoginResponse;
import com.chronosx.cx_shop.responses.RegisterResponse;
import com.chronosx.cx_shop.services.UserService;
import com.chronosx.cx_shop.utils.MessageKeys;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    LocalizationUtils localizationUtils;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> createUser(@Valid @RequestBody UserDto userDto, BindingResult result) {
        RegisterResponse registerResponse = new RegisterResponse();
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();

            registerResponse.setMessage(errorMessages.toString());
            return ResponseEntity.badRequest().body(registerResponse);
        }

        if (!userDto.getPassword().equals(userDto.getRetypePassword())) {
            registerResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH.getKey()));
            return ResponseEntity.badRequest().body(registerResponse);
        }

        try {
            User user = userService.createUser(userDto);
            registerResponse.setMessage(
                    localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY.getKey()));
            registerResponse.setUser(user);
            return ResponseEntity.ok(registerResponse);
        } catch (Exception e) {
            registerResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(registerResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody UserLoginDto userLoginDto) {
        try {
            String token = userService.login(
                    userLoginDto.getPhoneNumber(), userLoginDto.getPassword(), userLoginDto.getRoleId());
            return ResponseEntity.ok(LoginResponse.builder()
                    .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY.getKey()))
                    .token(token)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(LoginResponse.builder()
                            .message(localizationUtils.getLocalizedMessage(
                                    MessageKeys.LOGIN_FAILED.getKey(), e.getMessage()))
                            .build());
        }
    }
}
