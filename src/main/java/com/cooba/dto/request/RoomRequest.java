package com.cooba.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RoomRequest {
    private Long id;
    @NotEmpty
    private String name;
}
