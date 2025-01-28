package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_apply_permit", columnNames = {"applyUserId", "permitUserId"})
}, indexes = {
        @Index(name = "idx_apply", columnList = "applyUserId"),
        @Index(name = "idx_permit", columnList = "permitUserId")
})
public class FriendApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long applyUserId;

    @Column(nullable = false)
    private Long permitUserId;

    @Column
    private boolean isPermit = false;

    @Column(nullable = false)
    private LocalDateTime createdTime = LocalDateTime.now();
}
