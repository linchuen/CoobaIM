package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_agent_user", columnNames = {"agentUserId"})
}, indexes = {
        @Index(name = "idx_customer", columnList = "customerUserId"),
})
public class AgentCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long agentUserId;

    @Column(nullable = false)
    private Long customerUserId;

    @Column(nullable = false)
    private String showName;

    @Column
    private Long roomId;

    @Column(nullable = false)
    private LocalDateTime createdTime = LocalDateTime.now();
}
