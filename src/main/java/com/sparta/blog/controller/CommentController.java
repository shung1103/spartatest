package com.sparta.blog.controller;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/blog")
public class CommentController {
    private final CommentService commentService;

    // 게시글 생성
    @PostMapping("/comments")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest req) {
        return commentService.createComment(requestDto, userDetails, req);
    }

    // 전체 게시글 목록 조회
    @GetMapping("/comments")
    public List<CommentResponseDto> getComments() {
        return commentService.getComments();
    }

    // ID로 특정 게시글 조회
    @GetMapping("/comments/{id}")
    public Comment getCommentsById(@PathVariable Long id) {
        return commentService.getCommentsById(id);
    }

    // 키워드로 특정 게시글 검색
    @GetMapping("/comments/contents")
    public List<CommentResponseDto> getCommentsByKeyword(String keyword) {
        return commentService.getCommentsByKeyword(keyword);
    }

    // 게시글 수정
    @PutMapping("/comments/{id}")
    public CommentRequestDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest req) {
        return commentService.updateComment(id, requestDto, userDetails, req);
    }

    // 게시글 삭제
    @DeleteMapping("/comments/{id}")
    public String deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest req) {
        return commentService.deleteComment(id, userDetails, req);
    }
}
