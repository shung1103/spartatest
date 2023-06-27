package com.sparta.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Size(min = 4, max = 10, message = "아이디는 최소 4자 이상, 10자 이하로 해주세요.")
    private String username;
    @NotBlank
    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자 이상, 15자 이하로 해주세요.")
    private String password;
    @Email(message = "email@address.com 의 형식으로 입력해 주세요.")
    @NotBlank
    private String email;
    private boolean admin = false;
    private String adminToken = "";
}