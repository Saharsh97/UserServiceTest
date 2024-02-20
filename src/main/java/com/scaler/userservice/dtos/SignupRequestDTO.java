package com.scaler.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDTO {
    private String email;
    private String password;
    // roles are done separately. not at singup
    private String name;
}
