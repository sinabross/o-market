package com.omarket.user.service;

import com.omarket.global.fixture.UserFixture.*;
import com.omarket.global.util.crypto.Encryptor;
import com.omarket.user.dto.SignInRequestDto;
import com.omarket.user.domain.User;
import com.omarket.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserSessionLoginServiceTest {

	@InjectMocks
	private UserSessionLoginService userSessionLoginService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private Encryptor encryptor;

	@Mock
	private HttpSession httpSession;

	@DisplayName("이메일이 존재하고 패스워드가 일치하면 로그인에 성공한다.")
	@Test
	void signIn() {
		// given
		final SignInRequestDto dto = SignInRequestDto.builder()
			.email(User1.EMAIL)
			.password(User1.PASSWORD)
			.build();
		final Optional<User> user = Optional.of(User1.USER);
		final String encryptedPassword = User1.ENCRYPTED_PASSWORD;

		given(userRepository.findByEmail(dto.getEmail())).willReturn(user);
		given(encryptor.encrypt(any())).willReturn(encryptedPassword);

		// when
		userSessionLoginService.login(dto);

		// then
		then(httpSession).should(times(1)).setAttribute(any(), any());
	}

	@DisplayName("존재하지 않은 이메일이면 에러를 내보낸다.")
	@Test
	void signInWithNotFoundEmail() {
		//given
		final SignInRequestDto dto = SignInRequestDto.builder()
			.email(User1.EMAIL)
			.password(User1.PASSWORD)
			.build();
		final Optional<User> notFoundUser = Optional.ofNullable(null);

		given(userRepository.findByEmail(any())).willReturn(notFoundUser);

		// then
		assertThrows(IllegalArgumentException.class, () -> userSessionLoginService.login(dto));
	}

	@DisplayName("이메일이 존재하지만 패스워드가 일치하지 않으면 에러를 내보낸다.")
	@Test
	void signInWithInvalidPassword() {
		// given
		final SignInRequestDto dto = SignInRequestDto.builder()
			.email(User1.EMAIL)
			.password(User2.PASSWORD)
			.build();
		final Optional<User> user = Optional.of(User1.USER);
		final String encryptedPassword = User2.ENCRYPTED_PASSWORD;

		given(userRepository.findByEmail(any())).willReturn(user);
		given(encryptor.encrypt(any())).willReturn(encryptedPassword);

		// then
		assertThrows(IllegalArgumentException.class, () -> userSessionLoginService.login(dto));
	}
}
