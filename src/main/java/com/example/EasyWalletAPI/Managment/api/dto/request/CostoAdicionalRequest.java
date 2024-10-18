package com.example.EasyWalletAPI.Managment.api.dto.request;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CostoAdicionalRequest {
    private String descripcion;
    private BigDecimal monto;
}
