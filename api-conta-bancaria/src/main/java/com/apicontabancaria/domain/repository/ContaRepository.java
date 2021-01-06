package com.apicontabancaria.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apicontabancaria.domain.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

}
