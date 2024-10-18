package com.example.EasyWalletAPI.Reports.api.rest;

import com.example.EasyWalletAPI.Authentication.domain.model.entity.User;
import com.example.EasyWalletAPI.Authentication.domain.persistence.UserRepository;
import com.example.EasyWalletAPI.Reports.api.dto.response.ReporteResponse;
import com.example.EasyWalletAPI.Reports.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Report", description = "Post and get reports")
@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final UserRepository userRepository;

    // Endpoint para generar o actualizar un reporte de TCEA total
    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<ReporteResponse> generateOrUpdateReport(@PathVariable Long usuarioId) {
        // Obtener el usuario por ID
        User usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + usuarioId));

        // Generar o actualizar el reporte del usuario
        ReporteResponse reporteResponse = reportService.generateReportForUser(usuario);

        // Devolver la respuesta con el reporte generado o actualizado
        return ResponseEntity.ok(reporteResponse);
    }

    // Endpoint para obtener el reporte de un usuario por su ID
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ReporteResponse> getReportByUserId(@PathVariable Long usuarioId) {
        // Obtener el usuario por ID
        User usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + usuarioId));

        // Obtener el reporte del usuario
        ReporteResponse reporteResponse = reportService.getReportForUser(usuario);

        // Devolver la respuesta con el reporte
        return ResponseEntity.ok(reporteResponse);
    }
}
