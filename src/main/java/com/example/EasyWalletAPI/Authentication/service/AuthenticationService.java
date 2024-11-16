package com.example.EasyWalletAPI.Authentication.service;


import com.example.EasyWalletAPI.Authentication.domain.model.entity.User;
import com.example.EasyWalletAPI.Authentication.domain.persistence.UserRepository;
import com.example.EasyWalletAPI.Authentication.service.dto.AuthenticationRequestDto;
import com.example.EasyWalletAPI.Authentication.service.dto.AuthenticationResponseDto;
import com.example.EasyWalletAPI.Authentication.service.dto.RegisterRequestDto;
import com.example.EasyWalletAPI.shared.exception.GlobalExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDto register(RegisterRequestDto registerRequestDto){
        if(userRepository.existsByEmail(registerRequestDto.getEmail())){
            throw new GlobalExceptionHandler("User","Email already exists");
        }

        if(!registerRequestDto.getPassword().matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$")){
            throw new GlobalExceptionHandler("User","Password must contain at least one letter, at least one number, and be longer than six characters");
        }

        if(!registerRequestDto.getEmail().matches("^(.+)@(.+)$")){
            throw new GlobalExceptionHandler("User","Email is not valid");
        }

        var user = User.builder()
                .name(registerRequestDto.getName())
                .username(registerRequestDto.getUsername())
                .email(registerRequestDto.getEmail())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .build();
        userRepository.save(user);
        var jwttoken = jwtService.generateToken(user, user.getId());
        return AuthenticationResponseDto.builder()
                .token(jwttoken)
                .user(user)
                .build();
    }

    public AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequestDto.getEmail(),
                            authenticationRequestDto.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new GlobalExceptionHandler("User","Email or password is incorrect");
        }
        var user = userRepository.findByEmail(authenticationRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Email or password is incorrect"));

        var jwtToken = jwtService.generateToken(user, user.getId());
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .user(user)
                .build();
    }
}
