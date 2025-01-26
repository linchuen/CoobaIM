package com.cooba.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_email", columnNames = {"email"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String email;

    @Column
    private LocalDateTime createdTime;
}
