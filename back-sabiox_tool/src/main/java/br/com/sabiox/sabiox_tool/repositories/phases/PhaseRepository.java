package br.com.sabiox.sabiox_tool.repositories.phases;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sabiox.sabiox_tool.domain.sabiox.phase.Phase;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Long> {}