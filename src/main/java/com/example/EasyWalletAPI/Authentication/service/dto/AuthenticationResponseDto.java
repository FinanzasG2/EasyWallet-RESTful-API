package com.example.EasyWalletAPI.Authentication.service.dto;

import com.example.EasyWalletAPI.Authentication.domain.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {
    private String token;
    private User user;
}