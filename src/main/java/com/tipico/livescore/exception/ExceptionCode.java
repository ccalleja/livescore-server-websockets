package com.tipico.livescore.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionCode {

	// Specific errors
	EVENT_MAPPING_ERROR(HttpStatus.NOT_FOUND, "Event mapping error.");

	private HttpStatus code;
	private String message;

	private ExceptionCode(HttpStatus code, String message) {
		this.code = code;
		this.message = message;
	}

	public HttpStatus getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
