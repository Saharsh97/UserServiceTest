package com.scaler.userservice.controllers;

import com.scaler.userservice.dtos.LoginRequestDTO;
import com.scaler.userservice.dtos.LoginResponseDTO;
import com.scaler.userservice.dtos.LogoutRequestDTO;
import com.scaler.userservice.dtos.SignupRequestDTO;
import com.scaler.userservice.exceptions.InvalidPasswordException;
import com.scaler.userservice.exceptions.TokenNotExistsOrAlreadyExpiredException;
import com.scaler.userservice.exceptions.UserNotFoundException;
import com.scaler.userservice.models.User;
import com.scaler.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) throws UserNotFoundException, InvalidPasswordException {
        // check if email and pwd in DB
        // if yes, return user, else throw error.
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setTokenValue(userService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()).getValue());
        responseDTO.setMessage("SUCCESS");

        ResponseEntity<LoginResponseDTO> responseEntity = new ResponseEntity<>(
                responseDTO,
                HttpStatus.OK
        );
        return responseEntity;
    }

    @PostMapping("/register")
    public User signup(@RequestBody SignupRequestDTO signupRequestDTO){
        // no need to hash password for now.
        // just store user as is, in the DB
        // no need to have email verification either.

        return userService.signup(signupRequestDTO.getName(), signupRequestDTO.getEmail(), signupRequestDTO.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDTO logoutRequestDTO) throws TokenNotExistsOrAlreadyExpiredException {
        // delete if token exists -> 200
        // if it doesnt exist -> 404
        boolean isDeleted = userService.logout(logoutRequestDTO.getToken());
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(
                isDeleted == true ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR
        );
        return responseEntity;
    }
}
