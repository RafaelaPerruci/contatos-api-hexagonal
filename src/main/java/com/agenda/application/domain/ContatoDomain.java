package com.agenda.application.domain;

import com.agenda.application.port.in.ContatoUseCase;
import com.agenda.application.port.out.ContatoRepositoryPort;
import com.agenda.infrastructure.exception.DuplicateResourceException;
import com.agenda.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ContatoDomain implements ContatoUseCase {

    private static final Set<String> CAMPOS_BUSCA_PERMITIDOS = Set.of(
            "nome", "telefone", "email", "endereco", "idade", "tipo", "dataCadastro", "ativo");

    private final ContatoRepositoryPort repositoryPort;

    public Contato construir(String nome, String telefone, String email,
            String endereco, Integer idade, ContatoTipo tipo,
            String dataCadastro, String ativo) {
        return new Contato(null, nome, telefone, email, endereco, idade, tipo, dataCadastro, ativo);
    }

    @Override
    public Contato create(Contato contato) {
        if (repositoryPort.existsByEmail(contato.getEmail())) {
            throw new DuplicateResourceException("Contato", "email", contato.getEmail());
        }
        return repositoryPort.save(contato);
    }

    @Override
    public List<Contato> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    public void delete(Long id) {
        repositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato", id.toString()));
        repositoryPort.deleteById(id);
    }

    @Override
    public Contato update(Long id, Contato contato) {
        Contato existente = repositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato", id.toString()));
        return repositoryPort.save(new Contato(
                existente.getId(),
                contato.getNome(),
                contato.getTelefone(),
                contato.getEmail(),
                contato.getEndereco(),
                contato.getIdade(),
                contato.getTipo(),
                contato.getDataCadastro(),
                contato.getAtivo()
        ));
    }

    @Override
    public List<Contato> searchContact(String tipoBusca, String valor) {
        if (tipoBusca == null || tipoBusca.isBlank()) {
            throw new IllegalArgumentException("tipoBusca obrigatorio");
        }
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("valor obrigatorio");
        }
        if (!CAMPOS_BUSCA_PERMITIDOS.contains(tipoBusca.trim())) {
            throw new IllegalArgumentException("Campo de busca invalido: " + tipoBusca.trim());
        }
        return repositoryPort.search(tipoBusca.trim(), valor);
    }

    @Override
    public long findCount() {
        return repositoryPort.count();
    }
}
