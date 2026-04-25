package com.agenda.service;

import com.agenda.domain.ContatoDomain;
import com.agenda.dto.ContatoResponseDTO;
import com.agenda.entity.Contato;
import com.agenda.exception.DuplicateResourceException;
import com.agenda.exception.ResourceNotFoundException;
import com.agenda.mapper.ContatoMapper;
import com.agenda.repository.ContatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ContatoService {

    private static final Set<String> CAMPOS_BUSCA_PERMITIDOS = new HashSet<>(
            List.of("nome", "telefone", "email", "endereco", "idade", "tipo", "dataCadastro", "ativo")
    );

    private final ContatoRepository contatoRepository;
    private final  ContatoMapper mapper;

    public ContatoResponseDTO create(ContatoDomain contato) {
        boolean emailJaExiste = contatoRepository.existsByEmail(contato.getEmail());
        if (emailJaExiste) {
            throw new DuplicateResourceException("Contato", "email", contato.getEmail());
        }
        Contato salvo = contatoRepository.save(mapper.toEntity(contato));
        return new ContatoResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getTelefone(),
                salvo.getEmail());

    }

    public List<ContatoResponseDTO> findAll() {
        List<Contato> contatos = contatoRepository.findAll();
        List<ContatoResponseDTO> responseDtos = contatos.stream().map(contato -> new ContatoResponseDTO(contato))
                .toList();
        return responseDtos;
    }

    public void delete(Long id) {
        contatoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contato", id.toString()));
        contatoRepository.deleteById(id);
    }

    public ContatoResponseDTO update(Long id, ContatoDomain contato) {
        Contato entity = contatoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato", id.toString()));
        entity.setNome(contato.getNome());
        entity.setTelefone(contato.getTelefone());
        entity.setEmail(contato.getEmail());
        entity.setEndereco(contato.getEndereco());
        entity.setIdade(contato.getIdade());
        entity.setTipo(contato.getTipo());
        entity.setDataCadastro(contato.getDataCadastro());
        entity.setAtivo(contato.getAtivo());
        Contato atualizado = contatoRepository.save(entity);
        return new ContatoResponseDTO(atualizado);
    }

    public List<ContatoResponseDTO> searchContact(String tipoBusca, String valor) {
        if (tipoBusca == null || tipoBusca.isBlank()) {
            throw new IllegalArgumentException("tipoBusca obrigatorio");
        }
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("valor obrigatorio");
        }

        String campo = tipoBusca.trim();
        if (!CAMPOS_BUSCA_PERMITIDOS.contains(campo)) {
            throw new IllegalArgumentException("Campo de busca invalido: " + campo);
        }

        Contato probe = new Contato();
        ExampleMatcher matcher;

        if ("idade".equals(campo)) {
            try {
                probe.setIdade(Integer.parseInt(valor.trim()));
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Para buscar por idade, o valor deve ser numerico");
            }
            matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("idade", ExampleMatcher.GenericPropertyMatchers.exact());
        } else {
            switch (campo) {
                case "nome" -> probe.setNome(valor.trim());
                case "telefone" -> probe.setTelefone(valor.trim());
                case "email" -> probe.setEmail(valor.trim());
                case "endereco" -> probe.setEndereco(valor.trim());
                case "tipo" -> probe.setTipo(valor.trim());
                case "dataCadastro" -> probe.setDataCadastro(valor.trim());
                case "ativo" -> probe.setAtivo(valor.trim());
                default -> throw new IllegalArgumentException("Campo de busca invalido: " + campo);
            }

            matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        }

        Example<Contato> example = Example.of(probe, matcher);
        return contatoRepository.findAll(example).stream().map(ContatoResponseDTO::new).toList();
    }

    public long findCount() {
        return contatoRepository.count();
    }
}
