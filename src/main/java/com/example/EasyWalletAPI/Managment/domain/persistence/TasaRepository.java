package com.example.EasyWalletAPI.Managment.domain.persistence;

import com.example.EasyWalletAPI.Managment.domain.model.entity.CostoAdicional;
import com.example.EasyWalletAPI.Managment.domain.model.entity.Letter;
import com.example.EasyWalletAPI.Managment.domain.model.entity.Tasa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TasaRepository extends JpaRepository<Tasa, Long> {
    void deleteAllByLetter(Letter letra);



}
