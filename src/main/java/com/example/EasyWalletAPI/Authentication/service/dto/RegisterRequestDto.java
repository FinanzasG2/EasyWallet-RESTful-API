package com.example.EasyWalletAPI.Authentication.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    private String name;
    private String username;
    private String email;
    private String password;
}