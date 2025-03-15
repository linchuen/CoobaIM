package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_name", columnNames = {"name"})
})
public class OfficialChannel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isPublic = false;

    @Column(nullable = false)
    private LocalDateTime createdTime = LocalDateTime.now();
}
