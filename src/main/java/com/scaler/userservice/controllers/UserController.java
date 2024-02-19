package com.scaler.userservice.controllers;

import com.scaler.userservice.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    public User login(){
        // check if email and pwd in DB
        // if yes, return user, else throw error.
        return null;
    }

    public User signup(){
        // no need to hash password for now.
        // just store user as is, in the DB
        // no need to have email verification either.
        return null;
    }

    public ResponseEntity<Void> logout(){
        // delete if token exists -> 200
        // if it doesnt exist -> 404
        return null;
    }
}
