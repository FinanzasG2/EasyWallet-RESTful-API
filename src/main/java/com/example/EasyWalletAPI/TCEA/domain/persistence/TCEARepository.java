package com.example.EasyWalletAPI.TCEA.domain.persistence;

import com.example.EasyWalletAPI.Managment.domain.model.entity.Letter;
import com.example.EasyWalletAPI.TCEA.domain.model.entity.TCEA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TCEARepository extends JpaRepository<TCEA, Long> {
    Optional<TCEA> findByLetter(Letter letter);
    void deleteByLetter(Letter letter);
}