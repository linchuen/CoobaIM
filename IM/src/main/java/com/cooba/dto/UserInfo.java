package com.cooba.dto;

import com.cooba.entity.User;
import lombok.Data;

@Data
public class UserInfo {
    private Long id;
    private String name;
    private String token;
    private User origin;
    private String partner;
}
