package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cooba.constant.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "t_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_email", columnNames = {"email"}),
                @UniqueConstraint(name = "uk_name", columnNames = {"name"})
        })
@TableName("t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String role = RoleEnum.USER.getRole();

    @Column
    private LocalDateTime createdTime = LocalDateTime.now();

    @Transient
    @TableField(exist = false)
    private Set<UserAuthority> authorities;

    public Set<UserAuthority> getAuthorities() {
        return RoleEnum.getFromType(role);
    }
}
