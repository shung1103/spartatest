package com.sparta.blog.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String title;
    private String username;
    private String contents;
}
