package com.example.EasyWalletAPI.Authentication.controllers;

import com.example.EasyWalletAPI.Authentication.service.AuthenticationService;
import com.example.EasyWalletAPI.Authentication.service.dto.AuthenticationRequestDto;
import com.example.EasyWalletAPI.Authentication.service.dto.AuthenticationResponseDto;
import com.example.EasyWalletAPI.Authentication.service.dto.RegisterRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Register and login user")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Register user", responses = {
            @ApiResponse(description = "Successfully registered",
                    responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterRequestDto.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(
            @RequestBody RegisterRequestDto registerRequestDto
    ) {
        return ResponseEntity.ok(authenticationService.register(registerRequestDto));
    }

    @Operation(summary = "User login", responses = {
            @ApiResponse(description = "Successfully logged in",
                    responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterRequestDto.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(
            @RequestBody AuthenticationRequestDto loginRequest
    ) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }
}
