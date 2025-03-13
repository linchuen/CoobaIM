package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cooba.annotation.IMEntity;
import com.cooba.constant.RoomRoleEnum;
import com.cooba.constant.RoomTypeEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@IMEntity
@Entity
@Table
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private RoomTypeEnum roomTypeEnum = RoomTypeEnum.GROUP;

    @Column(nullable = false)
    private LocalDateTime createdTime = LocalDateTime.now();
}
