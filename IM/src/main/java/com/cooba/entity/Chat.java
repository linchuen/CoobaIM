package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cooba.constant.MessageTypeEnum;
import com.cooba.dto.SendMessage;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(indexes = {
        @Index(name = "idx_roomId", columnList = "roomId, userId")
})
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String message;

    @Column(length = 1000)
    private String url;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private MessageTypeEnum type = MessageTypeEnum.TEXT;

    @Column
    private int version = 1;

    @Column(nullable = false)
    private LocalDateTime createdTime = LocalDateTime.now();

    public Chat() {
    }

    public Chat(SendMessage sendMessage) {
        this.roomId = sendMessage.getRoomId();
        this.userId = sendMessage.getUserId();
        this.name = sendMessage.getName();
        this.message = sendMessage.getMessage();
        this.url = sendMessage.getUrl();
        this.type = sendMessage.getType();
    }
}
