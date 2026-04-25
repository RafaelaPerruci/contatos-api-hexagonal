package com.agenda.service;

import com.agenda.dto.ContatoRequestDTO;
import com.agenda.dto.ContatoResponseDTO;
import com.agenda.entity.Contato;
import com.agenda.exception.DuplicateResourceException;
import com.agenda.exception.ResourceNotFoundException;
import com.agenda.repository.ContatoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContatoService {

    private final ContatoRepository contatoRepository;

    public ContatoResponseDTO create(ContatoRequestDTO contato) {
        Contato c = new Contato(contato);
        boolean emailJaExiste = contatoRepository.existsByEmail(c.getEmail());
        if (emailJaExiste) {
            throw new DuplicateResourceException("Contato", "email", c.getEmail());
        }
        Contato salvo = contatoRepository.save(c);
        return new ContatoResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getTelefone(),
                salvo.getEmail()
        );

    }


    public List<ContatoResponseDTO> findAll() {
        List<Contato> contatos = contatoRepository.findAll();
        List<ContatoResponseDTO> responseDtos = contatos.stream().map(contato -> new ContatoResponseDTO(contato) ).toList();
        return responseDtos;
    }

    public void delete(Long id) {
        contatoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contato", id.toString()));
        contatoRepository.deleteById(id);
    }

    public ContatoResponseDTO update(Long id, ContatoRequestDTO contato) {
        Contato contatoAtualizado = contatoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contato", id.toString()));
        contatoAtualizado.setNome(contato.nome());
        contatoAtualizado.setTelefone(contato.telefone());
        contatoAtualizado.setEmail(contato.email());
        contatoAtualizado.setEndereco(contato.endereco());
        contatoAtualizado.setIdade(contato.idade());
        contatoAtualizado.setTipo(contato.tipo());
        contatoAtualizado.setDataCadastro(contato.dataCadastro().toString());
        contatoAtualizado.setAtivo(contato.ativo());
        Contato atualizado = contatoRepository.save(contatoAtualizado);
        return new ContatoResponseDTO(atualizado);
    }
}
