package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cooba.annotation.IMEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.*;

@Data
@RequiredArgsConstructor
@IMEntity
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk", columnNames = {"userId", "authority"})
})
public class UserAuthority implements GrantedAuthority {
    public UserAuthority(String authority) {
        this.authority = authority;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
