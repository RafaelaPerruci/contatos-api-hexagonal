package com.agenda.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Contato {

    private final Long id;
    private final String nome;
    private final String telefone;
    private final String email;
    private final String endereco;
    private final Integer idade;
    private final ContatoTipo tipo;
    private final String dataCadastro;
    private final String ativo;
}
