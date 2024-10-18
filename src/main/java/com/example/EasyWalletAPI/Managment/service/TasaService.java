package com.example.EasyWalletAPI.Managment.service;

import com.example.EasyWalletAPI.Managment.domain.model.entity.Capitalizacion;
import com.example.EasyWalletAPI.Managment.domain.model.entity.Tasa;
import com.example.EasyWalletAPI.Managment.domain.model.entity.Tipo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TasaService {
    // Convierte una tasa nominal a una tasa efectiva
    public BigDecimal convertirTasaNominalAEfectiva(BigDecimal tasaNominal, int m) {
        // Fórmula: TEP = (1 + (TN / m))^n - 1
        return BigDecimal.valueOf((Math.pow(1 + (tasaNominal.doubleValue()/100) / m, 360) - 1)*100);
    }

    // Convierte una tasa efectiva a otro periodo
    public BigDecimal convertirTasaEfectiva(BigDecimal tasaEfectiva, int n1) {
        // Fórmula: TEP2 = (1 + TEP1)^(n2 / n1) - 1
        return BigDecimal.valueOf((Math.pow(1 + (tasaEfectiva.doubleValue()/100), (double) 360 / n1) - 1)*100);
    }

    // Obtiene el número de periodos según la capitalización
    public BigDecimal obtenerFactorCapitalizacion(Capitalizacion capitalizacion) {
        switch (capitalizacion) {
            case DIARIA:
                return BigDecimal.valueOf(1);
            case MENSUAL:
                return BigDecimal.valueOf(30);
            case TRIMESTRAL:
                return BigDecimal.valueOf(90);
            case SEMESTRAL:
                return BigDecimal.valueOf(180);
            case ANUAL:
                return BigDecimal.valueOf(365);
            case BIMESTRAL:
                return BigDecimal.valueOf(60);
            case CUATRIMESTRAL:
                return BigDecimal.valueOf(120);
            case QUINCENAL:
                return BigDecimal.valueOf(15);
            default:
                throw new IllegalArgumentException("Capitalización no soportada.");
        }
    }

    // Convierte a Tasa Efectiva Anual si la tasa no es efectiva anual
    public BigDecimal convertirATasaEfectivaAnual(Tasa tasa) {
        BigDecimal factor = obtenerFactorCapitalizacion(tasa.getCapitalizacion());

        // Si la tasa es nominal, convertir a efectiva
        if (tasa.getTipo() == Tipo.NOMINAL) {
            return convertirTasaNominalAEfectiva(tasa.getValor(), factor.intValue());
        } else if (tasa.getTipo() == Tipo.EFECTIVA) {
            // Si la tasa ya es efectiva, convertir a otro periodo
            return convertirTasaEfectiva(tasa.getValor(), factor.intValue());
        } else {
            throw new IllegalArgumentException("Tipo de tasa no soportada.");
        }

    }
}
