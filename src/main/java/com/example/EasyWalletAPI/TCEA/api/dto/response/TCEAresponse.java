package com.example.EasyWalletAPI.TCEA.api.dto.response;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class TCEAresponse {
    private Long id;
    private BigDecimal valorEntregado;
    private BigDecimal valorRecibido;
    private BigDecimal tcea;

    public TCEAresponse(Long id, BigDecimal valorEntregado, BigDecimal valorRecibido, BigDecimal tcea) {
        this.id = id;
        this.valorEntregado = valorEntregado;
        this.valorRecibido = valorRecibido;
        this.tcea = tcea;
    }
}
