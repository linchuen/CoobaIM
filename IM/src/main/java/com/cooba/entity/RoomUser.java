package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.cooba.annotation.IMEntity;
import jakarta.persistence.*;

import com.cooba.constant.RoomRoleEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@IMEntity
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_roomId", columnNames = {"roomId", "userId"})
}, indexes = {
        @Index(name = "idx_userId", columnList = "userId, roomId")
})
public class RoomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String showName;

    @Column
    private String avatar;

    @Column(nullable = false)
    private RoomRoleEnum roomRoleEnum = RoomRoleEnum.MEMBER;

    @Column(nullable = false)
    private LocalDateTime createdTime = LocalDateTime.now();
}
