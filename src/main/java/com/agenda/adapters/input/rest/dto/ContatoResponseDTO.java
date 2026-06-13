package com.agenda.adapters.input.rest.dto;

public record ContatoResponseDTO(
        Long id,
        String nome,
        String telefone,
        String email
) {}
