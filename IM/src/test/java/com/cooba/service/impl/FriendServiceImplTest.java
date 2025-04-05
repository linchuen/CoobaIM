package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.constant.ErrorEnum;
import com.cooba.entity.Friend;
import com.cooba.entity.FriendApply;
import com.cooba.entity.Room;
import com.cooba.entity.User;
import com.cooba.exception.BaseException;
import com.cooba.repository.FriendApplyRepository;
import com.cooba.repository.FriendRepository;
import com.cooba.repository.UserRepository;
import com.cooba.service.FriendService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;


@MybatisLocalTest
@ContextConfiguration(classes = {FriendServiceImpl.class})
@Sql(scripts = {"/sql/Friend-schema.sql", "/sql/FriendApply-schema.sql", "/sql/User-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {"/delete/Friend-delete.sql", "/delete/FriendApply-delete.sql", "/delete/User-delete.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class FriendServiceImplTest {
    @Autowired
    FriendService friendService;
    @Autowired
    FriendApplyRepository friendApplyRepository;
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("朋友申請")
    void apply() {
        FriendApply friendApply = Instancio.create(FriendApply.class);
        friendService.apply(friendApply);

        FriendApply apply = friendApplyRepository.selectById(friendApply.getId());
        Assertions.assertNotNull(apply);
    }

    @Test
    @DisplayName("朋友重複申請")
    void applyTwice() {
        FriendApply friendApply = Instancio.create(FriendApply.class);
        friendService.apply(friendApply);

        BaseException baseException = Assertions.assertThrows(BaseException.class, () -> friendService.apply(friendApply));

        Assertions.assertEquals(ErrorEnum.FRIEND_APPLY_EXIST, baseException.getError());
    }

    @Test
    @DisplayName("允許加入朋友")
    void bindPermit() {
        FriendApply friendApply = Instancio.create(FriendApply.class);
        User applyUser = Instancio.create(User.class);
        applyUser.setId(friendApply.getApplyUserId());
        userRepository.insert(applyUser);
        User permitUser = Instancio.create(User.class);
        permitUser.setId(friendApply.getPermitUserId());
        userRepository.insert(permitUser);

        friendApply.setPermit(true);
        friendService.apply(friendApply);
        friendService.bind(friendApply, Room::new);

        Friend apply = new Friend();
        apply.setUserId(friendApply.getApplyUserId());
        apply.setFriendUserId(friendApply.getPermitUserId());
        boolean applyFriend = friendService.isFriend(apply);
        Assertions.assertTrue(applyFriend);

        Friend permit = new Friend();
        permit.setUserId(friendApply.getPermitUserId());
        permit.setFriendUserId(friendApply.getApplyUserId());
        boolean permitFriend = friendService.isFriend(permit);
        Assertions.assertTrue(permitFriend);
    }

    @Test
    @DisplayName("拒絕加入朋友")
    void bindDeny() {
        FriendApply friendApply = Instancio.create(FriendApply.class);
        friendApply.setPermit(false);
        friendService.apply(friendApply);
        friendService.bind(friendApply, () -> null);

        Friend apply = new Friend();
        apply.setUserId(friendApply.getApplyUserId());
        apply.setFriendUserId(friendApply.getPermitUserId());
        boolean applyFriend = friendService.isFriend(apply);
        Assertions.assertFalse(applyFriend);

        Friend permit = new Friend();
        permit.setUserId(friendApply.getPermitUserId());
        permit.setFriendUserId(friendApply.getApplyUserId());
        boolean permitFriend = friendService.isFriend(permit);
        Assertions.assertFalse(permitFriend);
    }

    @Test
    @DisplayName("刪除朋友")
    void unbind() {
        FriendApply friendApply = Instancio.create(FriendApply.class);
        User applyUser = Instancio.create(User.class);
        applyUser.setId(friendApply.getApplyUserId());
        userRepository.insert(applyUser);
        User permitUser = Instancio.create(User.class);
        permitUser.setId(friendApply.getPermitUserId());
        userRepository.insert(permitUser);

        friendApply.setPermit(true);
        friendService.apply(friendApply);
        friendService.bind(friendApply, Room::new);
        friendService.unbind(friendApply);

        FriendApply select = friendApplyRepository.selectById(friendApply.getId());
        Assertions.assertNull(select);

        Friend apply = new Friend();
        apply.setUserId(friendApply.getApplyUserId());
        apply.setFriendUserId(friendApply.getPermitUserId());
        boolean applyFriend = friendService.isFriend(apply);
        Assertions.assertFalse(applyFriend);

        Friend permit = new Friend();
        permit.setUserId(friendApply.getPermitUserId());
        permit.setFriendUserId(friendApply.getApplyUserId());
        boolean permitFriend = friendService.isFriend(permit);
        Assertions.assertFalse(permitFriend);
    }
}