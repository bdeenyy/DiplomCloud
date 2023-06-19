package com.example.diplomcloud.repository;

import com.example.diplomcloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Находит пользователя по логину
    User findByLogin(String login);
}
