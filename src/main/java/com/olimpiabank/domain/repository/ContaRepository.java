package com.olimpiabank.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.olimpiabank.domain.model.Conta;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
}
