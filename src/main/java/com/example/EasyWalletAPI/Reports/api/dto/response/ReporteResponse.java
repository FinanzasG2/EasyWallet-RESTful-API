package com.example.EasyWalletAPI.Reports.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteResponse {
    private Long id;
    private Long usuarioId;
    private Date fechaReporte;
    private Integer totalLetras;
    private BigDecimal tceaTotal;
}
