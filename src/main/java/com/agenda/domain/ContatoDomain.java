package com.agenda.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContatoDomain {

    private final String nome;

    private final String telefone;

    private final String email;

    private final String endereco;

    private final Integer idade;

    private final String tipo;

    private final String dataCadastro;

    private final String ativo;

}
