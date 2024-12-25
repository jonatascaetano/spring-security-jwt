package com.jonatas.teste_security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jonatas.teste_security.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{
    
    Optional<UserModel> findByEmail(String email);
}
