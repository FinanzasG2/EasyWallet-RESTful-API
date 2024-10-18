package com.example.EasyWalletAPI.Managment.api.dto.response;

import com.example.EasyWalletAPI.Managment.domain.model.entity.Capitalizacion;
import com.example.EasyWalletAPI.Managment.domain.model.entity.Tipo;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class TasaResponse {
    private Long id;
    private BigDecimal valor;
    private Tipo tipo;
    private Capitalizacion capitalizacion;

}
