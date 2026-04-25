package com.agenda.repository;

import com.agenda.entity.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

    boolean existsByEmail(String email);
}
