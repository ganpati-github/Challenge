package com.challenge.exception;

public class ApplicationRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -2473008991473822800L;

	public ApplicationRuntimeException() {
		super();
	}

	public ApplicationRuntimeException(String errorMessage) {
		super(errorMessage);
	}

	public ApplicationRuntimeException(String errorMessage, Throwable err) {
	    super(errorMessage, err);
	}
}
