package com.sparta.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.blog.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String writeAt;

    @JsonIgnore
    Date today = new Date();

    @JsonIgnore
    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss");

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.title = comment.getTitle();;
        this.content = comment.getContent();
        this.author = comment.getAuthor();
        this.writeAt = comment.getWriteAt();
    }

    public CommentResponseDto(Long id, String title, String content, String author, String writeAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.writeAt = writeAt;
    }
}
