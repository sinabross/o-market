package com.omarket.user.service;

import com.omarket.global.constant.SessionKey;
import com.omarket.global.util.crypto.CryptoData;
import com.omarket.global.util.crypto.Encryptor;
import com.omarket.user.dto.SignInRequestDto;
import com.omarket.user.domain.User;
import com.omarket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSessionLoginService implements LoginService{

    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final Encryptor encryptor;

    @Override
    public void login(SignInRequestDto dto){
        if (!userRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("존재하지 않는 이메일입니다.");
        }
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        CryptoData cryptoData = CryptoData.WithSaltBuilder()
            .plainText(dto.getPassword())
            .salt(user.get().getSalt())
            .build();
        String encryptedPassword = encryptor.encrypt(cryptoData);

        if(!encryptedPassword.equals(user.get().getPassword())){
            throw new IllegalArgumentException("패스워드가 틀렸습니다.");
        }
        httpSession.setAttribute(SessionKey.LOGIN_USER_ID, user.get().getId());
    }

    @Override
    public void logout(){
        httpSession.removeAttribute(SessionKey.LOGIN_USER_ID);
    }

    @Override
    public long getLoginUserId() {
        try {
            return (long)httpSession.getAttribute(SessionKey.LOGIN_USER_ID);
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }
    }
