package com.agenda.adapters.input.rest.controller;

import com.agenda.adapters.input.rest.dto.ContatoRequestDTO;
import com.agenda.adapters.input.rest.dto.ContatoResponseDTO;
import com.agenda.adapters.input.rest.mapper.ContatoRestMapper;
import com.agenda.application.port.in.ContatoUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contatos")
@RequiredArgsConstructor
public class ContatoController {

    private final ContatoUseCase contatoUseCase;
    private final ContatoRestMapper mapper;

    @PostMapping("/incluir")
    public ResponseEntity<ContatoResponseDTO> incluir(@RequestBody @Valid ContatoRequestDTO contato) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponseDTO(contatoUseCase.create(mapper.toDomain(contato))));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ContatoResponseDTO>> listar() {
        return ResponseEntity.ok(contatoUseCase.findAll().stream().map(mapper::toResponseDTO).toList());
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<ContatoResponseDTO>> pesquisar(
            @RequestParam String tipoBusca,
            @RequestParam String valor) {
        return ResponseEntity.ok(contatoUseCase.searchContact(tipoBusca, valor).stream()
                .map(mapper::toResponseDTO).toList());
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ContatoResponseDTO> editar(
            @PathVariable Long id,
            @RequestBody @Valid ContatoRequestDTO contato) {
        return ResponseEntity.ok(mapper.toResponseDTO(contatoUseCase.update(id, mapper.toDomain(contato))));
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        contatoUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/logs")
    public ResponseEntity<String> verLogs() {
        return ResponseEntity.ok("Total operacoes: " + contatoUseCase.findCount());
    }
}
