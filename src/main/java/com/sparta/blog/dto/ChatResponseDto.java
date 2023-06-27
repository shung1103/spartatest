package com.sparta.blog.dto;

import com.sparta.blog.entity.Chat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatResponseDto {
    private Long id;
    private String username;
    private String contents;
    private LocalDateTime writtenAt;

    public ChatResponseDto(Chat chat) {
        this.id = chat.getId();
        this.username = chat.getUsername();
        this.contents = chat.getContents();
        this.writtenAt = chat.getWrittenAt();
    }
}
