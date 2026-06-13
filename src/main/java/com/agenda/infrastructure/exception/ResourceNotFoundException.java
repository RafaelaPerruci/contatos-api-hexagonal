package com.agenda.infrastructure.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, String id) {
        super(resource + " com id " + id + " nao encontrado.");
    }
}
