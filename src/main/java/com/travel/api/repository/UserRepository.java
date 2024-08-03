package com.travel.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.api.vo.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String username);
    User save(User user);
}
