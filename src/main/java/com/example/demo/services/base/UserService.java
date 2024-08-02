package com.example.demo.services.base;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.components.JwtTokenUtil;
import com.example.demo.dtos.UpdatePasswordDTO;
import com.example.demo.dtos.UserDTO;
import com.example.demo.entities.Token;
import com.example.demo.entities.User;
import com.example.demo.exceptions.DataNotFoundException;
import com.example.demo.repositories.TokenRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.impl.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    @Transactional
    public User createUser(UserDTO userDTO) throws Exception {
        //register user
        String phoneNumber = userDTO.getPhoneNumber();
        //Check if phonenumber exists already
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number exists already !");
        }
        //convert from userDTO => user
        User newUser = User.builder()
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .name(userDTO.getName())
                .isActive(true)
                .createdAt(new Date())
                .role("user")
                .build();
                
        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
            
        return userRepository.save(newUser);
    }

    @Override
    public String loginUser(String phoneNumber, String password) throws Exception {

        Optional<User> user= userRepository.findByPhoneNumber(phoneNumber);
        
        if(user.isEmpty()){
            throw new DataNotFoundException("Invalid phonenuber/password !");
        }

        List<Token> tokens = tokenService.findByUser(user.get());
        tokens.sort((t1, t2) -> t2.getExpirationDate().compareTo(t1.getExpirationDate()));

        if(tokens.size() >= 3){
            tokenService.deleteToken(tokens.get(0));
        }

        System.out.println(tokens.size());
        System.out.println(tokens.get(0));

        User existinguser = user.get();

        if(!passwordEncoder.matches(password, existinguser.getPassword())){
            throw new BadCredentialsException("Invalid phonenuber/password !");
        }

        User existingUser = user.orElseThrow(() -> new DataNotFoundException("Invalid phonenuber/password !"));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phoneNumber, password, existingUser.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existinguser);

    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if(jwtTokenUtil.isTokenExpired(token)) {
            throw new Exception("Token is expired");
        }
        String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);

        if (user.isPresent()) {

            if(user.get().isActive() == false) {
                throw new Exception("User is blocked !");
            }
            
            return user.get();
        } else {
            throw new Exception("User not found");
        }
    }

    @Override
    public User getUserDetailsFromRefreshToken(String refreshToken) throws Exception {
        Token existingToken = tokenRepository.findByRefreshToken(refreshToken);
        return getUserDetailsFromToken(existingToken.getToken());
    }

    @Override
    public Page<User> findAll(String keyword, Pageable pageable) throws Exception {
        return userRepository.findAll(keyword, pageable);
    }

    @Override
    @Transactional
    public void blockOrEnable(String userId, boolean active) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found !"));
        existingUser.setActive(active);
        userRepository.save(existingUser);              
    }

    @Override
    public User creatReceptionist(UserDTO userDTO) throws Exception {
        //register user
        String phoneNumber = userDTO.getPhoneNumber();
        //Check if phonenumber exists already
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number exists already !");
        }
        //convert from userDTO => user
        User newUser = User.builder()
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .name(userDTO.getName())
                .isActive(true)
                .createdAt(new Date())
                .role("receptionist")
                .build();
                
        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
            
        return userRepository.save(newUser);
    }

    @Override
    public User updateMe(UpdatePasswordDTO updatePasswordDTO, String token) throws Exception {
        String extractedToken = token.substring(7); // Clear "Bearer" from token
        User user = getUserDetailsFromToken(extractedToken);

        if(!passwordEncoder.matches(updatePasswordDTO.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Old Password is incorrect !");
        }

        String password = updatePasswordDTO.getNewPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
            
        return userRepository.save(user);

    }

}
