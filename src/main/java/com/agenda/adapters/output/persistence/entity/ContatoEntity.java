package com.agenda.adapters.output.persistence.entity;

import com.agenda.application.domain.ContatoTipo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contatos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContatoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    private String email;

    private String endereco;

    private Integer idade;

    @Enumerated(EnumType.STRING)
    private ContatoTipo tipo;

    private String dataCadastro;

    private String ativo;
}
