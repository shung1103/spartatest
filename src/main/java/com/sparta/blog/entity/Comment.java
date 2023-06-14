package com.sparta.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.blog.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String writeAt;

    @JsonIgnore
    Date today = new Date();

    @JsonIgnore
    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss");

    public Comment(CommentRequestDto requestDto) {
        this.writeAt = simple.format(today);
        this.password = requestDto.getPassword();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.author = requestDto.getAuthor();
    }
}
