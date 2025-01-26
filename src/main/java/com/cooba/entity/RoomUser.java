package com.cooba.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_roomId", columnNames = {"roomId", "userId"})
}, indexes = {
        @Index(name = "idx_userId", columnList = "userId, roomId")
})
public class RoomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDateTime createdTime;
}
