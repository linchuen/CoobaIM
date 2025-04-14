package com.cooba.service.impl;

import com.cooba.core.spring.ProtoMessageConverter;
import com.cooba.entity.Chat;
import com.cooba.entity.User;
import com.cooba.proto.ChatProto;
import com.google.protobuf.InvalidProtocolBufferException;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
public class ProtoMessageConverterTest {

    @Test
    public void randomMessageWriteTest() {
        Chat chat = Instancio.create(Chat.class);

        Assertions.assertDoesNotThrow(() -> ProtoMessageConverter.buildChatProto(chat));
    }

    @Test
    public void randomMessageReadTest() {
        Chat chat = Instancio.create(Chat.class);
        byte[] chatProto = ProtoMessageConverter.buildChatProto(chat);

        Assertions.assertDoesNotThrow(() -> ProtoMessageConverter.readChatProto(chatProto));
    }

    @Test
    public void longMessageWriteTest() {
        Chat chat = Instancio.create(Chat.class);
        chat.setMessage("http://127.0.0.1:9000/bucket-2/22a494ab-7a4f-4e88-b437-d424a2afa403-avatar.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioadmin%2F20250412%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250412T173321Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=a878c2c072f9a4aad935606736f4b6f40cb00de62946dc6a4dbe4f6370cfe956");
        Assertions.assertDoesNotThrow(() -> ProtoMessageConverter.buildChatProto(chat));
    }

    @Test
    public void longMessageReadTest() {
        Chat chat = Instancio.create(Chat.class);
        chat.setMessage("http://127.0.0.1:9000/bucket-2/22a494ab-7a4f-4e88-b437-d424a2afa403-avatar.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioadmin%2F20250412%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250412T173321Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=a878c2c072f9a4aad935606736f4b6f40cb00de62946dc6a4dbe4f6370cfe956");
        byte[] chatProto = ProtoMessageConverter.buildChatProto(chat);

        Assertions.assertDoesNotThrow(() -> ProtoMessageConverter.readChatProto(chatProto));
    }

    @Test
    public void imageMessageWriteTest() {
        Chat chat = Instancio.create(Chat.class);
        chat.setMessage(null);
        chat.setUrl("http://127.0.0.1:9000/bucket-2/22a494ab-7a4f-4e88-b437-d424a2afa403-avatar.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioadmin%2F20250412%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250412T173321Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=a878c2c072f9a4aad935606736f4b6f40cb00de62946dc6a4dbe4f6370cfe956");
        Assertions.assertDoesNotThrow(() -> ProtoMessageConverter.buildChatProto(chat));
    }

    @Test
    public void imageMessageReadTest() {
        Chat chat = Instancio.create(Chat.class);
        chat.setMessage(null);
        chat.setUrl("http://127.0.0.1:9000/bucket-2/22a494ab-7a4f-4e88-b437-d424a2afa403-avatar.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioadmin%2F20250412%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250412T173321Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=a878c2c072f9a4aad935606736f4b6f40cb00de62946dc6a4dbe4f6370cfe956");
        byte[] chatProto = ProtoMessageConverter.buildChatProto(chat);

        Assertions.assertDoesNotThrow(() -> ProtoMessageConverter.readChatProto(chatProto));
    }
}
