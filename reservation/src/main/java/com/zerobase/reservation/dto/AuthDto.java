package com.zerobase.reservation.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class AuthDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateAccount {

        @Email(message = "이메일 형식에 맞게 입력해주세요")
        @NotBlank(message = "이메일은 필수 입력항목입니다.")
        private String email;

        @NotBlank(message = "이름은 필수 입력항목입니다.")
        private String userName;

        @NotBlank(message = "연락처는 필수입력 항목입니다.")
        @Size(max = 20, message = "20자까지 입력 가능")
        private String phoneNum;

        @Size(min = 8, message = "8자리 이상 입력하십시오")
        @NotBlank(message = "비밀번호는 필수 입력항목입니다.")
        private String password;

        @NotBlank(message = "관리자 / 이용자 여부")
        private String role;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateAccount {

        @Email(message = "이메일 형식에 맞게 입력해주세요")
        @NotBlank(message = "이메일은 필수 입력항목입니다.")
        private String email;

        @Size(min = 8, message = "8자리 이상 입력하십시오")
        private String newPassword;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DeleteAccount {

        @Email(message = "이메일 형식에 맞게 입력해주세요")
        @NotBlank(message = "이메일을 입력하십시오.")
        private String email;

        @NotBlank(message = "비밀번호를 입력하십시오")
        private String password;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LogIn {

        @Email(message = "이메일 형식에 맞게 입력해주세요")
        @NotBlank(message = "이메일은 필수 입력항목입니다.")
        private String email;

        @Size(min = 8, message = "8자리 이상 입력하십시오")
        @NotBlank(message = "비밀번호는 필수 입력항목입니다.")
        private String password;
    }

}
