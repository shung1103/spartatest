package com.sparta.blog.service;

import com.sparta.blog.dto.ChatRequestDto;
import com.sparta.blog.dto.ResponseDto;
import com.sparta.blog.entity.Chat;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.entity.UserRoleEnum;
import com.sparta.blog.jwt.JwtUtil;
import com.sparta.blog.repository.ChatRepository;
import com.sparta.blog.repository.CommentRepository;
import com.sparta.blog.repository.UserRepository;
import com.sparta.blog.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ChatService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseEntity<?> getAllChat(Long commentsId) {
        Optional<Comment> foundComments = commentRepository.findById(commentsId);
        if (foundComments.isPresent()) {
            List<Chat> chatList = foundComments.get().getChatList();
            return new ResponseEntity<>(ResponseDto.success(chatList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseDto.fail("NULL_POST_ID", "해당 게시글은 존재하지 않는 게시글입니다."),
                    HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<?> createChat(Long commentsId, ChatRequestDto requestDto, UserDetailsImpl userDetails, HttpServletRequest req) {
        String tokenValue = jwtUtil.getTokenFromRequest(req);
        tokenValue = jwtUtil.substringToken(tokenValue);
        if (!jwtUtil.validateToken(tokenValue)) {
            log.error("Token Error");
            return null;
        } else {
            Optional<Comment> checkComments = commentRepository.findById(commentsId);

            if (checkComments.isPresent()) {
                Comment foundComment = checkComments.get();
                requestDto.setComment(foundComment);
                Chat saveChat = chatRepository.save(new Chat(requestDto, userDetails, foundComment));
                foundComment.addChat(saveChat);
                return new ResponseEntity<>(ResponseDto.success(saveChat), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ResponseDto.fail("NULL_POST_ID", "해당 게시글은 존재하지 않는 게시글입니다."),
                        HttpStatus.NOT_FOUND);
            }
        }
    }

    @Transactional
    public ResponseEntity<?> updateChat(Long commentsId, Long chatId, ChatRequestDto requestDto, UserDetailsImpl userDetails, HttpServletRequest req) {
        String tokenValue = jwtUtil.getTokenFromRequest(req);
        tokenValue = jwtUtil.substringToken(tokenValue);
        if (!jwtUtil.validateToken(tokenValue)) {
            log.error("Token Error");
            return null;
        } else {
            Optional<Comment> checkComments = commentRepository.findById(commentsId);
            Chat foundChat;

            if (checkComments.isPresent()) {
                List<Chat> chatList = checkComments.get().getChatList();
                try {
                    foundChat = chatList.get(chatId.intValue()-1);
                } catch (IndexOutOfBoundsException e) {
                    return new ResponseEntity<>(ResponseDto.fail("NULL_POST_ID", "존재하지 않는 댓글입니다."),
                            HttpStatus.NOT_FOUND);
                }

                if(userDetails.getRole() == UserRoleEnum.ADMIN) {
                    foundChat.update(requestDto, checkComments.get(), userDetails);
                    return new ResponseEntity<>(ResponseDto.success(foundChat), HttpStatus.OK);
                } else {
                    if (userDetails.getUsername().equals(foundChat.getUsername())) {
                        foundChat.update(requestDto, checkComments.get(), userDetails);
                        return new ResponseEntity<>(ResponseDto.success(foundChat), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(ResponseDto.fail("UNAUTHORIZED", "작성자만 수정할 수 있습니다."),
                                HttpStatus.UNAUTHORIZED);
                    }
                }
            } else {
                return new ResponseEntity<>(ResponseDto.fail("NULL_POST_ID", "해당 게시글은 존재하지 않습니다."),
                        HttpStatus.NOT_FOUND);
            }
        }
    }

    @Transactional
    public ResponseEntity<?> deleteChat(Long commentsId, Long chatId, UserDetailsImpl userDetails, HttpServletRequest req) {
        String tokenValue = jwtUtil.getTokenFromRequest(req);
        tokenValue = jwtUtil.substringToken(tokenValue);
        if (!jwtUtil.validateToken(tokenValue)) {
            log.error("Token Error");
            return null;
        } else {
            Optional<Comment> foundComments = commentRepository.findById(commentsId);
            Chat foundChat;

            if (foundComments.isPresent()) {
                try {
                    foundChat = foundComments.get().getChatList().get(chatId.intValue()-1);
                } catch (IndexOutOfBoundsException e) {
                    return new ResponseEntity<>(ResponseDto.fail("NULL_POST_ID", "존재하지 않는 댓글입니다."),
                            HttpStatus.NOT_FOUND);
                }

                if (userDetails.getUsername().equals(foundChat.getUsername())) {
                    chatRepository.deleteById(chatId);
                    return new ResponseEntity<>(ResponseDto.success("delete success"),HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(ResponseDto.fail("UNAUTHORIZED", "작성자만 삭제할 수 있습니다."),
                            HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>(ResponseDto.fail("NULL_POST_ID", "해당 게시글은 존재하지 않는 게시글입니다."),
                        HttpStatus.NOT_FOUND);
            }
        }
    }

    private Chat findChat(Long id) {
        return chatRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
    }
}
