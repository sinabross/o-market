package com.omarket.user.repository;

import com.omarket.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Mapper
@Repository
public interface UserRepository {

    void insertUser(User user);

    Optional<User> findByEmail(String email);

    User findById(long id);

    void updateUser(User user);
}
