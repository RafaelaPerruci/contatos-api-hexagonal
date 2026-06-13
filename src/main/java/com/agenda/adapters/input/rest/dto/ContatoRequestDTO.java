package com.agenda.adapters.input.rest.dto;

import com.agenda.application.domain.ContatoTipo;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ContatoRequestDTO(

        @NotBlank(message = "Nome obrigatorio")
        @Size(min = 3, message = "Nome deve ter pelo menos 3 caracteres")
        String nome,

        @NotBlank(message = "Telefone obrigatorio")
        String telefone,

        @NotBlank(message = "Email obrigatorio")
        @Email(message = "Email invalido")
        String email,

        @NotBlank(message = "Endereco obrigatorio")
        String endereco,

        @NotNull
        @Min(1)
        @Max(150)
        Integer idade,

        @NotNull(message = "Tipo obrigatorio")
        ContatoTipo tipo,

        @NotNull
        LocalDate dataCadastro,

        @NotBlank(message = "Ativo obrigatorio")
        String ativo

) {}
