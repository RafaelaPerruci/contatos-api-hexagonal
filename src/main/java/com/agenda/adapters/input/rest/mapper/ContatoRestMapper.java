package com.agenda.adapters.input.rest.mapper;

import com.agenda.adapters.input.rest.dto.ContatoRequestDTO;
import com.agenda.adapters.input.rest.dto.ContatoResponseDTO;
import com.agenda.application.domain.Contato;
import com.agenda.application.domain.ContatoDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContatoRestMapper {

    private final ContatoDomain contatoDomain;

    public Contato toDomain(ContatoRequestDTO dto) {
        return contatoDomain.construir(
                dto.nome(),
                dto.telefone(),
                dto.email(),
                dto.endereco(),
                dto.idade(),
                dto.tipo(),
                dto.dataCadastro().toString(),
                dto.ativo()
        );
    }

    public ContatoResponseDTO toResponseDTO(Contato contato) {
        return new ContatoResponseDTO(
                contato.getId(),
                contato.getNome(),
                contato.getTelefone(),
                contato.getEmail()
        );
    }
}
