package com.cooba.exception;

import com.cooba.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatMessageException extends Exception{
    private Chat chat;
}
