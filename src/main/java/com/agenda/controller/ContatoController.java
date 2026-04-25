package com.agenda.controller;

import com.agenda.domain.ContatoDomain;
import com.agenda.dto.ContatoRequestDTO;
import com.agenda.dto.ContatoResponseDTO;
import com.agenda.mapper.ContatoMapper;
import com.agenda.service.ContatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/contatos")
@RequiredArgsConstructor
public class ContatoController {

    private final ContatoService service;
    private final ContatoMapper mapper;
    public static List<String> logs = new ArrayList<>();

    public static int cont = 0;

    @PostMapping("/incluir")
    public ResponseEntity<ContatoResponseDTO> incluir(@RequestBody @Valid ContatoRequestDTO contato) {
        ContatoResponseDTO responseDTO = service.create(mapper.toDomain(contato));
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ContatoResponseDTO>> listar() {
        return ResponseEntity.ok(service.findAll());

    }

    // @GetMapping("/pesquisar")
    // public ResponseEntity<String> pesquisar(@RequestParam String tipoBusca,
    // @RequestParam String valor) {
    //
    // }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ContatoResponseDTO> editar(@PathVariable Long id, @RequestBody @Valid ContatoRequestDTO contato) {
        ContatoDomain domain = mapper.toDomain(contato);
        ContatoResponseDTO responseDTO = service.update(id, domain);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/logs")
    public ResponseEntity<String> verLogs() {
        String s = "=== LOGS ===\n";
        s = s + "Total operacoes: " + cont + "\n\n";
        for (int i = 0; i < logs.size(); i++) {
            s = s + logs.get(i) + "\n";
        }
        return ResponseEntity.ok(s);
    }
}
