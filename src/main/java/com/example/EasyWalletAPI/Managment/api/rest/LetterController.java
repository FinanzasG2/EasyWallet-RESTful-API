package com.example.EasyWalletAPI.Managment.api.rest;

import com.example.EasyWalletAPI.Managment.api.dto.request.LetterRequest;
import com.example.EasyWalletAPI.Managment.api.dto.response.LetterResponse;
import com.example.EasyWalletAPI.Managment.service.LetterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Letter", description = "Register, update, delete, and get letters")
@RestController
@RequestMapping("/api/letras")
@RequiredArgsConstructor
public class LetterController {

    private final LetterService letterService;

    // Método para crear una nueva letra
    @PostMapping
    public ResponseEntity<LetterResponse> createLetter(@RequestBody LetterRequest letterRequest) {
        LetterResponse response = letterService.createLetter(letterRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Método para actualizar una letra existente
    @PutMapping("/{id}")
    public ResponseEntity<LetterResponse> updateLetter(@PathVariable Long id, @RequestBody LetterRequest letterRequest) {
        LetterResponse response = letterService.updateLetter(id, letterRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Método para obtener una letra por su ID
    @GetMapping("/{id}")
    public ResponseEntity<LetterResponse> getLetterById(@PathVariable Long id) {
        LetterResponse response = letterService.getLetterById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<LetterResponse>> getLettersByUserId(@PathVariable Long usuarioId) {
        List<LetterResponse> responses = letterService.getLettersByUserId(usuarioId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    // Método para obtener todas las letras
    @GetMapping
    public ResponseEntity<List<LetterResponse>> getAllLetters() {
        List<LetterResponse> responses = letterService.getAllLetters();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    // Método para eliminar una letra por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLetter(@PathVariable Long id) {
        letterService.deleteLetter(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}