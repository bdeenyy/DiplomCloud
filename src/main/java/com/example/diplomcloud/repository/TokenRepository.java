package com.example.diplomcloud.repository;

import com.example.diplomcloud.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    // Находит токен по значению
    Token findByToken(String token);

    // Удаляет токен по значению
    void deleteByToken(String token);

    // Проверяет, существует ли токен с заданным значением
    boolean existsByToken(String token);
}

