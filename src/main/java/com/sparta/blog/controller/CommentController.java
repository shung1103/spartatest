package com.sparta.blog.controller;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.entity.Comment;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final Map<Long, Comment> commentList = new HashMap<>();

    @PostMapping("/comments")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto) {
        // RequestDto -> Entity
        Comment comment = new Comment(requestDto);

        // Comment Max ID Check
        Long maxId = commentList.size() > 0 ? Collections.max(commentList.keySet()) + 1 : 1;
        comment.setId(maxId);

        // DB 저장
        commentList.put(comment.getId(), comment);

        // Entity -> ResponseDto
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);

        return commentResponseDto;
    }

    @GetMapping("/comments")
    public List<CommentResponseDto> getComments() {
        // Map to List
        List<CommentResponseDto> responseList = commentList.values().stream()
                .sorted(Comparator.comparing(Comment::getCreatedAt).reversed()).map(CommentResponseDto::new).toList();
        // 생성된 날짜 기준으로 내림차순 정렬 후 List 화

        return responseList;
    }

    @PutMapping("/comments/{id}")
    public Long updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        // 해당 댓글이 DB에 존재하는지 확인
        if(commentList.containsKey(id)) {
            // 해당 댓글 가져오기
            Comment comment = commentList.get(id);

            // comment 수정
            comment.update(requestDto);
            return comment.getId();
        } else {
            throw new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/comments/{id}")
    public Long deleteComment(@PathVariable Long id) {
        // 해당 댓글이 DB에 존재하는지 확인
        if(commentList.containsKey(id)) {
            // 해당 댓글 삭제하기
            commentList.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.");
        }
    }
}
