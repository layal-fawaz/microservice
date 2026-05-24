package com.AI_project.idea_service.repositories;

import com.AI_project.idea_service.models.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
    List<Idea> findByUserId(Long userId);
}
