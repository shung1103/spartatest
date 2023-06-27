package com.sparta.blog.controller;

import com.sparta.blog.dto.ChatRequestDto;
import com.sparta.blog.dto.ChatResponseDto;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/blog/comments/{id}")
public class ChatController {
    private final ChatService chatService;

    // 전체 댓글 목록 조회
    @GetMapping("/chat")
    public List<ChatResponseDto> getAllChat(@PathVariable(name = "id") Long commentsId) {
        return chatService.getAllChat(commentsId);
    }

    // 댓글 생성
    @PostMapping("/chat")
    public ResponseEntity<?> createChat(@PathVariable(name = "id") Long commentsId, @RequestBody ChatRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest req) {
        return chatService.createChat(commentsId, requestDto, userDetails, req);
    }

    // 댓글 수정
    @PutMapping("/chat/{chatId}")
    public ResponseEntity<?> updateChat(@PathVariable(name = "id") Long commentsId, @PathVariable(name = "chatId") Long chatId, @RequestBody ChatRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest req) {
        return chatService.updateChat(commentsId, chatId, requestDto, userDetails, req);
    }

    // 댓글 삭제
    @DeleteMapping("/chat/{chatId}")
    public ResponseEntity<?> deleteChat(@PathVariable(name = "id") Long commentsId, @PathVariable(name = "chatId") Long chatId, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest req) {
        return chatService.deleteChat(commentsId, chatId, userDetails, req);
    }
}
