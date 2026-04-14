package com.dylansyardsale.exception;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;//added after code generation to remove warning
    
	
	public ResourceNotFoundException(String message) {
        super(message);
    }
}
