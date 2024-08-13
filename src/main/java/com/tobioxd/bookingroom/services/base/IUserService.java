package com.tobioxd.bookingroom.services.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import com.tobioxd.bookingroom.dtos.RefreshTokenDTO;
import com.tobioxd.bookingroom.dtos.UpdatePasswordDTO;
import com.tobioxd.bookingroom.dtos.UserDTO;
import com.tobioxd.bookingroom.dtos.UserLoginDTO;
import com.tobioxd.bookingroom.entities.User;
import com.tobioxd.bookingroom.responses.LoginResponse;
import com.tobioxd.bookingroom.responses.RegisterResponse;
import com.tobioxd.bookingroom.responses.UserListResponse;

public interface IUserService {

    RegisterResponse createUser(UserDTO userDTO, BindingResult result) throws Exception;

    LoginResponse loginUser(UserLoginDTO userLoginDTO) throws Exception;

    LoginResponse refreshToken(RefreshTokenDTO refreshTokenDTO) throws Exception;

    User getUserDetailsFromToken(String token) throws Exception;

    User getUserDetailsFromRefreshToken(String refreshToken) throws Exception;

    User updatePassword(UpdatePasswordDTO updatePasswordDTO, String token) throws Exception;

    Page<User> findAll(String keyword, Pageable pageable) throws Exception;

    public void blockOrEnable(String userId, boolean active) throws Exception;

    UserListResponse getAllUser(String keyword, int page, int limit) throws Exception;

    String blockOrEnableUser(String userId, boolean active) throws Exception;

    RegisterResponse updateMe(String token, UpdatePasswordDTO updatePasswordDTO, BindingResult result) throws Exception;

    RegisterResponse createReceptionist(UserDTO userDTO, BindingResult result) throws Exception;

}
