package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cooba.annotation.IMEntity;
import com.cooba.constant.PlatformEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@IMEntity
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_userId", columnNames = {"userId", "platform"})
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

    @Column(length = 1000)
    private String preToken;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private PlatformEnum platform;

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
