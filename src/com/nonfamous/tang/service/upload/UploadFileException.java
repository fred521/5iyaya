/*
 * Created on 2006-3-15
 */
package com.nonfamous.tang.service.upload;

/**
 * @author fred
 * @version $id$
 */
public class UploadFileException extends Exception {

	private static final long serialVersionUID = -2799657332043304031L;

	public UploadFileException() {
		super();
	}

	public UploadFileException(String message) {
		super(message);
	}

	public UploadFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public UploadFileException(Throwable cause) {
		super(cause);
	}
}