package br.com.sabiox.sabiox_tool.repositories.sabiox;

import br.com.sabiox.sabiox_tool.domain.sabiox.lifecycle.LifeCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifeCycleRepository extends JpaRepository<LifeCycle, Long> {}
