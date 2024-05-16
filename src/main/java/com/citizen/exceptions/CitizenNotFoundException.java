package com.citizen.exceptions;

public class CitizenNotFoundException extends RuntimeException {
    public CitizenNotFoundException(String message) {
	super(message);
    }
}