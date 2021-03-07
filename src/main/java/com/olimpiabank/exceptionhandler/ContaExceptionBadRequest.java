package com.olimpiabank.exceptionhandler;

public class ContaExceptionBadRequest extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContaExceptionBadRequest(String message) {
		super(message);
	}
}
