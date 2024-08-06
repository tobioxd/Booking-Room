package com.example.demo.services.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dtos.UpdatePasswordDTO;
import com.example.demo.dtos.UserDTO;
import com.example.demo.entities.User;

public interface IUserService {

    User createUser(UserDTO userDTO) throws Exception;

    String loginUser(String phoneNumber, String password) throws Exception;

    User getUserDetailsFromToken(String token) throws Exception;

    User getUserDetailsFromRefreshToken(String refreshToken) throws Exception;

    User updateMe(UpdatePasswordDTO updatePasswordDTO, String token) throws Exception;

    Page<User> findAll(String keyword, Pageable pageable) throws Exception;

    public void blockOrEnable(String userId, boolean active) throws Exception;

    User creatReceptionist(UserDTO userDTO) throws Exception;

}
