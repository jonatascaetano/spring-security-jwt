package com.jonatas.teste_security.models.dtos;

import com.jonatas.teste_security.models.enums.UserRole;

public record RegisterDto(String name, String email, String password, UserRole role) {
    
}
