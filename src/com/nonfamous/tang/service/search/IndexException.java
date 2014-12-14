package com.nonfamous.tang.service.search;

public class IndexException extends Exception {

	private static final long serialVersionUID = -2799657332043304031L;

	public IndexException() {
		super();
	}

	public IndexException(String message) {
		super(message);
	}

	public IndexException(String message, Throwable cause) {
		super(message, cause);
	}

	public IndexException(Throwable cause) {
		super(cause);
	}
}