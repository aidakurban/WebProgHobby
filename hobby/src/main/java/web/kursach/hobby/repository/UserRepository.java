package web.kursach.hobby.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.kursach.hobby.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Здесь вы можете добавить дополнительные методы для работы с пользователями, если это необходимо
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
}
