package com.sparta.blog.repository;

import com.sparta.blog.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByOrderByWrittenAtDesc();
}
