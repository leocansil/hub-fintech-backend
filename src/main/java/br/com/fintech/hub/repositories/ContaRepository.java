package br.com.fintech.hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fintech.hub.entities.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {

}
