package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cooba.constant.RoleEnum;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "t_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_email", columnNames = {"email"}),
                @UniqueConstraint(name = "uk_name", columnNames = {"name"})
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
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
