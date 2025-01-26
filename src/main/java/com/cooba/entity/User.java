package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_user",
        uniqueConstraints = {
        @UniqueConstraint(name = "uk_email", columnNames = {"email"})
})
@TableName("t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private LocalDateTime createdTime = LocalDateTime.now();
}
