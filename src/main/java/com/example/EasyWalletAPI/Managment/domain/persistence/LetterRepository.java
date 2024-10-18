package com.example.EasyWalletAPI.Managment.domain.persistence;

import com.example.EasyWalletAPI.Authentication.domain.model.entity.User;
import com.example.EasyWalletAPI.Managment.domain.model.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {
    List<Letter> findAllByUser(User user);
}
