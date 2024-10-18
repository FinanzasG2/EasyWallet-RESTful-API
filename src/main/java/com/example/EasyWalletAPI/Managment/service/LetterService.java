package com.example.EasyWalletAPI.Managment.service;

import com.example.EasyWalletAPI.Authentication.domain.model.entity.User;
import com.example.EasyWalletAPI.Authentication.domain.persistence.UserRepository;
import com.example.EasyWalletAPI.Managment.api.dto.request.CostoAdicionalRequest;
import com.example.EasyWalletAPI.Managment.api.dto.request.LetterRequest;
import com.example.EasyWalletAPI.Managment.api.dto.response.CostoAdicionalResponse;
import com.example.EasyWalletAPI.Managment.api.dto.response.LetterResponse;
import com.example.EasyWalletAPI.Managment.api.dto.response.TasaResponse;
import com.example.EasyWalletAPI.Managment.domain.model.entity.*;
import com.example.EasyWalletAPI.Managment.domain.persistence.CostoAdicionalRepository;
import com.example.EasyWalletAPI.Managment.domain.persistence.LetterRepository;
import com.example.EasyWalletAPI.Managment.domain.persistence.TasaRepository;
import com.example.EasyWalletAPI.TCEA.service.TCEAService;
import com.example.EasyWalletAPI.shared.exception.GlobalExceptionHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LetterService {
    @Autowired
    private LetterRepository letterRepository;
    @Autowired
    private TasaRepository tasaRepository;
    @Autowired
    private CostoAdicionalRepository costoAdicionalRepository;
    @Autowired
    private TasaService tasaService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TCEAService tceaService;

    public LetterResponse createLetter(LetterRequest letterRequest) {
        Letter letter = new Letter();

        // Configuración de los atributos de la letra
        letter.setValorNominal(letterRequest.getValorNominal());
        letter.setFechaRegistro(letterRequest.getFechaRegistro());
        letter.setFechaVencimiento(letterRequest.getFechaVencimiento());
        letter.setFechaDescuento(letterRequest.getFechaDescuento());
        User user = userRepository.findById(letterRequest.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        letter.setUser(user);

        // Guardar letra y costos adicionales
        letter = letterRepository.save(letter);

        saveCostosAdicionales(letterRequest.getCostosAdicionales(), letter);

        // Manejo de la tasa
        Tasa tasa = createAndConvertTasa(letterRequest, letter);

        // Calcular TCEA
        tceaService.calculateTCEA(letter, tasa.getValor());

        return mapToResponse(letter, tasa);
    }

    @Transactional
    public LetterResponse updateLetter(Long letterId, LetterRequest letterRequest) {
        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new GlobalExceptionHandler("Letter", "not found with id " + letterId));

        // Actualizar valores de la carta
        letter.setValorNominal(letterRequest.getValorNominal());
        letter.setFechaRegistro(letterRequest.getFechaRegistro());
        letter.setFechaVencimiento(letterRequest.getFechaVencimiento());
        letter.setFechaDescuento(letterRequest.getFechaDescuento());

        // Eliminar tasas, costos adicionales anteriores y TCEA
        tasaRepository.deleteAllByLetter(letter);
        costoAdicionalRepository.deleteAllByLetter(letter);
        tceaService.deleteTCEA(letter);

        // Asegurarse de que se apliquen las eliminaciones antes de las inserciones
        letterRepository.flush();  // Sincroniza con la base de datos

        // Crear nueva tasa y costos adicionales
        Tasa tasa = createAndConvertTasa(letterRequest, letter);
        saveCostosAdicionales(letterRequest.getCostosAdicionales(), letter);

        // Guardar la letra actualizada
        letter = letterRepository.save(letter);

        tceaService.calculateTCEA(letter, tasa.getValor());

        return mapToResponse(letter, tasa);
    }

    public LetterResponse getLetterById(Long id) {
        Letter letter = letterRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler("Letter", "not found with id " + id));
        return mapToResponse(letter, letter.getTasas().get(0));
    }

    public List<LetterResponse> getAllLetters() {
        List<Letter> letters = letterRepository.findAll();
        return letters.stream()
                .map(letter -> mapToResponse(letter, letter.getTasas().get(0)))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteLetter(Long id) {
        Letter letter = letterRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler("Letter", "not found with id " + id));

        // Eliminar los costos adicionales asociados
        costoAdicionalRepository.deleteAllByLetter(letter);
        tasaRepository.deleteAllByLetter(letter);
        tceaService.deleteTCEA(letter);


        // Eliminar la letra
        letterRepository.delete(letter);
    }

    private Tasa createAndConvertTasa(LetterRequest letterRequest, Letter letter) {
        Tasa tasa = new Tasa();
        tasa.setTipo(letterRequest.getTipoTasa());
        tasa.setValor(letterRequest.getValorTasa());
        tasa.setCapitalizacion(letterRequest.getCapitalizacionTasa());

        if (tasa.getTipo() != Tipo.EFECTIVA || tasa.getCapitalizacion() != Capitalizacion.ANUAL) {
            BigDecimal tasaConvertida = tasaService.convertirATasaEfectivaAnual(tasa);
            tasa.setTipo(Tipo.EFECTIVA);
            tasa.setValor(tasaConvertida);
            tasa.setCapitalizacion(Capitalizacion.ANUAL);
        }

        tasa.setLetter(letter);
        return tasaRepository.save(tasa);
    }


    private List<CostoAdicional> saveCostosAdicionales(List<CostoAdicionalRequest> costoAdicionalRequests, Letter letter) {
        List<CostoAdicional> costos = costoAdicionalRequests.stream()
                .map(request -> new CostoAdicional(null, letter, request.getDescripcion(), request.getMonto(), request.getTiempo()))
                .collect(Collectors.toList());
        return costoAdicionalRepository.saveAll(costos);
    }

    private LetterResponse mapToResponse(Letter letter, Tasa tasa) {
        LetterResponse response = new LetterResponse();
        response.setId(letter.getId());
        response.setUsuarioId(letter.getUser().getId());
        response.setValorNominal(letter.getValorNominal());
        response.setFechaRegistro(letter.getFechaRegistro());
        response.setFechaVencimiento(letter.getFechaVencimiento());
        response.setFechaDescuento(letter.getFechaDescuento());

        TasaResponse tasaResponse = new TasaResponse();
        tasaResponse.setId(tasa.getId());
        tasaResponse.setValor(tasa.getValor());
        tasaResponse.setTipo(tasa.getTipo());
        tasaResponse.setCapitalizacion(tasa.getCapitalizacion());
        response.setTasa(tasaResponse);

        List<CostoAdicionalResponse> costoResponses = letter.getCostosAdicionales().stream()
                .map(costo -> new CostoAdicionalResponse(costo.getId(), costo.getDescripcion(), costo.getMonto(), costo.getTiempo()))
                .collect(Collectors.toList());
        response.setCostosAdicionales(costoResponses);

        return response;
    }
    public Letter findLetterById(Long id) {
        return letterRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler("Letter", "not found with id " + id));
    }

}
