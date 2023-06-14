package com.sparta.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class CommentRequestDto {
    private String password;
    private String title;
    private String content;
    private String author;

    @JsonIgnore
    Date today = new Date();

    @JsonIgnore
    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss");

    @JsonIgnore
    private String writeAt = simple.format(today);
}
