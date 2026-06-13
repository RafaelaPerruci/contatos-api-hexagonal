package com.agenda.application.port.out;

import com.agenda.application.domain.Contato;

import java.util.List;
import java.util.Optional;

public interface ContatoRepositoryPort {

    Contato save(Contato contato);

    Optional<Contato> findById(Long id);

    List<Contato> findAll();

    void deleteById(Long id);

    boolean existsByEmail(String email);

    List<Contato> search(String field, String value);

    long count();
}
