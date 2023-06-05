package com.example.diplomcloud.service;

import com.example.diplomcloud.entity.Token;
import com.example.diplomcloud.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public boolean isValid(String authToken) {
        // Проверяем, существует ли токен в базе данных
        Token token = tokenRepository.findByToken(authToken);
        return token != null;
    }

    public String createToken(Long userId) {
        // Создаем новый токен и сохраняем его в базе данных
        String authToken = UUID.randomUUID().toString();
        Token token = new Token(authToken, userId);
        tokenRepository.save(token);
        return authToken;
    }

    public void deleteToken(String authToken) {
        // Удаляем токен из базы данных
        tokenRepository.deleteByToken(authToken);
    }
}

