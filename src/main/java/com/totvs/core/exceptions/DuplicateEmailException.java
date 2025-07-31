package com.totvs.core.exceptions;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("Email '" + email + "' already exists!");
    }
}

