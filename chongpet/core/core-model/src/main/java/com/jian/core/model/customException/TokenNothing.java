package com.jian.core.model.customException;

public class TokenNothing extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenNothing() {
	}

	public TokenNothing(String message) {
		super(message);
	}

	public TokenNothing(Throwable cause) {
		super(cause);
	}

	public TokenNothing(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenNothing(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
