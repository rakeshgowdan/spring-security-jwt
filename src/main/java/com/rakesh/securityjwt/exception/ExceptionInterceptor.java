package com.rakesh.securityjwt.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rakesh.securityjwt.dto.CommonResponse;
import com.rakesh.securityjwt.dto.exception.UserRoleExceptionDTO;

@RestControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public final CommonResponse handleUserException(UserException ex) {

		return new CommonResponse(500, true, ex.getMessage(), new Date(), ex.getData(), ex.getCause());
	}

	@ExceptionHandler(UserRoleException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public final CommonResponse handleUserRoleExceptions(UserException ex) {
		UserRoleExceptionDTO exceptionResponse = new UserRoleExceptionDTO(500, true, ex.getMessage(), new Date(),
				ex.getData(), null);
		return new CommonResponse(500, true, ex.getMessage(), new Date(), exceptionResponse, ex.getCause());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public final CommonResponse handleIllegalArgumentException(IllegalArgumentException ex) {

		return new CommonResponse(500, true, ex.getMessage(), new Date(), ex.getMessage(), ex.getCause());
	}

	
	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public final CommonResponse handleusernameNotFoundException(UsernameNotFoundException ex) {
		return new CommonResponse(404, true,ex.getMessage(), new Date(), ex.getMessage(), ex.getCause());
	}
	
}
