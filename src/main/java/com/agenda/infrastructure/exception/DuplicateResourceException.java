package com.agenda.infrastructure.exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String resource, String field, String value) {
        super(resource + " já cadastrado(a) com " + field + ": " + value);
    }
}
