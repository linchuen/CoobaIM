package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_user", columnNames = {"userId"})
})
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private boolean isDisable = false;

    @Column(nullable = false)
    private boolean isDefault = false;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private LocalDateTime createdTime = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedTime = LocalDateTime.now();
}
