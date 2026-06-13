package com.agenda.adapters.output.persistence;

import com.agenda.adapters.output.persistence.entity.ContatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContatoJpaRepository extends JpaRepository<ContatoEntity, Long>, JpaSpecificationExecutor<ContatoEntity> {

    boolean existsByEmail(String email);
}
