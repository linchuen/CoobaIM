package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    private Long agentUserId;

    @Column(nullable = false)
    private Long customerUserId;

    @Column(nullable = false)
    private boolean isOpen = true;

    @Column(nullable = false)
    private LocalDateTime createdTime = LocalDateTime.now();
}
