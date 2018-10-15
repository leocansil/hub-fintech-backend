package br.com.fintech.hub.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fintech.hub.entities.enums.TipoPessoa;

@Entity
@Table(name = "PESSOAS")
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID_PESSOA", nullable = false)
	private Long id;

	@NotEmpty
	@Column(name = "NOME_RAZAO_SOCIAL", nullable = false)
	private String nomeOuRazaoSocial;
	
	@NotEmpty
	@Column(name = "CPF_CNPJ")
	private String cpfCnpj;
	
	@Column(name = "NOME_FANTASIA")
	private String nomeFantasia;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "DATA_NASCIMENTO")
	private LocalDate dataNascimento;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO_PESSOA")
	private TipoPessoa tipoPessoa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeOuRazaoSocial() {
		return nomeOuRazaoSocial;
	}

	public void setNomeOuRazaoSocial(String nomeOuRazaoSocial) {
		this.nomeOuRazaoSocial = nomeOuRazaoSocial;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
