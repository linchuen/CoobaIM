package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cooba.annotation.IMEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@IMEntity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_userId", columnNames = {"userId"})
})
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 1000)
    private String token;

    @Column
    private String platform;

    @Column
    private String ip;

    @Column(nullable = false)
    private LocalDateTime loginTime;

    @Column(nullable = false)
    private LocalDateTime expireTime;

    @Column
    private LocalDateTime logoutTime;

    @Column(nullable = false)
    private Boolean enable;
}
