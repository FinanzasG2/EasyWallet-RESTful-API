package com.example.EasyWalletAPI.Managment.domain.persistence;

import com.example.EasyWalletAPI.Managment.domain.model.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, Long> {

}
