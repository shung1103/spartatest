package com.sparta.blog.service;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
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
        return commentRepository.findAllByOrderByWrittenAtDesc().stream().map(CommentResponseDto::new).toList();
    }

    public Comment getCommentsById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
    }

    public List<CommentResponseDto> getCommentsByKeyword(String keyword) {
        return commentRepository.findAllByContentsContainsOrderByWrittenAtDesc(keyword).stream().map(CommentResponseDto::new).toList();
    }

    @Transactional
    public CommentRequestDto updateComment(Long id, CommentRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Comment comment = findComment(id);
        // comment 내용 수정
        comment.update(requestDto);

        return requestDto;
    }

    public String deleteComment(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        Comment comment = findComment(id);
        // comment 삭제
        commentRepository.delete(comment);

        return "게시글을 삭제하는 데 성공하였습니다.";
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
    }
}