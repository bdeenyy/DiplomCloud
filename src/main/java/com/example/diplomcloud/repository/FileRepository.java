package com.example.diplomcloud.repository;

import com.example.diplomcloud.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    // Удаляет файл по имени
    void deleteByFilename(String filename);

//    // Обновляет имя файла
//    @Modifying
//    @Query("UPDATE FileEntity f SET f.filename = :newFilename WHERE f.filename = :filename")
//    void updateFilename(@Param("filename") String filename, @Param("newFilename") String newFilename);

    // Находит файл по имени
    FileEntity findByFilename(String filename);

    // Находит все файлы, принадлежащие пользователю с заданным идентификатором
    List<FileEntity> findAllByUserId(Long userId);

}
