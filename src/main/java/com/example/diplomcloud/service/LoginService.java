package com.example.diplomcloud.service;

import com.example.diplomcloud.entity.User;
import com.example.diplomcloud.exception.BadCredentialsException;
import com.example.diplomcloud.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LoginService {
    private final Logger logger = LoggerFactory.getLogger(LoginService.class);
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(TokenService tokenService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String login, String password) {
        logger.debug("Logging in user: {}", login);
        // Ищем пользователя по логину
        User user = userRepository.findByLogin(login);
        if (user == null) {
            // Если пользователь не найден, регистрируем нового пользователя
            register(login, password);
            // Получаем идентификатор нового пользователя
            Long userId = getUserIdByLogin(login);
            // Генерируем токен и возвращаем
            String token = tokenService.generateToken(userId);
            logger.debug("User logged in successfully: {}", login);
            return token;
        } else {
            // Если пользователь найден, проверяем пароль
            if (!passwordEncoder.matches(password, user.getPassword())) {
                logger.error("Invalid credentials for user: {}", login);
                throw new BadCredentialsException();
            }
            // Генерируем токен и возвращаем
            String token = tokenService.generateToken(user.getId());
            logger.debug("User logged in successfully: {}", login);
            return token;
        }
    }

    public void logout(String token) {
        logger.debug("Logging out user with token: {}", token);
        // Удаляем токен
        tokenService.removeToken(token);
        logger.debug("User logged out successfully");
    }

    private String getPasswordHashFromDatabase(String login) {
        // Ищем пользователя по логину
        User user = userRepository.findByLogin(login);
        // Возвращаем хеш пароля пользователя
        return user.getPassword();
    }

    private Long getUserIdByLogin(String login) {
        // Ищем пользователя по логину
        User user = userRepository.findByLogin(login);
        // Возвращаем идентификатор пользователя
        return user.getId();
    }

    public void register(String login, String password) {
        logger.debug("Registering new user: {}", login);
        // Кодируем пароль с использованием PasswordEncoder
        String encodedPassword = passwordEncoder.encode(password);
        // Создаем нового пользователя
        User user = new User();
        user.setLogin(login);
        user.setPassword(encodedPassword);
        // Сохраняем пользователя в базе данных
        userRepository.save(user);
        logger.info("User registered successfully: {}", login);
    }
}
