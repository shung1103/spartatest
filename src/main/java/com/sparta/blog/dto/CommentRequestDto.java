package com.sparta.blog.dto;

import com.sparta.blog.entity.Chat;
import com.sparta.blog.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentRequestDto {
    private String title;
    private String contents;
    private User user;
    private List<Chat> chatList;
}
