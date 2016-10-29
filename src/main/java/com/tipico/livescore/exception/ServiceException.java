package com.tipico.livescore.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(value = { "cause", "stackTrace", "localizedMessage",
	"suppressed" })
@JsonPropertyOrder({"code", "message" })
public class ServiceException extends RuntimeException {
	private final ExceptionCode code;

	public ServiceException(String message, ExceptionCode code) {
		super(message);
		this.code = code;
	}

	public ExceptionCode getCode() {
		return code;
	}
}
