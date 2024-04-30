package com.omarket.user.service;

import com.omarket.global.util.crypto.CryptoData;
import com.omarket.global.util.crypto.Encryptor;
import com.omarket.global.util.crypto.SaltGenerator;
import com.omarket.user.dto.SignUpRequestDto;
import com.omarket.user.dto.UpdateUserRequestDto;
import com.omarket.user.domain.EncryptedPassword;
import com.omarket.user.domain.User;
import com.omarket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    @Qualifier("sha256Encryptor")
    private final Encryptor encryptor;

    public void join(SignUpRequestDto dto){
        if (checkIsUserExist(dto.getEmail())) {
            throw new IllegalArgumentException("이미 등록된 메일입니다.");
        }

        EncryptedPassword pw = encryptPasswordWithSalt(dto.getPassword());
        User user = dto.toEntity(pw.getSalt(), pw.getPassword());
        userRepository.insertUser(user);
    }

    private EncryptedPassword encryptPasswordWithSalt(String plainPassword) {
        String salt = SaltGenerator.generateSalt();
        CryptoData cryptoData = CryptoData.WithSaltBuilder()
                .plainText(plainPassword)
                .salt(salt)
                .build();
        String encryptedPassword = encryptor.encrypt(cryptoData);
        return EncryptedPassword.builder()
                .salt(salt)
                .password(encryptedPassword)
                .build();
    }

    private boolean checkIsUserExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public void updateUser(long id, UpdateUserRequestDto dto){
        EncryptedPassword pw = encryptPasswordWithSalt(dto.getPassword());
        User user = dto.toEntity(id, pw.getSalt(), pw.getPassword());
        userRepository.updateUser(user);
    }
}


