package com.chronosx.cx_shop.services.implement;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chronosx.cx_shop.components.JwtTokenUtils;
import com.chronosx.cx_shop.components.LocalizationUtils;
import com.chronosx.cx_shop.dtos.UserDto;
import com.chronosx.cx_shop.exceptions.DataNotFoundException;
import com.chronosx.cx_shop.exceptions.PermissionDenyException;
import com.chronosx.cx_shop.models.Role;
import com.chronosx.cx_shop.models.User;
import com.chronosx.cx_shop.repositories.RoleRepository;
import com.chronosx.cx_shop.repositories.UserRepository;
import com.chronosx.cx_shop.services.UserService;
import com.chronosx.cx_shop.utils.MessageKeys;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;
    JwtTokenUtils jwtTokenUtils;
    AuthenticationManager authenticationManager;
    LocalizationUtils localizationUtils;

    @Override
    public User createUser(UserDto userDto) throws Exception {
        String phoneNumber = userDto.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber))
            throw new DataIntegrityViolationException("phone number already exists");

        Role role = roleRepository
                .findById(userDto.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("role not found"));

        if (role.getName().toUpperCase().equals(Role.ADMIN)) {
            throw new PermissionDenyException("cannot create admin account");
        }
        User newUser = User.builder()
                .fullName(userDto.getFullName())
                .phoneNumber(userDto.getPhoneNumber())
                .password(userDto.getPassword())
                .address(userDto.getAddress())
                .dateOfBirthDay(userDto.getDateOfBirth())
                .facebookAccountId(userDto.getFacebookAccountId())
                .googleAccountId(userDto.getGoogleAccountId())
                .build();

        newUser.setRole(role);
        if (userDto.getFacebookAccountId() == 0 && userDto.getGoogleAccountId() == 0) {
            String password = userDto.getPassword();
            String encodePassword = passwordEncoder.encode(password);
            newUser.setPassword(encodePassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password, Long roleId) throws Exception {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        if (user.isEmpty()) {
            throw new DataNotFoundException("invalid input");
        }

        User existingUser = user.get();
        if (existingUser.getFacebookAccountId() == 0 && existingUser.getGoogleAccountId() == 0) {
            if (!passwordEncoder.matches(password, existingUser.getPassword())) {
                throw new BadCredentialsException("invalid input");
            }
        }

        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isEmpty() || !roleId.equals(existingUser.getRole().getId())) {
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.ROLE_NOT_EXIST.getKey()));
        }

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(phoneNumber, password, existingUser.getAuthorities());

        // authenticate with Java Spring Security
        authenticationManager.authenticate(authToken);
        return jwtTokenUtils.generateToken(existingUser);
    }
}
