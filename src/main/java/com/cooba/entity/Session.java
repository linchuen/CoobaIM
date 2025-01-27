package com.cooba.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk-userId", columnNames = {"userId"})
})
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
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
