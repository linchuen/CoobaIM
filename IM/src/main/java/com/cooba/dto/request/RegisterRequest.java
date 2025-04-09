package com.cooba.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;

@Data
public class RegisterRequest {
    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @NotEmpty
    private String email;

    private String partner;
}
