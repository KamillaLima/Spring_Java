package com.mballem.demoparkapi.exception;

public class UsernameUniqueViolationExpcetion extends RuntimeException {

	public UsernameUniqueViolationExpcetion( String message) {
		super(message);
	}

}
