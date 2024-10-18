package com.example.EasyWalletAPI.Reports.domain.persistence;

import com.example.EasyWalletAPI.Authentication.domain.model.entity.User;
import com.example.EasyWalletAPI.Reports.domain.model.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByUser(User user);

}
