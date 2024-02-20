package com.scaler.userservice.services;

import com.scaler.userservice.exceptions.InvalidPasswordException;
import com.scaler.userservice.exceptions.TokenNotExistsOrAlreadyExpiredException;
import com.scaler.userservice.exceptions.UserNotFoundException;
import com.scaler.userservice.models.Token;
import com.scaler.userservice.models.User;
import com.scaler.userservice.repositories.TokenRepository;
import com.scaler.userservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


@Service
public class UserService {

    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, TokenRepository tokenRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User signup(String name, String email, String password){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashPassword(bCryptPasswordEncoder.encode(password));

        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public Token login(String email, String password) throws UserNotFoundException, InvalidPasswordException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User does not exist");
        }
        User savedUser = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, savedUser.getHashPassword())){
            throw new InvalidPasswordException("password is incorrect");
        }

        Token token = new Token();
        token.setUser(savedUser);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 30);
        Date currentDatePlus30 = calendar.getTime();
        token.setExpiryAt(currentDatePlus30);

        token.setValue(RandomStringUtils.randomAlphanumeric(120));

        Token savedToken = tokenRepository.save(token);
        return savedToken;
    }

    public boolean logout(String tokenValue) throws TokenNotExistsOrAlreadyExpiredException {
        Optional<Token> tokenOptional = tokenRepository.findTokenByValueAndDeleted(tokenValue, false);
        if(tokenOptional.isEmpty()){
            throw new TokenNotExistsOrAlreadyExpiredException("token is already delete, or not found");
        }
        Token token = tokenOptional.get();
        token.setDeleted(true);
        Token softDeletedToken = tokenRepository.save(token);
        return true;
    }
}
