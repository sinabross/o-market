package com.omarket.user.dto;

import com.omarket.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class UpdateUserRequestDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp="[0-9]{10,11}", message = "10-11 자리의 전화번호를 입력해야 해요.")
    private String phone;

    @Builder
    public UpdateUserRequestDto(String name, String password, String phone) {
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public User toEntity(long id, String salt, String encryptedPassword){
        return User.builder()
                .id(id)
                .name(name)
                .salt(salt)
                .password(encryptedPassword)
                .phone(phone)
                .build();
    }
}
