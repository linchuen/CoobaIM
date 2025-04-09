package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cooba.annotation.IMEntity;
import com.cooba.constant.RoleEnum;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@IMEntity
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_email", columnNames = {"email"}),
        @UniqueConstraint(name = "uk_name", columnNames = {"name"})
})
public class User implements UserDetails {
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

    @Column(nullable = false)
    @ColumnDefault("''")
    private String avatar = "";

    @Column(nullable = false)
    @ColumnDefault("'cooba'")
    private String partner = "";

    @Column
    private LocalDateTime createdTime = LocalDateTime.now();

    @Transient
    @TableField(exist = false)
    private Set<UserAuthority> authorities;

    @Override
    public Set<UserAuthority> getAuthorities() {
        return RoleEnum.getFromType(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
