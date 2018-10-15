package br.com.fintech.hub.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.fintech.hub.dtos.enums.TipoOperacao;
import br.com.fintech.hub.entities.Conta;

public class ContaFilterAporteTransferencia implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Conta contaOrigem;
	private Conta contaDestino;
	private BigDecimal valor;
	private String justificativa;
	private TipoOperacao tipoOperacao;
	
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
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	public TipoOperacao getTipoOperacao() {
		return tipoOperacao;
	}
	public void setTipoOperacao(TipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}
	
	
}
