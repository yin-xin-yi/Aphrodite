package com.example.data.repository;

import org.springframework.data.jpa.repository.JpaRepository; // 注意这里导入的是 User
import org.springframework.stereotype.Repository;

import com.example.data.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { // <-- 关键修正：这里必须是 <User, Long>

}
