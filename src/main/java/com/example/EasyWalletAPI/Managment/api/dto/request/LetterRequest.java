package com.example.EasyWalletAPI.Managment.api.dto.request;

import com.example.EasyWalletAPI.Managment.domain.model.entity.Capitalizacion;
import com.example.EasyWalletAPI.Managment.domain.model.entity.Tipo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class LetterRequest {
    private Long usuarioId;
    private BigDecimal valorNominal;
    private Date fechaRegistro;
    private Date fechaVencimiento;
    private Date fechaDescuento;

    private BigDecimal valorTasa;
    private Tipo tipoTasa;
    private Capitalizacion capitalizacionTasa;

    private List<CostoAdicionalRequest> costosAdicionales;
}
