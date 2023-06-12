package com.example.diplomcloud.repository;

import com.example.diplomcloud.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByToken(String token);
    void deleteByToken(String token);
}
