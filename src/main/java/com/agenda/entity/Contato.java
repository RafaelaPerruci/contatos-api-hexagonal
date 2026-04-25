package com.agenda.entity;

import com.agenda.dto.ContatoRequestDTO;
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
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;


    private String email;

    private String endereco;

    private int idade;

    private String tipo;

    private String dataCadastro;

    private String ativo;

    public Contato(ContatoRequestDTO contato) {
        this.nome = contato.nome();
        this.telefone = contato.telefone();
        this.email = contato.email();
        this.endereco = contato.endereco();
        this.idade = contato.idade();
        this.tipo = contato.tipo();
        this.dataCadastro = contato.dataCadastro().toString();
        this.ativo = contato.ativo();
    }
}
