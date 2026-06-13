package com.agenda.application.port.in;

import com.agenda.application.domain.Contato;

import java.util.List;

public interface ContatoUseCase {

    Contato create(Contato contato);

    List<Contato> findAll();

    void delete(Long id);

    Contato update(Long id, Contato contato);

    List<Contato> searchContact(String tipoBusca, String valor);

    long findCount();
}
