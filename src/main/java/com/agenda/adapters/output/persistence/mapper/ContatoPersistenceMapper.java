package com.agenda.adapters.output.persistence.mapper;

import com.agenda.adapters.output.persistence.entity.ContatoEntity;
import com.agenda.application.domain.Contato;
import org.springframework.stereotype.Component;

@Component
public class ContatoPersistenceMapper {

    public ContatoEntity toEntity(Contato contato) {
        return new ContatoEntity(
                contato.getId(),
                contato.getNome(),
                contato.getTelefone(),
                contato.getEmail(),
                contato.getEndereco(),
                contato.getIdade(),
                contato.getTipo(),
                contato.getDataCadastro(),
                contato.getAtivo()
        );
    }

    public Contato toDomain(ContatoEntity entity) {
        return new Contato(
                entity.getId(),
                entity.getNome(),
                entity.getTelefone(),
                entity.getEmail(),
                entity.getEndereco(),
                entity.getIdade(),
                entity.getTipo(),
                entity.getDataCadastro(),
                entity.getAtivo()
        );
    }
}
