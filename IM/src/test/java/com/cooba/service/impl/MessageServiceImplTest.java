package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.aop.UserThreadLocal;
import com.cooba.constant.MessageTypeEnum;
import com.cooba.core.SocketConnection;
import com.cooba.dto.NotifyMessage;
import com.cooba.dto.SendMessage;
import com.cooba.entity.Chat;
import com.cooba.entity.ChatSearch;
import com.cooba.entity.Notification;
import com.cooba.mapper.ChatMapper;
import com.cooba.repository.ChatRepository;
import com.cooba.service.MessageService;
import com.cooba.util.CacheUtil;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@Rollback(value = false)
@MybatisLocalTest
@ContextConfiguration(classes = {MessageServiceImpl.class})
@Sql(scripts = {"/sql/Chat-schema.sql",
        "/sql/Notification-schema.sql",
        "/sql/ChatSearch-schema.sql",
        "/sql/ChatRead-schema.sql",
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class MessageServiceImplTest {
    @Autowired
    MessageService messageService;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    ChatMapper chatMapper;
    @MockitoBean
    SocketConnection socketConnection;
    @MockitoBean
    UserThreadLocal userThreadLocal;
    @Autowired
    CacheUtil cacheUtil;

    @AfterEach
    void deleteAll() {
        chatMapper.delete(null);
    }

    @Test
    @DisplayName("發送訊息給用戶")
    void sendToUser() {
        Mockito.doNothing().when(cacheUtil).set(anyString(), anyString(), any(Duration.class));

        SendMessage sendMessage = Instancio.create(SendMessage.class);
        sendMessage.setType(MessageTypeEnum.TEXT);
        messageService.sendToUser(sendMessage);

        List<String> chats = messageService.getRoomChats(sendMessage.getRoomId())
                .stream()
                .map(Chat::getMessage)
                .toList();

        boolean hasSend = chats.contains(sendMessage.getMessage());
        Assertions.assertTrue(hasSend);
    }

    @Test
    @DisplayName("發送訊息給聊天室")
    void sendToRoom() {
        Mockito.doNothing().when(cacheUtil).set(anyString(), anyString(), any(Duration.class));

        SendMessage sendMessage = Instancio.create(SendMessage.class);
        sendMessage.setType(MessageTypeEnum.TEXT);
        messageService.sendToRoom(sendMessage);

        List<String> chats = messageService.getRoomChats(sendMessage.getRoomId())
                .stream()
                .map(Chat::getMessage)
                .toList();

        boolean hasSend = chats.contains(sendMessage.getMessage());
        Assertions.assertTrue(hasSend);
    }

    @Test
    @DisplayName("發送訊息給全部")
    void sendToAll() {
        NotifyMessage notifyMessage = Instancio.create(NotifyMessage.class);
        messageService.sendToAll(notifyMessage);

        List<String> chats = messageService.getNotifications()
                .stream()
                .map(Notification::getMessage)
                .toList();

        boolean hasSend = chats.contains(notifyMessage.getMessage());
        Assertions.assertTrue(hasSend);
    }

    @Test
    @DisplayName("根據id取得聊天記錄")
    void getRoomChatsById() {
        long roomId = 1;
        List<Chat> chats = IntStream.rangeClosed(1, 10)
                .boxed()
                .map(i -> {
                    Chat chat = Instancio.create(Chat.class);
                    chat.setId(Long.valueOf(i));
                    chat.setRoomId(roomId);
                    return chat;
                })
                .toList();
        chatRepository.insert(chats);

        List<Chat> roomChats = messageService.getRoomChats(roomId, 5L, true);
        Assertions.assertEquals(5, roomChats.size());
    }

    @Test
    @DisplayName("根據時間取得聊天記錄")
    void getRoomChatsByTime() {
        long roomId = 2;
        LocalDateTime now = LocalDateTime.now();
        List<Chat> chats = IntStream.rangeClosed(1, 10)
                .boxed()
                .map(i -> {
                    Chat chat = Instancio.create(Chat.class);
                    chat.setId(Long.valueOf(i));
                    chat.setCreatedTime(now.minusDays(i));
                    chat.setRoomId(roomId);
                    return chat;
                })
                .toList();
        chatRepository.insert(chats);

        List<Chat> roomChats = messageService.getRoomChats(roomId, now.minusDays(5), now);
        Assertions.assertEquals(5, roomChats.size());
    }

    @Test
    @DisplayName("根據日期取得聊天記錄")
    void getRoomChatsByDate() {
        long roomId = 3;
        LocalDateTime now = LocalDateTime.now();
        List<Chat> chats = IntStream.rangeClosed(1, 3)
                .boxed()
                .map(i -> {
                    Chat chat = Instancio.create(Chat.class);
                    chat.setId(Long.valueOf(i));
                    chat.setCreatedTime(now.minusDays(i));
                    chat.setRoomId(roomId);
                    return chat;
                })
                .toList();
        chatRepository.insert(chats);

        List<Chat> roomChats = messageService.getRoomChats(roomId, now.minusDays(3).toLocalDate());
        Assertions.assertEquals(1, roomChats.size());
    }

    @Test
    @DisplayName("添加聊天關鍵字")
    void insertMessageGram() {
        Chat chat = Instancio.create(Chat.class);
        chat.setType(MessageTypeEnum.TEXT);
        chat.setMessage("良好的 Java 開發不僅在於寫出可運行的程式，更在於編寫可讀、可維護、可擴展的代碼。");
        List<ChatSearch> chatSearches = messageService.insertMessageGram(chat);

        List<String> gramStrings = chatSearches.stream().map(ChatSearch::getMessageGram).toList();
        Assertions.assertTrue(gramStrings.containsAll(List.of("java", "ja", "程式", "开发")));
    }

    @Test
    @DisplayName("搜尋聊天關鍵字")
    void searchWord() {
        long roomId = 4;

        Chat chat1 = Instancio.create(Chat.class);
        chat1.setRoomId(roomId);
        chat1.setType(MessageTypeEnum.TEXT);
        chat1.setMessage("結構化設計：在 Java 開發中，良好的架構設計能讓程式更具可維護性與可擴展性。例如，遵循 SOLID 原則可以確保類別之間的責任單一、依賴關係清晰，從而減少未來修改時的影響範圍。");
        messageService.insertMessageGram(chat1);

        Chat chat2 = Instancio.create(Chat.class);
        chat2.setRoomId(roomId);
        chat2.setType(MessageTypeEnum.TEXT);
        chat2.setMessage("異常處理與日誌：一個穩健的 Java 應用應該有完善的異常處理機制，避免應用崩潰。同時，整合如 Logback 或 SLF4J 的日誌系統，能幫助開發者迅速定位錯誤並分析系統行為，提升維護效率。");
        messageService.insertMessageGram(chat2);

        Chat chat3 = Instancio.create(Chat.class);
        chat3.setRoomId(roomId);
        chat3.setType(MessageTypeEnum.TEXT);
        chat3.setMessage("效能與優化：在開發過程中，適當使用 Java 的併發工具（如 CompletableFuture、ForkJoinPool）提升執行效率，並定期進行性能分析（Profiling），找出潛在的效能瓶頸，如不必要的物件創建或 SQL 查詢過多，確保應用高效運行。");
        messageService.insertMessageGram(chat3);

        chatRepository.insert(List.of(chat1, chat2, chat3));
        System.out.println(ZhConverterUtil.toSimple("責任"));
        List<Chat> searchJava = messageService.searchWord(roomId, "java");
        Assertions.assertEquals(3, searchJava.size());

        List<Chat> searchEfficiency = messageService.searchWord(roomId, "效率");
        Assertions.assertEquals(2, searchEfficiency.size());

        List<Chat> searchResponsibility = messageService.searchWord(roomId, "責任");
        Assertions.assertEquals(1, searchResponsibility.size());
    }

    @Test
    @DisplayName("驗證已讀數量")
    void getRoomUnread() {
        long roomId = 5;

        List<Chat> chats = IntStream.range(1, 10)
                .boxed()
                .map(i -> {
                    Chat chat = Instancio.create(Chat.class);
                    chat.setId(Long.valueOf(i));
                    chat.setRoomId(roomId);
                    return chat;
                })
                .toList();
        chatRepository.insert(chats);

        messageService.setRoomIsRead(roomId, 3);

        long unread = messageService.getRoomUnread(roomId);
        Assertions.assertEquals(6, unread);
    }
}