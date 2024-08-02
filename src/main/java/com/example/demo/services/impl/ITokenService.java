package com.example.demo.services.impl;

import com.example.demo.entities.Token;
import com.example.demo.entities.User;

public interface ITokenService {

    Token addToken(User user, String token);

    Token refreshToken(String refreshToken, User user) throws Exception;

}
