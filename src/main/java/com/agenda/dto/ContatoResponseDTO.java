package com.agenda.dto;

import com.agenda.entity.Contato;

public record ContatoResponseDTO(
        Long id,
        String nome,
        String telefone,
        String email

) {

    public ContatoResponseDTO(Contato contato) {
        this(contato.getId(), contato.getNome(), contato.getTelefone(), contato.getEmail());
    }
}
