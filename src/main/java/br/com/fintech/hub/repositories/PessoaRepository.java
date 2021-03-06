package br.com.fintech.hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fintech.hub.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	Pessoa findByCpfCnpj(String cpfCnpj);
}
