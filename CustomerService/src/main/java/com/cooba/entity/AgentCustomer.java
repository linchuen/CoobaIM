package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table
public class AgentCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long agentId;

    @Column(nullable = false)
    private Long agentUserId;

    @Column(nullable = false)
    private Long customerUserId;

    @Column(nullable = false)
    private String showName;

    @Column(nullable = false)
    private LocalDateTime createdTime = LocalDateTime.now();
}
