package com.example.EasyWalletAPI.TCEA.service;

import com.example.EasyWalletAPI.Managment.domain.model.entity.CostoAdicional;
import com.example.EasyWalletAPI.Managment.domain.model.entity.Letter;
import com.example.EasyWalletAPI.Managment.domain.model.entity.Tiempo;
import com.example.EasyWalletAPI.Managment.domain.persistence.CostoAdicionalRepository;
import com.example.EasyWalletAPI.TCEA.api.dto.response.TCEAresponse;
import com.example.EasyWalletAPI.TCEA.domain.model.entity.TCEA;
import com.example.EasyWalletAPI.TCEA.domain.persistence.TCEARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TCEAService {
    private final TCEARepository tceaRepository;
    private final CostoAdicionalRepository costoAdicionalRepository;

    public TCEAresponse calculateTCEA(Letter letter, BigDecimal tasaEfectiva) {
        // Calcular la diferencia de días
        int diasTrasladar = (int) ((letter.getFechaDescuento().getTime() - letter.getFechaRegistro().getTime()) / (1000 * 60 * 60 * 24));
        System.out.println("Días a trasladar: " + diasTrasladar);

        // Calcular la tasa efectiva convertida para los días a trasladar
        BigDecimal tasaConvertida = BigDecimal.valueOf(Math.pow(1 + tasaEfectiva.doubleValue() / 100, (double) diasTrasladar / 360) - 1);
        System.out.println("Tasa efectiva convertida: " + tasaConvertida);


        // Calcular la tasa de descuento
        BigDecimal tasaDescuento = tasaConvertida.divide(BigDecimal.ONE.add(tasaConvertida), BigDecimal.ROUND_HALF_EVEN);
        System.out.println("Tasa de descuento: " + tasaDescuento);


        // Calcular el Descuento
        BigDecimal descuento = letter.getValorNominal().multiply(tasaDescuento);
        System.out.println("Descuento: " + descuento);

        // Cargar los costos adicionales explícitamente desde el repositorio
        List<CostoAdicional> costosAdicionales = costoAdicionalRepository.findByLetter(letter);
        System.out.println("Lista de costos adicionales:");
        costosAdicionales.forEach(c -> System.out.println("Costo: " + c.getDescripcion() + ", Monto: " + c.getMonto() + ", Tiempo: " + c.getTiempo()));


        // Calcular el valor recibido
        // Filtrar y sumar los costos al inicio
        List<BigDecimal> costosInicio = costosAdicionales.stream()
                .filter(c -> c.getTiempo() == Tiempo.INICIO)
                .map(CostoAdicional::getMonto)
                .collect(Collectors.toList());
        BigDecimal sumaCostosInicio = costosInicio.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Suma de costos adicionales al inicio: " + sumaCostosInicio);

        BigDecimal valorRecibido = letter.getValorNominal().subtract(descuento).subtract(sumaCostosInicio);
        System.out.println("Valor recibido: " + valorRecibido);

        // Calcular el valor entregado
        // Filtrar y sumar los costos al vencimiento
        List<BigDecimal> costosVencimiento = costosAdicionales.stream()
                .filter(c -> c.getTiempo() == Tiempo.VENCIMIENTO)
                .map(CostoAdicional::getMonto)
                .collect(Collectors.toList());
        BigDecimal sumaCostosVencimiento = costosVencimiento.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Suma de costos adicionales al vencimiento: " + sumaCostosVencimiento);

        BigDecimal valorEntregado = letter.getValorNominal().add(sumaCostosVencimiento);
        System.out.println("Valor entregado: " + valorEntregado);



        // Calcular la TCEA según la fórmula
        if (valorRecibido.compareTo(BigDecimal.ZERO) > 0) {  // Prevenir división por cero
            BigDecimal tcea = BigDecimal.valueOf(Math.pow(valorEntregado.divide(valorRecibido, 8, BigDecimal.ROUND_HALF_EVEN).doubleValue(), 360.0 / diasTrasladar) - 1)
                    .multiply(BigDecimal.valueOf(100));  // Convertimos a porcentaje
            System.out.println("TCEA calculada: " + tcea);

            // Guardar la entidad TCEA
            TCEA tceaEntity = new TCEA();
            tceaEntity.setLetter(letter);
            tceaEntity.setValorEntregado(valorEntregado);
            tceaEntity.setValorRecibido(valorRecibido);
            tceaEntity.setTcea(tcea);

            tceaRepository.save(tceaEntity);

            return new TCEAresponse(tceaEntity.getId(), valorEntregado, valorRecibido, tcea);
        } else {
            throw new RuntimeException("El valor recibido no puede ser 0 o negativo.");
        }
    }

    public TCEAresponse getTCEAByLetter(Letter letter) {
        TCEA tcea = tceaRepository.findByLetter(letter)
                .orElseThrow(() -> new RuntimeException("TCEA not found for letter with id " + letter.getId()));
        return new TCEAresponse(tcea.getId(), tcea.getValorEntregado(), tcea.getValorRecibido(), tcea.getTcea());
    }
    // Método para eliminar la TCEA asociada a una letra
    public void deleteTCEA(Letter letter) {
        tceaRepository.deleteByLetter(letter);
    }
}
