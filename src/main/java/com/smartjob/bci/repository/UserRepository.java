package com.smartjob.bci.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartjob.bci.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // Since email is unique, we'll find users by email
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
}