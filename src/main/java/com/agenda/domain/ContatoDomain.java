package com.agenda.domain;

import com.agenda.enums.ContatoTipo;
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

    private final ContatoTipo tipo;

    private final String dataCadastro;

    private final String ativo;

}
