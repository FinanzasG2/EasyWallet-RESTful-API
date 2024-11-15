package com.example.EasyWalletAPI.Managment.api.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class LetterResponse {
    private Long id;
    private Long usuarioId;
    private String letterNumber;
    private BigDecimal valorNominal;
    private String currency;
    private Date fechaRegistro;
    private Date fechaVencimiento;
    private Date fechaDescuento;

    private TasaResponse tasa;
    private List<CostoAdicionalResponse> costosAdicionales;
}
