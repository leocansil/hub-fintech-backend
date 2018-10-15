package br.com.fintech.hub.exceptions;

public class DataDuplicityException extends Exception {

	private static final long serialVersionUID = 1L;

	public DataDuplicityException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DataDuplicityException(String arg0) {
		super(arg0);
	}

	
	
}
