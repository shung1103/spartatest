package com.sparta.blog.dto;

import com.sparta.blog.entity.Comment;
import com.sparta.blog.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequestDto {
    private String contents;
    private Comment comment;
    private User user;
}
