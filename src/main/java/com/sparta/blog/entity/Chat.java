package com.sparta.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.blog.dto.ChatRequestDto;
import com.sparta.blog.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "chat")
public class Chat extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ID")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    @Transient
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMMENT_ID")
    @JsonIgnore
    private Comment comment;

    public Chat(ChatRequestDto requestDto, UserDetailsImpl userDetails, Comment comment) {
        User user =  userDetails.getUser();
        this.comment = comment;
        this.username = user.getUsername();
        this.contents = requestDto.getContents();
    }

    public void update(ChatRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }
}
