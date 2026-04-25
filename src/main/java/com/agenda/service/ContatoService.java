package com.agenda.service;

import com.agenda.domain.ContatoDomain;
import com.agenda.dto.ContatoResponseDTO;
import com.agenda.entity.Contato;
import com.agenda.exception.DuplicateResourceException;
import com.agenda.exception.ResourceNotFoundException;
import com.agenda.mapper.ContatoMapper;
import com.agenda.repository.ContatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContatoService {

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
}
