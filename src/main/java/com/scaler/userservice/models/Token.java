package com.scaler.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Token extends BaseModel {
    private String value;
    // if you want to allow multiple login, via different devices
    @ManyToOne
    private User user;
    private Date expiryAt;
}
