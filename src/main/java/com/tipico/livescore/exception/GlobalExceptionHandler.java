package com.tipico.livescore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jsetzepfand on 29/10/16.
 */
public class GlobalExceptionHandler {
	/**
	 * Error handling for service exceptions
	 *
	 * @param e The exception.
	 * @return Error data for display.
	 */
	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	public ResponseEntity<String> defaultErrorHandler(ServiceException e) {
		return new ResponseEntity<String>(e.getCode().getMessage(), e.getCode().getCode());
	}
}
