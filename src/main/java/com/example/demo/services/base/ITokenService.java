package com.example.demo.services.base;

import java.util.List;

import com.example.demo.entities.Token;
import com.example.demo.entities.User;

public interface ITokenService {

    List<Token> findByUser(User user);

    Token addToken(User user, String token);

    Token refreshToken(String refreshToken, User user) throws Exception;

    void deleteToken(Token token);

}
