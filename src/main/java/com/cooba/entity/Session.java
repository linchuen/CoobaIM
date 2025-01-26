package com.cooba.entity;

import jakarta.persistence.*;
import lombok.Data;

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
}
