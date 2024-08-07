package com.tobioxd.bookingroom.services.base;

import java.util.List;

import com.tobioxd.bookingroom.entities.Token;
import com.tobioxd.bookingroom.entities.User;

public interface ITokenService {

    List<Token> findByUser(User user);

    Token addToken(User user, String token);

    Token refreshToken(String refreshToken, User user) throws Exception;

    void deleteToken(Token token);

}
