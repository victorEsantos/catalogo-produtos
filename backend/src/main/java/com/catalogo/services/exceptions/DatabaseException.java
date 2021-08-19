package com.catalogo.services.exceptions;

public class DatabaseException extends RuntimeException{
	private static final long serialVersionUID = 4061822480187898165L;
	
	public DatabaseException(String msg) {
		super(msg);
	}

}
