package web.kursach.hobby.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import web.kursach.hobby.entity.Material;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    Optional<Material> findById(Long id);
    List<Material> findByHobbyId(Long hobbyId);
}
