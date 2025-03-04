package com.cooba.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class RoomBuildRequest {
    @NotEmpty
    private String name;
    private List<Long> userIds;
}
