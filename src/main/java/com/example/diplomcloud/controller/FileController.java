package com.example.diplomcloud.controller;

import com.example.diplomcloud.dto.EditFileNameRequest;
import com.example.diplomcloud.entity.FileEntity;
import com.example.diplomcloud.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public void uploadFile(@RequestHeader("auth-token") String authToken,
                           @RequestParam("filename") String filename,
                           @RequestPart("file") MultipartFile file) {
        fileService.uploadFile(authToken, filename, file);
    }

    @DeleteMapping
    public void deleteFile(@RequestHeader("auth-token") String authToken,
                           @RequestParam("filename") String filename) {
        fileService.deleteFile(authToken, filename);
    }

    @GetMapping
    public FileEntity downloadFile(@RequestHeader("auth-token") String authToken,
                                   @RequestParam("filename") String filename) {
        return fileService.downloadFile(authToken, filename);
    }

    @PutMapping
    public void editFileName(@RequestHeader("auth-token") String authToken,
                             @RequestParam("filename") String filename,
                             @RequestBody EditFileNameRequest request) {
        fileService.editFileName(authToken, filename, request.getNewFilename());
    }
}