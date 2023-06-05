package com.example.diplomcloud.service;

import com.example.diplomcloud.entity.Token;
import com.example.diplomcloud.entity.User;
import com.example.diplomcloud.repository.TokenRepository;
import com.example.diplomcloud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    public LoginService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public String login(String login, String password) {
        // Проверяем учетные данные пользователя
        User user = userRepository.findByLogin(login);
        if (user == null || !password.equals(user.getPassword())) {
            throw new RuntimeException("Неверные учетные данные");
        }

        // Создаем новый токен аутентификации
        String authToken = tokenService.createToken(user.getId());
        return authToken;
    }

    public void logout(String authToken) {
        // Удаляем токен аутентификации
        tokenService.deleteToken(authToken);
    }
}
