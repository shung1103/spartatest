package com.sparta.blog.controller;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(requestDto);
    }

    @GetMapping("/comments")
    public List<CommentResponseDto> getComments() {
        return commentService.getComments();
    }

    @GetMapping("/comments/{id}")
    public Comment getOneComment(@PathVariable Long id) {
        return commentService.getOneComment(id);
    }

    @GetMapping("/comments/forDeveloperOnly/{id}")
    public Comment getOnePassword(@PathVariable Long id) {
        return commentService.getOnePassword(id);
    }

    @PutMapping("/comments/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        if (requestDto.getPassword().equals(getOnePassword(id).getPassword())) {
            return commentService.updateComment(id, requestDto);
        } else {
            throw new IllegalArgumentException("잘못 된 비밀번호입니다.");
        }
    }

    @DeleteMapping("/comments/{id}/{password}")
    public String deleteComment(@PathVariable Long id, @PathVariable String password) {
        if (password.equals(getOnePassword(id).getPassword())) {
            return commentService.deleteComment(id);
        } else {
            throw new IllegalArgumentException("잘못 된 비밀번호입니다.");
        }
    }
}
