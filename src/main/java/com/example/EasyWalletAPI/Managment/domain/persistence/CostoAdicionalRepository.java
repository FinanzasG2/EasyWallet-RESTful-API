package com.example.EasyWalletAPI.Managment.domain.persistence;

import com.example.EasyWalletAPI.Managment.domain.model.entity.CostoAdicional;
import com.example.EasyWalletAPI.Managment.domain.model.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CostoAdicionalRepository extends JpaRepository<CostoAdicional, Long> {
    void deleteAllByLetter(Letter letra);

}
