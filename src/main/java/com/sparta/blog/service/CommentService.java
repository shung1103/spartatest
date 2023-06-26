package com.sparta.blog.service;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.entity.UserRoleEnum;
import com.sparta.blog.repository.CommentRepository;
import com.sparta.blog.security.UserDetailsImpl;
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

    public CommentResponseDto createComment(CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        // RequestDto -> Entity
        Comment comment = new Comment(requestDto, userDetails);
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
    public CommentRequestDto updateComment(Long id, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        // 해당 메모가 DB에 존재하는지 확인
        Comment comment = findComment(id);
        // 권한 admin 확인
        if (userDetails.getRole() == UserRoleEnum.ADMIN) {
            // comment 내용 수정
            comment.update(requestDto);
        } else {
            if (comment.getUsername().equals(userDetails.getUsername())) {
                // comment 내용 수정
                comment.update(requestDto);
            } else {
                throw new IllegalArgumentException("해당 게시글의 작성자가 아닙니다.");
            }
        }

        return requestDto;
    }

    public String deleteComment(Long id, UserDetailsImpl userDetails) {
        // 해당 메모가 DB에 존재하는지 확인
        Comment comment = findComment(id);
        // 권한 admin 확인
        if (userDetails.getRole() == UserRoleEnum.ADMIN) {
            commentRepository.delete(comment);
            return "게시글을 삭제하는 데 성공하였습니다.";
        } else {
            if (comment.getUsername().equals(userDetails.getUsername())) {
                // comment 삭제
                commentRepository.delete(comment);
                return "게시글을 삭제하는 데 성공하였습니다.";
            } else {
//            throw new IllegalArgumentException("해당 게시글의 작성자가 아닙니다.");
                return "redirect:/blog";
            }
        }
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
    }
}