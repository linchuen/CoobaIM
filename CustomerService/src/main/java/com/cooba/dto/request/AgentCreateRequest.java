package com.cooba.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AgentCreateRequest {
    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @NotEmpty
    private String email;

    @NotEmpty
    private String department;
}
