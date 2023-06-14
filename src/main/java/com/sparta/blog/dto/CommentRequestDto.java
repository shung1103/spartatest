package com.sparta.blog.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String password;
    private String title;
    private String content;
    private String author;
}
