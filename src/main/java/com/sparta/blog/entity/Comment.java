package com.sparta.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.blog.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.lang.model.util.SimpleAnnotationValueVisitor6;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Comment {
    private long id;
    private String password;
    private String title;
    private String content;
    private String author;
    private String createdAt;
    private String modifiedAt;
    Date today = new Date();
    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss");

    public Comment(CommentRequestDto requestDto) {
        this.createdAt = simple.format(today);
        this.modifiedAt = simple.format(today);
        this.password = requestDto.getPassword();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.author = requestDto.getAuthor();
    }

    public void update(CommentRequestDto requestDto) {
        this.modifiedAt = simple.format(today);
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.author = requestDto.getAuthor();
    }
}
