package com.omarket.user.service;

import com.omarket.global.util.crypto.Encryptor;
import com.omarket.user.dto.SignUpRequestDto;
import com.omarket.user.dto.UpdateUserRequestDto;
import com.omarket.user.domain.User;
import com.omarket.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.omarket.global.fixture.UserFixture.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Encryptor encryptor;

    @DisplayName("중복되지 않은 이메일이면 회원가입에 성공한다.")
    @Test
    void signUp() {
        // given
        final SignUpRequestDto dto = SignUpRequestDto.builder()
                .name(User1.NAME)
                .email(User1.EMAIL)
                .password(User1.PASSWORD)
                .phone(User1.PHONE)
                .build();
        final Optional<User> notFoundUser = Optional.ofNullable(null);

        given(userRepository.findByEmail(any())).willReturn(notFoundUser);

        // when
        userService.join(dto);

        // then
        then(userRepository).should(times(1)).insertUser(any());

    }

    @DisplayName("중복된 이메일이면 에러를 내보낸다.")
    @Test
    void signUpWithDuplicateEmail() {
        // given
        final SignUpRequestDto dto = SignUpRequestDto.builder()
            .name(User1.NAME)
            .email(User1.EMAIL)
            .password(User1.PASSWORD)
            .phone(User1.PHONE)
            .build();
        final Optional<User> user = Optional.of(User1.USER);

        given(userRepository.findByEmail(any())).willReturn(user);

        // then
        assertThrows(IllegalArgumentException.class, () -> userService.join(dto));
    }

    @DisplayName("유저에 대한 정보를 수정한다.")
    @Test
    void updateUser() {
        // given
        final UpdateUserRequestDto dto = UpdateUserRequestDto.builder()
                .name(User2.NAME)
                .password(User2.PASSWORD)
                .phone(User2.PHONE)
                .build();
        final long id = User1.ID;

        // when
        userService.updateUser(id, dto);

        // then
        then(userRepository).should(times(1)).updateUser(any());
    }
}