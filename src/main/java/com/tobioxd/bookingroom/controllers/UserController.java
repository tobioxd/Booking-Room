package com.tobioxd.bookingroom.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.tobioxd.bookingroom.dtos.RefreshTokenDTO;
import com.tobioxd.bookingroom.dtos.UpdatePasswordDTO;
import com.tobioxd.bookingroom.dtos.UserDTO;
import com.tobioxd.bookingroom.dtos.UserLoginDTO;
import com.tobioxd.bookingroom.entities.Token;
import com.tobioxd.bookingroom.entities.User;
import com.tobioxd.bookingroom.responses.LoginResponse;
import com.tobioxd.bookingroom.responses.RegisterResponse;
import com.tobioxd.bookingroom.responses.UserListResponse;
import com.tobioxd.bookingroom.responses.UserResponse;
import com.tobioxd.bookingroom.services.impl.TokenService;
import com.tobioxd.bookingroom.services.impl.UserService;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/register")
    @Operation(summary = "Register account")
    public ResponseEntity<RegisterResponse> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result) {
        RegisterResponse registerResponse = new RegisterResponse();

        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();

            registerResponse.setMessage(errorMessages.toString());
            return ResponseEntity.badRequest().body(registerResponse);
        }

        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
            registerResponse.setMessage("Password do not match !");
            return ResponseEntity.badRequest().body(registerResponse);
        }

        try {
            User user = userService.createUser(userDTO);
            registerResponse.setMessage("Register successfully !");
            registerResponse.setUser(user);
            return ResponseEntity.ok(registerResponse);
        } catch (Exception e) {
            registerResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(registerResponse);
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            String token = userService.loginUser(
                    userLoginDTO.getPhoneNumber(),
                    userLoginDTO.getPassword());

            User userDetail = userService.getUserDetailsFromToken(token);
            Token jwtToken = tokenService.addToken(userDetail, token);

            return ResponseEntity.ok(LoginResponse.builder()
                    .message("Login successfully !")
                    .token(jwtToken.getToken())
                    .tokenType(jwtToken.getTokenType())
                    .refreshToken(jwtToken.getRefreshToken())
                    .username(userDetail.getUsername())
                    .roles(userDetail.getAuthorities().stream().map(item -> item.getAuthority()).toList())
                    .id(userDetail.getId())
                    .build());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message(e.getMessage())
                            .build());
        }
    }

    @PostMapping("/refreshToken")
    @Operation(summary = "Refresh Token")
    public ResponseEntity<LoginResponse> refreshToken(
            @Valid @RequestBody RefreshTokenDTO refreshTokenDTO) {
        try {
            User userDetail = userService.getUserDetailsFromRefreshToken(refreshTokenDTO.getRefreshToken());
            Token jwtToken = tokenService.refreshToken(refreshTokenDTO.getRefreshToken(), userDetail);
            return ResponseEntity.ok(LoginResponse.builder()
                    .message("Refresh token successfully")
                    .token(jwtToken.getToken())
                    .tokenType(jwtToken.getTokenType())
                    .refreshToken(jwtToken.getRefreshToken())
                    .username(userDetail.getUsername())
                    .roles(userDetail.getAuthorities().stream().map(item -> item.getAuthority()).toList())
                    .id(userDetail.getId())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message(e.getMessage())
                            .build());
        }
    }

    @PutMapping("/updateMe")
    @Operation(summary = "Update user information")
    public ResponseEntity<RegisterResponse> updateMe(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody UpdatePasswordDTO updatePasswordDTO,
            BindingResult result) {
        RegisterResponse registerResponse = new RegisterResponse();

        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();

            registerResponse.setMessage(errorMessages.toString());
            return ResponseEntity.badRequest().body(registerResponse);
        }

        try {
            User user = userService.updateMe(updatePasswordDTO, token);
            registerResponse.setMessage("Update successfully !");
            registerResponse.setUser(user);
            return ResponseEntity.ok(registerResponse);
        } catch (Exception e) {
            registerResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(registerResponse);
        }
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users")
    public ResponseEntity<UserListResponse> getAllUser(
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            PageRequest pageRequest = PageRequest.of(
                    page,limit,
                    Sort.by("id").descending()
            );

            Page<UserResponse> users = userService.findAll(keyword, pageRequest).map(UserResponse::fromUser);

            int totalPages = users.getTotalPages();
            List<UserResponse> userResponses = users.getContent();

            return ResponseEntity.ok(UserListResponse.builder()
                    .users(userResponses)
                    .totalPages(totalPages)
                    .build()); 
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);  
        }
    }

    @PutMapping("/blockOrEnable/{userId}/{active}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Block or enable user")
    public ResponseEntity<?> blockOrEnable(
            @PathVariable String userId,
            @PathVariable boolean active) {
        try {
            userService.blockOrEnable(userId, active);
            String message = active ? "User is enabled !" : "User is blocked !";
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createreceptionist")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create Receptionist account")
    public ResponseEntity<RegisterResponse> createReceptionist(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result) {
        RegisterResponse registerResponse = new RegisterResponse();

        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();

            registerResponse.setMessage(errorMessages.toString());
            return ResponseEntity.badRequest().body(registerResponse);
        }

        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
            registerResponse.setMessage("Password do not match !");
            return ResponseEntity.badRequest().body(registerResponse);
        }

        try {
            User user = userService.creatReceptionist(userDTO);
            registerResponse.setMessage("Create receptionist account successfully !");
            registerResponse.setUser(user);
            return ResponseEntity.ok(registerResponse);
        } catch (Exception e) {
            registerResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(registerResponse);
        }
    }

}