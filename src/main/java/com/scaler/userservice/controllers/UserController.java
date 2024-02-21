package com.scaler.userservice.controllers;

import com.scaler.userservice.dtos.*;
import com.scaler.userservice.exceptions.InvalidPasswordException;
import com.scaler.userservice.exceptions.TokenNotExistsOrAlreadyExpiredException;
import com.scaler.userservice.exceptions.UserNotFoundException;
import com.scaler.userservice.models.Token;
import com.scaler.userservice.models.User;
import com.scaler.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        LoginResponseDTO responseDTO = new LoginResponseDTO();

        String loginToken = userService.login(
                    loginRequestDTO.getEmail(),
                    loginRequestDTO.getPassword()
                ).getValue();
        responseDTO.setTokenValue(loginToken);
        responseDTO.setMessage("SUCCESS");

        ResponseEntity<LoginResponseDTO> responseEntity = new ResponseEntity<>(
                responseDTO,
                HttpStatus.OK
        );
        return responseEntity;
    }

    @PostMapping("/register")
    public User signup(@RequestBody SignupRequestDTO signupRequestDTO){

        return userService.signup(
                signupRequestDTO.getName(),
                signupRequestDTO.getEmail(),
                signupRequestDTO.getPassword());
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

    @GetMapping("/validate/{token}")
    public UserDTO validateToken(@PathVariable(name = "token") String token){
        User user = userService.validateToken(token);
        UserDTO userDTO = UserDTO.from(user);
        return userDTO;
    }
}
