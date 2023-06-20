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
    public Comment getCommentsById(@PathVariable Long id) {
        return commentService.getCommentsById(id);
    }

    @GetMapping("/comments/contents")
    public List<CommentResponseDto> getCommentsByKeyword(String keyword) {
        return commentService.getCommentsByKeyword(keyword);
    }

    @PutMapping("/comments/{id}")
    public CommentRequestDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(id, requestDto);
    }

    @DeleteMapping("/comments/{id}")
    public String deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }
}
