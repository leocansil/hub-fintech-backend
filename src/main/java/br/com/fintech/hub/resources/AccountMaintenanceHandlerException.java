package br.com.fintech.hub.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.fintech.hub.dtos.AccountMaintenanceError;
import br.com.fintech.hub.exceptions.DataDuplicityException;
import br.com.fintech.hub.exceptions.InsufficientFundsException;
import br.com.fintech.hub.exceptions.NecessaryFieldsException;
import br.com.fintech.hub.utils.ConstantesUtils;
import javassist.NotFoundException;

@ControllerAdvice
public class AccountMaintenanceHandlerException {

	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<AccountMaintenanceError> handlerException(Exception ex) {
		AccountMaintenanceError ss = new AccountMaintenanceError();
		ss.setStatus(ConstantesUtils.STATUS_ERRO);
		ss.setMensagem("Erro ao processar dados: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ss);
	}
	
	@ResponseBody
	@ExceptionHandler(NecessaryFieldsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<AccountMaintenanceError> handlerNecessaryFieldsException(NecessaryFieldsException ex) {
		AccountMaintenanceError ss = new AccountMaintenanceError();
		ss.setStatus(ConstantesUtils.STATUS_ERRO);
		ss.setMensagem("Erro ao processar dados: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ss);
	}
	
	@ResponseBody
	@ExceptionHandler(DataDuplicityException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<AccountMaintenanceError> handlerDataDuplicityException(DataDuplicityException ex) {
		AccountMaintenanceError ss = new AccountMaintenanceError();
		ss.setStatus(ConstantesUtils.STATUS_ERRO);
		ss.setMensagem("Erro ao processar dados: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ss);
	}

	@ResponseBody
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<AccountMaintenanceError> handlerNotFoundException(NotFoundException ex) {
		AccountMaintenanceError ss = new AccountMaintenanceError();
		ss.setStatus(ConstantesUtils.STATUS_ERRO);
		ss.setMensagem("Erro ao processar dados: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ss);
	}
	
	@ResponseBody
	@ExceptionHandler(InsufficientFundsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<AccountMaintenanceError> handlerDataDuplicityException(InsufficientFundsException ex) {
		AccountMaintenanceError ss = new AccountMaintenanceError();
		ss.setStatus(ConstantesUtils.STATUS_ERRO);
		ss.setMensagem("Conta não possui saldo suficiente: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ss);
	}
}
