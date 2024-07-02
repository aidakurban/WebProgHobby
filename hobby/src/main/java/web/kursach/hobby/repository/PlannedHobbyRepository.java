package web.kursach.hobby.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import web.kursach.hobby.entity.PlannedHobby;

public interface PlannedHobbyRepository extends JpaRepository <PlannedHobby, Long>{
    Optional<PlannedHobby> findById(Long id);

    List<PlannedHobby> findByUserId(Long userId);
    List<PlannedHobby> findByHobbyId(Long hobbyId);
}
