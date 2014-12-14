package com.nonfamous.tang.service.search;

public class SearchException extends Exception {

	private static final long serialVersionUID = -2799657332043304031L;

	public SearchException() {
		super();
	}

	public SearchException(String message) {
		super(message);
	}

	public SearchException(String message, Throwable cause) {
		super(message, cause);
	}

	public SearchException(Throwable cause) {
		super(cause);
	}
}