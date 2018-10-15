package br.com.fintech.hub.exceptions;

public class InsufficientFundsException extends Exception {

	private static final long serialVersionUID = 1L;

	public InsufficientFundsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InsufficientFundsException(String arg0) {
		super(arg0);
	}

	
	
}
