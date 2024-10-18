package com.example.EasyWalletAPI.TCEA.api.rest;

import com.example.EasyWalletAPI.Managment.domain.model.entity.Letter;
import com.example.EasyWalletAPI.Managment.service.LetterService;
import com.example.EasyWalletAPI.TCEA.api.dto.response.TCEAresponse;
import com.example.EasyWalletAPI.TCEA.service.TCEAService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "TCEA", description = "Get TCEA")
@RestController
@RequestMapping("/api/TCEA")
@RequiredArgsConstructor
public class TCEAController {
    private final TCEAService tceaService;
    private final LetterService letterService;

    // Obtener TCEA por id de letter
    @GetMapping("/letter/{id}")
    public ResponseEntity<TCEAresponse> getTCEAByLetterId(@PathVariable Long id) {
        Letter letter = letterService.findLetterById(id);
        TCEAresponse response = tceaService.getTCEAByLetter(letter);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
