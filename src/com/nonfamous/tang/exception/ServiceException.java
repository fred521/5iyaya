package com.nonfamous.tang.exception;

/**
 * @author fred
 * 
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 3121428621161048258L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}
