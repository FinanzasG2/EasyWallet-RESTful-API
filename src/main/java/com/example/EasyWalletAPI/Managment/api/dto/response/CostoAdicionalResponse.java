package com.example.EasyWalletAPI.Managment.api.dto.response;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CostoAdicionalResponse {
    private Long id;
    private String descripcion;
    private BigDecimal monto;

    public CostoAdicionalResponse(Long id, String descripcion, BigDecimal monto) {
        this.id = id;
        this.descripcion = descripcion;
        this.monto = monto;
    }
}
