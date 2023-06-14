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
    private long id;
    private String title;
    private String content;
    private String author;
    private String createdAt;
    private String modifiedAt;

    @JsonIgnore
    Date today = new Date();

    @JsonIgnore
    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss");

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.title = comment.getTitle();;
        this.content = comment.getContent();
        this.author = comment.getAuthor();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
