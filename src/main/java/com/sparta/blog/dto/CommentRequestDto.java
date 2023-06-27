package com.sparta.blog.dto;

import com.sparta.blog.entity.User;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String title;
    private String contents;
    private User user;
}
