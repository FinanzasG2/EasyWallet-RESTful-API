package com.example.EasyWalletAPI.Managment.service;

import com.example.EasyWalletAPI.Managment.domain.model.entity.Capitalizacion;
import com.example.EasyWalletAPI.Managment.domain.model.entity.Frecuency;
import com.example.EasyWalletAPI.Managment.domain.model.entity.Tasa;
import com.example.EasyWalletAPI.Managment.domain.model.entity.Tipo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TasaService {
    // Convierte una tasa nominal a una tasa efectiva
    public BigDecimal convertirTasaNominalAEfectiva(BigDecimal tasaNominal, int n, int m) {
        // Fórmula: TEP = (1 + (TN / m))^n - 1
        return BigDecimal.valueOf(100*(Math.pow(1 + (tasaNominal.doubleValue()*n)/(100*m),m)-1));
    }

    // Convierte una tasa efectiva a otro periodo
    public BigDecimal convertirTasaEfectiva(BigDecimal tasaEfectiva, int n) {
        // Fórmula: TEP2 = (1 + TEP1)^(n2 / n1) - 1
        return BigDecimal.valueOf(100*(Math.pow(1+tasaEfectiva.doubleValue()/100,n)-1));
    }

    // Obtiene el número de periodos según la capitalización
    public BigDecimal obtenerFactorCapitalizacion(Capitalizacion capitalizacion) {
        switch (capitalizacion) {
            case DIARIA:
                return BigDecimal.valueOf(360);
            case MENSUAL:
                return BigDecimal.valueOf(12);
            case TRIMESTRAL:
                return BigDecimal.valueOf(4);
            case SEMESTRAL:
                return BigDecimal.valueOf(2);
            case ANUAL:
                return BigDecimal.valueOf(1);
            case BIMESTRAL:
                return BigDecimal.valueOf(6);
            case CUATRIMESTRAL:
                return BigDecimal.valueOf(3);
            case QUINCENAL:
                return BigDecimal.valueOf(24);
            default:
                throw new IllegalArgumentException("Capitalización no soportada.");
        }
    }

    public BigDecimal obtenerFactorFrecuencia(Frecuency frecuency) {
        switch (frecuency) {
            case DIARIA:
                return BigDecimal.valueOf(360);
            case MENSUAL:
                return BigDecimal.valueOf(12);
            case TRIMESTRAL:
                return BigDecimal.valueOf(4);
            case SEMESTRAL:
                return BigDecimal.valueOf(2);
            case ANUAL:
                return BigDecimal.valueOf(1);
            case BIMESTRAL:
                return BigDecimal.valueOf(6);
            case CUATRIMESTRAL:
                return BigDecimal.valueOf(3);
            case QUINCENAL:
                return BigDecimal.valueOf(24);
            default:
                throw new IllegalArgumentException("Frecuencia no soportada.");
        }
    }

    // Convierte a Tasa Efectiva Anual si la tasa no es efectiva anual
    public BigDecimal convertirATasaEfectivaAnual(Tasa tasa) {
        BigDecimal factor = obtenerFactorFrecuencia(tasa.getFrecuency());

        // Si la tasa es nominal, convertir a efectiva
        if (tasa.getTipo() == Tipo.NOMINAL) {
            BigDecimal factor2 = obtenerFactorCapitalizacion(tasa.getCapitalizacion());
            return convertirTasaNominalAEfectiva(tasa.getValor(), factor.intValue(), factor2.intValue());
        } else if (tasa.getTipo() == Tipo.EFECTIVA) {
            // Si la tasa ya es efectiva, convertir a otro periodo
            return convertirTasaEfectiva(tasa.getValor(), factor.intValue());
        } else {
            throw new IllegalArgumentException("Tipo de tasa no soportada.");
        }

    }
}
