package br.edu.ifpb.dac.getservices.service;

import exception.DacException;

public class PersistenciaException extends DacException{
	
	private static final long serialVersionUID = 7159282553688713660L;

	public PersistenciaException(String message) {
		super(message);
	}

	public PersistenciaException(String message, Throwable cause) {
		super(message, cause);
	}

}
