package com.scaler.userservice.dtos;

import com.scaler.userservice.models.Role;
import com.scaler.userservice.models.User;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String email;
    @OneToMany
    private List<Role> roles;
    private boolean isEmailVerified;

    public static UserDTO from(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.name = user.getName();
        userDTO.email = user.getEmail();
        userDTO.isEmailVerified = user.isEmailVerified();
        userDTO.roles = user.getRoles();
        return userDTO;
    }
}
