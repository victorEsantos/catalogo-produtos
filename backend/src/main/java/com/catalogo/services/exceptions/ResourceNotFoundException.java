package com.catalogo.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -6345718764180045045L;
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}

}
