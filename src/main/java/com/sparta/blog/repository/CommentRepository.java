package com.sparta.blog.repository;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.entity.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Comment save(Comment comment) {
        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO comment (password, title, content, author, writeAt) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, comment.getPassword());
                    preparedStatement.setString(2, comment.getTitle());
                    preparedStatement.setString(3, comment.getContent());
                    preparedStatement.setString(4, comment.getAuthor());
                    preparedStatement.setString(5, comment.getWriteAt());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        comment.setId(id);

        return comment;
    }

    public List<CommentResponseDto> findAll() {
        // DB 조회 및 입력 날짜 기준으로 내림차순
        String sql = "SELECT * FROM comment ORDER BY writeAt DESC";

        return jdbcTemplate.query(sql, new RowMapper<CommentResponseDto>() {
            @Override
            public CommentResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 comment 데이터들을 commentResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String author = rs.getString("author");
                String writeAt = rs.getString("writeAt");
                return new CommentResponseDto(id, title, content, author, writeAt);
            }
        });
    }

    public void update(Long id, CommentRequestDto requestDto) {
        String sql = "UPDATE comment SET title = ?, content = ?, author = ?, writeAt = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getContent(), requestDto.getAuthor(), requestDto.getWriteAt(), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM comment WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Comment findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM comment WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getLong("id"));
                comment.setTitle(resultSet.getString("title"));
                comment.setContent(resultSet.getString("content"));
                comment.setAuthor(resultSet.getString("author"));
                comment.setWriteAt(resultSet.getString("writeAt"));
                return comment;
            } else {
                throw new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.");
            }
        }, id);
    }

    public Comment findByPass(Long id) {
        // DB 조회
        String sql = "SELECT * FROM comment WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Comment comment = new Comment();
                comment.setPassword(resultSet.getString("password"));
                return comment;
            } else {
                throw new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.");
            }
        }, id);
    }
}