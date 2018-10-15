package br.com.fintech.hub.exceptions;

public class NecessaryFieldsException extends Exception {

	private static final long serialVersionUID = 1L;

	public NecessaryFieldsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NecessaryFieldsException(String arg0) {
		super(arg0);
	}

	
	
}
