package com.agenda.adapters.output.persistence;

import com.agenda.adapters.output.persistence.entity.ContatoEntity;
import com.agenda.adapters.output.persistence.mapper.ContatoPersistenceMapper;
import com.agenda.application.domain.Contato;
import com.agenda.application.domain.ContatoTipo;
import com.agenda.application.port.out.ContatoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContatoJpaRepositoryAdapter implements ContatoRepositoryPort {

    private final ContatoJpaRepository jpaRepository;
    private final ContatoPersistenceMapper mapper;

    @Override
    public Contato save(Contato contato) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(contato)));
    }

    @Override
    public Optional<Contato> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Contato> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public List<Contato> search(String field, String value) {
        ContatoEntity probe = new ContatoEntity();
        ExampleMatcher matcher;

        if ("idade".equals(field)) {
            try {
                probe.setIdade(Integer.parseInt(value.trim()));
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Para buscar por idade, o valor deve ser numerico");
            }
            matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("idade", ExampleMatcher.GenericPropertyMatchers.exact());
        } else if ("tipo".equals(field)) {
            try {
                probe.setTipo(ContatoTipo.valueOf(value.trim().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Tipo de contato invalido: " + value);
            }
            matcher = ExampleMatcher.matching().withIgnoreNullValues();
        } else {
            switch (field) {
                case "nome" -> probe.setNome(value.trim());
                case "telefone" -> probe.setTelefone(value.trim());
                case "email" -> probe.setEmail(value.trim());
                case "endereco" -> probe.setEndereco(value.trim());
                case "dataCadastro" -> probe.setDataCadastro(value.trim());
                case "ativo" -> probe.setAtivo(value.trim());
                default -> throw new IllegalArgumentException("Campo de busca invalido: " + field);
            }
            matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        }

        return jpaRepository.findAll(Example.of(probe, matcher)).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }
}
