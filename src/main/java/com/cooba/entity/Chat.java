package com.cooba.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(indexes = {
        @Index(name = "idx-roomId", columnList = "roomId, userId")
})
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String message;

    @Column
    private int version;

    @Column(nullable = false)
    private LocalDateTime createdTime;
}
