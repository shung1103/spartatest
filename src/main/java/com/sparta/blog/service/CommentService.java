package com.sparta.blog.service;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.repository.CommentRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentService {
    // 멤버 변수 선언
    private final CommentRepository commentRepository;
    // 생성자: commentService(JdbcTemplate jdbcTemplate)가 생성될 때 호출됨
    public CommentService(CommentRepository commentRepository) {
        // 멤버 변수 생성
        this.commentRepository = commentRepository;
    }

    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        // RequestDto -> Entity
        Comment comment = new Comment(requestDto);
        // DB 저장
        Comment saveComment = commentRepository.save(comment);
        // Entity -> ResponseDto
        CommentResponseDto commentResponseDto = new CommentResponseDto(saveComment);

        return commentResponseDto;
    }

    public List<CommentResponseDto> getComments() {
        // DB 조회
        return commentRepository.findAll();
    }

    public Comment getOneComment(Long id) {
        // DB 조회
        return commentRepository.findById(id);
    }

    public Comment getOnePassword(Long id) {
        // DB 조회
        return commentRepository.findByPass(id);
    }

    public Comment updateComment(Long id, CommentRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Comment comment = commentRepository.findById(id);
        if (comment != null) {
            // comment 내용 수정
            commentRepository.update(id, requestDto);

            return commentRepository.findById(id);
        } else {
            throw new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.");
        }
    }

    public String deleteComment(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        Comment comment = commentRepository.findById(id);
        if (comment != null) {
            // comment 삭제
            commentRepository.delete(id);

            return "선택한 댓글을 삭제하였습니다.";
        } else {
            throw new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.");
        }
    }
}