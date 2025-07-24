package com.example.core.exception;




public class AccountClosedException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountClosedException(String message) {
        super(message);
    }
}