package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cooba.annotation.IMEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@IMEntity
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_roomId", columnNames = {"roomId", "messageGram", "chatId"})
})
public class ChatSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    private String messageGram;

    @Column(nullable = false)
    private Long chatId;

    @Column(nullable = false)
    private LocalDateTime createdTime = LocalDateTime.now();
}
