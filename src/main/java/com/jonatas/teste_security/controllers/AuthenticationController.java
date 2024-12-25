package com.jonatas.teste_security.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.jonatas.teste_security.models.UserModel;
import com.jonatas.teste_security.models.dtos.LoginDto;
import com.jonatas.teste_security.models.dtos.RefreshTokenRequestDto;
import com.jonatas.teste_security.models.dtos.RegisterDto;
import com.jonatas.teste_security.models.dtos.TokenResponseDto;
import com.jonatas.teste_security.repositories.UserRepository;
import com.jonatas.teste_security.services.TokenService;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenService tokenService;

  // Método de login com retorno de resposta adequada
  @PostMapping(value = "/login")
  public ResponseEntity<TokenResponseDto> login(@RequestBody LoginDto user) {
    try {
      // UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(user.email(),
      //     user.password());
      // Authentication auth = authenticationManager.authenticate(usernamePassword);
      // String token = tokenService.generateAccessToken(auth.getName());
      // // Se a autenticação for bem-sucedida, você pode gerar um token JWT aqui
       // A autenticação de usuário seria feita aqui (verificação de email e senha)
        // Supondo que o usuário foi autenticado corretamente, geramos o access token e refresh token
        String accessToken = tokenService.generateAccessToken(user.email());
        String refreshToken = tokenService.generateRefreshToken(user.email());

        return ResponseEntity.ok(new TokenResponseDto(accessToken, refreshToken));
      // return ResponseEntity.ok(token);
    } catch (Exception e) {
      return ResponseEntity.status(401).build();
    }
  }

  @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        try {
            // Valida o refresh token
            String username = tokenService.validateRefreshToken(refreshToken);

            // Gera um novo access token com base no refresh token
            String newAccessToken = tokenService.generateAccessToken(username);

            // Retorna o novo access token
            return ResponseEntity.ok(new TokenResponseDto(newAccessToken, refreshToken)); // Retornamos o mesmo refresh token
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Refresh token inválido");
        }
    }

  // Método de registro com verificação de usuário já existente
  @PostMapping(value = "/register")
  public ResponseEntity<String> register(@RequestBody RegisterDto user) {
    Optional<UserModel> existingUser = userRepository.findByEmail(user.email());
    if (existingUser.isPresent()) {
      return ResponseEntity.status(400).body("Usuário já registrado");
    }

    String passwordEncrypt = new BCryptPasswordEncoder().encode(user.password());
    UserModel userModel = new UserModel();
    userModel.setName(user.name());
    userModel.setEmail(user.email());
    userModel.setPassword(passwordEncrypt);
    userModel.setRole(user.role());
    userRepository.save(userModel);
    return ResponseEntity.status(201).body("Usuário registrado com sucesso");
  }
}
