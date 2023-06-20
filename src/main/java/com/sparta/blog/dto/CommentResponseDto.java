package com.sparta.blog.dto;

import com.sparta.blog.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private String title;
    private String username;
    private String contents;
    private LocalDateTime writtenAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.title = comment.getTitle();;
        this.username = comment.getUsername();
        this.contents = comment.getContents();
        this.writtenAt = comment.getWrittenAt();
    }
}
