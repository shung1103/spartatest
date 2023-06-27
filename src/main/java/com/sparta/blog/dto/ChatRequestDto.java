package com.sparta.blog.dto;

import com.sparta.blog.entity.User;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import com.sparta.blog.entity.Comment;

@Getter
public class ChatRequestDto {
    @Size(max = 50, message = "50자 이내로 작성해 주세요.")
    private String chat;
    private Comment comment;
    private User user;
}
