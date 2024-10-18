package com.example.EasyWalletAPI.Reports.service;

import com.example.EasyWalletAPI.Authentication.domain.model.entity.User;
import com.example.EasyWalletAPI.Managment.domain.model.entity.Letter;
import com.example.EasyWalletAPI.Managment.domain.persistence.LetterRepository;
import com.example.EasyWalletAPI.Reports.api.dto.response.ReporteResponse;
import com.example.EasyWalletAPI.Reports.domain.model.entity.Report;
import com.example.EasyWalletAPI.Reports.domain.persistence.ReportRepository;
import com.example.EasyWalletAPI.TCEA.domain.model.entity.TCEA;
import com.example.EasyWalletAPI.TCEA.domain.persistence.TCEARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final LetterRepository letterRepository;
    private final TCEARepository tceaRepository;

    public ReporteResponse generateReportForUser(User usuario) {
        // Obtener todas las letras del usuario
        List<Letter> letters = letterRepository.findAllByUser(usuario);

        // Verificar si el usuario tiene letras
        if (letters.isEmpty()) {
            throw new RuntimeException("El usuario no tiene letras para generar el reporte.");
        }

        // Calcular el total de TCEA sumando la TCEA de cada letra del usuario
        BigDecimal tceaTotal = calculateTCEATotalForUser(letters);

        // Buscar si ya existe un reporte para este usuario
        Report report = reportRepository.findByUser(usuario).orElse(null);

        // Si el reporte ya existe, se actualiza; si no, se crea uno nuevo
        if (report == null) {
            report = new Report();
            report.setUser(usuario);
        }

        // Actualizar la información del reporte
        report.setFechaReporte(new Date());
        report.setTotalLetras(letters.size());
        report.setTceaTotal(tceaTotal);

        // Guardar el reporte actualizado en la base de datos
        report = reportRepository.save(report);

        // Mapear a ReporteResponse
        return new ReporteResponse(
                report.getId(),
                report.getUser().getId(),
                report.getFechaReporte(),
                report.getTotalLetras(),
                report.getTceaTotal()
        );
    }
    public ReporteResponse getReportForUser(User user) {
        Report reporte = reportRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado para el usuario con id " + user.getId()));

        // Mapear a ReporteResponse
        return new ReporteResponse(
                reporte.getId(),
                reporte.getUser().getId(),
                reporte.getFechaReporte(),
                reporte.getTotalLetras(),
                reporte.getTceaTotal()
        );
    }
    private BigDecimal calculateTCEATotalForUser(List<Letter> letters) {
        // Inicializar la TCEA total en cero
        BigDecimal tceaTotal = BigDecimal.ZERO;

        // Recorrer todas las letras y sumar la TCEA de cada una
        for (Letter letter : letters) {
            // Obtener la TCEA de cada letra
            TCEA tcea = tceaRepository.findByLetter(letter)
                    .orElseThrow(() -> new RuntimeException("TCEA no encontrada para la letra con id " + letter.getId()));

            // Sumar la TCEA de la letra al total
            tceaTotal = tceaTotal.add(tcea.getTcea());
        }

        // Dividir la TCEA total entre el número de letras para obtener el promedio
        if (!letters.isEmpty()) {
            tceaTotal = tceaTotal.divide(BigDecimal.valueOf(letters.size()), BigDecimal.ROUND_HALF_EVEN);
        }

        return tceaTotal;
    }



}
