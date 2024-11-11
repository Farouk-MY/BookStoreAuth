package com.Bee.Store.BeeStore.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String fullName;
    private String email;
    private String password;
}
