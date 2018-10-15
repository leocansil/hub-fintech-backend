package br.com.fintech.hub.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.fintech.hub.dtos.enums.TipoOperacao;

@Entity
@Table(name="HISTORICO_MOVIMENTACAO")
public class HistoricoMovimentacao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_MOVIMENTACAO")
	private Long id;
	
	@Column(name = "DATA_MOVIMENTACAO")
	private LocalDateTime dataMovimentacao;

	@Column(name = "VALOR_MOVIMENTACAO")
	private BigDecimal valorMovimentado;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO_OPERACAO")
	private TipoOperacao tipoOperacao;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ID_CONTA_ORIGEM")
	private Conta contaOrigem;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ID_CONTA_DESTINO")
	private Conta contaDestino;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataMovimentacao() {
		return dataMovimentacao;
	}

	public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
		this.dataMovimentacao = dataMovimentacao;
	}

	public BigDecimal getValorMovimentado() {
		return valorMovimentado;
	}

	public void setValorMovimentado(BigDecimal valorMovimentado) {
		this.valorMovimentado = valorMovimentado;
	}

	public TipoOperacao getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(TipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Conta getContaOrigem() {
		return contaOrigem;
	}

	public void setContaOrigem(Conta contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	public Conta getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(Conta contaDestino) {
		this.contaDestino = contaDestino;
	}
	
}
