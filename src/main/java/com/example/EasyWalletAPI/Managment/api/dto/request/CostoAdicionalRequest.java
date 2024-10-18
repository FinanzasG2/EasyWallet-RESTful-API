package com.example.EasyWalletAPI.Managment.api.dto.request;

import com.example.EasyWalletAPI.Managment.domain.model.entity.Tiempo;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class CostoAdicionalRequest {
    private String descripcion;
    private BigDecimal monto;
    private Tiempo tiempo;
}
