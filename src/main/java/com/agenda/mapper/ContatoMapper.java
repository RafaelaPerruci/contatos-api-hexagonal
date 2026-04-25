package com.agenda.mapper;

import com.agenda.domain.ContatoDomain;
import com.agenda.dto.ContatoRequestDTO;
import com.agenda.entity.Contato;
import org.springframework.stereotype.Component;

@Component
public final class ContatoMapper {

    private ContatoMapper() {
    }

    public ContatoDomain toDomain(ContatoRequestDTO dto) {
        return new ContatoDomain(
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

    public Contato toEntity(ContatoDomain domain) {
        Contato entity = new Contato();
        entity.setNome(domain.getNome());
        entity.setTelefone(domain.getTelefone());
        entity.setEmail(domain.getEmail());
        entity.setEndereco(domain.getEndereco());
        entity.setIdade(domain.getIdade());
        entity.setTipo(domain.getTipo());
        entity.setDataCadastro(domain.getDataCadastro());
        entity.setAtivo(domain.getAtivo());
        return entity;
    }

}
