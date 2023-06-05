package com.example.diplomcloud.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class FileService {
    private final TokenService tokenService;

    public FileService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public void uploadFile(String authToken, String filename, MultipartFile file) {
        // Проверяем токен аутентификации
        if (!tokenService.isValid(authToken)) {
            throw new RuntimeException("Недействительный токен аутентификации");
        }

        // Сохраняем файл на сервере
        try {
            Path filePath = Paths.get("/path/to/upload/dir", filename);
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении файла", e);
        }
    }

    public void deleteFile(String authToken, String filename) {
        // Проверяем токен аутентификации
        if (!tokenService.isValid(authToken)) {
            throw new RuntimeException("Недействительный токен аутентификации");
        }

        // Удаляем файл с сервера
        try {
            Path filePath = Paths.get("/path/to/upload/dir", filename);
            Files.delete(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при удалении файла", e);
        }
    }

    public Resource downloadFile(String authToken, String filename) {
        // Проверяем токен аутентификации
        if (!tokenService.isValid(authToken)) {
            throw new RuntimeException("Недействительный токен аутентификации");
        }

        // Загружаем файл с сервера
        Path filePath = Paths.get("/path/to/upload/dir", filename);
        Resource fileResource = null;
        try {
            fileResource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return fileResource;
    }

    public void editFileName(String authToken, String filename, String newFilename) {
        // Проверяем токен аутентификации
        if (!tokenService.isValid(authToken)) {
            throw new RuntimeException("Недействительный токен аутентификации");
        }

        // Изменяем имя файла на сервере
        try {
            Path oldFilePath = Paths.get("/path/to/upload/dir", filename);
            Path newFilePath = Paths.get("/path/to/upload/dir", newFilename);
            Files.move(oldFilePath, newFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при изменении имени файла", e);
        }
    }
}