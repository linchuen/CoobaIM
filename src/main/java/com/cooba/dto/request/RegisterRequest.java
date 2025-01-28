package com.cooba.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterRequest {
    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @NotEmpty
    private String email;
}
