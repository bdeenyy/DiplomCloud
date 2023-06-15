package com.example.diplomcloud.service;

import com.example.diplomcloud.entity.FileEntity;
import com.example.diplomcloud.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    private final TokenService tokenService;
    private final FileRepository fileRepository;

    public FileService(TokenService tokenService, FileRepository fileRepository) {
        this.tokenService = tokenService;
        this.fileRepository = fileRepository;
    }

    public void uploadFile(String authToken, String filename, MultipartFile file) {
        // Проверяем токен аутентификации
        checkAuthToken(authToken);

        // Получаем идентификатор пользователя из токена аутентификации
        Long userId = tokenService.getUserIdFromAuthToken(authToken);

        // Сохраняем информацию о файле в базе данных
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFilename(filename);
        fileEntity.setSize(file.getSize());
        fileEntity.setUserId(userId); // Сохраняем идентификатор пользователя
        fileRepository.save(fileEntity);
    }

    public void deleteFile(String authToken, String filename) {
        // Проверяем токен аутентификации
        checkAuthToken(authToken);

        // Удаляем информацию о файле из базы данных
        fileRepository.deleteByFilename(filename);
    }

    public FileEntity downloadFile(String authToken, String filename) {
        // Проверяем токен аутентификации
        checkAuthToken(authToken);

        // Получаем информацию о файле из базы данных
        return fileRepository.findByFilename(filename);
    }

    public void editFileName(String authToken, String filename, String newFilename) {
        // Проверяем токен аутентификации
        checkAuthToken(authToken);

        // Изменяем имя файла в базе данных
        fileRepository.updateFilename(filename, newFilename);
    }

    public boolean checkAuthToken(String authToken) {
        // Проверяем токен аутентификации с помощью TokenService
        return tokenService.isValid(authToken);
    }
}