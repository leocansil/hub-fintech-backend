package br.com.fintech.hub.dtos;

import java.io.Serializable;

public class AccountMaintenanceError implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String status;
	private String mensagem;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
