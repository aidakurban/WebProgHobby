package web.kursach.hobby.repository;

import web.kursach.hobby.entity.Hobby;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Long> {
    // методы для работы с хобби
    Optional<Hobby> findById(Long id);
}