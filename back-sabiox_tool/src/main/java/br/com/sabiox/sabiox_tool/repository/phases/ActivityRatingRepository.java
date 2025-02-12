package br.com.sabiox.sabiox_tool.repository.phases;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sabiox.sabiox_tool.model.phases.ActivityRating;

@Repository
public interface ActivityRatingRepository extends JpaRepository<ActivityRating, Long> {}