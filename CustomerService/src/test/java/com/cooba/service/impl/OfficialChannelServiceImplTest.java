package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.aop.UserThreadLocal;
import com.cooba.constant.RoleEnum;
import com.cooba.entity.OfficialChannel;
import com.cooba.entity.User;
import com.cooba.repository.OfficialChannelRepository;
import com.cooba.service.OfficialChannelService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@MybatisLocalTest
@ContextConfiguration(classes = {OfficialChannelServiceImpl.class})
@Sql(scripts = {"/sql/OfficialChannel-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class OfficialChannelServiceImplTest {
    @Autowired
    OfficialChannelService officialChannelService;
    @Autowired
    OfficialChannelRepository officialChannelRepository;
    @MockitoBean
    UserThreadLocal userThreadLocal;

    @Test
    @DisplayName("建立頻道")
    void create() {
        OfficialChannel officialChannel = Instancio.create(OfficialChannel.class);
        officialChannel.setId(null);
        Long channelId = officialChannelService.create(officialChannel);

        Assertions.assertNotNull(channelId);
    }

    @Test
    @DisplayName("刪除頻道")
    void delete() {
        OfficialChannel officialChannel = Instancio.create(OfficialChannel.class);
        officialChannel.setId(null);
        Long channelId = officialChannelService.create(officialChannel);

        officialChannelService.delete(channelId);

        OfficialChannel channel = officialChannelRepository.selectById(channelId);
        Assertions.assertNull(channel);
    }

    @Test
    @DisplayName("訪客搜尋頻道")
    void guestSearchAll() {
        User user = new User();
        user.setRole(RoleEnum.GUEST.getRole());

        Mockito.when(userThreadLocal.getCurrentUser()).thenReturn(user);

        OfficialChannel officialChannel1 = Instancio.create(OfficialChannel.class);
        officialChannel1.setIsPublic(true);
        officialChannelService.create(officialChannel1);

        OfficialChannel officialChannel2 = Instancio.create(OfficialChannel.class);
        officialChannel2.setIsPublic(false);
        officialChannelService.create(officialChannel2);

        List<OfficialChannel> officialChannels = officialChannelService.searchAll();

        Assertions.assertEquals(1, officialChannels.size());
    }

    @Test
    @DisplayName("會員搜尋頻道")
    void userSearchAll() {
        User user = new User();
        user.setRole(RoleEnum.USER.getRole());

        Mockito.when(userThreadLocal.getCurrentUser()).thenReturn(user);

        OfficialChannel officialChannel1 = Instancio.create(OfficialChannel.class);
        officialChannel1.setIsPublic(true);
        officialChannelService.create(officialChannel1);

        OfficialChannel officialChannel2 = Instancio.create(OfficialChannel.class);
        officialChannel2.setIsPublic(false);
        officialChannelService.create(officialChannel2);

        List<OfficialChannel> officialChannels = officialChannelService.searchAll();

        Assertions.assertEquals(2, officialChannels.size());
    }
}