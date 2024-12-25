package com.jonatas.teste_security.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jonatas.teste_security.models.UserModel;
import com.jonatas.teste_security.repositories.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserModel> getUsers() {
        // Recupera o nome de usuário do SecurityContext
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("username: " + username);
        return userRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public UserModel getUserById(@PathVariable Long id) {
        // Recupera o nome de usuário do SecurityContext
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("username: " + username);
        return userRepository.findById(id).orElse(null);
    }

}
